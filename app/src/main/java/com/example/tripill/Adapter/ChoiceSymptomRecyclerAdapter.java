package com.example.tripill.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Activity.MainActivity;
import com.example.tripill.Dialog.NotChoiceDialog;
import com.example.tripill.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChoiceSymptomRecyclerAdapter extends RecyclerView.Adapter<ChoiceSymptomRecyclerAdapter.MainHolder>  {

    private List<SymptomList> list;
    private List<SymptomList> selected_list = new ArrayList<>();
    public static Context context;
    private RecyclerView recyclerView;
    private LayoutInflater inflate;

    public int pos = -1;

    int count = 0;

    public String getCheckedItem() {  //엑티비티를 위한 메소드 인텐트에 담아줄거
        return list.get(pos).toString();
    } // 선택 한 아이템 포지션 TODO: 2개선택시?


    public ChoiceSymptomRecyclerAdapter(List<SymptomList> list, Context context) {
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

    public List<SymptomList> getSelected_list() { // // TODO: 2020-11-04 선택된 아이템 스코어값
        return selected_list;
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder,final int position) {

        holder.SymptomText.setText(list.get(position).getSymptom());

        holder.SymptomLay.setOnClickListener(new View.OnClickListener() { //레이아웃을 선택하면
            @Override
            public void onClick(View view) {
                holder.SymptomBtn.performClick();   //체크박스가 체크된다
                holder.SymptomBtn.setTag(list.get(position));
            }
        });


        CheckBox.OnCheckedChangeListener checkedChangeListener =new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("TAG :" , "pre Count : " + count);
                if (b) {
                    count++;


                    selected_list.add(list.get(position));
                    Log.d("TAG","선택된 아이템 : "+selected_list.toString());

                    if (count > 2) {
                        // 3개쨰 선택시
                        holder.SymptomBtn.setOnCheckedChangeListener(null); //리스너 막기
                        holder.SymptomBtn.setChecked(false);  //체크 금지
                        Toast.makeText(context, "2개만 선택가능", Toast.LENGTH_SHORT).show();
                        holder.SymptomBtn.setOnCheckedChangeListener(this);

                        count--;
                    }else{

                    }
                }else{
                    count--;
                    selected_list.remove(list.get(position));
                    Log.d("TAG","해체된 아이템 : "+selected_list.toString());
                }
                Log.d("TAG :" , "post Count : " + count);

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
        public LinearLayout SymptomLay;

        public MainHolder(View v){
            super(v);

            this.SymptomBtn = v.findViewById(R.id.SymptomBtn);
            this.SymptomText = v.findViewById(R.id.SymptomText);
            this.SymptomLay = v.findViewById(R.id.SymptomLay);

            SymptomBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos = getAdapterPosition();
                    notifyDataSetChanged();

                    Log.d("position","선택한 값"+list.get(pos).toString());
                }
            });

        }
    }
}



