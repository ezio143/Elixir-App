package com.example.dell.elixir.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dell.elixir.R;

import java.util.ArrayList;

/**
 * Created by DELL on 10-02-2018.
 */

public class My_home_Adapter extends PagerAdapter {
    private ArrayList<Integer> images;
    private Context context;
    private LayoutInflater layoutInflater;

    public My_home_Adapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images=images;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = layoutInflater.inflate(R.layout.slide, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        myImage.setImageResource(images.get(position));
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
