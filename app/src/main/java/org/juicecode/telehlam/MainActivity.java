package org.juicecode.telehlam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.juicecode.telehlam.database.DataBaseTask;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageViewModel;
import org.juicecode.telehlam.rest.user.AuthInfo;
import org.juicecode.telehlam.socketio.AppSocket;
import org.juicecode.telehlam.socketio.LoginEvent;
import org.juicecode.telehlam.socketio.MessageEvent;
import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.KeyboardManager;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;


public class MainActivity extends AppCompatActivity implements FragmentManagerSimplifier  {
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    private NavigationView navigationView;
    private AppSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferencesRepository repository = new SharedPreferencesRepository(this);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Checking permission if user tapped
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(R.id.contactsFragment);
            }
        });
        //all drawer stuff
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

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_home) {
                    fab.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                } else if (destination.getId() == R.id.nav_logout
                        || destination.getId() == R.id.contactsFragment
                        || destination.getId() == R.id.requestFingerPrintFragment) {
                    toolbar.setVisibility(View.VISIBLE);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    navigationView.setVisibility(View.GONE);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
            }
        });

        if (repository.getToken() == null) {
            addFragment(R.id.authorisationFragment);
        }  else if(repository.getFingerPrint()){
            addFragment(R.id.confirmScannerPrint);
        }


        socket = AppSocket.getInstance(Constant.baseUrl);
        socket.connect();

        // Login
        AuthInfo info = new AuthInfo(0,
                new SharedPreferencesRepository(this).getToken());
        new LoginEvent(socket).login(info);
        // TODO: login listener

        final MessageViewModel viewModel = ViewModelProviders
                .of(this)
                .get(MessageViewModel.class);
        socket.addListener("message", new MessageEvent.MessageListener(this) {
            @Override
            public void onNewMessage(Message message) {
                viewModel.insert(message);
            }
        });
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
    public void onBackPressed() {
        KeyboardManager.hideKeyboard(this);
        // FIXME: при нажатии на home fragment назад, приложение не закрывается
        switch (navController.getCurrentDestination().getId()) {
            case R.id.nav_home:
            case R.id.nav_logout:
            case R.id.confirmScannerPrint:
            case R.id.authorisationFragment:
                // Do nothing
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

    @Override
    public void addWithArguments(int id, Bundle bundle) {
        navController.navigate(id, bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            socket.disconnect();
        }
    }
}

