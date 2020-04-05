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
    private String name;
    // TODO(all): create class for telephone numbers
    @ColumnInfo
    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int id) {
        this.contactId = id;
    }

    @NonNull
    @Override
    public String toString() {
        return  " " + phone;
    }
}
