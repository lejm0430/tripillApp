package com.example.tripill.Dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Activity.AgeActivity;
import com.example.tripill.Adapter.ChoiceSymptomRecyclerAdapter;
import com.example.tripill.Adapter.SymptomList;
import com.example.tripill.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ChoicedSymptomSlide extends BottomSheetDialogFragment {

    ArrayList<SymptomList> list = new ArrayList();
    public String title;

    ChoicedSymptomSlide choicedSymptomSlide;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        title = data.getStringExtra("ChoicedSymptomSlide");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.activity_choiced_symptom_slide, container, false);

        final int dutong = 1;
        int agirum = 2;
        int balyel = 4;

        int inhutong = 7;
        int gichim = 8;

        int sohwabullyang = 40;
        int boktong = 50;
        int sanglitong = 100;

        int musclepain = 20;
        int wound = 25;
        int burn = 60;
        int hangover = 35;


        TextView nextBtn=v.findViewById(R.id.nextBtn);
        TextView TwoChoiceMungu=v.findViewById(R.id.TwoChoiceMungu);
        ImageView backBtn=v.findViewById(R.id.backbtn);
        RecyclerView recyclerView=v.findViewById(R.id.recycler_symptom);
        LinearLayout bottom_sheet = v.findViewById(R.id.bottom_sheet);
        TextView SymptomArea=v.findViewById(R.id.SymptomArea);



        SymptomArea.setText(title);

        list=new ArrayList<>();

        if(title.contains("head")){
            TwoChoiceMungu.setVisibility((View.VISIBLE));

            list.add(new SymptomList("두통", 1));
            list.add(new SymptomList("어지럼증",2));
            list.add(new SymptomList("발열",4));
        }else if(title.contains("neck")){
            TwoChoiceMungu.setVisibility((View.VISIBLE));

            list.add(new SymptomList("인후통",4));
            list.add(new SymptomList("기침",4));
        }else if(title.contains("stomach")){

            TwoChoiceMungu.setVisibility((View.VISIBLE));

            list.add(new SymptomList("소화불량",40));
            list.add(new SymptomList("복통",50));
            list.add(new SymptomList("생리통",100));

        }else if(title.contains("muscle pain")){
            list.add(new SymptomList("근육통",20));

        }else if(title.contains("wound")){
            list.add(new SymptomList("상처",25));

        }else if(title.contains("burn")){
            list.add(new SymptomList("화상",60));

        }else if(title.contains("hangover")){
            list.add(new SymptomList("숙취",35));

        }


        ChoiceSymptomRecyclerAdapter adapter=new ChoiceSymptomRecyclerAdapter(list, getContext());   //세팅된 리스트를 어댑터로 보냄
        recyclerView.setAdapter(adapter);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AgeActivity.class);
                intent.putExtra("part",title);
                intent.putExtra("s1", adapter.getSelected_list().toString());  // 선택한 값 포지션

               /* if(adapter.getSelected_list() = dutong) { // TODO: 2020-11-03 선택한게 두통이랑 어지럼증일때
                    intent.putExtra("s1",list.get(0).toString());
                    intent.putExtra("s2",list.get(0).toString());
                    intent.putExtra("sum", sum(dutong,agirum));
                }else if(adapter.getCheckedItem() == list.get(0)){

                }*/


                getContext().startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;

    }

    public int sum(int s1, int s2) {
        return s1+s2;
    }



}

