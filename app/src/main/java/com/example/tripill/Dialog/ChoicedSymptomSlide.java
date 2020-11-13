package com.example.tripill.Dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tripill.Activity.AgeActivity;
import com.example.tripill.Activity.MainActivity;
import com.example.tripill.Adapter.ChoiceSymptomRecyclerAdapter;
import com.example.tripill.DataBase.SymptomList;
import com.example.tripill.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.tripill.Props.INTE_SELECT_PART;
import static com.example.tripill.Props.INTE_SELECT_SYMPTOM1;
import static com.example.tripill.Props.INTE_SELECT_SYMPTOM1_KR;
import static com.example.tripill.Props.INTE_SELECT_SYMPTOM2;
import static com.example.tripill.Props.INTE_SELECT_SYMPTOM2_KR;
import static com.example.tripill.Props.INTE_SYMPTOM_SUM;
import static com.example.tripill.Props.REQUEST_CODE;

public class ChoicedSymptomSlide extends BottomSheetDialogFragment {

    ArrayList<SymptomList> list = new ArrayList();
    ArrayList<String> symptomlist;

    public String title;



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode != Activity.RESULT_OK){
                return;
            }
            dismiss();
        }
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



        SymptomArea.setText(title);

        list=new ArrayList<>();

        if(title.contains(getString(R.string.head))){
            TwoChoiceMungu.setVisibility((View.VISIBLE));

            list.add(new SymptomList(getString(R.string.headache), 1 ,"두통"));
            list.add(new SymptomList(getString(R.string.dizziness),2, "어지럼증"));
            list.add(new SymptomList(getString(R.string.fever),4, "발열"));
        }else if(title.contains(getString(R.string.neck))){
            TwoChoiceMungu.setVisibility((View.VISIBLE));

            list.add(new SymptomList(getString(R.string.sore_throat),7, "인후통"));
            list.add(new SymptomList(getString(R.string.cough),8, "기침"));
        }else if(title.contains(getString(R.string.abdominal))){

            TwoChoiceMungu.setVisibility((View.VISIBLE));

            list.add(new SymptomList(getString(R.string.indigestion),40, "소화불량"));
            list.add(new SymptomList(getString(R.string.colic),50, "복통"));
            list.add(new SymptomList(getString(R.string.period_pains),100, "생리통"));

        }

        symptomlist = new ArrayList<String>();


        ChoiceSymptomRecyclerAdapter adapter=new ChoiceSymptomRecyclerAdapter(list, getContext());   //세팅된 리스트를 어댑터로 보냄
        recyclerView.setAdapter(adapter);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AgeActivity.class);
                intent.putExtra(INTE_SELECT_PART,title);

                if (adapter.getSelected_list().isEmpty()){
                   BaseDialog dialog = new BaseDialog(getActivity());
                    String contents,confirm;
                    contents = getString(R.string.select_symptom);
                    confirm = getString(R.string.confirm);
                    dialog.init(contents,null,confirm);
                    dialog.show();


                } else if(adapter.getSelected_list().size() == 1){
                        intent.putExtra(INTE_SELECT_SYMPTOM1,adapter.getSelected_list().get(0).getSymptom());
                        intent.putExtra(INTE_SELECT_SYMPTOM1_KR,adapter.getSelected_list().get(0).getSymptomkr());
                        Log.e("TESTslide",adapter.getSelected_list().get(0).getSymptomkr());
                        intent.putExtra(INTE_SYMPTOM_SUM,Integer.toString(adapter.getSelected_list().get(0).getScore()));
                    startActivityForResult(intent,REQUEST_CODE);

                } else {
                    intent.putExtra(INTE_SELECT_SYMPTOM1,adapter.getSelected_list().get(0).getSymptom());
                    intent.putExtra(INTE_SELECT_SYMPTOM1_KR,adapter.getSelected_list().get(0).getSymptomkr());
                    intent.putExtra(INTE_SELECT_SYMPTOM2,adapter.getSelected_list().get(1).getSymptom());
                    intent.putExtra(INTE_SELECT_SYMPTOM2_KR,adapter.getSelected_list().get(1).getSymptomkr());
                    Log.e("TESTslidesecond",adapter.getSelected_list().get(0).getSymptomkr()+adapter.getSelected_list().get(1).getSymptomkr());
                    intent.putExtra(INTE_SYMPTOM_SUM, Integer.toString(sum(adapter.getSelected_list().get(0).getScore(),adapter.getSelected_list().get(1).getScore())));
                    startActivityForResult(intent,REQUEST_CODE);
                }


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

