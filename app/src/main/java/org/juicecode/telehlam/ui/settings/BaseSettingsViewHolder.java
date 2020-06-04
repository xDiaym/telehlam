package org.juicecode.telehlam.ui.settings;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


abstract class BaseSettingsViewHolder extends RecyclerView.ViewHolder {
    protected String SettingName;

    public BaseSettingsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(SettingsItem item);
}
