package com.lukmanh.android.lookaround.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.lukmanh.android.lookaround.R;
import com.lukmanh.android.lookaround.domain.Event;

import java.util.List;

/**
 * Created by opaw on 6/17/17.
 */

public class EventInfoAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private Event event;

    public EventInfoAdapter(Context context, Event event) {
        this.context = context;
        this.event = event;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.event_info, null);
        TextView lblTitle = (TextView) rootView.findViewById(R.id.lbl_title);
        TextView lblDescripion = (TextView) rootView.findViewById(R.id.lbl_description);
        TextView lblReference = (TextView) rootView.findViewById(R.id.lbl_reference);

        lblTitle.setText(event.getTitle());
        lblDescripion.setText(event.getDescription());
        lblReference.setText(event.getReference());
        return rootView;
    }
}
