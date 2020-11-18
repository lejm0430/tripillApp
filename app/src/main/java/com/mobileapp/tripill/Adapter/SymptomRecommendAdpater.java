package com.mobileapp.tripill.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.tripill.R;

public class SymptomRecommendAdpater extends RecyclerView.Adapter<SymptomRecommendAdpater.MainHolder> {
    private String[] main_text;


    public SymptomRecommendAdpater(String[] main_text){

        this.main_text = main_text;

    }

    public static class MainHolder extends RecyclerView.ViewHolder{

        public TextView main_text;

        public MainHolder(View view){

            super(view);

            this.main_text = view.findViewById(R.id.symptom);


        }


    }


    @NonNull
    @Override
    public SymptomRecommendAdpater.MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_recommend, parent, false);

        return new SymptomRecommendAdpater.MainHolder(holderView);

    }

    @Override
    public void onBindViewHolder(@NonNull SymptomRecommendAdpater.MainHolder holder, int i) {
        holder.main_text.setText(this.main_text[i]);

    }

    @Override
    public int getItemCount() {

        return main_text.length;
    }
}
