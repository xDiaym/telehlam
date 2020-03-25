package org.juicecode.hlam.core.contacts;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    public Contact(int contact_id, String name, String phone) {
        this.contact_id = contact_id;
        this.name = name;
        this.phone = phone;
    }

    @PrimaryKey(autoGenerate = true)
    private int contact_id;
    private String name;
    // TODO(all): create class for telephone numbers
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
}
