package app.hong.com.myapp3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Date;
import java.text.SimpleDateFormat;


public class Schedular extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedular);
        CalendarView calender = findViewById(R.id.calender);
        TimePicker time =  findViewById(R.id.time);
        TextView today = findViewById(R.id.today);
        TextView year =findViewById(R.id.year);
        TextView month = findViewById(R.id.month);
        TextView day =findViewById(R.id.day);
        TextView hour = findViewById(R.id.hour);
        TextView minute = findViewById(R.id.minute);

        calender.setVisibility(View.VISIBLE);
        time.setVisibility(View.INVISIBLE);
        today.setText(new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date()));


        class Reserve{
            String year,month,day,hour,minute;
        }
        final Reserve reserve = new Reserve();

        findViewById(R.id.rdoCalendar).setOnClickListener(
                (View v)->{
                    calender.setVisibility(View.VISIBLE);
                    time.setVisibility(View.INVISIBLE);
                }
        );
        findViewById(R.id.rdoTime).setOnClickListener(
                (View v)->{
                    calender.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.VISIBLE);

                }
        );
        findViewById(R.id.btnEnd).setOnClickListener(
              (View v)->{
                  reserve.hour=time.getHour()+"";
                  reserve.minute=time.getMinute()+"";
                  year.setText(reserve.year);
                  month.setText(reserve.month);
                  day.setText(reserve.day);
                  hour.setText(reserve.day);
                  minute.setText(reserve.minute);
              }
        );
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                reserve.year=year+"";
                reserve.month=month+"";
                reserve.day=dayOfMonth+"";
            }
        });




    }
}

