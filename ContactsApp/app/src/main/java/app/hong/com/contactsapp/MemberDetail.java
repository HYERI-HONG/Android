package app.hong.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        Context context = MemberDetail.this;
        findViewById(R.id.detail_goList).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(context,MemberList.class));
                }
        );
        findViewById(R.id.detail_goUpdate).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(context,MemberUpdate.class));
                }
        );
    }
}
