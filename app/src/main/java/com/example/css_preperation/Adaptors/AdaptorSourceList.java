package com.example.css_preperation.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.css_preperation.Models.*;
import com.example.css_preperation.NewSourceDetailAct;
import com.example.css_preperation.R;

import java.util.ArrayList;

public class AdaptorSourceList extends RecyclerView.Adapter<AdaptorSourceList.HolderSourceList> implements Filterable {
    private Context context;
    public ArrayList<ModelSourceList> sourceLists,filerlist;
    private FilterSouceList filter;

    public AdaptorSourceList(Context context, ArrayList<ModelSourceList> sourceLists) {
        this.context = context;
        this.sourceLists = sourceLists;
        this.filerlist= sourceLists;
    }

    @NonNull
    @Override
    public HolderSourceList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_source_list,parent,false);


        return new HolderSourceList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSourceList holder, int position) {
        //get data
        ModelSourceList model= sourceLists.get(position);
        final String id = model.getId();
        final String name= model.getName();
        final String description = model.getDescription();
        final String country = model.getCountry();
        final String category = model.getCetagory();
        final String language = model.getLanguage();

        holder.nameTv.setText(name);
        holder.descriptionTv.setText(description);
        holder.countryTv.setText("Country: "+country);
        holder.cetagoryTv.setText("Category: "+category);
        holder.languageTv.setText("Language: "+language);

        ///handle Click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewSourceDetailAct.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("description",description);
                intent.putExtra("county",country);
                intent.putExtra("category",category);
                intent.putExtra("language",language);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return sourceLists.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter= new FilterSouceList(this,filerlist);

        }
        return filter;
    }


    //Setting Ui Source List

    class HolderSourceList extends RecyclerView.ViewHolder{

        TextView nameTv,descriptionTv,countryTv,cetagoryTv,languageTv;
        public HolderSourceList(@NonNull View itemView) {
            super(itemView);
            nameTv= itemView.findViewById(R.id.nameTv);
            descriptionTv= itemView.findViewById(R.id.descriptionTv);
            countryTv= itemView.findViewById(R.id.countryTv);
            cetagoryTv= itemView.findViewById(R.id.catagoryTv);
            languageTv= itemView.findViewById(R.id.languageTv);

        }
    }
}
