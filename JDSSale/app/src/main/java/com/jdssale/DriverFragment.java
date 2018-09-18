package com.jdssale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jdssale.Adapter.OrderAdapter;
import com.jdssale.Response.ForgetResponse;
import com.jdssale.Response.MapModel;
import com.jdssale.Response.OrderResponse;
import com.jdssale.Response.RouteResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.MyLocationService;
import com.jdssale.Utilities.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DriverFragment extends Fragment implements OnMapReadyCallback,LocationListener {

    private OnFragmentInteractionListener mListener;

    GoogleMap map;
    TextView textView;
    View viewMarker;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    List<MapModel> mapModels;
    LocationManager locationManager;;
    public DriverFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver, container, false);
        ButterKnife.bind(this, view);
        mapModels = new ArrayList<>();
        viewMarker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_view, null);
        textView = (TextView) viewMarker.findViewById(R.id.tv_no);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        ((Driver1Activity) getActivity()).setTitle("ORDER LIST");
        getActivity().startService(new Intent(getActivity(),MyLocationService.class));
        getList();
        getLocation();
        getRoute();
        return view;
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getRoute() {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsSaleService.getRoute(Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<RouteResponse>() {
                    @Override
                    public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            ArrayList<LatLng> list = new ArrayList<>();
                            list.add(new LatLng(52.281589, 4.797780));
                            for (int i=0;i<response.body().getRouteDataList().size();i++) {
                                list.add(new LatLng(Double.parseDouble(response.body().getRouteDataList().get(i).getLat()), Double.parseDouble(response.body().getRouteDataList().get(i).getLng())));
                                if (response.body().getRouteDataList().get(i).getIsDelivered()==null) {
                                    String quoteId=response.body().getRouteDataList().get(i).getId();
                                    Location loc1 = new Location("");
                                    loc1.setLatitude(Double.parseDouble(response.body().getRouteDataList().get(i).getLat()));
                                    loc1.setLongitude(Double.parseDouble(response.body().getRouteDataList().get(i).getLng()));

                                    Location loc2 = new Location("");
                                    loc2.setLatitude(51.9218993);
                                    loc2.setLongitude(4.4807755);

                                    float distanceInMeters = loc1.distanceTo(loc2);
                                    if(distanceInMeters<=200){
                                        callServer(quoteId);
                                    }
                                    Log.d("distanceInMeters","distanceInMeters"+distanceInMeters);
                                }
                            }
                            list.add(new LatLng(52.281589, 4.797780));
                            PolylineOptions options = new PolylineOptions().width(15).color(Color.parseColor("#00B4F9")).geodesic(true);
                            for (int z = 0; z < list.size(); z++) {
                                if (z == 0 || z == list.size() - 1) {
                                    textView.setText("S");
                                    Bitmap bmp=createDrawableFromView(getContext(),viewMarker);
                                    map.addMarker(new MarkerOptions().alpha(0.5f).icon(BitmapDescriptorFactory.fromBitmap(bmp)).position(list.get(z)).title("JDS Foods").snippet("Aalsmeerderweg 285B, 1432 CN Aalsmeer, Netherlands"));
                                } else {
                                    textView.setText(String.valueOf(z));
                                    Bitmap bmp=createDrawableFromView(getContext(),viewMarker);
                                    map.addMarker(new MarkerOptions().alpha(0.5f).icon(BitmapDescriptorFactory.fromBitmap(bmp)).position(list.get(z)).title("Billa"));
                                }
                                LatLng point = list.get(z);
                                options.add(point);
                            }
                            map.addPolyline(options);

                            CameraPosition cameraPosition;
                            cameraPosition = new CameraPosition.Builder().target(new LatLng(52.281589, 4.797780)).zoom(8).build();

                            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        } else {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<RouteResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void callServer(String quoteId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsSaleService.orderDelivery(quoteId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ForgetResponse>() {
                    @Override
                    public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                        if (response.body().getFlag() == 1) {

                        } else {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ForgetResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getList() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsSaleService.orderList(Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if (response.body().getFlag() == 1) {
                            if (response.body().getOrderData().size()==0){
                                ivNo.setVisibility(View.VISIBLE);
                                rvList.setVisibility(View.GONE);
                            }
                            else {
                                ivNo.setVisibility(View.GONE);
                                rvList.setVisibility(View.VISIBLE);
                                OrderAdapter orderAdapter = new OrderAdapter(getContext(), response.body().getOrderData());
                                rvList.setAdapter(orderAdapter);
                            }
                        } else {
                            ivNo.setVisibility(View.VISIBLE);
                            rvList.setVisibility(View.GONE);
                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        ivNo.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                        Toast.makeText(getContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels,
                displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        map.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMinZoomPreference(6);
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
     LOCATION_PERMISSION_REQUEST_CODE);
        } else if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(getContext(), "Location permission not granted, " + "showing default location", Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(30.900965, 75.857276);
        map.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    map.setMinZoomPreference(15);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
                    map.setMinZoomPreference(12);
//                    CircleOptions circleOptions = new CircleOptions();
//                    circleOptions.center(new LatLng(location.getLatitude(), location.getLongitude()));
//                    circleOptions.radius(200);
//                    circleOptions.fillColor(Color.TRANSPARENT);
//                    circleOptions.strokeWidth(6);
//                    map.addCircle(circleOptions);
                }
            };


    @Override
    public void onLocationChanged(Location location) {
        Log.d("ludhiana","ludhiana"+location.getLatitude()+location.getLongitude());
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
       // map.addMarker(new MarkerOptions().alpha(0.5f).icon(BitmapDescriptorFactory.fromResource(R.drawable.locicon)).position(latLng).title("Ludhiana").snippet("Aalsmeerderweg 285B, 1432 CN Aalsmeer, Netherlands"));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @BindView(R.id.iv_map)
    ImageView ivMap;

    @BindView(R.id.iv_list)
    ImageView ivList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rl_map)
    void rlMap() {
        getRoute();
        rlList.setVisibility(View.GONE);
        rlMap.setVisibility(View.VISIBLE);
        ivList.setVisibility(View.INVISIBLE);
        ivMap.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rl_list)
    void rlList() {
        getList();
        rlList.setVisibility(View.VISIBLE);
        rlMap.setVisibility(View.GONE);
        ivList.setVisibility(View.VISIBLE);
        ivMap.setVisibility(View.INVISIBLE);
    }

    @BindView(R.id.rl_list_view)
    RelativeLayout rlList;

    @BindView(R.id.rl_map_view)
    RelativeLayout rlMap;

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.iv_no)
    ImageView ivNo;


}
