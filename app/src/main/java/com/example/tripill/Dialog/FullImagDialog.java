package com.example.tripill.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripill.R;

public class FullImagDialog extends AppCompatActivity {
    public Context context;

    public FullImagDialog(Context context){this.context = context;}
    public void callFunction(Integer i){
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dlg.setContentView(R.layout.dialog_image);

        ImageView pillphoto = (ImageView) dlg.findViewById(R.id.pillphoto);
        pillphoto.setImageResource(R.drawable.mibo); //수정사항

        WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dlg.show();
        dlg.setCancelable(true);
    }

}
