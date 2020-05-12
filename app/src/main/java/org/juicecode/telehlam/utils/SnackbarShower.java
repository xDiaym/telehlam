package org.juicecode.telehlam.utils;

import android.widget.LinearLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;



public class SnackbarShower {
    private LinearLayout layout;
    public SnackbarShower(LinearLayout layout){
        this.layout = layout;
    }
    public void showSnackbar(String text){
        Snackbar snackbar = Snackbar.make(layout, text, Snackbar.LENGTH_SHORT).setDuration(3000);
        snackbar.show();
    }
}
