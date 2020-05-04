package org.juicecode.telehlam;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.juicecode.telehlam.socketio.AppSocket;
import org.juicecode.telehlam.ui.contacts.ContactsFragment;
import org.juicecode.telehlam.ui.registration.AuthorisationFragment;
import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.PermissionCode;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

import javax.security.auth.PrivateCredentialPermission;


public class MainActivity extends AppCompatActivity implements FragmentManagerSimplifier,
        DrawerLocker {
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    private SharedPreferences sharedPreferences;
    private AppSocket appSocket;
    private Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: add logout button
        SharedPreferences preferences = getSharedPreferences( "org.juicecode.telehlam", MODE_PRIVATE);
        preferences.edit().remove("token").apply();

        //check if user has registered
        SharedPreferencesRepository repository = new SharedPreferencesRepository(this);
        if (repository.getToken() == null) {
            replaceFragment(new AuthorisationFragment(), "authorisation");
        } else {
            appSocket = new AppSocket();
            Socket socket = appSocket.getSocket();
            socket.connect();
            socket.emit("login", repository.getToken());
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Checking permission if user tapped
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFragment("authorisation")) {

                } else {
                    checkPermission();
                }
            }
        });
        //all drawer stuff
        drawer = findViewById(R.id.drawer_layout);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    public void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.drawer_layout, fragment, tag)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void remove(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentByTag(tag) != null) {
            fragmentTransaction.remove(fragmentManager.findFragmentByTag(tag)).commit();
            fragmentManager.popBackStack();
        }
    }


    public void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager
                .beginTransaction()
                .replace(R.id.drawer_layout, fragment, tag)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }


    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, PermissionCode.PERMISSION_READ_CONTACTS);
        } else {
            addFragment(new ContactsFragment(), "contacts");
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionCode.PERMISSION_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addFragment(new ContactsFragment(), "contacts");
                }
            }
        }
    }

    @Override
    public void setDrawerLock(boolean lock) {
        drawer.setDrawerLockMode(lock
                ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED
                : DrawerLayout.LOCK_MODE_UNLOCKED);
    }
    @Override
    public boolean checkFragment(String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
            return true;
        } else {
            return false;
        }
    }

}
