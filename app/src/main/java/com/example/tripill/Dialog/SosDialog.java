package com.example.tripill.Dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tripill.Activity.PillRecommendActivity;
import com.example.tripill.R;

public class SosDialog extends AppCompatActivity {

    public Context context;

    public SosDialog(Context context){this.context = context;}
    public void callFunction(){
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dlg.setContentView(R.layout.dialog_alluse);

        WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dlg.show();
        dlg.setCancelable(false);

        final Button cancle = (Button) dlg.findViewById(R.id.canclebtn);
        final Button ok = (Button) dlg.findViewById(R.id.okbtn);
        final RelativeLayout layout = (RelativeLayout) dlg.findViewById(R.id.layout);
        final TextView text = (TextView) dlg.findViewById(R.id.text);

        text.setText("당신의 증상과 위치가\n응급메시지로 전송됩니다.\n정말 메시지를 전송할까요?");
        ok.setText("전송");

        layout.setClipToOutline(true);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PillRecommendActivity)context).intent();
                dlg.dismiss();
            }
        });

    }

}
