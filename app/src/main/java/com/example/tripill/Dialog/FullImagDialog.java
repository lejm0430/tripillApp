package com.example.tripill.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripill.Activity.PillRecommendActivity;
import com.example.tripill.R;

public class FullImagDialog extends Dialog {
    Activity activity;
    String pillname;

    public FullImagDialog(@NonNull Activity context) {
        super(context);
        activity = context;
    }

    public void init(String pillname) {
        this.pillname = pillname;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_image, null);
        Rect displayRectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        layout.setBackgroundColor(activity.getColor(android.R.color.transparent));
        layout.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (displayRectangle.width() * 0.9f);
        getWindow().setAttributes(params);
        setContentView(layout);
        setCancelable(true);

        ImageView pillphoto = (ImageView) findViewById(R.id.pillphoto);

        if (pillname.equals("Penzal")) {
            pillphoto.setImageResource(R.drawable.penzal); //수정사항
        } else if (pillname.equals("Tylenol")) {
            pillphoto.setImageResource(R.drawable.tylenol); //수정사항
        } else if (pillname.equals("Strepsil")) {
            pillphoto.setImageResource(R.drawable.strepsil); //수정사항
        } else if (pillname.equals("Minol-F Troche")) {
            pillphoto.setImageResource(R.drawable.minol); //수정사항
        } else if (pillname.equals("Mucopect Tab")) {
            pillphoto.setImageResource(R.drawable.mucoj); //수정사항
        } else if (pillname.equals("Mucopect Syrup")) {
            pillphoto.setImageResource(R.drawable.mucos); //수정사항
        } else if (pillname.equals("Lirexpen Tab")) {
            pillphoto.setImageResource(R.drawable.lirexpen); //수정사항
        } else if (pillname.equals("Fucidin Ointment")) {
            pillphoto.setImageResource(R.drawable.whosidin); //수정사항
        } else if (pillname.equals("RU-21")) {
            pillphoto.setImageResource(R.drawable.ru); //수정사항
        } else if (pillname.equals("Gas Whal Myung Su")) {
            pillphoto.setImageResource(R.drawable.sohwa); //수정사항
        } else if (pillname.equals("Buscopan Plus Tab")) {
            pillphoto.setImageResource(R.drawable.buscopan); //수정사항
        } else if (pillname.equals("Mebo Ointment")) {
            pillphoto.setImageResource(R.drawable.mibo); //수정사항
        } else if (pillname.equals("EZN 6 Eve")) {
            pillphoto.setImageResource(R.drawable.easyn); //수정사항
        }


    }
}
//    public Context context;
//
//    public FullImagDialog(Context context){this.context = context;}
//    public void callFunction(String pillname){
//        final Dialog dlg = new Dialog(context);
//
//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        dlg.setContentView(R.layout.dialog_image);
//
//        ImageView pillphoto = (ImageView) dlg.findViewById(R.id.pillphoto);
//
//        WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//        if(pillname.equals("Penzal")){
//            pillphoto.setImageResource(R.drawable.penzal); //수정사항
//        }else if(pillname.equals("Tylenol")){
//            pillphoto.setImageResource(R.drawable.tylenol); //수정사항
//        }else if(pillname.equals("Strepsil")){
//            pillphoto.setImageResource(R.drawable.strepsil); //수정사항
//        }else if(pillname.equals("Minol-F Troche")){
//            pillphoto.setImageResource(R.drawable.minol); //수정사항
//        }else if(pillname.equals("Mucopect Tab")){
//            pillphoto.setImageResource(R.drawable.mucoj); //수정사항
//        }else if(pillname.equals("Mucopect Syrup")){
//            pillphoto.setImageResource(R.drawable.mucos); //수정사항
//        }else if(pillname.equals("Lirexpen Tab")){
//            pillphoto.setImageResource(R.drawable.lirexpen); //수정사항
//        }else if(pillname.equals("Fucidin Ointment")){
//            pillphoto.setImageResource(R.drawable.whosidin); //수정사항
//        }else if(pillname.equals("RU-21")){
//            pillphoto.setImageResource(R.drawable.ru); //수정사항
//        }else if(pillname.equals("Gas Whal Myung Su")){
//            pillphoto.setImageResource(R.drawable.sohwa); //수정사항
//        }else if(pillname.equals("Buscopan Plus Tab")){
//            pillphoto.setImageResource(R.drawable.buscopan); //수정사항
//        }else if(pillname.equals("Mebo Ointment")){
//            pillphoto.setImageResource(R.drawable.mibo); //수정사항
//        }else if(pillname.equals("EZN 6 Eve")){
//            pillphoto.setImageResource(R.drawable.easyn); //수정사항
//        }
//
//        dlg.show();
//        dlg.setCancelable(true);
//    }
