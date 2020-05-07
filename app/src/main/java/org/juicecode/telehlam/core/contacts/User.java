package org.juicecode.telehlam.core.contacts;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contacts")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long DataBaseId;
    @ColumnInfo
    private long id;
    @ColumnInfo
    private String login;
    @ColumnInfo
    private String name;
    @ColumnInfo
    @Nullable
    private String surname;

    public User(){}
    public User(String login,long userId,String name, String surname) {
        this.login = login;
        this.id = userId;
        this.name = name;
        this.surname = surname;
    }

    public long getDataBaseId() {
        return DataBaseId;
    }

    public void setDataBaseId(long dataBaseId) {
        DataBaseId = dataBaseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getSurname() {
        return surname;
    }

    public void setSurname(@Nullable String surname) {
        this.surname = surname;
    }
}
