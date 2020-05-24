package org.juicecode.telehlam;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.juicecode.telehlam.database.UserIds;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageViewModel;
import org.juicecode.telehlam.database.users.User;
import org.juicecode.telehlam.database.users.UserViewModel;
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
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    private NavigationView navigationView;
    private AppSocket socket;
    private SharedPreferencesRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                R.id.requestFingerPrintFragment)
                .setDrawerLayout(drawer)
                .build();

        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                // TODO: add switch case
                switch (destination.getId()){
                    case R.id.nav_home:
                        fab.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        break;
                    case R.id.nav_logout:
                    case R.id.requestFingerPrintFragment:
                        toolbar.setVisibility(View.VISIBLE);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        fab.setVisibility(View.GONE);
                        break;
                    case R.id.contactsFragment :
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

        final MessageViewModel messageViewModel = ViewModelProviders
                .of(this)
                .get(MessageViewModel.class);
        final UserViewModel userViewModel = ViewModelProviders
                .of(this)
                .get(UserViewModel.class);

        final UserIds userId = UserIds.getInstance(this, this);

        socket.addListener("message", new MessageEvent.MessageListener(this) {
            @Override
            public void onNewMessage(final Message message) {
                long authorId = message.getAuthorId();
                if (!userId.contains(authorId)) {
                    new UserRepository(new RetrofitBuilder()).byId(authorId).observe(MainActivity.this, new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            userViewModel.insert(user);
                            // We insert message here, cuz get user byId execute in other thread
                            messageViewModel.insert(message);
                        }
                    });
                } else {
                    messageViewModel.insert(message);
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

    @Override
    protected void onPause() {
        super.onPause();
        if (repository.getFingerPrint()) {
            addFragment(R.id.confirmScannerPrint);
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
        AuthInfo info = new AuthInfo(-1,
                new SharedPreferencesRepository(this).getToken());
        new LoginEvent(socket).login(info);
    }

    public void logOut() {
        if (socket != null) {
            socket.disconnect();
            socket.off("message");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logOut();
    }
}

