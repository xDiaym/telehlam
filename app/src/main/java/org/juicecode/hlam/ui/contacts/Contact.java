package org.juicecode.hlam.ui.contacts;

class Contact {
private String name;
private String LastTimeOnline;
private String phone;
    public Contact(String name, String lastTimeOnline, String phone) {
        this.name = name;
        LastTimeOnline = lastTimeOnline;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastTimeOnline() {
        return LastTimeOnline;
    }

    public void setLastTimeOnline(String lastTimeOnline) {
        LastTimeOnline = lastTimeOnline;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
