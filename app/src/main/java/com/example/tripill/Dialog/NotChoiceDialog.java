package com.example.tripill.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripill.R;

import org.w3c.dom.Text;

public class NotChoiceDialog extends AppCompatActivity {
    public Context context;

    public NotChoiceDialog(Context context){this.context = context;}
    public void callFunction(){

        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dlg.setContentView(R.layout.dialog_alluse);



        WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;


        final RelativeLayout layout = (RelativeLayout) dlg.findViewById(R.id.layout);
        final Button okbtn = (Button) dlg.findViewById(R.id.okbtn);
        final Button canclebtn = (Button) dlg.findViewById(R.id.canclebtn);
        final TextView text = dlg.findViewById(R.id.text);


        text.setText(R.string.select_symptom);

        canclebtn.setVisibility(View.GONE);

        dlg.show();
        dlg.setCancelable(true);

        layout.setClipToOutline(true);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
    }
}
