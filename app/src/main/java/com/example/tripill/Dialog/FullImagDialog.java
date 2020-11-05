package com.example.tripill.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripill.R;

public class FullImagDialog extends AppCompatActivity {
    public Context context;

    public FullImagDialog(Context context){this.context = context;}
    public void callFunction(String pillname){
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dlg.setContentView(R.layout.dialog_image);

        ImageView pillphoto = (ImageView) dlg.findViewById(R.id.pillphoto);

        WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        if(pillname.equals("penzal")){
            pillphoto.setImageResource(R.drawable.penzal); //수정사항
        }else if(pillname.equals("tylenol")){
            pillphoto.setImageResource(R.drawable.tylenol); //수정사항
        }else if(pillname.equals("strepsil")){
            pillphoto.setImageResource(R.drawable.strepsil); //수정사항
        }else if(pillname.equals("minol")){
            pillphoto.setImageResource(R.drawable.minol); //수정사항
        }else if(pillname.equals("mucoj")){
            pillphoto.setImageResource(R.drawable.mucoj); //수정사항
        }else if(pillname.equals("mucos")){
            pillphoto.setImageResource(R.drawable.mucos); //수정사항
        }else if(pillname.equals("lirexpen")){
            pillphoto.setImageResource(R.drawable.lirexpen); //수정사항
        }else if(pillname.equals("whosidin")){
            pillphoto.setImageResource(R.drawable.whosidin); //수정사항
        }else if(pillname.equals("ru")){
            pillphoto.setImageResource(R.drawable.ru); //수정사항
        }else if(pillname.equals("sohwa")){
            pillphoto.setImageResource(R.drawable.sohwa); //수정사항
        }else if(pillname.equals("buscopan")){
            pillphoto.setImageResource(R.drawable.buscopan); //수정사항
        }else if(pillname.equals("mibo")){
            pillphoto.setImageResource(R.drawable.mibo); //수정사항
        }else if(pillname.equals("easyn")){
            pillphoto.setImageResource(R.drawable.easyn); //수정사항
        }

        dlg.show();
        dlg.setCancelable(true);
    }

}
