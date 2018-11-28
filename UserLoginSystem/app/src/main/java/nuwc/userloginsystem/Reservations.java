package nuwc.userloginsystem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Reservations extends AppCompatActivity {

    private CalendarView calendarView;
    private LinearLayout times;
    boolean startEnd = false;
    private EditText startsDate;
    private EditText endsDate;
    private EditText startsTime;
    private EditText endsTime;
    Button [] slot = new Button[24];

    String dayNnite;

    int count;

    int monthD;
    int dayD;
    int yearD;

    int m;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        startsDate = (EditText) findViewById(R.id.startsDate);
        endsDate = (EditText) findViewById(R.id.endsDate);
        startsTime = (EditText) findViewById(R.id.startsTime);
        endsTime = (EditText) findViewById(R.id.endsTime);

        final String TAG = "calendarActivity";
        Calendar now = Calendar.getInstance();
        int year1 = now.get(Calendar.YEAR);
        int month1 = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day1 = now.get(Calendar.DAY_OF_MONTH);
        int hour1 = now.get(Calendar.HOUR_OF_DAY);
        int minute1 = now.get(Calendar.MINUTE);
        int second1 = now.get(Calendar.SECOND);
        int millis1 = now.get(Calendar.MILLISECOND);



        final String current = (Integer.toString(month1) + "/" + Integer.toString(day1) + "/" + Integer.toString(year1));
        startsDate.setText(current);


        SimpleDateFormat f = new SimpleDateFormat("MM-dd-yyy");
        try {
            Date d = f.parse(current);
            long currentDate = d.getTime();
            calendarView.setDate(currentDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        times = (LinearLayout) findViewById(R.id.times);





        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setShowWeekNumber(false);

        showTimeSlots(month1, day1, year1);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;

                showTimeSlots(month, day, year);



                if(startEnd == true || startsDate.getText().toString() == date){
                    startsDate.setText(date);
                    startEnd = false;
                    Log.d("StartEnd True",Boolean.toString(startEnd));
                }else if(startEnd == false){
                    endsDate.setText(date);
                    startEnd = true;
                    Log.d("StartEnd False",Boolean.toString(startEnd));

                }
            }
        });


    }

    public void showTimeSlots(int month, int day, int year){
        monthD = month;
        dayD = day;
        yearD = year;
        m = 12;
        dayNnite = "am";
        for( count = 0; count < 24; count ++){
            slot[count] = new Button(this);
            if(count == 1){
                m -= 12;
            }else if(count == 12){
                dayNnite = "pm";
            }
            else if( count == 13){
                m -=12;
            }

            slot[count].setText(m + dayNnite);
            slot[count].setId(count);
            slot[count].setTextSize(40);
            slot[count].setBackgroundResource(R.drawable.time_slot);
            slot[count].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String starts = startsDate.getText().toString();
                    String ends = endsDate.getText().toString();
                    String thisString = Integer.toString(monthD) + "/" + Integer.toString(dayD) + "/" + Integer.toString(yearD);

                    Log.d("getText",starts);

                    int time = view.getId();
                    if(time >= 12){
                        dayNnite = "pm";
                    }else{
                        dayNnite = "am";
                    }
                    if (time == 0){
                        time = 12;
                    }
                    if(time >= 13){
                        time -= 12;
                    }

                    if(starts.equals(thisString)){
                        startsTime.setText(Integer.toString(time) + dayNnite);
                        Log.d("this",thisString);

                    }else if(ends.equals(thisString)){
                        endsTime.setText(Integer.toString(time) + dayNnite);

                    }

                }
            });


            m++;

            times.addView(slot[count]);
        }

    }
    public void checkButtonClick(){

    }

}
