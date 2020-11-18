package com.mobileapp.tripill.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.mobileapp.tripill.R;

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
            pillphoto.setImageResource(R.drawable.penzal);
        } else if (pillname.equals("Tylenol")) {
            pillphoto.setImageResource(R.drawable.tylenol);
        } else if (pillname.equals("Strepsil")) {
            pillphoto.setImageResource(R.drawable.strepsil);
        } else if (pillname.equals("Minol-F Troche")) {
            pillphoto.setImageResource(R.drawable.minol);
        } else if (pillname.equals("Mucopect Tab")) {
            pillphoto.setImageResource(R.drawable.mucoj);
        } else if (pillname.equals("Mucopect Syrup")) {
            pillphoto.setImageResource(R.drawable.mucos);
        } else if (pillname.equals("Lirexpen Tab")) {
            pillphoto.setImageResource(R.drawable.lirexpen);
        } else if (pillname.equals("Fucidin Ointment")) {
            pillphoto.setImageResource(R.drawable.whosidin);
        } else if (pillname.equals("RU-21")) {
            pillphoto.setImageResource(R.drawable.ru);
        } else if (pillname.equals("Gas Whal Myung Su")) {
            pillphoto.setImageResource(R.drawable.sohwa);
        } else if (pillname.equals("Buscopan Plus Tab")) {
            pillphoto.setImageResource(R.drawable.buscopan);
        } else if (pillname.equals("Mebo Ointment")) {
            pillphoto.setImageResource(R.drawable.mibo);
        } else if (pillname.equals("EZN 6 Eve")) {
            pillphoto.setImageResource(R.drawable.easyn);
        }


    }
}
