package org.juicecode.telehlam.database.users;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepositoryDatabase repository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepositoryDatabase(application);
        allUsers = repository.getAllUsers();
    }

    public LiveData<List<Long>> getUsersIds() {
        return repository.getUsersIds();
    }

    public LiveData<List<User>> getAll() {
        return allUsers;
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
