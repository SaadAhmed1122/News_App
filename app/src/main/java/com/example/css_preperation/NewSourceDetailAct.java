package com.example.css_preperation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.css_preperation.Adaptors.AdaptorNewDetail;
import com.example.css_preperation.Adaptors.AdaptorSourceList;
import com.example.css_preperation.Models.ModelNewsDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewSourceDetailAct extends AppCompatActivity {

    TextView nametv,descriptiontv,countrytv,categorytv,languagetv;
    RecyclerView newRv;

    private ArrayList<ModelNewsDetail> sourceDatailArrayList;
    private AdaptorNewDetail adaptorNewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_source_detail);

        nametv= (TextView) findViewById(R.id.nameTv3);
        descriptiontv= (TextView) findViewById(R.id.descriptionTv3);
        countrytv = (TextView) findViewById(R.id.countryTv3);
        categorytv = (TextView) findViewById(R.id.catagoryTv3);
        languagetv = (TextView) findViewById(R.id.languageTv3);
        newRv = (RecyclerView) findViewById(R.id.newRV3);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Latest News");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent i= getIntent();
        String id= i.getStringExtra("id");
        String name= i.getStringExtra("name");
        String description = i.getStringExtra("description");
        String country = i.getStringExtra("county");
        String category= i.getStringExtra("category");
        String language= i.getStringExtra("Language");
        actionBar.setTitle(name);

        nametv.setText(name);
        descriptiontv.setText(description);
        countrytv.setText("Country: "+country);
        categorytv.setText("Category: "+category);
        languagetv.setText("language: "+language);
        loadnewData(id);
    }

    private void loadnewData(String id) {

        sourceDatailArrayList= new ArrayList<>();

        String url= "https://newsapi.org/v2/top-headlines?sources="+id+"&apiKey="+Constants.API_KEY;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait .....");
        progressDialog.setMessage("Loading News");
        progressDialog.show();

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("articles");

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObjectnew= jsonArray.getJSONObject(i);

                        String title= jsonObjectnew.getString("title");
                        String description= jsonObjectnew.getString("description");
                        String url= jsonObjectnew.getString("url");
                        String urlToImage= jsonObjectnew.getString("urlToImage");
                        String publisedAt= jsonObjectnew.getString("publishedAt");
                        String content= jsonObjectnew.getString("content");

                        SimpleDateFormat dateFormat1= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        SimpleDateFormat dateFormat2= new SimpleDateFormat("dd/MM/yyyy'T'HH:mm");
                        String farmatedDate= "";
                        try {
                            Date date = dateFormat1.parse(publisedAt);
                            farmatedDate= dateFormat2.format(date);

                        }
                        catch (Exception s){
                            farmatedDate= publisedAt;
                            Toast.makeText(NewSourceDetailAct.this, ""+s.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        ModelNewsDetail model = new ModelNewsDetail(""+title,
                                ""+description,
                                ""+url,
                                ""+urlToImage,
                                ""+farmatedDate,
                                ""+content
                        );
                        sourceDatailArrayList.add(model);

                    }
                    progressDialog.dismiss();
                    adaptorNewDetail = new AdaptorNewDetail(NewSourceDetailAct.this,sourceDatailArrayList);
                    newRv.setAdapter(adaptorNewDetail);
                    Toast.makeText(NewSourceDetailAct.this, "Data fatch Successfully", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(NewSourceDetailAct.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(NewSourceDetailAct.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //Add query to volly request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}