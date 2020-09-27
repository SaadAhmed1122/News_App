package com.example.css_preperation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.css_preperation.Adaptors.AdaptorSourceList;
import com.example.css_preperation.Models.ModelSourceList;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class News_Main extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView sourceRV;
    ImageButton filerbtn;
    EditText searchEt;

    ArrayList<ModelSourceList> sourceLists;
    AdaptorSourceList adaptorSourceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__main);

        progressBar= (ProgressBar) findViewById(R.id.ProgressBar);
        sourceRV= (RecyclerView) findViewById(R.id.sourcesRV);
        filerbtn= (ImageButton) findViewById(R.id.filterbtn);
        searchEt = (EditText) findViewById(R.id.searchEt);


        loadSouces();
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adaptorSourceList.getFilter().filter(s);
                }
                catch (Exception ad){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        filerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterBottomSheet();
            }
        });

    }
    private String selectedCounties ="ALL", selectedCategories="ALL" , selectedLanguage="ALL";
    private int selectcounttyposition=0,slectedcategoryposition= 0, selectedlanguagepostion = 0;

    private void filterBottomSheet() {
        View v= LayoutInflater.from(this).inflate(R.layout.filter_layout,null);
        Spinner countryspinner= v.findViewById(R.id.countryspinner);
        Spinner cetagoryspinner= v.findViewById(R.id.cetagoryspinner);
        Spinner languagespinner= v.findViewById(R.id.languagespinner);
        Button applyBtn= v.findViewById(R.id.submitbtn);

        ArrayAdapter<String> adapterCountries = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,Constants.COUNTRIES);
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,Constants.CATEGORIES);
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,Constants.LANGUAGES);

        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_item);

        //Apply adaptor to the spinner
        countryspinner.setAdapter(adapterCountries);
        cetagoryspinner.setAdapter(adapterCategories);
        languagespinner.setAdapter(adapterLanguage);

      countryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              selectedCounties= Constants.COUNTRIES[position];
              selectcounttyposition=position;
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });

      cetagoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              selectedCategories= Constants.CATEGORIES[position];
              slectedcategoryposition = position;
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });

      languagespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              selectedLanguage = Constants.LANGUAGES[position];
              selectedlanguagepostion = position;
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
    //Setup bottom Sheet Dialog

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.show();

        //Submit button
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                loadSouces();
            }
        });

    }

    private void loadSouces() {

        //Action bar with county
        getSupportActionBar().setTitle("Country: "+selectedCounties+"Category: "+selectedCategories+"Language: "+selectedLanguage);

        sourceLists= new ArrayList<>();
        sourceLists.clear();

        if(selectedCounties.equals("ALL")){
            selectedCounties="";
        } if(selectedCategories.equals("ALL")){
            selectedCategories="";
        } if(selectedLanguage.equals("ALL")){
            selectedLanguage="";
        }

        progressBar.setVisibility(View.VISIBLE);
        //Request data
        String url= "https://newsapi.org/v2/sources?apiKey="+Constants.API_KEY+"&country="+selectedCounties+"&category="+selectedCategories+"&language="+selectedLanguage;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            try {
                JSONObject jasonobject= new JSONObject(response);
                JSONArray jsonarray =jasonobject.getJSONArray("sources");

                for(int i=0; i< jsonarray.length(); i++){
                    JSONObject jsonObject1 = jsonarray.getJSONObject(i);
                    String id= jsonObject1.getString("id");
                    String name= jsonObject1.getString("name");
                    String description= jsonObject1.getString("description");
                    String url= jsonObject1.getString("url");
                    String country= jsonObject1.getString("country");
                    String category= jsonObject1.getString("category");
                    String language= jsonObject1.getString("language");

                    ModelSourceList model = new ModelSourceList(
                            ""+id,
                            ""+name,
                            ""+description,
                            ""+url,
                            ""+category,
                            ""+language,
                            ""+country
                    );

                    sourceLists.add(model);
                }
                progressBar.setVisibility(View.GONE);
                adaptorSourceList = new AdaptorSourceList(News_Main.this,sourceLists);
                sourceRV.setAdapter(adaptorSourceList);


            }
            catch (Exception aa){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(News_Main.this, ""+aa.getMessage(), Toast.LENGTH_SHORT).show();

            }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(News_Main.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //add request to que
        RequestQueue requestQueuev = Volley.newRequestQueue(this);
        requestQueuev.add(stringRequest);


    }


}