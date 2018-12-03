package nuwc.userloginsystem;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.*;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Reservations extends AppCompatActivity  {

    private CalendarView calendarView;
    private LinearLayout times;

    private EditText printName;
    private EditText employeeExt;
    private EditText startsDate;
    private EditText endsDate;
    private NumberPicker endsTimeHour;
    private NumberPicker endsTimeMin;
    private NumberPicker startsTimeHour;
    private NumberPicker startsTimeMin;
    private EditText printDetails;
    private CheckBox printerA;
    private CheckBox printerB;
    private CheckBox printerC;
    private CheckBox printerD;
    private CheckBox printerE;



    Button [] slot = new Button[24];

    Button submitRes;

    String dayNnite;

    int count;

    int monthD;
    int dayD;
    int yearD;
    int m;


    String pName;
    int pNumber;
    int ext;
    String sDate;
    int sTimeHour, sTimeMin;
    String eDate;
    int eTimeHour, eTimeMin;
    String details;

    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    Date startDate = new Date();
    Date endDate = new Date();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        //All fields in reservation page
        printName = (EditText) findViewById(R.id.printName);
        employeeExt = (EditText) findViewById(R.id.ext);

        startsDate = (EditText) findViewById(R.id.startsDate);
        endsDate = (EditText) findViewById(R.id.endsDate);

        endsTimeHour = (NumberPicker) findViewById(R.id.endsTimeHour);
        endsTimeHour.setMaxValue(100);
        endsTimeHour.setMinValue(0);
        endsTimeHour.setWrapSelectorWheel(true);

        endsTimeMin = (NumberPicker) findViewById(R.id.endsTimeMin);
        endsTimeMin.setMaxValue(59);
        endsTimeMin.setMinValue(0);
        endsTimeMin.setWrapSelectorWheel(true);

        startsTimeHour = (NumberPicker) findViewById(R.id.startsTimeHour);
        startsTimeHour.setMaxValue(23);
        startsTimeHour.setMinValue(0);
        startsTimeHour.setWrapSelectorWheel(true);

        startsTimeMin = (NumberPicker) findViewById(R.id.startsTimeMin);
        startsTimeMin.setMaxValue(59);
        startsTimeMin.setMinValue(0);
        startsTimeMin.setWrapSelectorWheel(true);


        printDetails = (EditText) findViewById(R.id.details);

        submitRes = (Button) findViewById(R.id.submitRes);


        times = (LinearLayout) findViewById(R.id.times);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setShowWeekNumber(false);

        printerA = (CheckBox) findViewById(R.id.printerA);
        printerB = (CheckBox) findViewById(R.id.printerB);
        printerC = (CheckBox) findViewById(R.id.printerC);
        printerD = (CheckBox) findViewById(R.id.printerD);
        printerE = (CheckBox) findViewById(R.id.printerE);




        //gives access to current time
        Calendar now = Calendar.getInstance();
        int year1 = now.get(Calendar.YEAR);
        int month1 = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day1 = now.get(Calendar.DAY_OF_MONTH);
        int hour1 = now.get(Calendar.HOUR_OF_DAY);
        int minute1 = now.get(Calendar.MINUTE);

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

        //show time slots for current day
        showTimeSlots(month1, day1, year1);


        //sets start date equal to whichever date chosen
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month += 1;
                String dateString = month + "/" + day + "/" + year;
                //show time slots for day changed to
                showTimeSlots(month, day, year);
                try {
                    startDate = df.parse(dateString);
                    Log.d("startsDate",startsDate.toString());

                }catch (ParseException e){

                }

                startsDate.setText(dateString);

                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(startDate); // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, startsTimeHour.getValue()); // adds one hour
                cal.add(Calendar.HOUR_OF_DAY, endsTimeHour.getValue()); // adds one hour
                cal.getTime(); // returns new date object, one hour in the future


                endsDate.setText(cal.getTime().toString());
            }
        });

        submitRes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Code for saving reservation in database2
                boolean ready = true;


                pName = printName.getText().toString();
                if(pName.length() > 16 || pName.length() == 0){
                    printName.getText().clear();
                    printName.setHintTextColor(getResources().getColor(R.color.red));
                    printName.setHint("Name must be below 16 characters");
                    ready = false;
                }


                if(isInteger(employeeExt.getText().toString())){
                    ext = Integer.parseInt(employeeExt.getText().toString());
                }else{
                    employeeExt.getText().clear();
                    employeeExt.setHintTextColor(getResources().getColor(R.color.red));
                    employeeExt.setHint("Enter employee enxtension");
                    ready = false;
                }

                try {
                    startDate = df.parse(startsDate.getText().toString());

                }catch (ParseException e){
                    startsDate.getText().clear();
                    startsDate.setHintTextColor(getResources().getColor(R.color.red));
                    startsDate.setHint("MM/dd/yyyy");
                    ready = false;
                }
                try {
                    endDate = df.parse(endsDate.getText().toString());

                }catch (ParseException e){
                    endsDate.getText().clear();
                    endsDate.setHintTextColor(getResources().getColor(R.color.red));
                    endsDate.setHint("MM/dd/yyyy");
                    ready = false;
                }

                if(printerA.isChecked()){

                }
                if(printerB.isChecked()){

                }if(printerC.isChecked()){

                }if(printerD.isChecked()){

                }if(printerE.isChecked()){

                }


                sTimeHour = startsTimeHour.getValue();
                sTimeMin = startsTimeMin.getValue();

                eTimeHour = endsTimeHour.getValue();
                eTimeMin = endsTimeMin.getValue();

                details = printDetails.getText().toString();


                if(!ready){
                    Toast.makeText(Reservations.this, "Reservation for " + pName + " has been made! ", Toast.LENGTH_SHORT).show();

                }

            }
        });


        endsTimeHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(startDate); // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, startsTimeHour.getValue()); // adds one hour
                cal.add(Calendar.HOUR_OF_DAY, endsTimeHour.getValue()); // adds one hour
                cal.add(Calendar.MINUTE, startsTimeMin.getValue()); // adds one hour
                cal.add(Calendar.MINUTE, endsTimeMin.getValue()); // adds one hour
                cal.getTime(); // returns new date object, one hour in the future


                endsDate.setText(cal.getTime().toString());


            }
        });
        endsTimeMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(startDate); // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, startsTimeHour.getValue()); // adds one hour
                cal.add(Calendar.HOUR_OF_DAY, endsTimeHour.getValue()); // adds one hour
                cal.add(Calendar.MINUTE, startsTimeMin.getValue()); // adds one hour
                cal.add(Calendar.MINUTE, endsTimeMin.getValue()); // adds one hour
                cal.getTime(); // returns new date object, one hour in the future


                endsDate.setText(cal.getTime().toString());


            }
        });
        startsTimeMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(startDate); // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, startsTimeHour.getValue()); // adds one hour
                cal.add(Calendar.HOUR_OF_DAY, endsTimeHour.getValue()); // adds one hour
                cal.add(Calendar.MINUTE, startsTimeMin.getValue()); // adds one hour
                cal.add(Calendar.MINUTE, endsTimeMin.getValue()); // adds one hour
                cal.getTime(); // returns new date object, one hour in the future


                endsDate.setText(cal.getTime().toString());


            }
        });
        startsTimeHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(startDate); // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, startsTimeHour.getValue()); // adds one hour
                cal.add(Calendar.HOUR_OF_DAY, endsTimeHour.getValue()); // adds one hour
                cal.add(Calendar.MINUTE, startsTimeMin.getValue()); // adds one hour
                cal.add(Calendar.MINUTE, endsTimeMin.getValue()); // adds one hour
                cal.getTime(); // returns new date object, one hour in the future


                endsDate.setText(cal.getTime().toString());

            }
        });

    }
    public void showTimeSlots(int month, int day, int year){
        times.removeAllViews();


        monthD = month;
    dayD = day;
    yearD = year;
    m = 0;
    dayNnite = "am";
        for( count = 0; count < 24; count ++){

        slot[count] = new Button(this);
        slot[count].setText(m + ":00");
        slot[count].setId(count);
        slot[count].setTextSize(40);
        slot[count].setBackgroundResource(R.drawable.time_slot);
        slot[count].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    startsTimeHour.setValue(view.getId());
            }
        });


        m++;

        times.addView(slot[count]);
    }

}


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }



}
