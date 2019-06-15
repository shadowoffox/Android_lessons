package com.example.testapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AnyTypeAdapter extends RecyclerView.Adapter<ViewHolder> {

    public static final int WEATHER_TYPE = 0;
    public static final int ADV_TYPE = 1;

    public AnyTypeAdapter(List<Object> objects) {
        this.objects = objects;
    }

    private List<Object> objects = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == WEATHER_TYPE) {
            return new ViewHolderWeather(inflater.inflate(R.layout.item_weth_layout,parent,false));
        } else {
            return new ViewHolderAdv(inflater.inflate(R.layout.item_adv_layout,parent,false));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (objects.get(position) instanceof Weather) {
            return WEATHER_TYPE;
        } else {
            return ADV_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bindView(objects.get(position));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
