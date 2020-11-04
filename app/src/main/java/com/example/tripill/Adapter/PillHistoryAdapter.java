package com.example.tripill.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tripill.Activity.PillRecommendActivity;
import com.example.tripill.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PillHistoryAdapter extends RecyclerView.Adapter<PillHistoryAdapter.MainHolder>{

    ArrayList<PillList> pillList = new ArrayList<PillList>();
    private Context context;
    private RecyclerView recyclerView;
    private LayoutInflater inflate;



    public PillHistoryAdapter(ArrayList<PillList> pillList,Context context) {
        this.pillList = pillList;
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
        holder.onBind(pillList.get(position)); //SymptomBtn
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mcontext = v.getContext();
                Intent intent = new Intent(v.getContext(), PillRecommendActivity.class);
                intent.putExtra("s1",holder.symptom1.getText());
                intent.putExtra("s2",holder.symptom2.getText());
                intent.putExtra("age",holder.age.getText());
                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return pillList.size();
    }



    public class MainHolder extends RecyclerView.ViewHolder{

        public TextView symptom1;
        public TextView symptom2;
        public TextView age;
        public TextView date;
        public TextView pillname;
        public LinearLayout linear;

        public MainHolder(View v){
            super(v);

            symptom1 = v.findViewById(R.id.symptom1);
            symptom2 = v.findViewById(R.id.symptom2);
            age = v.findViewById(R.id.age);
            date = v.findViewById(R.id.date);
            pillname = v.findViewById(R.id.pillname);
            linear = v.findViewById(R.id.layout);

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
