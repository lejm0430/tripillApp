package com.example.tripill.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripill.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class PiilHistoryAdapter extends RecyclerView.Adapter<PiilHistoryAdapter.MainHolder>{


    private List<String> pill_history_list;
    private Context context;
    private RecyclerView recyclerView;
    private LayoutInflater inflate;


    public PiilHistoryAdapter(List<String> pill_history_list, Context context) {

        this.pill_history_list = pill_history_list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }




    @NonNull
    @Override
    public PiilHistoryAdapter.MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pill_history, parent, false);

        PiilHistoryAdapter.MainHolder mainHolder = new PiilHistoryAdapter.MainHolder(v);

        return mainHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
    }



    @Override
    public int getItemCount() {
        return pill_history_list.size();
    }



    public class MainHolder extends RecyclerView.ViewHolder{

        public MainHolder(View v){
            super(v);



        }
    }







}
