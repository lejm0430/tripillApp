package com.example.tripill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.MainHolder> {
    private String[] main_text;


    public SymptomAdapter(String[] main_text){

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
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_symptom_item, parent, false);

        return new MainHolder(holderView);

    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int i) {
        holder.main_text.setText(this.main_text[i]);
    }

    @Override
    public int getItemCount() {

        return main_text.length;
    }
}