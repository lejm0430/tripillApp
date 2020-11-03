package com.example.tripill.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tripill.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PillHistoryAdapter extends RecyclerView.Adapter<PillHistoryAdapter.MainHolder>{

    ArrayList<PillList> pilllist = new ArrayList<PillList>();
    private Context context;
    private RecyclerView recyclerView;
    private LayoutInflater inflate;



    public PillHistoryAdapter(ArrayList<PillList> pilllist,Context context) {
        this.pilllist = pilllist;
        this.context = context;
    }




    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pill_history, parent, false);

        MainHolder mainHolder = new MainHolder(v);

        return mainHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {

        final PillList pillList = pilllist.get(position);

        holder.onBind(pilllist.get(position)); //SymptomBtn


    }



    @Override
    public int getItemCount() {
        return pilllist.size();
    }



    public class MainHolder extends RecyclerView.ViewHolder{

        public TextView symptom1;
        public TextView symptom2;
        public TextView age;
        public TextView date;
        public TextView pillname;

        public MainHolder(View v){
            super(v);

            symptom1 = v.findViewById(R.id.symptom1);
            symptom2 = v.findViewById(R.id.symptom2);
            age = v.findViewById(R.id.age);
            date = v.findViewById(R.id.date);
            pillname = v.findViewById(R.id.pillname);

        }

        public void onBind(PillList list) {
            symptom1.setText(list.getSymptom1());
            symptom2.setText(list.getSymptom2());
            age.setText(list.getAge());
            date.setText(list.getDate());
            pillname.setText(list.getPillname());
        }

    }






}
