package com.coupondunia.assignment.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.coupondunia.assignment.BaseActivity;
import com.coupondunia.assignment.R;
import com.coupondunia.assignment.adapters.NearByStoreRecyclerAdapter;
import com.coupondunia.assignment.model.NearByStoreModel;
import com.coupondunia.assignment.parsers.ContentParser;
import com.coupondunia.assignment.parsers.MainResponseParser;
import com.coupondunia.assignment.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * DashBoardActivity holds the parent screen where the Store list is populated
 */
public class DashBoardActivity extends BaseActivity {

    private ProgressDialog apiProgressDialog;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location userCurrentLocation;
    private ArrayList<NearByStoreModel> storeList = new ArrayList<>();

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private NearByStoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);

        //set toolbar as you actionbar
        setSupportActionBar(toolbar);

        //enable back icon for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //check for internet connection
        if (Utils.checkInternetConnection(activity)) {
            //setup location listener
            setUpLocationManager();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        } else {
            //no internet connection --> Prompt to turn it on and retry
            Utils.showThisMsg(activity, null, "No network connection detected.\nPlease connect to a " +
                    "network and try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }, null);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // set up location listener
    private void setUpLocationManager() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //remove all locationlistener
                revokeAllLocationListenerIfAny();
                hideProgressDialog();

                //store current location
                userCurrentLocation = location;

                //make the api call to get store data
                makeGetDataAPICall();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
                //show progress while reading location
                showThisProgressDialog("Reading Location.. (Cancel to set as Pune)", true);
            }

            public void onProviderDisabled(String provider) {
                hideProgressDialog();
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Utils.showThisMsg(activity, null, "Please turn on your location service", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    showPuneLocationDialog();
                }
            });
        } else {
            showThisProgressDialog("Reading Location.. (Cancel to set as Pune)", true);
        }

        // register listener
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }


    private void makeGetDataAPICall() {
        //remove all listener
        revokeAllLocationListenerIfAny();

        //make the get store api call
        showThisProgressDialog("Loading..", false);
        getBaseApplication().getWebServiceInterface().makeTaskDataCall(new Callback<MainResponseParser>() {
            @Override
            public void success(MainResponseParser responseParser, Response response) {
                if (responseParser != null) {
                    if (responseParser.status.rcode == 200) {
                        storeList = new ArrayList<>();
                        for (Object o : responseParser.data.entrySet()) {
                            Map.Entry pair = (Map.Entry) o;
                            NearByStoreModel model = new NearByStoreModel(((ContentParser) pair.getValue()));
                            //immediately compute the distance from your current location
                            model.setComputeDistanceFromCurrentLocation(userCurrentLocation);
                            storeList.add(model);
                        }

                        // sort list with respect to the distance value
                        Collections.sort(storeList, new Comparator<NearByStoreModel>() {
                            @Override
                            public int compare(NearByStoreModel nearByStoreModel1, NearByStoreModel nearByStoreModel2) {
                                return nearByStoreModel1.getComputeDistanceFromCurrentLocation() > nearByStoreModel2.getComputeDistanceFromCurrentLocation() ? 1 : -1;
                            }
                        });

                        hideProgressDialog();

                        //add data to listView
                        populateDataOnListView();
                    } else {
                        hideProgressDialog();
                        Utils.showThisMsg(activity, "Error:", responseParser.status.message, null, null);
                    }
                } else {
                    hideProgressDialog();
                    Utils.showToast(activity, "Oops: Something went wrong");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                //if you are here than something went wrong with the API call

                apiProgressDialog.dismiss();
                Timber.e("Error Reason: " + error.getCause().toString());
                Utils.showThisMsg(activity, "Error:", "Oops! Something looks wrong.\nPlease try again later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }, null);
            }
        });
    }

    private void populateDataOnListView() {
        //set adapter to recycler views
        adapter = new NearByStoreRecyclerAdapter(activity, storeList);
        recyclerView.setAdapter(adapter);
    }

    private void revokeAllLocationListenerIfAny() {
        try {
            //remove location listener
            locationManager.removeUpdates(locationListener);
        } catch (Exception ex) {
            Timber.e(Log.getStackTraceString(ex));
        }
    }


    //common method for showing all the progress dialog
    private void showThisProgressDialog(String msg, final boolean forGpsLocation) {
        hideProgressDialog();
        apiProgressDialog = ProgressDialog.show(activity, null, msg, true, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (forGpsLocation) {
                    // proceed with Pune Location as default
                    showPuneLocationDialog();

                } else {
                    // close the app if canceled
                    finish();
                }
            }
        });
    }

    // proceed with Pune as current location
    private void showPuneLocationDialog() {
        revokeAllLocationListenerIfAny();
        Utils.showThisMsg(activity, "Location", "Considering you current location as Pune", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                proceedWithPuneAsCurrentLocation();
            }
        }, null);
    }

    private void proceedWithPuneAsCurrentLocation() {
        userCurrentLocation = new Location("currentLocation");
        userCurrentLocation.setLatitude(18.5203);
        userCurrentLocation.setLongitude(73.8567);
        makeGetDataAPICall();
    }

    private void hideProgressDialog() {
        if (apiProgressDialog != null) {
            if (apiProgressDialog.isShowing()) {
                apiProgressDialog.dismiss();
            }
        }
    }
}
