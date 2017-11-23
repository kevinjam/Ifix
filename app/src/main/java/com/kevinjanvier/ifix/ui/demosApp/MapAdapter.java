package com.kevinjanvier.ifix.ui.demosApp;

/**
 * Created by kevinjanvier on 22/11/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.kevinjanvier.ifix.R;
import com.kevinjanvier.ifix.ui.CategoryMore;

public abstract class MapAdapter extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Toolbar toolbar;

    protected int getLayoutId() {
        return R.layout.map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setTitle("Near You");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapAdapter.this, CategoryMore.class);
                startActivity(intent);
            }
        });
        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }
        mMap = map;
        // Add a marker in Sydney and move the camera
        startDemo();
    }

    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    /**
     * Run the demo-specific code.
     */
    protected abstract void startDemo();

    protected GoogleMap getMap() {
        return mMap;
    }
}
