package com.jdssale;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.Adapter.DrawerItemCustomAdapter;
import com.jdssale.Response.DataModel;
import com.jdssale.Response.DataResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.MyLocationService;
import com.jdssale.Utilities.Preferences;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Driver1Activity extends BaseActivity implements DPayHistoryFragment.OnFragmentInteractionListener,DriverFragment.OnFragmentInteractionListener,DPendingPayFragment.OnFragmentInteractionListener {

    Toolbar toolbar;
    TextView title,dashboardName;
    CircleImageView dashboardImage;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver1);
        ButterKnife.bind(this);
        startService(new Intent(Driver1Activity.this,MyLocationService.class));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DriverFragment()).commit();
        }
        setupToolbar();
        setSideMenu();
        drawer.setDrawerListener(mDrawerToggle);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        setupDrawerToggle();
    }

    private void setSideMenu() {
        DataModel[] drawerItem = new DataModel[4];
        drawerItem[0] = new DataModel(R.drawable.list_icon, "List View/Map View");
        drawerItem[1] = new DataModel(R.drawable.potli_icon, "Pending Payment");
        drawerItem[2] = new DataModel(R.drawable.money_icon, "Payment History");
        drawerItem[3] = new DataModel(R.drawable.dlog_icon, "Logout");
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DrawerItemClickListener());
        dashboardImage = (CircleImageView) findViewById(R.id.d_image);
        dashboardName = (TextView) findViewById(R.id.d_name);
        setHeader();
    }

    private void setHeader() {
        dashboardName.setText(Preferences.getInstance().getUserName());
        Picasso.with(dashboardImage.getContext()).load(R.drawable.dash).into(dashboardImage);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void logout() {
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(Driver1Activity.this);
        alertDialogBuilder.setTitle(getString(R.string.app_driver));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setIcon(R.drawable.appicon);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutApi();
                Preferences.getInstance().setLogIn(false);
                Preferences.getInstance().clearUserDetails();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                drawer.closeDrawer(GravityCompat.START);
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        pbutton.setTextColor(Color.parseColor("#000000"));
        nbutton.setTextColor(Color.parseColor("#000000"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void logoutApi() {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Logging Out...");
        jdsSaleService.logout(Preferences.getInstance().getUserId()).
                enqueue(new Callback<DataResponse>() {
                    @Override
                    public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                        if (response.body().getFlag() == 1) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getData(), Toast.LENGTH_SHORT).show();
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<DataResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position,view);

        }

    }

    private void selectItem(int position,View view) {

        Fragment fragment = null;
        ImageView ivIcon=(ImageView)view.findViewById(R.id.imageViewIcon);
        TextView tvName=(TextView)view.findViewById(R.id.textViewName);
        RelativeLayout rlCell=(RelativeLayout)view.findViewById(R.id.rl_cell);

        switch (position) {
            case 0:
                fragment = new DriverFragment();
                break;
            case 1:
                fragment = new DPendingPayFragment();
                break;
            case 2:
                fragment = new DPayHistoryFragment();
                break;
            case 3:
                logout();
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            listView.setItemChecked(position, true);
            listView.setSelection(position);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_driver);
        title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    public void setTitle(String titletxt) {
        title.setText(titletxt);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.menu_sale, this.getTheme());
        drawable.setTint(getResources().getColor(R.color.colorWhite));
        mDrawerToggle.setHomeAsUpIndicator(drawable);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        mDrawerToggle.syncState();
    }


    @BindView(R.id.drawer_layout1)
    DrawerLayout drawer;

    @BindView(R.id.left_drawer)
    ListView listView;


}
