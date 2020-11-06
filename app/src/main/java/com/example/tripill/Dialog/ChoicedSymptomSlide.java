package com.example.tripill.Dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChoicedSymptomSlide extends BottomSheetDialogFragment {

    ArrayList<SymptomList> list = new ArrayList();
    ArrayList<Integer> scorelist;
    ArrayList<String> symptomlist;

    public String title;

    private static final int REQUEST_CODE = 1001;

    ChoicedSymptomSlide choicedSymptomSlide;

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

        if(title.contains("head")){
            TwoChoiceMungu.setVisibility((View.VISIBLE));

            list.add(new SymptomList("두통", 1));
            list.add(new SymptomList("어지럼증",2));
            list.add(new SymptomList("발열",4));
        }else if(title.contains("neck")){
            TwoChoiceMungu.setVisibility((View.VISIBLE));

            list.add(new SymptomList("인후통",7));
            list.add(new SymptomList("기침",8));
        }else if(title.contains("stomach")){

            TwoChoiceMungu.setVisibility((View.VISIBLE));

            list.add(new SymptomList("소화불량",40));
            list.add(new SymptomList("복통",50));
            list.add(new SymptomList("생리통",100));

        }

        symptomlist = new ArrayList<String>();


        ChoiceSymptomRecyclerAdapter adapter=new ChoiceSymptomRecyclerAdapter(list, getContext());   //세팅된 리스트를 어댑터로 보냄
        recyclerView.setAdapter(adapter);

        NotChoiceDialog notChoiceDialog = new NotChoiceDialog(getContext());

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AgeActivity.class);
                intent.putExtra("part",title);

                if (adapter.getSelected_list().isEmpty()){
                    notChoiceDialog.callFunction();

                } else if(adapter.getSelected_list().size() == 1){
                        intent.putExtra("s1",adapter.getSelected_list().get(0).getSymptom());
                        intent.putExtra("sum",Integer.toString(adapter.getSelected_list().get(0).getScore()));
                    startActivityForResult(intent,REQUEST_CODE);

                } else {
                    intent.putExtra("s1",adapter.getSelected_list().get(0).getSymptom());
                    intent.putExtra("s2",adapter.getSelected_list().get(1).getSymptom());  // TODO: 2020-11-04 증상 글자
                    intent.putExtra("sum", Integer.toString(sum(adapter.getSelected_list().get(0).getScore(),adapter.getSelected_list().get(1).getScore())));
                    Log.d("TAG","sum"+ sum(adapter.getSelected_list().get(0).getScore(),adapter.getSelected_list().get(1).getScore()));
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

