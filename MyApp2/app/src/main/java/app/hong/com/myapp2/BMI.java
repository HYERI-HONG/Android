package app.hong.com.myapp2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BMI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);
        final EditText hei = findViewById(R.id.height);
        final EditText wei = findViewById(R.id.weight);
        final Context this_ = BMI.this;
        final TextView result= findViewById(R.id.result);

        final class bmiCalc{
            double weight, height;
            String rs;
            public void setRs() {
                double value =weight/(height*height);
                if(value<18.5){
                    rs ="저체중";
                }else if(18.5<=value&&value<23){
                    rs ="정상";
                }else if(23<=value&&value<25){
                    rs ="비만 전단계";
                }else if(25<=value&&value<30){
                    rs ="1단계 비만";
                }else if(30<=value&&value<35){
                    rs ="2단계 비만";
                }else{
                    rs ="3단계 비만";
                }
            }
        }
        findViewById(R.id.submit_btn).setOnClickListener(
                (View v)->{
                    bmiCalc bmi=new bmiCalc();
                    bmi.height=Double.parseDouble(hei.getText().toString())/100.0;
                    bmi.weight=Double.parseDouble(wei.getText().toString());
                    bmi.setRs();
                    result.setText("결과 : "+bmi.rs);
                }
        );
    }
}

