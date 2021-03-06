package com.mobileapp.tripill.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobileapp.tripill.Activity.PillRecommendActivity;
import com.mobileapp.tripill.DataBase.PillList;
import com.mobileapp.tripill.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.mobileapp.tripill.Props.INTE_INPUT_AGE;
import static com.mobileapp.tripill.Props.INTE_SELECT_PILLNAME;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM1;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM1_KR;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM2;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM2_KR;

public class PillHistoryAdapter extends RecyclerView.Adapter<PillHistoryAdapter.MainHolder>{

    ArrayList<PillList> pilllist = new ArrayList<PillList>();
    private Context context;
    private RecyclerView recyclerView;
    private LayoutInflater inflate;
    String s2;



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
        holder.onBind(pilllist.get(position));
        s2 = holder.symptom2.getText().toString();
        if(s2.isEmpty() || holder.s2kr == null){
            holder.coma.setVisibility(View.GONE);
        }else{
            holder.coma.setVisibility(View.VISIBLE);
        }
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PillRecommendActivity.class);
                intent.putExtra(INTE_SELECT_SYMPTOM1,holder.symptom1.getText().toString());
                intent.putExtra(INTE_SELECT_SYMPTOM2,holder.symptom2.getText().toString());
                intent.putExtra(INTE_INPUT_AGE,holder.age.getText().toString());
                intent.putExtra(INTE_SELECT_PILLNAME,holder.pillname.getText().toString());
                intent.putExtra(INTE_SELECT_SYMPTOM1_KR,holder.s1kr);
                intent.putExtra(INTE_SELECT_SYMPTOM2_KR,holder.s2kr);
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
        public String s1kr;
        public String s2kr;

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
            symptom1.setTypeface(symptom1.getTypeface(), Typeface.BOLD);
            symptom2.setTypeface(symptom2.getTypeface(), Typeface.BOLD);

        }

    }






}
