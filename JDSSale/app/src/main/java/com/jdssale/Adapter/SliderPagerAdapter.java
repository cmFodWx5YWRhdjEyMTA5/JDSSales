package com.jdssale.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jdssale.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dikhong on 14-07-2018.
 */

public class SliderPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    List<String>images;
    String image;

    public SliderPagerAdapter(Context context, List<String> images,String image) {
        this.context = context;
        this.images=images;
        this.image=image;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_slider, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.im_slider);
        Picasso.with(context).load(image+images.get(position)).into(imageView);


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}