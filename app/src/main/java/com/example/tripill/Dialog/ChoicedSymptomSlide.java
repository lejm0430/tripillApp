package com.example.tripill.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripill.Activity.AgeActivity;
import com.example.tripill.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class ChoicedSymptomSlide extends BottomSheetDialogFragment {

/*    List<String> list;
    RecyclerView.LayoutManager layoutManager;
    ChoiceSymptomRecyclerAdapter adapter;*/

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_choiced_symptom_slide, container, false);

        TextView nextBtn = v.findViewById(R.id.nextBtn);
        ImageView backBtn = v.findViewById(R.id.backbtn);
        CheckBox Symptom1 = v.findViewById(R.id.Symptom1);
        CheckBox Symptom2 = v.findViewById(R.id.Symptom2);
        CheckBox Symptom3 = v.findViewById(R.id.Symptom3);

/*        RecyclerView recyclerView = v.findViewById(R.id.recycler_symptom);

//        Context context_re = v.getContext();  //권한부여
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ChoiceSymptomRecyclerAdapter adapter = new ChoiceSymptomRecyclerAdapter(list,this);
        recyclerView.setAdapter(adapter);

        list = new ArrayList<>();
        list.add("두통");
        list.add("어지럼증");
        list.add("발열");*/

        Symptom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Symptom1.setSelected(true);
            }
        });

        Symptom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Symptom2.setSelected(true);
            }
        });

        Symptom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Symptom3.setSelected(true);
            }
        });



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = v.getContext();
                Intent intent = new Intent(getContext(), AgeActivity.class);
                context.startActivity(intent);
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



    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

            View view = getView();
            view.post(() -> {
                View parent = (View) view.getParent();
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
                bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
                ((View) bottomSheet.getParent()).setBackgroundColor(Color.TRANSPARENT);

            });
        }
    }



}