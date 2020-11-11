package com.example.tripill.Dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tripill.Activity.PharmacyMap;
import com.example.tripill.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import androidx.annotation.Nullable;

public class NaverMapBottomSheet extends BottomSheetDialogFragment {

    public String name;
    public String Snippet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        View v=inflater.inflate(R.layout.activity_naver_map_bottom_sheet, container, false);

        TextView pharmacyName_kr = v.findViewById(R.id.pharmacyName_kr);
        TextView pharmacyName_en = v.findViewById(R.id.pharmacyName_en);
        TextView findroadBtn = v.findViewById(R.id.findroadBtn);


        pharmacyName_kr.setText(name);
        pharmacyName_en.setText("phamacy");
        //각국언어




        findroadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ((PharmacyMap)PharmacyMap.context_bottom).intent1();


                Uri gmmIntentUri = Uri.parse("google.navigation:q="+Snippet+"&mode=w");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        return v;
    }

}

