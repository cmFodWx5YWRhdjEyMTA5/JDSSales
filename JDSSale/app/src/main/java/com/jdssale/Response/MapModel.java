package com.jdssale.Response;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by dikhong on 25-07-2018.
 */

public class MapModel {

   LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public MapModel(LatLng latLngs, String title, String snippt) {
        this.latLng = latLngs;
        this.title = title;
        this.snippt = snippt;
    }



    String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippt() {
        return snippt;
    }

    public void setSnippt(String snippt) {
        this.snippt = snippt;
    }

    String snippt;
}
