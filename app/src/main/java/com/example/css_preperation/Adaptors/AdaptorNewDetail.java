package com.example.css_preperation.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.css_preperation.Models.ModelNewsDetail;
import com.example.css_preperation.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptorNewDetail extends RecyclerView.Adapter<AdaptorNewDetail.HolderNewDetail>{

    private Context context;
    ArrayList<ModelNewsDetail> newsSourceDetailArraylist;

    public AdaptorNewDetail(Context context, ArrayList<ModelNewsDetail> newsSourceDetailArraylist) {
        this.context = context;
        this.newsSourceDetailArraylist = newsSourceDetailArraylist;
    }

    @NonNull
    @Override
    public HolderNewDetail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.row_news_source_detail,parent,false);

        return new HolderNewDetail(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNewDetail holder, int position) {
        ModelNewsDetail modelNewsDetail= newsSourceDetailArraylist.get(position);

        String content= modelNewsDetail.getContent();
        String description = modelNewsDetail.getDescription();
        String publishedAt = modelNewsDetail.getPublishedAt();
        String urlToImage= modelNewsDetail.getUrlToImage();
        String url = modelNewsDetail.getUrl();
        String title = modelNewsDetail.getTitle();


        holder.titletv.setText(title);
        holder.descriptiontv.setText(description);
        holder.datetv.setText(publishedAt);
        Picasso.get().load(urlToImage).into(holder.imageIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return newsSourceDetailArraylist.size();
    }

    class HolderNewDetail extends RecyclerView.ViewHolder{

        TextView titletv,descriptiontv,datetv;
        ImageView imageIv;

        public HolderNewDetail(@NonNull View itemView) {
            super(itemView);
            titletv =itemView.findViewById(R.id.titleTV2);
            descriptiontv = itemView.findViewById(R.id.descriptionTv2);
            datetv = itemView.findViewById(R.id.dateTv2);
            imageIv = itemView.findViewById(R.id.imageTV);

        }
    }
}
