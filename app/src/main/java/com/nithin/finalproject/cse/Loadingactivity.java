package com.nithin.finalproject.cse;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.nithin.finalproject.Adapter.GalleryAdapter;
import com.nithin.finalproject.Adapter.SlideshowDialogFragment;
import com.nithin.finalproject.AppConroller.AppController;
import com.nithin.finalproject.Model.Image;
import com.nithin.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Loadingactivity extends AppCompatActivity {
    private static final String endpoint = "https://api.myjson.com/bins/";
    private String TAG = Cseactivity.class.getSimpleName();
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String getButton;

        Bundle b = new Bundle();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingactivity);

        b = getIntent().getExtras();
        getButton = b.getString("button");


        recyclerview = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(mAdapter);

        recyclerview.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerview, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                /* Fragment Manager manages all fragments in android specifically it handles all transactions
                in fragment . Transaction is a way to add,replace,remove fragments */

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        fetchImages(getButton);

    }

    private void fetchImages(String b) {

        pDialog.setMessage("Loading Images..");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(endpoint + b,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        images.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(object.getString("name"));

                                JSONObject url = object.getJSONObject("url");

                                image.setMedium(url.getString("medium"));
                                image.setLarge(url.getString("large"));

                                images.add(image);

                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
       RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }
}

