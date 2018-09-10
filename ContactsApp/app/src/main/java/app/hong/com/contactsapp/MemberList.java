package app.hong.com.contactsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import static app.hong.com.contactsapp.Main.*;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        Context context = MemberList.this;
        ItemList query = new ItemList(context);
        ItemDelete delete = new ItemDelete(context);
        ListView memberList = findViewById(R.id.list_memberList);
        memberList.setAdapter(new MemberAdapter(context,(ArrayList<Main.Member>) new Main.ListService(){
            @Override
            public List<?> perform() {
                return query.execute();
            }
        }.perform()
        ));
        memberList.setOnItemClickListener(
                (AdapterView<?> p, View v, int i, long l)->{
                    Intent intent = new Intent(context,MemberDetail.class);
                    Main.Member m = (Main.Member) memberList.getItemAtPosition(i);
                    intent.putExtra("seq",m.seq+"");
                    startActivity(intent);
                }
        );
        memberList.setOnItemLongClickListener(
                (AdapterView<?> p, View v, int i, long l)->{
                    Main.Member m= (Main.Member)memberList.getItemAtPosition(i);
                    new AlertDialog.Builder(context)
                            .setTitle("DELETE").setMessage("정말로 삭제 할까요?").setPositiveButton(
                            android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    new Main.StatusService() {
                                        @Override
                                        public void perform() {
                                            Log.d("들어옴, 삭제할 seq : ",m.seq+"");
                                            delete.seq=m.seq+"";
                                            delete.execute();
                                            startActivity(new Intent(context,MemberList.class));
                                        }
                                    }.perform();
                                    Toast.makeText(context,"삭제 실행",Toast.LENGTH_LONG).show();
                                }
                            }
                    ).setNegativeButton(android.R.string.no,
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context,"삭제 취소",Toast.LENGTH_LONG).show();
                        }
                    }).show();
                    return true;
                }
        );
        findViewById(R.id.list_goAdd).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(context, MemberAdd.class));
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
    private class ItemList extends ListQuery {
        public ItemList(Context context) {
            super(context);
        }

        public ArrayList<Main.Member> execute() {
            ArrayList<Main.Member> list = new ArrayList<>();
            Main.Member member = null;
            Cursor cursor = this.getDatabase().rawQuery(
                    String.format("SELECT * FROM %s", MEMTAB), null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    member = new Main.Member();
                    member.seq = cursor.getInt(cursor.getColumnIndex(MEMSEQ));
                    member.name = cursor.getString(cursor.getColumnIndex(MEMNAME));
                    member.pass = cursor.getString(cursor.getColumnIndex(MEMPASS));
                    member.email = cursor.getString(cursor.getColumnIndex(MEMEMAIL));
                    member.phone = cursor.getString(cursor.getColumnIndex(MEMPHONE));
                    member.addr = cursor.getString(cursor.getColumnIndex(MEMADDR));
                    member.photo = cursor.getString(cursor.getColumnIndex(MEMPHOTO));
                    list.add(member);
                }
                Log.d("등록 회원 확인", "" + list.size());
            } else {
                Log.d("등록된 회원이", "없습니다.");

            }
            return list;
        }
    }
    private class MemberAdapter extends BaseAdapter {
        ArrayList<Main.Member> list;
        LayoutInflater inflater;
        Context context;

        public MemberAdapter(Context context,ArrayList<Main.Member> list) {
            this.list = list;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null){
                v=inflater.inflate(R.layout.member_item,null);
                holder = new ViewHolder();
                holder.profile = v.findViewById(R.id.profile);
                holder.name=v.findViewById(R.id.item_name);
                holder.phone=v.findViewById(R.id.item_phone);
                v.setTag(holder);

            }else{
                holder =(ViewHolder) v.getTag();
            }
            ItemProfile query = new ItemProfile(context);
            query.seq = list.get(i).seq+"";
            holder.profile.setImageDrawable(
                    getResources().getDrawable(
                            getResources().getIdentifier(
                                    context.getPackageName()+":drawable/"
                                            + (new DetailService() {
                                        @Override
                                        public Object perform() {
                                            return query.execute();
                                        }
                                    }.perform())
                                    , null, null
                            ), context.getTheme()
                    )
            );
            holder.name.setText(list.get(i).name);
            holder.phone.setText(list.get(i).phone);
            return v;
        }
    }
    static class ViewHolder{
        ImageView profile;
        TextView name,phone;

    }
    private class DeleteQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;

        public DeleteQuery(Context context) {
            super(context);
            helper = new Main.SQLiteHelper(context);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class ItemDelete extends DeleteQuery{
        String seq;
        public ItemDelete(Context context) {
            super(context);
        }
        public void execute(){
            getDatabase().execSQL(
                    String.format(
                            " DELETE FROM MEMBER " +
                                    " WHERE SEQ LIKE '%s'",seq));
        }
    }

    private class MemberProfileQuery extends QueryFactory{
        SQLiteOpenHelper helper;
        public MemberProfileQuery(Context context) {
            super(context);
            helper = new SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemProfile extends MemberProfileQuery{
        String seq;
        public ItemProfile(Context _this) {
            super(_this);
        }
        public String execute(){
            Cursor c = getDatabase()
                    .rawQuery(String.format(
                            " SELECT %s FROM %s WHERE %s LIKE '%s' "
                            , MEMPHOTO, MEMTAB, MEMSEQ, seq),null);
            String result = "";
            if(c != null){
                if(c.moveToNext()){
                    result = c.getString(c.getColumnIndex(MEMPHOTO));
                }
            }
            return result;
        }
    }
}

