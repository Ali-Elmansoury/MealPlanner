package com.ities45.mealplanner.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.AreaMapper;

import java.util.List;

public class AreasAdapter extends RecyclerView.Adapter<AreasAdapter.ViewHolder> {
    private List<Area> areas;
    private IOnAreaClickListener listener;

    public AreasAdapter(List<Area> areas, IOnAreaClickListener listener) {
        this.areas = areas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AreasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.area_item, parent, false);
        AreasAdapter.ViewHolder viewHolder = new AreasAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AreasAdapter.ViewHolder holder, int position) {
        Area area = areas.get(position);
        holder.areaName.setText(areas.get(position).getStrArea());
        Glide.with(holder.areaFlagThumb.getContext())
                .load(area.getFlagUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.areaFlagThumb);
        holder.area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAreaClick(area);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView areaFlagThumb;
        public TextView areaName;
        public View area;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            area = itemView;
            areaFlagThumb = area.findViewById(R.id.imgFlag);
            areaName = area.findViewById(R.id.txtAreaName);
        }
    }
}
