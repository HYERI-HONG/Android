package app.hong.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import static app.hong.com.contactsapp.Main.*;

public class MemberAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_add);
        final Context context = MemberAdd.this;
        EditText name = findViewById(R.id.add_name);
        EditText email = findViewById(R.id.add_email);
        EditText phone = findViewById(R.id.add_phone);
        EditText addr = findViewById(R.id.add_addr);
        EditText photo = findViewById(R.id.add_profileName);
        ImageView img = findViewById(R.id.add_profile);

        Button imgBtn = findViewById(R.id.add_imgBtn);
        imgBtn.setOnClickListener(
                (View v)->{
                    img.setImageDrawable(getResources().getDrawable(
                            getResources().getIdentifier(
                                    this.getPackageName()+":drawable/"+photo.getText().toString(),
                                    null, null
                            ), context.getTheme()));
                    img.setTag(photo.getText().toString());
                }
        );
        findViewById(R.id.add_add).setOnClickListener(
                (View v)->{
                    ItemAdd query = new ItemAdd(context);
                    query.m.phone = ((phone.getText()+"").equals(""))? "" : phone.getText()+"";
                    query.m.email = ((email.getText()+"").equals(""))? "" : email.getText()+"";
                    query.m.addr = ((addr.getText()+"").equals(""))? "" : addr.getText()+"";
                    query.m.name = ((name.getText()+"").equals(""))? "" : name.getText()+"";
                    query.m.photo = (img.getTag().toString().equals(""))? "profile_1" :img.getTag().toString();
                    new Main.StatusService() {
                        @Override
                        public void perform() {
                            query.execute();
                        }
                    }.perform();
                    startActivity(new Intent(context, MemberList.class));
                }
        );
        findViewById(R.id.add_goList).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(context, MemberList.class));
                }
        );
    }
    private class MemberInsertQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;
        public MemberInsertQuery(Context context) {
            super(context);
            helper = new Main.SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class ItemAdd extends MemberInsertQuery{
        Main.Member m;
        public ItemAdd(Context context) {
            super(context);
            m = new Main.Member();
        }
        public void execute(){
            getDatabase().execSQL(
                    String.format(
                            " INSERT INTO  %s "
                                    + " ( %s , %s , %s , %s , %s , %s ) "
                                    + " VALUES "
                                    + " ( '%s', '%s', '%s', '%s', '%s', '%s' ) "
                            , MEMTAB
                            , MEMNAME, MEMPASS, MEMEMAIL, MEMPHONE, MEMADDR, MEMPHOTO
                            , m.name, "1", m.email, m.phone, m.addr, m.photo ));
        }
    }
}
