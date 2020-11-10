package com.example.tripill.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tripill.Activity.PillRecommendActivity;
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
    String s2;
    String s1kr;
    String s2kr;


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
        //final PillList pillList = pilllist.get(position);
        holder.onBind(pilllist.get(position));
        s2 = holder.symptom2.getText().toString();
        if(s2.isEmpty() && s2kr == null){
            holder.coma.setVisibility(View.GONE);
        }
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mcontext = v.getContext();
                Intent intent = new Intent(v.getContext(), PillRecommendActivity.class);
                intent.putExtra("s1",holder.symptom1.getText().toString());
                intent.putExtra("s2",holder.symptom2.getText().toString());
                intent.putExtra("age",holder.age.getText().toString());
                intent.putExtra("name",holder.pillname.getText().toString());
                intent.putExtra("s1kr",s1kr);
                intent.putExtra("s2kr",s2kr);
                context.startActivity(intent);

            }
        });
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
        public TextView coma;
        public TextView pillname;
        public LinearLayout linear;
        String sum;

        public MainHolder(View v){
            super(v);

            symptom1 = v.findViewById(R.id.symptom1);
            symptom2 = v.findViewById(R.id.symptom2);
            age = v.findViewById(R.id.age);
            date = v.findViewById(R.id.date);
            pillname = v.findViewById(R.id.pillname);
            linear = v.findViewById(R.id.layout);
            coma = v.findViewById(R.id.coma);

        }

        public void onBind(PillList list) {
            symptom1.setText(list.getSymptom1());
            symptom2.setText(list.getSymptom2());
            age.setText(list.getAge());
            date.setText(list.getDate());
            pillname.setText(list.getPillname());
            s1kr = list.getSymptom1kr();
            s2kr = list.getSymptom2kr();
        }

    }






}
