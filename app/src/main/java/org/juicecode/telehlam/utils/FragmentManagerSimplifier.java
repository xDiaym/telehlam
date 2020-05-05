package org.juicecode.telehlam.utils;

import androidx.fragment.app.Fragment;

public interface FragmentManagerSimplifier {
    void replaceFragment(Fragment fragment, String tag);

    void addFragment(int id, String tag);

    void remove(String tag);

    boolean checkFragment(String tag);
}
