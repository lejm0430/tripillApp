package com.example.tripill.Dialog;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tripill.Activity.PillRecommendActivity;
import com.example.tripill.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SosDialog extends AppCompatActivity {

    public Context context;

    private static final int PERMISSIONS_REQUEST_CODE=200;
    String[] REQUIRED_PERMISSIONS={Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소


    public SosDialog(Context context) {
        this.context=context;
    }




    public void callFunction() {

        final Dialog dlg=new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dlg.setContentView(R.layout.dialog_alluse);

        WindowManager.LayoutParams params=dlg.getWindow().getAttributes();
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;

        dlg.show();
        dlg.setCancelable(true);

        final Button cancle=(Button) dlg.findViewById(R.id.canclebtn);
        final Button ok=(Button) dlg.findViewById(R.id.okbtn);
        final RelativeLayout layout=(RelativeLayout) dlg.findViewById(R.id.layout);
        final TextView text=(TextView) dlg.findViewById(R.id.text);

        text.setText(R.string.sos);
        ok.setText(R.string.send);

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


                dlg.dismiss();

                check();

                ((PillRecommendActivity) context).intent();

            }
        });

    }




    public void check(){
        int hasFineLocationPermission=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) { //퍼미션 허용 O


        } else {  //퍼미션 허용

            // 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 퍼미션 허용됨. 요청 결과는 onRequestPermissionResult에서 수신
                ActivityCompat.requestPermissions(SosDialog.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);

            } else {
                // 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로
                // 요청 결과는 onRequestPermissionResult에서 수신
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }


        }
    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }

            } //for
        }

    }






}
