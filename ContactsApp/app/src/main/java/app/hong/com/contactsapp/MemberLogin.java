package app.hong.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static app.hong.com.contactsapp.Main.*;



public class MemberLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_login);
        Context context = MemberLogin.this;


        findViewById(R.id.login_submit).setOnClickListener(
                (View v)->{
                    ItemExist exist = new ItemExist(context);
                    EditText x = findViewById(R.id.login_id);
                    EditText y = findViewById(R.id.login_pass);
                    exist.id = x.getText().toString();
                    exist.pw = y.getText().toString();
                    new Main.StatusService(){
                        @Override
                        public void perform() {
                            if(exist.execute()){
                                Toast.makeText(context,"로그인 성공",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(context,MemberList.class));
                            }else{
                                Toast.makeText(context,"로그인 실패",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(context,MemberLogin.class));
                            }
                        }
                    }.perform();
                }
        );
    }
    private class LoginQuery extends Main.QueryFactory {
        SQLiteOpenHelper helper;
        public LoginQuery(Context context) {
            super(context);
            helper = new Main.SQLiteHelper(context);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemExist extends LoginQuery{
        String id,pw;
        public ItemExist(Context context) {
            super(context);
        }
        public boolean execute(){
            return super.getDatabase().rawQuery(String.format("SELECT * FROM %s " +
                    "WHERE %s LIKE '%s' " +
                    "AND %s LIKE '%s' "
                    ,MEMTAB,MEMSEQ,id,MEMPASS,pw),null).moveToNext();
        }
    }

}
