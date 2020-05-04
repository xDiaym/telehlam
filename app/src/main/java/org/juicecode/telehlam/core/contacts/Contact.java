package org.juicecode.telehlam.core.contacts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int contactId;
    @ColumnInfo
    private String login;

    public Contact(String login) {
        this.login = login;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int id) {
        this.contactId = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
