package com.example.tripill;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AgeDialog extends AppCompatActivity {
    public Context context;

    public AgeDialog(Context context) {
        this.context = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void callFunction() {
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dlg.setContentView(R.layout.allusedialog);

        WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dlg.show();
        dlg.setCancelable(false);

        final RelativeLayout layout = (RelativeLayout) dlg.findViewById(R.id.layout);
        final Button okbtn = (Button) dlg.findViewById(R.id.okbtn);


        layout.setClipToOutline(true);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
    }
}
