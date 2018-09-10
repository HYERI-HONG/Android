package app.hong.com.contactsapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

public class PhoneUtil {
    private Context context;
    private Activity act;
    private String phoneNum;

    public PhoneUtil(Context context, Activity act){
        this.context=context;
        this.act=act;
    }
    public void dial(){
        context.startActivity(
                new Intent(
                        Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNum)));
    }
    public void call(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.CALL_PHONE},
                    2);
        }else{
            context.startActivity(
                    new Intent(
                            Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum)));
        }
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
