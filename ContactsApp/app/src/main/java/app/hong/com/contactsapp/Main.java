package app.hong.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Context context = Main.this;
        findViewById(R.id.main_start).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(context,MemberLogin.class));
                }
        );
        findViewById(R.id.main_creatdb).setOnClickListener(
                (View v)->{
                    SQLiteHelper helper = new SQLiteHelper(context);
                }
        );
    }
    //------------bean---------------//
    static class Member{int seq;String name,pass,email,photo,addr,phone;}

    //------------service---------------//
    static interface  StatusService{public void perform();}
    static interface  ListService{public List<?> perform();}
    static interface  DetailService{public Object perform();}
    static String DBNAME="hongdb";
    static String MEMTAB="MEMBER";
    static String MEMSEQ="SEQ";
    static String MEMNAME="NAME";
    static String MEMPASS="PASS";
    static String MEMEMAIL="EMAIL";
    static String MEMPHOTO="PHOTO";
    static String MEMADDR="ADDR";
    static String MEMPHONE="PHONE";

    static abstract class QueryFactory{
        Context context;

        public QueryFactory(Context context) {
            this.context = context;
        }
        public abstract SQLiteDatabase getDatabase();
    }

    //SQLite 만들기
    static class SQLiteHelper extends SQLiteOpenHelper{ //Ctrl+enter - Import , Alt+Insert - construct
        public SQLiteHelper(Context context) {
            super(context,DBNAME, null,1);//null - 내장되어있는 factory을 쓰겠다
            this.getWritableDatabase();
        }
        @Override
        public void onCreate(SQLiteDatabase db) {//DB실행
            String sql = String.format(
                    "CREATE TABLE IF NOT EXISTS %s " +
                            "(%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT)"
                    ,MEMTAB,MEMSEQ,MEMNAME,MEMPASS,MEMEMAIL,MEMPHONE,MEMADDR,MEMPHOTO);
            db.execSQL(sql);

            for(int i=0;i<5;i++){
                db.execSQL(String.format("INSERT INTO %s (%s ,%s ,%s ,%s ,%s ,%s ) " +
                        "VALUES('%s' ,'%s' ,'%s' ,'%s' ,'%s' ,'%s')"
                        ,MEMTAB,MEMNAME,MEMPASS,MEMEMAIL,MEMPHONE,MEMADDR,MEMPHOTO,
                        "홍길동"+i,"1","hong"+i+"@test.com","010-1234-567"+i,"김포한강"+i+"로","hong"+i+".jpg"));
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MEMTAB);
            onCreate(db);

        }
    }
}
