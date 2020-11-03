package com.example.tripill.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Activity.MainActivity;
import com.example.tripill.Dialog.NotChoiceDialog;
import com.example.tripill.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChoiceSymptomRecyclerAdapter extends RecyclerView.Adapter<ChoiceSymptomRecyclerAdapter.MainHolder>  {

    private List<String> list;
    public static Context context;
    private RecyclerView recyclerView;
    private LayoutInflater inflate;

    private int pos = -1;

    int count = 0;

    public String getCheckedItem() {  //엑티비티를 위한 메소드 인텐트에 담아줄거
        return list.get(pos);
    } //체크된 체크박스의 포지션 개수?


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
    public void onBindViewHolder(@NonNull MainHolder holder,final int position) {


        String selectedList = list.get(position);
        holder.SymptomText.setText(list.get(position));
//        holder.SymptomBtn.setChecked(selectedList.isEmpty());



        CheckBox.OnCheckedChangeListener checkedChangeListener =new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    count++;
                    if (count > 2) {
                        // 3개쨰 선택시
                        holder.SymptomBtn.setOnCheckedChangeListener(null);
                        holder.SymptomBtn.setChecked(false);
                        Toast.makeText(context, "2개만 선택가능", Toast.LENGTH_SHORT).show();
                        holder.SymptomBtn.setOnCheckedChangeListener(this);
                        count--;
                    }
                }else{
                    count--;
                }

            }
        };

        holder.SymptomBtn.setOnCheckedChangeListener(checkedChangeListener);

    }



    @Override
    public int getItemCount() { //  총 만들어진 아이템이 몇개인지 개수 판단
        return list.size();
    }


    public class MainHolder extends RecyclerView.ViewHolder{

        public CheckBox SymptomBtn;
        public TextView SymptomText;

        public MainHolder(View v){
            super(v);

            this.SymptomBtn = v.findViewById(R.id.SymptomBtn);
            this.SymptomText = v.findViewById(R.id.SymptomText);

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
