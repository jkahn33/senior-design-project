package nuwc.userloginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vinny on 11/5/18.
 */
public class reserveOptionsPrint extends AppCompatActivity{

    NumberPicker startTimeHourP;
    NumberPicker startTimeMinP;
    NumberPicker endTimehourP;
    NumberPicker endTimeMinP;

    LinearLayout times;
    Button[] slot = new Button[24];

    EditText printName;
    EditText startDate;
    EditText employExt;
    EditText printDetails;

    TextView endDate;
    TextView MDY;
    TextView dayWeek;

    int year = 1997;
    int month = 0;
    int day = 19;


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
    Calendar calStart = Calendar.getInstance();
    Calendar calEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_options_printer);

        times = (LinearLayout) findViewById(R.id.times);

        endDate = (TextView) findViewById(R.id.endDate);

        employExt = (EditText) findViewById(R.id.employExt);
        printDetails = (EditText) findViewById(R.id.printDetails);
        printName = (EditText) findViewById(R.id.printName);
        startDate = (EditText) findViewById(R.id.startDate);

        MDY = (TextView) findViewById(R.id.MDY);
        dayWeek = (TextView) findViewById(R.id.dayWeek);







        startTimeHourP = (NumberPicker) findViewById(R.id.startTimeHourP);
        String[] nums = new String[24];
        for(int i=0; i<nums.length; i++)
            nums[i] = Integer.toString(i);

        startTimeHourP.setMinValue(0);
        startTimeHourP.setMaxValue(23);
        startTimeHourP.setWrapSelectorWheel(true);
        startTimeHourP.setDisplayedValues(nums);
        startTimeHourP.setValue(0);

        startTimeMinP = (NumberPicker) findViewById(R.id.startTimeMinP);
        String[] nums2 = new String[60];
        for(int i=0; i<nums2.length; i++)
            nums2[i] = Integer.toString(i);

        startTimeMinP.setMinValue(0);
        startTimeMinP.setMaxValue(59);
        startTimeMinP.setWrapSelectorWheel(true);
        startTimeMinP.setDisplayedValues(nums2);
        startTimeMinP.setValue(0);

        endTimehourP = (NumberPicker) findViewById(R.id.endTimehourP);
        String[] nums3 = new String[200];
        for(int i=0; i<nums3.length; i++)
            nums3[i] = Integer.toString(i);

        endTimehourP.setMinValue(0);
        endTimehourP.setMaxValue(199);
        endTimehourP.setWrapSelectorWheel(true);
        endTimehourP.setDisplayedValues(nums3);
        endTimehourP.setValue(0);

        endTimeMinP = (NumberPicker) findViewById(R.id.endTimeMinP);
        String[] nums4 = new String[60];
        for(int i=0; i<nums4.length; i++)
            nums4[i] = Integer.toString(i);

        endTimeMinP.setMinValue(0);
        endTimeMinP.setMaxValue(59);
        endTimeMinP.setWrapSelectorWheel(true);
        endTimeMinP.setDisplayedValues(nums4);
        endTimeMinP.setValue(0);

        calStart.set(year,month,day);
        MDY.setText(getMonth(month) + " " + day + "," + year);

        dayWeek.setText(getDayWeek(calStart.get(Calendar.DAY_OF_WEEK)));

        startTimeHourP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {

                calStart.set(Calendar.HOUR_OF_DAY,i2);
                startDate.setText(simpleDateFormat.format(calStart.getTime()));

                calEnd = calStart;
                endDate.setText(simpleDateFormat.format(calStart.getTime()));


            }
        });
        startTimeMinP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {

                calStart.set(Calendar.MINUTE,i2);
                startDate.setText(simpleDateFormat.format(calStart.getTime()));

                calEnd = calStart;
                endDate.setText(simpleDateFormat.format(calStart.getTime()));


            }
        });
        endTimehourP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {

                calStart.add(Calendar.HOUR_OF_DAY,-i);
                calStart.add(Calendar.HOUR_OF_DAY,i2);
                endDate.setText(simpleDateFormat.format(calEnd.getTime()));

            }
        });

        endTimeMinP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {

                calStart.add(Calendar.MINUTE,-i);
                calStart.add(Calendar.MINUTE,i2);
                endDate.setText(simpleDateFormat.format(calEnd.getTime()));

            }
        });


        showTimeSlots(month,day,year);


    }



    public void showTimeSlots(int month, int day, int year){
        times.removeAllViews();


        int hour = 0;
        for( int count = 0; count < 24; count ++){

            slot[count] = new Button(this);
            slot[count].setText(hour + ":00");
            slot[count].setId(hour);
            slot[count].setTextSize(40);
            slot[count].setBackgroundResource(R.drawable.time_slot);
            slot[count].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String starts = startDate.getText().toString();
                    String ends = endDate.getText().toString();
                    String thisString = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);


                    int time = view.getId();

                    startTimeHourP.setValue(time);
                    calStart.set(Calendar.HOUR_OF_DAY,time);


                }
            });
            hour++;
            times.addView(slot[count]);
        }


    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    public String getDayWeek(int day){
        String theDay;

        switch (day) {
            case 1:
                theDay = "Sunday";
                break;
            case 2:
                theDay = "Monday";
                break;
            case 3:
                theDay = "Tuesday";
                break;
            case 4:
                theDay = "Wednesday";
                break;
            case 5:
                theDay = "Thursday";
                break;
            case 6:
                theDay = "Friday";
                break;
            case 7:
                theDay = "Saturday";
                break;
            default:
                theDay = "Invalid day";
                break;
        }


        return theDay;
    }



}