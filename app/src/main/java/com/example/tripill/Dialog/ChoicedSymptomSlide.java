package com.example.tripill.Dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Activity.AgeActivity;
import com.example.tripill.Adapter.ChoiceSymptomRecyclerAdapter;
import com.example.tripill.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ChoicedSymptomSlide extends BottomSheetDialogFragment {

    List<String> list;
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

        TextView nextBtn=v.findViewById(R.id.nextBtn);
        TextView TwoChoiceMungu=v.findViewById(R.id.TwoChoiceMungu);
        ImageView backBtn=v.findViewById(R.id.backbtn);
        RecyclerView recyclerView=v.findViewById(R.id.recycler_symptom);
        LinearLayout bottom_sheet = v.findViewById(R.id.bottom_sheet);
        TextView SymptomArea=v.findViewById(R.id.SymptomArea);




        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AgeActivity.class);
                intent.putExtra("part",title);
                getContext().startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        SymptomArea.setText(title);

        list=new ArrayList<>();

        if(title.contains("head")){
            TwoChoiceMungu.setVisibility((View.VISIBLE));
            list.add("두통");
            list.add("어지럼증");
            list.add("발열");
        }else if(title.contains("neck")){
            TwoChoiceMungu.setVisibility((View.VISIBLE));
            list.add("인후통");
            list.add("기침");
        }else if(title.contains("stomach")){
            TwoChoiceMungu.setVisibility((View.VISIBLE));
            list.add("소화불량");
            list.add("복통");
            list.add("생리통");
        }else if(title.contains("muscle pain")){
            list.add("근육통");
        }else if(title.contains("wound")){
            list.add("상처");
        }else if(title.contains("burn")){
            list.add("화상");
        }else if(title.contains("hangover")){
            list.add("숙취");
        }


        ChoiceSymptomRecyclerAdapter adapter=new ChoiceSymptomRecyclerAdapter(list, getContext());   //세팅된 리스트를 어댑터로 보냄
        recyclerView.setAdapter(adapter);

        return v;

    }
}

