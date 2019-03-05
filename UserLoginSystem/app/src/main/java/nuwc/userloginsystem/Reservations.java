package nuwc.userloginsystem;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.*;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nuwc.userloginsystem.objects.CalenImport;
import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.util.RequestUtil;


public class Reservations extends AppCompatActivity {

    ConstraintLayout conLay;

    TextView monthYear;

    ImageView leftArrow;
    ImageView rightArrow;

    TableLayout table;

    TableRow row1;
    TableRow row2;
    TableRow row3;
    TableRow row4;
    TableRow row5;

    View[] days;
    TextView[] textDay;
    int calSize = 42;

    int year1;
    int month1;
    int day1;
    int hour1;
    int minute1;





    SimpleDateFormat fullTimeFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    Date startDate = new Date();
    Date endDate = new Date();

    boolean overallSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        conLay = (ConstraintLayout) findViewById(R.id.ConLay);

        monthYear = (TextView) findViewById(R.id.month_year);

        leftArrow = (ImageView) findViewById(R.id.leftArrow);
        rightArrow = (ImageView) findViewById(R.id.rightArrow);




        table = (TableLayout) findViewById(R.id.calendar);

        row1 = (TableRow) findViewById(R.id.row1);
        row2 = (TableRow) findViewById(R.id.row2);
        row3 = (TableRow) findViewById(R.id.row3);
        row4 = (TableRow) findViewById(R.id.row4);
        row5 = (TableRow) findViewById(R.id.row5);

        days = new View[calSize];
        textDay = new TextView[calSize];









        //All fields in reservation page


        //gives access to current time
        Calendar now = Calendar.getInstance();
         year1 = now.get(Calendar.YEAR);
         month1 = now.get(Calendar.MONTH) + 1; // Note: zero based!
         day1 = now.get(Calendar.DAY_OF_MONTH);
         hour1 = now.get(Calendar.HOUR_OF_DAY);
         minute1 = now.get(Calendar.MINUTE);

        final String current = (Integer.toString(month1) + "/" + Integer.toString(day1) + "/" + Integer.toString(year1));


        SimpleDateFormat f = new SimpleDateFormat("MM-dd-yyy");
        try {
            Date d = f.parse(current);
            long currentDate = d.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        displayCalendar(month1,day1,year1);

        leftArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(month1 - 1 == 0){
                    month1 = 12;
                    year1 --;
                    displayCalendar(month1 - 1,day1,year1);
                }else{
                    displayCalendar(month1 - 1,day1,year1);
                    month1 = month1 - 1;
                }


            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(month1 + 1  == 13){
                    month1 = 1;
                    year1 ++;
                    displayCalendar(month1,day1,year1);
                }else{
                    displayCalendar(month1 + 1,day1,year1);
                    month1 = month1 + 1;
                }


            }
        });
    }

    public void displayCalendar(int month, int day, int year){
        CalenImport calendar = new CalenImport();

        monthYear.setText(month + " " + year);

        //day of the week (sun-sat) 1-7
        int dayOfWeek = calendar.day(month,1,year);
        dayOfWeek += 1;

        //id of day block in calendar D1,D2,D3 etc
        String dayID = dayOfWeekID(dayOfWeek);




            if(1 < dayOfWeek){
                hideDays(1,dayOfWeek);


            }

            printDays(dayOfWeek,calendar.lastDay(month,year));

    }

    public void hideDays(int start, int end){
        for(int i = start; i != end; i ++){
            Log.d("hide","i = " + i);
            String viewID = "D" + i;
            int resID = getResources().getIdentifier(viewID, "id", getPackageName());
            days[i] = ((View) findViewById(resID));
            textDay[i] = (TextView) days[i].findViewById(R.id.date);


            textDay[i].setText("");
        }


    }

    public void printDays(int start,int end) {
        int day = 1;

        for(int i = 1; i <= end; i ++){
            Log.d("Days","days" + i);
            String viewID = "D" + start;
            int resID = getResources().getIdentifier(viewID, "id", getPackageName());
            days[i] = ((View) findViewById(resID));
            textDay[i] = (TextView) days[i].findViewById(R.id.date);

            textDay[i].setText(day + "th");
            day ++;
            start++;
        }
        hideDays(start,calSize);
    }

    public String dayOfWeekID(int dayOfWeek){
        String dayID = null;

        switch (dayOfWeek) {
            case 0:
                dayID = "D1";
                return dayID;
            case 1:
                dayID = "D2";
                return dayID;
            case 2:
                dayID = "D3";
                return dayID;
            case 3:
                dayID = "D4";
                return dayID;
            case 4:
                dayID = "D5";
                return dayID;
            case 5:
                dayID = "D6";
                return dayID;
            case 6:
                dayID = "D7";
                return dayID;
            default:
                dayID = "Invalid day";
                return dayID;
        }
    }


}
