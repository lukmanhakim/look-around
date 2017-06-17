package com.lukmanh.android.lookaround;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lukmanh.android.lookaround.domain.Event;
import com.lukmanh.android.lookaround.service.GPSTracker;
import com.lukmanh.android.lookaround.service.HttpService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HttpService service = new HttpService();
    private GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        gpsTracker = new GPSTracker(this);
        LatLng pos;
        if(gpsTracker.getIsGPSTrackingEnabled()){
            pos = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        } else {
            pos = new LatLng(-6.175054, 106.827148);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 17.0f));
        new GetListEventExecutor().execute();
    }

    private class GetListEventExecutor extends AsyncTask<Void, Void, ResponseEntity>{

        @Override
        protected ResponseEntity doInBackground(Void... voids) {
            try {
                return service.listEvent();
            } catch(HttpClientErrorException e){
                Log.d("Get List Event Error : ", e.getLocalizedMessage());
                return new ResponseEntity(e.getStatusCode());
            } catch(Exception e) {
                Log.d("Get List Event Error : ", e.getLocalizedMessage());
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @Override
        protected void onPostExecute(ResponseEntity responseEntity) {
            super.onPostExecute(responseEntity);
            if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
                Event[] arrEvents = (Event[]) responseEntity.getBody();
                List<Event> events = Arrays.asList(arrEvents);
                for(final Event event : events){
                    double lat = Double.valueOf(event.getLat());
                    double lng = Double.valueOf(event.getLng());
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(event.getTitle()).snippet(event.getDescription()));
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            String url = event.getReference();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    });
                }
            } else {
                Toast.makeText(getApplicationContext(), "Can't get list event from server", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
