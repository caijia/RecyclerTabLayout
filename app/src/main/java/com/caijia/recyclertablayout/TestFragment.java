package com.caijia.recyclertablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestFragment extends Fragment {

    static final String POSITION_EXTRA = "position:id";

    int position = -1;

    public static TestFragment getInstance(int position) {
        TestFragment f = new TestFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_EXTRA, position);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(POSITION_EXTRA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText("Item position = " + position
                + "---savedInstanceState="
                + (savedInstanceState != null ? savedInstanceState.getString("args") : ""));
        return textView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("args", "Item=" + position);
    }
}