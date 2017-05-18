package com.caijia.recyclertablayout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cai.jia on 2017/5/17 0017
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleVH> {

    private List<String> list;

    public SimpleAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public SimpleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_view, parent, false);
        return new SimpleVH(view);
    }

    @Override
    public void onBindViewHolder(SimpleVH holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class SimpleVH extends RecyclerView.ViewHolder{

        private TextView textView;
        public SimpleVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
