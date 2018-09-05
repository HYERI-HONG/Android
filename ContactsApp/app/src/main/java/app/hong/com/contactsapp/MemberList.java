package app.hong.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        Context context = MemberList.this;
        findViewById(R.id.list_goAdd).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(context,MemberAdd.class));
                }
        );
        findViewById(R.id.list_goDetail).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(context,MemberDetail.class));
                }
        );
        findViewById(R.id.list_goDelete).setOnClickListener(
                (View v)->{

                }
        );
    }
}
