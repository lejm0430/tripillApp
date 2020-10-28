package com.example.tripill.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tripill.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import androidx.annotation.Nullable;

public class ChoicedSymptomSlideActivity extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_choiced_symptom_slide, container, false);

        final CheckBox Symptom1 = v.findViewById(R.id.Symptom1);
        final CheckBox Symptom2 = v.findViewById(R.id.Symptom2);
        final CheckBox Symptom3 = v.findViewById(R.id.Symptom3);
        TextView nextBtn = v.findViewById(R.id.nextBtn);
        ImageView backBtn = v.findViewById(R.id.backbtn);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Symptom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Symptom1.setSelected(true);
                mListener.onButtonClicked("Button 1 clicked");
                dismiss();
            }
        });

        Symptom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Symptom2.setSelected(true);
                mListener.onButtonClicked("Button 2 clicked");
                dismiss();
            }
        });

        Symptom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Symptom3.setSelected(true);
                mListener.onButtonClicked("Button 2 clicked");
                dismiss();
            }
        });

            return v;

    }





    public interface BottomSheetListener {
    void onButtonClicked(String text);
}




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

}