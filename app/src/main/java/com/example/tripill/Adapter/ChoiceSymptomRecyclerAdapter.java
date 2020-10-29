package com.example.tripill.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.tripill.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChoiceSymptomRecyclerAdapter extends RecyclerView.Adapter<ChoiceSymptomRecyclerAdapter.MainHolder>  {

    private List<String> list;
    private Context context;
    private RecyclerView recyclerView;
    private LayoutInflater inflate;

    private int pos = -1;

    public String getCheckedItem() {  //엑티비티를 위한 메소드 인텐트에 담아줄거
        return list.get(pos);
    }

    public ChoiceSymptomRecyclerAdapter(List<String> list, Context context) {
        this.list=list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_symptom_choiced, parent, false);

        MainHolder mainHolder = new MainHolder(v);
        return mainHolder;
    }





    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {

        holder.SymptomBtn.setText(list.get(position));
        holder.SymptomBtn.setChecked(position == pos);

    }



    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MainHolder extends RecyclerView.ViewHolder{

        public CheckBox SymptomBtn;

        public MainHolder(View v){
            super(v);

            this.SymptomBtn = v.findViewById(R.id.SymptomBtn);

            SymptomBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });

        }
    }



}
