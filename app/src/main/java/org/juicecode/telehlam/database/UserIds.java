package org.juicecode.telehlam.database;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.juicecode.telehlam.database.users.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserIds {
    private static UserIds instance;
    private final ArrayList<Long> usersId = new ArrayList<>();
    private UserViewModel userViewModel;

    private UserIds(FragmentActivity fragmentActivity, LifecycleOwner owner) {
        userViewModel = ViewModelProviders.of(fragmentActivity).get(UserViewModel.class);
        init(owner);
    }

    private UserIds(Fragment fragment, LifecycleOwner owner) {
        userViewModel = ViewModelProviders.of(fragment).get(UserViewModel.class);
        init(owner);
    }

    public static UserIds getInstance(FragmentActivity fragmentActivity, LifecycleOwner owner) {
        if (instance == null) {
            instance = new UserIds(fragmentActivity, owner);
        }
        return instance;
    }

    public static UserIds getInstance(Fragment fragment, LifecycleOwner owner) {
        if (instance == null) {
            instance = new UserIds(fragment, owner);
        }
        return instance;
    }

    private void init(LifecycleOwner owner) {
        userViewModel.getUsersIds().observe(owner, new Observer<List<Long>>() {
            @Override
            public void onChanged(List<Long> ids) {
                usersId.clear();
                usersId.addAll(ids);
            }
        });
    }

    public boolean contains(long id) {
        return usersId.contains(id);
    }
}
