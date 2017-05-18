package com.caijia.recyclertablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.MessageFormat;

public class TestFragment extends Fragment {

    static final String POSITION_EXTRA = "position:id";

    String msg ;

    public static TestFragment getInstance(String msg) {
        TestFragment f = new TestFragment();
        Bundle args = new Bundle();
        args.putString(POSITION_EXTRA, msg);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msg = getArguments().getString(POSITION_EXTRA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText(MessageFormat.format("Item position = {0}", msg));
        return textView;
    }
}