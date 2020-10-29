package com.example.tripill.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripill.Activity.AgeActivity;
import com.example.tripill.Adapter.ChoiceSymptomRecyclerAdapter;
import com.example.tripill.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ChoicedSymptomSlide extends BottomSheetDialogFragment {
    static final int SYMPTOMCODE = 1111;
    List<String> list;
    public String title;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_choiced_symptom_slide, container, false);

        TextView nextBtn = v.findViewById(R.id.nextBtn);
        ImageView backBtn = v.findViewById(R.id.backbtn);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_symptom);
        TextView SymptomArea = v.findViewById(R.id.SymptomArea);

        list = new ArrayList<>();
        list.add("두통");
        list.add("어지럼증");
        list.add("발열");

        ChoiceSymptomRecyclerAdapter adapter = new ChoiceSymptomRecyclerAdapter(list,getContext());   //세팅된 리스트를 어댑터로 보냄
        recyclerView.setAdapter(adapter);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AgeActivity.class);
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

            return v;

    }




/*    public void onActivityResult(LayoutInflater inflater, @Nullable ViewGroup container,int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        View v = inflater.inflate(R.layout.activity_choiced_symptom_slide, container, false);

        TextView SymptomArea = v.findViewById(R.id.SymptomArea);
        String symptomNameText = data.getStringExtra("symptomName");

        if(requestCode == SYMPTOMCODE && resultCode == Activity.RESULT_OK){ //결과값
            SymptomArea.setText(symptomNameText);
        }

    */

}



