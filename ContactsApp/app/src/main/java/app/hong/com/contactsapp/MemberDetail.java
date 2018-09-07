package app.hong.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import static app.hong.com.contactsapp.Main.*;


public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);

        Context context = MemberDetail.this;

        Intent intent = this.getIntent();
        String seq = intent.getExtras().getString("seq");
        ItemSearch query = new ItemSearch(context);
        query.seq = seq;


        TextView name = findViewById(R.id.name);
        TextView phone = findViewById(R.id.phone);
        TextView email = findViewById(R.id.email);
        TextView addr = findViewById(R.id.addr);
        ImageView profile = findViewById(R.id.profile);


        Main.Member mem = (Main.Member) new Main.DetailService() {
            @Override
            public Object perform() {
                return query.execute();
            }
        }.perform();

        //int prof = getResources().getIdentifier(this.getPackageName() + ":drawable/" + mem.photo, null, null);
        profile.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(this.getPackageName() + ":drawable/" + mem.photo, null, null), context.getTheme()));

        name.setText(mem.name);
        phone.setText(mem.phone);
        email.setText(mem.email);
        addr.setText(mem.addr);

        findViewById(R.id.callBtn).setOnClickListener(
                (View v)->{ });
        findViewById(R.id.dialBtn).setOnClickListener(
                (View v)->{ });
        findViewById(R.id.smsBtn).setOnClickListener(
                (View v)->{ });
        findViewById(R.id.emailBtn).setOnClickListener(
                (View v)->{ });
        findViewById(R.id.albumBtn).setOnClickListener(
                (View v)->{ });
        findViewById(R.id.movieBtn).setOnClickListener(
                (View v)->{ });
        findViewById(R.id.mapBtn).setOnClickListener(
                (View v)->{ });
        findViewById(R.id.musicBtn).setOnClickListener(
                (View v)->{ });
        findViewById(R.id.updateBtn).setOnClickListener(
                (View v)->{
                    Intent intent1 = new Intent(context,MemberUpdate.class);
                    intent1.putExtra("spec",mem.seq+"/"+mem.name+"/"+mem.pass+"/"
                            +mem.phone+"/"+mem.email+"/"+mem.addr+"/"+mem.photo);
                    startActivity(intent1);
                }
        );
        findViewById(R.id.listBtn).setOnClickListener(
                (View v)->{ });


    }
    private class DetailQuery extends Main.QueryFactory {
        SQLiteOpenHelper helper;

        public DetailQuery(Context _this) {
            super(_this);
            helper = new Main.SQLiteHelper(_this);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }

    private class ItemSearch extends DetailQuery {
        String seq;

        public ItemSearch(Context _this) {
            super(_this);
        }

        public Main.Member execute() {
            Main.Member m = null;
            Cursor cursor = this.getDatabase().rawQuery(String.format(
                    "select * from member where %s " +
                            "like '%s' ", MEMSEQ, seq), null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    m = new Main.Member();
                    m.seq = cursor.getInt(cursor.getColumnIndex(MEMSEQ));
                    m.name = cursor.getString(cursor.getColumnIndex(MEMNAME));
                    m.addr = cursor.getString(cursor.getColumnIndex(MEMADDR));
                    m.email = cursor.getString(cursor.getColumnIndex(MEMEMAIL));
                    m.phone = cursor.getString(cursor.getColumnIndex(MEMPHONE));
                    m.photo = cursor.getString(cursor.getColumnIndex(MEMPHOTO));
                    m.pass = cursor.getString(cursor.getColumnIndex(MEMPASS));
                }

            }
            return m;
        }
    }
}



