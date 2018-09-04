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

        class Calc{
            String rs="",symbol="";
            int num, sum;

            public int getSum() { return sum; }
            public String getRs() { return rs; }

            public void setNum(int num) {
                this.num = num;
                this.rs+=num;
                this.setSum(num);
            }
            public void setSymbol(String symbol) {
                this.symbol = symbol;
                this.rs+=symbol.equals("=")?symbol+this.sum:symbol;
            }
            public void setSum(int num) {
                switch(this.symbol){
                    case "+": this.sum+=num;break;
                    case "-": this.sum-=num;break;
                    case "/": this.sum/=num;break;
                    case "*": this.sum*=num;break;
                    case "": this.sum = num;break;
                }
            }
            public void clear(){
                this.symbol="";
                this.rs="";
                this.sum=0;
                this.num=0;
            }

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context ctx = MainActivity.this;
        final EditText num = findViewById(R.id.num);
        final TextView result= findViewById(R.id.num);
        final Calc calc=new Calc();
        findViewById(R.id.plus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.setNum(Integer.parseInt(num.getText().toString()));
                calc.setSymbol("+");
                //num.setSelection(num.getText().length());
                result.setText("");
                Toast.makeText(ctx, "계산식:"+calc.getRs()+"누적값"+String.valueOf(calc.getSum()),Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.min_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.setNum(Integer.parseInt(num.getText().toString()));
                calc.setSymbol("-");
                result.setText("");
                Toast.makeText(ctx, "계산식:"+calc.getRs()+"누적값"+String.valueOf(calc.getSum()),Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.mult_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.setNum(Integer.parseInt(num.getText().toString()));
                calc.setSymbol("*");
                result.setText("");
                Toast.makeText(ctx, "계산식:"+calc.getRs()+"누적값"+String.valueOf(calc.getSum()),Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.div_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.setNum(Integer.parseInt(num.getText().toString()));
                calc.setSymbol("/");
                result.setText("");
                Toast.makeText(ctx, "계산식:"+calc.getRs()+"누적값"+String.valueOf(calc.getSum()),Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.rs_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.setNum(Integer.parseInt(num.getText().toString()));
                calc.setSymbol("=");
                result.setText(calc.getRs());
            }
        });
        findViewById(R.id.c_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
                calc.clear();
                Toast.makeText(ctx, "누적값:"+String.valueOf(calc.getSum()),Toast.LENGTH_LONG).show();
            }
        });

    }
}
