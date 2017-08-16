package com.example.devansh.indoreinfoline;

//Zomato API key : ab1f740899860307afcdace7be9fbc20

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Restaurants extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback,
                    GoogleApiClient.ConnectionCallbacks,
                        GoogleApiClient.OnConnectionFailedListener,
                            LocationListener{

    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private GoogleApiClient googleApiClient;

    private List<Restaurant> restaurantList=new ArrayList<>();
    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;
    private ProgressBar progressBar;

    final private int REQUEST_CODE_ASK_PERMISSION_LOCATION=786;

    public void zomato(Location location) {
        try {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://developers.zomato.com/api/v2.1/search?entity_id=14&entity_type=city&lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&radius=5000";
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                restaurantList.clear();
                                JSONArray array=response.getJSONArray("restaurants");
                                Restaurant restaurant;
                                for(int i = 0 ; i < array.length() ; i++)
                                {
                                    JSONObject jsonObject=array.getJSONObject(i).getJSONObject("restaurant");
                                    restaurant=new Restaurant(jsonObject.getString("name"),
                                            jsonObject.getString("url"),
                                            jsonObject.getString("thumb"),
                                            jsonObject.getString("featured_image"),
                                            jsonObject.getJSONObject("location").getString("address"),
                                            jsonObject.getJSONObject("location").getString("latitude"),
                                            jsonObject.getJSONObject("location").getString("longitude"));
                                    restaurantList.add(restaurant);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Restaurants.this, "Some error occurred !!", Toast.LENGTH_LONG).show();
                            }
                            finally {
                                restaurantAdapter.setRestaurantList(restaurantList);
                                restaurantAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Restaurants.this, "Some error occurred !!", Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<>();
                    params.put("user-key", "ab1f740899860307afcdace7be9fbc20");
                    return params;
                }
            };

            queue.add(jsObjRequest);
        } catch (Exception e) {
           Toast.makeText(Restaurants.this, "Some error occurred !!", Toast.LENGTH_LONG).show();
        }
    }

    public void activity()
    {
        progressBar.setVisibility(View.VISIBLE);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        locationRequest=new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setFastestInterval(30000);
        locationRequest.setInterval(60000);
        googleApiClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        progressBar=(ProgressBar)findViewById(R.id.res_progressBar);
        recyclerView=(RecyclerView)findViewById(R.id.res_recycler);
        restaurantAdapter= new RestaurantAdapter();
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantAdapter);
        int permission = AskLocationPermission();
        if(permission == PackageManager.PERMISSION_GRANTED)
            activity();
    }

    int AskLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_ASK_PERMISSION_LOCATION);
        return ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == REQUEST_CODE_ASK_PERMISSION_LOCATION) {
            if(grantResults.length==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                activity();
            else
                NavUtils.navigateUpFromSameTask(this);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
        } catch (SecurityException e) {
            Toast.makeText(Restaurants.this,"Some error occurred !!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(Restaurants.this,"Some error occurred !!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(Restaurants.this,"Some error occurred !!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        zomato(location);
    }
}
