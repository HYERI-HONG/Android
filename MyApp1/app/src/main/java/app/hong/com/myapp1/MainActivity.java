package app.hong.com.myapp1;

import android.content.Context;
import android.media.tv.TvContentRating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context ctx = MainActivity.this;
        findViewById(R.id.plus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText num1 = findViewById(R.id.num1);
                EditText num2 = findViewById(R.id.num2);
                TextView result= findViewById(R.id.result);
                result.setText(String.valueOf(
                        Integer.parseInt(num1.getText().toString())
                        +Integer.parseInt(num2.getText().toString())
                        ));
                //Toast.makeText(ctx, "입력한 숫자값:"+sNum1,Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.min_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText num1 = findViewById(R.id.num1);
                EditText num2 = findViewById(R.id.num2);
                TextView result= findViewById(R.id.result);
                result.setText(String.valueOf(
                        Integer.parseInt(num1.getText().toString())
                        -Integer.parseInt(num2.getText().toString())
                        ));
            }
        });
        findViewById(R.id.mult_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText num1 = findViewById(R.id.num1);
                EditText num2 = findViewById(R.id.num2);

                TextView result= findViewById(R.id.result);
                result.setText(String.valueOf(
                        Integer.parseInt(num1.getText().toString())
                        *Integer.parseInt(num2.getText().toString())
                        ));
            }
        });
        findViewById(R.id.div_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText num1 = findViewById(R.id.num1);
                EditText num2 = findViewById(R.id.num2);
                TextView result= findViewById(R.id.result);
                result.setText(String.valueOf(
                        Integer.parseInt(num1.getText().toString())
                        /Integer.parseInt(num2.getText().toString())
                        ));
            }
        });
    }
}
