package app.hong.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static app.hong.com.contactsapp.Main.MEMTAB;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        Context context = MemberList.this;

        findViewById(R.id.check).setOnClickListener(
                (View v)->{
                    GetList getList = new GetList(context);
                    ArrayList<?> list = new Main.ListService(){
                        @Override
                        public ArrayList<?> perform() {
                            return getList.execute();
                        }
                    }.perform();
                    Toast.makeText(context,String.valueOf(list.size()),Toast.LENGTH_LONG).show();
                }
        );



    }
    private class ListQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;
        public ListQuery(Context context) {
            super(context);
            helper = new Main.SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class GetList extends ListQuery{
        ArrayList<Main.Member> list = new ArrayList<>();
        Main.Member member = null;
        public GetList(Context context) {
            super(context);
        }
        public ArrayList<Main.Member> execute(){
            Cursor cursor = super.getDatabase().rawQuery(
                    String.format("SELECT * FROM %s",MEMTAB),null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    member = new Main.Member();
                    member.name=cursor.getString(1);
                    member.pass=cursor.getString(2);
                    member.email=cursor.getString(3);
                    member.phone=cursor.getString(4);
                    member.addr=cursor.getString(5);
                    member.photo=cursor.getString(6);
                    list.add(member);
                }
            }
            return list;
        }

    }
}
