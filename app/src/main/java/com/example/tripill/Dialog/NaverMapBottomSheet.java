package com.example.tripill.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tripill.Adapter.pharmacyList;
import com.example.tripill.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class NaverMapBottomSheet extends BottomSheetDialogFragment {
    ArrayList<pharmacyList> pharmacyLists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);


    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.activity_naver_map_bottom_sheet, container, false);

        TextView pharmacyName_kr = v.findViewById(R.id.pharmacyName_kr);
        TextView pharmacyName_en = v.findViewById(R.id.pharmacyName_en);
        RelativeLayout findroadBtn = v.findViewById(R.id.findroadBtn);

        pharmacyLists = new ArrayList<pharmacyList>();

        pharmacyLists.add(new pharmacyList("온수 약국","onsu pharmacy"));

        return v;
    }

}