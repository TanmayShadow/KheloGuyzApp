package com.example.kheloguys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyGridAdapter extends BaseAdapter {

    Context context;
    String[] gameName;
    int[]  images;
    LayoutInflater inflater;
    MyGridAdapter(Context c, String[] nm, int[] img)
    {
        this.context=c;
        this.gameName=nm;
        this.images=img;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater==null)
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null)
            view=inflater.inflate(R.layout.my_grid_layout,null);
        ImageView  img = view.findViewById(R.id.gridImage);
        img.setImageResource(images[i]);

        return view;
    }
}
