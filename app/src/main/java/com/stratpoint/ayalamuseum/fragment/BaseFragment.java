package com.stratpoint.ayalamuseum.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stratpoint.ayalamuseum.R;
import com.stratpoint.ayalamuseum.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    protected MainActivity mainActivity;

    public BaseFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

}
