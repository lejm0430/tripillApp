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

import org.w3c.dom.Text;

public class BaseDialog extends Dialog {
    Activity activity;
    String canclecontents,contents, confirm;

    public BaseDialog(@NonNull Activity context){
        super(context);
        activity = context;
    }
    public void init(String contents, String canclecontents, String confirm) {
        this.contents = contents;
        this.canclecontents = canclecontents;
        this.confirm = confirm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_alluse,null);
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
        setCancelable(false);

        RelativeLayout realtive = (RelativeLayout)findViewById(R.id.layout);
        Button okbtn = (Button)findViewById(R.id.okbtn);
        Button canclebtn = (Button)findViewById(R.id.canclebtn);
        TextView text = (TextView) findViewById(R.id.text);



        if(contents == null){
            text.setText(canclecontents);
            okbtn.setText(confirm);
        }else{
            text.setText(contents);
            okbtn.setText(confirm);
            canclebtn.setVisibility(View.GONE);
        }

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contents != null){
                    dismiss();
                }else{
                    //((PillRecommendActivity)PillRecommendActivity.prcontext).messege();
                }

            }
        });
        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
