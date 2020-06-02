package org.juicecode.telehlam.ui.settings;

class SettingsItem {
    public static final int CAMERA_IN_CHAT_SETTING = 1;
    public static final int FINGERPRINT = 2;
    private int type;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public SettingsItem(int type) {
        this.type = type;
    }
}
