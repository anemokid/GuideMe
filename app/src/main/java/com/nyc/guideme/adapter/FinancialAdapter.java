package com.nyc.guideme.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nyc.guideme.R;
import com.nyc.guideme.details.FoodStampsDetailsActivity;
import com.nyc.guideme.models.FoodStampOfficeModel;

import java.util.List;

/**
 * Created by Wayne Kellman on 3/4/18.
 */

public class FinancialAdapter extends RecyclerView.Adapter<FinancialAdapter.ViewHolder>{

    private List<FoodStampOfficeModel> foodStampOfficeModel;

    public FinancialAdapter(List<FoodStampOfficeModel> foodStampOfficeModel) {
        this.foodStampOfficeModel = foodStampOfficeModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_financial, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.medicalCenter.setText(foodStampOfficeModel.get(position).getFacility_name());
        holder.fpIndicator.setText(foodStampOfficeModel.get(position).getState());
        holder.postUntil.setText(foodStampOfficeModel.get(position).getStreet_address());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), FoodStampsDetailsActivity.class);
                String foodStampsJSON = new Gson().toJson(foodStampOfficeModel.get(position));
                intent.putExtra("FoodStamps", foodStampsJSON);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodStampOfficeModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicalCenter, fpIndicator, postUntil;
        public ViewHolder(View itemView) {
            super(itemView);

            medicalCenter = itemView.findViewById(R.id.fa_medical_Center);
            fpIndicator = itemView.findViewById(R.id.full_time_part_time_indicator);
            postUntil = itemView.findViewById(R.id.post_until);
        }
    }
}
