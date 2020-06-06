package org.juicecode.telehlam;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.juicecode.telehlam.database.UserIds;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageRepository;
import org.juicecode.telehlam.database.users.User;
import org.juicecode.telehlam.database.users.UserRepositoryDatabase;
import org.juicecode.telehlam.notifications.NotificationService;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.user.AuthInfo;
import org.juicecode.telehlam.rest.user.UserRepository;
import org.juicecode.telehlam.socketio.AppSocket;
import org.juicecode.telehlam.socketio.LoginEvent;
import org.juicecode.telehlam.socketio.MessageEvent;
import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.KeyboardManager;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;


public class MainActivity extends AppCompatActivity implements FragmentManagerSimplifier {
    public static final int CAMERA = 1;
    Switch checker;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    private NavigationView navigationView;
    private AppSocket socket;
    private SharedPreferencesRepository repository;
    private boolean isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isActive = true;
        repository = new SharedPreferencesRepository(this);

        // Block screenshots
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Checking permission if user tapped
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(R.id.contactsFragment);
            }
        });
        // All drawer stuff
        drawer = findViewById(R.id.drawer_layout);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_logout,
                R.id.settingsFragment)
                .setDrawerLayout(drawer)
                .build();

        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.nav_home:
                        fab.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        break;
                    case R.id.nav_logout:
                    case R.id.requestFingerPrintFragment:
                    case R.id.settingsFragment:
                        toolbar.setVisibility(View.VISIBLE);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        fab.setVisibility(View.GONE);
                        break;
                    case R.id.contactsFragment:
                        toolbar.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.GONE);
                        break;
                    default:
                        fab.setVisibility(View.GONE);
                        toolbar.setVisibility(View.GONE);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        break;
                }

            }
        });

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        socket = AppSocket.getInstance(Constant.baseUrl);
        socket.connect();

        final UserRepositoryDatabase userRepositoryDatabase = new UserRepositoryDatabase(getApplication());
        final MessageRepository messageRepository = new MessageRepository(getApplication());
        final UserIds userId = UserIds.getInstance(this, this);

        socket.addListener("message", new MessageEvent.MessageListener(this) {
            @Override
            public void onNewMessage(final Message message) {
                long authorId = message.getAuthorId();

                if (!userId.contains(authorId)) {
                    new UserRepository(new RetrofitBuilder()).byId(authorId).observe(MainActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            userRepositoryDatabase.insert(user);
                            //fullName = user.getSurname()+" "+user.getName();
                            // We insert message here, cuz get user byId execute in other thread
                            messageRepository.insert(message);


                        }
                    });
                } else {
                    messageRepository.insert(message);


                }
                if (!isActive) {

                     startService(message);
                }

            }
        });

        if (repository.getToken() == null) {
            addFragment(R.id.authorisationFragment);
        } else if (repository.getFingerPrint()) {
            addFragment(R.id.confirmScannerPrint);
        }

        login();
    }

    public void startService(Message message) {
        Intent createService = new Intent(this, NotificationService.class);
        createService.putExtra("message", message);
        startService(createService);
    }

    public void destroyService() {
        Intent destroyService = new Intent(this, NotificationService.class);
        stopService(destroyService);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hide keyboard while fragment changed
        KeyboardManager.hideKeyboard(this);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void addFragment(int id) {
        KeyboardManager.hideKeyboard(this);
        navController.navigate(id);
    }

    @Override
    public void addWithArguments(int id, Bundle bundle) {
        KeyboardManager.hideKeyboard(this);
        navController.navigate(id, bundle);
    }

    public void checkPermission(String permission, int requestCode, Switch checker) {
        this.checker = checker;
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        } else {

        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                repository.deleteCamera();
                repository.saveCamera(false);
                checker.setChecked(false);

            }
        }
    }


    @Override
    public void onBackPressed() {
        KeyboardManager.hideKeyboard(this);
        switch (navController.getCurrentDestination().getId()) {
            case R.id.nav_home:
            case R.id.confirmScannerPrint:
            case R.id.authorisationFragment:
                // Exit from app
                finish();
                break;
            case R.id.chatFragment:
                // If user open chat from user search and pressed back
                // we`ve show chat list, not user search
                addFragment(R.id.nav_home);
                break;
            default:
                super.onBackPressed();
                break;
        }
    }

    public void login() {
        if (socket != null) {
            socket.connect();

        }
        AuthInfo info = new AuthInfo(-1,
                new SharedPreferencesRepository(this).getToken());
        new LoginEvent(socket).login(info);
    }

    public void logOut() {
        if (socket != null) {
            socket.disconnect();
        }
    }

    @Override
    protected void onResume() {
        isActive = true;
        super.onResume();
        destroyService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (repository.getFingerPrint()) {
            addFragment(R.id.confirmScannerPrint);
        }

        isActive = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyService();
        logOut();

    }
}

