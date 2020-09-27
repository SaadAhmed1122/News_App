package com.example.css_preperation.Adaptors;

import android.widget.Adapter;
import android.widget.Filter;

import com.example.css_preperation.Models.ModelSourceList;

import java.util.ArrayList;

class FilterSouceList extends Filter {
    AdaptorSourceList adaptor;
    ArrayList<ModelSourceList> filterlist;

    public FilterSouceList(AdaptorSourceList adaptor, ArrayList<ModelSourceList> filterlist) {
        this.adaptor = adaptor;
        this.filterlist = filterlist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results= new FilterResults();

        if(constraint != null && constraint.length()>0){
            constraint= constraint.toString().toUpperCase();

            ArrayList<ModelSourceList> filtermodel =new ArrayList<>();
            for(int i=0; i<filterlist.size(); i++){
                if(filterlist.get(i).getName().toUpperCase().contains(constraint)){
                    filtermodel.add(filterlist.get(i));

                }
            }
            results.count= filtermodel.size();
            results.values= filtermodel;
        }
        else{
            results.count= filterlist.size();
            results.values= filterlist;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adaptor.sourceLists= (ArrayList<ModelSourceList>) results.values;
        adaptor.notifyDataSetChanged();
    }
}
