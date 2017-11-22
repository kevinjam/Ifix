package com.kevinjanvier.ifix.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
/**
 * Created by kevinjanvier on 22/11/2017.
 */

public class Person implements ClusterItem {
    public final String name;
    public final int profilePhoto;
    private final LatLng mPosition;
    public final String phoneNumber;

    public Person(LatLng position, String name, int pictureResource, String phone) {
        this.name = name;
        profilePhoto = pictureResource;
        mPosition = position;
        phoneNumber = phone;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
