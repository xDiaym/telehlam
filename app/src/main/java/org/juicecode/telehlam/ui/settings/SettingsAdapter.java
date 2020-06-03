package org.juicecode.telehlam.ui.settings;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

import java.util.ArrayList;
import java.util.List;

import static org.juicecode.telehlam.MainActivity.CAMERA;


class SettingsAdapter extends RecyclerView.Adapter<BaseSettingsViewHolder> {
    private List<SettingsItem> settingsList;

    public SettingsAdapter() {
        settingsList = new ArrayList<>();
        settingsList.add(new SettingsItem(SettingsItem.CAMERA_IN_CHAT_SETTING));
        settingsList.add(new SettingsItem(SettingsItem.FINGERPRINT));
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public BaseSettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case SettingsItem.CAMERA_IN_CHAT_SETTING:
                view = inflater.inflate(R.layout.camera_in_chat_setting, parent, false);
                return new CameraInChatHolder(view);
            case SettingsItem.FINGERPRINT:
                view = inflater.inflate(R.layout.fingerprint_setting, parent, false);
                return new FingerPrintHolder(view);
            default:
                throw new Error("shaise");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseSettingsViewHolder holder, int position) {
        holder.bind(settingsList.get(position));
    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return settingsList.get(position).getType();
    }

    //places for different holders for different settings
    static class CameraInChatHolder extends BaseSettingsViewHolder {
        Switch checker;

        public CameraInChatHolder(@NonNull final View itemView) {
            super(itemView);
            final SharedPreferencesRepository repository = new SharedPreferencesRepository(itemView.getContext());
            checker = itemView.findViewById(R.id.switchButton);
            checker.setChecked(repository.getCamera());
            checker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        ((MainActivity) itemView.getContext()).checkPermission(Manifest.permission.CAMERA, CAMERA, checker);
                    }
                    repository.deleteCamera();
                    repository.saveCamera(isChecked);
                    Log.i("camera", repository.getCamera() + "");

                }
            });
        }

        @Override
        public void bind(SettingsItem item) {

        }
    }

    static class FingerPrintHolder extends BaseSettingsViewHolder {
        LinearLayout layout;

        public FingerPrintHolder(@NonNull final View itemView) {
            super(itemView);
            final FragmentManagerSimplifier simplifier = (FragmentManagerSimplifier) itemView.getContext();
            layout = itemView.findViewById(R.id.fingerprint_setting_parent);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    simplifier.addFragment(R.id.requestFingerPrintFragment);
                }
            });
        }

        @Override
        public void bind(SettingsItem item) {

        }
    }
}
