package nuwc.userloginsystem;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.lang.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import nuwc.userloginsystem.objects.CalenImport;
import nuwc.userloginsystem.objects.PrinterReservations;
import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.util.RequestUtil;


public class Reservations extends AppCompatActivity {

    ConstraintLayout conLay;

    Button submit;
    Button cancel;

    TextView monthYear;
    TextView pleaseEnter;

    EditText employeeExt;

    ImageView leftArrow;
    ImageView rightArrow;
    ImageView myPrints;

    TableLayout table;

    TableRow row1;
    TableRow row2;
    TableRow row3;
    TableRow row4;
    TableRow row5;

    View[] days;
    TextView[] textDay;


    RadioGroup rGroup;
    RadioButton Abutton;
    RadioButton Bbutton;
    RadioButton Cbutton;
    RadioButton Dbutton;
    RadioButton Ebutton;


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
        pleaseEnter =(TextView) findViewById(R.id.enterInfoText);

        employeeExt = (EditText) findViewById(R.id.extBox);

        leftArrow = (ImageView) findViewById(R.id.leftArrow);
        rightArrow = (ImageView) findViewById(R.id.rightArrow);
        myPrints = (ImageView) findViewById(R.id.myReserve);

        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);




        table = (TableLayout) findViewById(R.id.calendar);

        row1 = (TableRow) findViewById(R.id.row1);
        row2 = (TableRow) findViewById(R.id.row2);
        row3 = (TableRow) findViewById(R.id.row3);
        row4 = (TableRow) findViewById(R.id.row4);
        row5 = (TableRow) findViewById(R.id.row5);

        days = new View[calSize];


        Abutton = (RadioButton) findViewById(R.id.Aprinter);
        Bbutton = (RadioButton) findViewById(R.id.Bprinter);
        Cbutton = (RadioButton) findViewById(R.id.Cprinter);
        Dbutton = (RadioButton) findViewById(R.id.Dprinter);
        Ebutton = (RadioButton) findViewById(R.id.Eprinter);

        Abutton.setChecked(true);

        for(int i = 1; i < calSize; i ++){
            String viewID = "D" + i;
            int resID = getResources().getIdentifier(viewID, "id", getPackageName());
            days[i] = ((View) findViewById(resID));
        }
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

        //Click listeners used for navigating calendar
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

        myPrints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.setVisibility(View.INVISIBLE);

                pleaseEnter.setVisibility(View.VISIBLE);
                employeeExt.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent checkout = new Intent(Reservations.this, myPrintList.class);
                checkout.putExtra("employee",employeeExt.getText());
                checkout.putExtra("reserveType","breakout");
                Reservations.this.startActivity(checkout);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.setVisibility(View.VISIBLE);

                pleaseEnter.setVisibility(View.INVISIBLE);
                employeeExt.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.INVISIBLE);

            }
        });
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }

        });

    }



    public void displayCalendar(int month, int day, int year){
        CalenImport calendar = new CalenImport();


        monthYear.setText(calendar.month(month) + " " + year);

        //day of the week (sun-sat) 1-7
        int dayOfWeek = calendar.day(month,1,year);
        dayOfWeek += 1;

        //id of day block in calendar D1,D2,D3 etc
        String dayID = dayOfWeekID(dayOfWeek);

        if(1 < dayOfWeek) {
            hideDays(1,dayOfWeek);
        }
        getReservationList(dayOfWeek, calendar.lastDay(month,year), month, year);
    }

    public void hideDays(int start, int end){
        for(int i = start; i != end; i ++){
            textDay[i] = (TextView) days[i].findViewById(R.id.date);
            textDay[i].setText("");
            days[i].setOnClickListener(null);


        }


    }

    public void printDays(List<PrinterReservations> printerReservationsList, int start,int end, int month, int year) {
        int d = 1;

        for(int i = 1; i <= end; i ++){
            textDay[i] = (TextView) days[start].findViewById(R.id.date);
            textDay[i].setText(d + "th");
            int finalDay = d;

            Date date = new GregorianCalendar(year, month, i).getTime();

            Log.d("RESSS", printerReservationsList.toString());

            for(PrinterReservations res : printerReservationsList){
                Log.d("RESSS", res.getPrinter());
                Log.d("RESID", checkPrinterButtons());
            }

            for(PrinterReservations res : printerReservationsList){
                if(res.getPrinter().equals(checkPrinterButtons()) && dayContained(date, res.getJobSchedule(), res.getJobScheduleEnd())){
                    addEventBubble(days[start], res.getName());
                }
            }

            days[start].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(Reservations.this, reserveOptionsPrint.class);
                    myIntent.putExtra("day",finalDay);
                    myIntent.putExtra("month",month - 1);
                    myIntent.putExtra("year",year);
                    myIntent.putExtra("printer",checkPrinterButtons());

                    Reservations.this.startActivity(myIntent);

                }
            });

            d ++;
            start++;
        }
        hideDays(start,calSize);
    }

    public boolean dayContained(Date current, Date start, Date end){
        return current.after(start) && current.before(end);
    }

    public void getReservationList(int dayOfWeek, int end, int month, int year){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                RequestUtil.BASE_URL + "/getPrinterReservations",
                null,
                response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        List<PrinterReservations> resList = mapper.readValue(response.toString(), new TypeReference<List<PrinterReservations>>(){});

                        printDays(resList, dayOfWeek,end, month, year);
                    }
                    catch(Exception e){
                        Log.e("EXCEPTION", e.toString());
                        //showError("Object mapping error. Please check logs.");
                    }
                },
                error -> {
                    Log.e("ERROR", "Error is: " + error.getMessage());
                    showError(error.getMessage());

                }
        );

        requestQueue.add(request);
    }

    private void showError(String message){
        Context context = this;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void addEventBubble(View day, String name){
        TextView view = (TextView) day.findViewById(R.id.bubb);
//        TextView view1 = (TextView) day.findViewById(R.id.bubb1);
//        TextView view2 = (TextView) day.findViewById(R.id.bubb2);

        view.setVisibility(View.VISIBLE);
//        view1.setVisibility(View.VISIBLE);
//        view2.setVisibility(View.VISIBLE);

        view.setText(name);
//        view1.setText("Bye");
//        view2.setText("Vinny");




    }

    public String checkPrinterButtons(){

        if(Abutton.isChecked()){
            return "A";
        }else if(Bbutton.isChecked()){
            return  "B";
        }else if(Cbutton.isChecked()){
            return  "C";
        }else if(Dbutton.isChecked()){
            return  "D";
        }else if(Ebutton.isChecked()){
            return  "E";
        }else {
            return null;
        }

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
