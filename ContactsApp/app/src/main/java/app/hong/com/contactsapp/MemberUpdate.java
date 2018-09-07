package app.hong.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);
        Context context = MemberUpdate.this;

        TextView name = findViewById(R.id.name);
        EditText email = findViewById(R.id.changeEmail);
        EditText phone = findViewById(R.id.changePhone);
        EditText addr = findViewById(R.id.changeAddress);
        Intent intent = getIntent();
        String[] spec = intent.getExtras().getString("spec").split("/");
        name.setText(spec[1]);
        email.setText(spec[4]);
        phone.setText(spec[3]);
        addr.setText(spec[5]);

        ImageView profile = findViewById(R.id.profileImg);
        profile.setImageDrawable(
                getResources().
                        getDrawable(
                                getResources().getIdentifier(this.getPackageName()
                                        + ":drawable/" + spec[6], null, null), context.getTheme()));



        findViewById(R.id.confirmBtn).setOnClickListener(
                (View v)->{
                    ItemUpdate query = new ItemUpdate(context);
                    query.seq = spec[0];
                    query.email= (email.getText().toString().equals(""))? spec[4]:email.getText().toString();
                    query.phone=(phone.getText().toString().equals(""))?spec[3]:phone.getText().toString();
                    query.addr=(addr.getText().toString().equals(""))?spec[5]:addr.getText().toString();

                    new Main.StatusService(){
                        @Override
                        public void perform() {
                            query.execute();
                        }
                    }.perform();

                    Intent intent1 = new Intent(context,MemberDetail.class);
                    intent1.putExtra("seq",spec[0]);
                    startActivity(intent1);

                }
        );
        findViewById(R.id.cancelBtn).setOnClickListener(
                (View v)->{
                    Intent intent1 = new Intent(context,MemberDetail.class);
                    intent1.putExtra("seq",spec[0]);
                    startActivity(intent1);
                }
                );
    }
    private class UpdateQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;

        public UpdateQuery(Context context) {
            super(context);
            helper = new Main.SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class ItemUpdate extends  UpdateQuery{
        String seq,email,phone,addr;
        public ItemUpdate(Context context) {

            super(context);
        }
        public void execute(){
            getDatabase().execSQL(String.format(
                    "  UPDATE MEMBER " +
                            "SET EMAIL = '%s', " +
                            "PHONE = '%s', " +
                            "ADDR = '%s' " +
                            "WHERE SEQ LIKE '%s' ",email,phone,addr,seq
            ));

        }
    }

}
