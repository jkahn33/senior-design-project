package nuwc.userloginsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.util.RequestUtil;

public class BreakoutReservation extends AppCompatActivity {

    private CalendarView calendarView;
    private LinearLayout times;
    boolean startEnd = false;

    private EditText printName;
    private EditText employeeExt;
    private EditText startsDate;
    private EditText endsDate;
    private NumberPicker endsTimeHour;
    private NumberPicker endsTimeMin;
    private NumberPicker startsTimeHour;
    private NumberPicker startsTimeMin;
    private EditText resDetails;
    private EditText guestNumber;
    private CheckBox roomA;
    private CheckBox roomB;
    private CheckBox roomC;
    private CheckBox roomD;
    private Button homeButton;
    private TextView success;

    private TextView startsAt;
    private TextView colonTop;
    private TextView on;
    private TextView endsAt;
    private TextView colonBottom;


    boolean overallSuccess;


    Button[] slot = new Button[24];

    Button submitRes;
    Button breakoutCancel;

    String dayNnite;

    int count;

    int monthD;
    int dayD;
    int yearD;
    int m;

    int checkCount = 0;
    int currCount = 0;


    String pName;
    int ext;
    int sTimeHour, sTimeMin;
    int eTimeHour, eTimeMin;
    String details;
    int numberGuests;

    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    Date startDate = new Date();
    Date endDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakout_reservation);

        //All fields in reservation page
        printName = (EditText) findViewById(R.id.printName);
        employeeExt = (EditText) findViewById(R.id.ext);

        startsDate = (EditText) findViewById(R.id.startsDate);
        endsDate = (EditText) findViewById(R.id.endsDate);

        guestNumber =(EditText) findViewById(R.id.guestNumber);

        endsTimeHour = (NumberPicker) findViewById(R.id.endsTimeHour);
        endsTimeHour.setMaxValue(24);
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

        homeButton = (Button) findViewById(R.id.btnBreakoutHome);
        success = (TextView) findViewById(R.id.txtBreakoutSuccess);
        breakoutCancel = (Button) findViewById(R.id.btnBreakoutCancel);
        homeButton.setVisibility(View.INVISIBLE);
        success.setVisibility(View.INVISIBLE);

        resDetails = (EditText) findViewById(R.id.details);
        startsAt = (TextView) findViewById(R.id.startsAt);
        endsAt = (TextView) findViewById(R.id.endsAt);
        colonTop = (TextView) findViewById(R.id.endsAt3);
        colonBottom = (TextView) findViewById(R.id.endsAt5);
        on = (TextView) findViewById(R.id.endsAt7);

        submitRes = (Button) findViewById(R.id.submitRes);


        times = (LinearLayout) findViewById(R.id.times);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setShowWeekNumber(false);

        roomA = (CheckBox) findViewById(R.id.roomA);
        roomB = (CheckBox) findViewById(R.id.roomB);
        roomC = (CheckBox) findViewById(R.id.roomC);
        roomD = (CheckBox) findViewById(R.id.roomD);



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

                if(startEnd || startsDate.getText().toString() == dateString){
                    startsDate.setText(dateString);
                    startEnd = false;
                    Log.d("StartEnd True",Boolean.toString(startEnd));
                }else if(!startEnd){
                    endsDate.setText(dateString);
                    startEnd = true;
                    Log.d("StartEnd False",Boolean.toString(startEnd));

                }
            }
        });

        submitRes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                overallSuccess = true;
                //Code for saving reservation in database2
                boolean ready = true;


                pName = printName.getText().toString();
                if(pName.length() > 16 || pName.length() == 0){
                    printName.getText().clear();
                    printName.setHintTextColor(getResources().getColor(R.color.red));
                    printName.setHint("Name under 16 characters!");
                    ready = false;
                }

                if(isInteger(employeeExt.getText().toString())){
                    ext = Integer.parseInt(employeeExt.getText().toString());
                }else{
                    employeeExt.getText().clear();
                    employeeExt.setHintTextColor(getResources().getColor(R.color.red));
                    employeeExt.setHint("Enter employee extension!");
                    ready = false;
                }
                if(isInteger(guestNumber.getText().toString())){
                    numberGuests = Integer.parseInt(guestNumber.getText().toString());
                }else{
                    guestNumber.getText().clear();
                    guestNumber.setHintTextColor(getResources().getColor(R.color.red));
                    guestNumber.setHint("Enter number of guests!");
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

                sTimeHour = startsTimeHour.getValue();
                sTimeMin = startsTimeMin.getValue();

                eTimeHour = endsTimeHour.getValue();
                eTimeMin = endsTimeMin.getValue();

                details = resDetails.getText().toString();

                Calendar startCal = Calendar.getInstance();
                Calendar endCal = Calendar.getInstance();
                startCal.setTime(startDate);
                endCal.setTime(endDate);
                startCal.add(Calendar.HOUR_OF_DAY, sTimeHour);
                startCal.add(Calendar.MINUTE, sTimeMin);


                if(ready) {
                    if (roomA.isChecked() && overallSuccess) {
                        checkCount++;
                        try {
                            logBreakoutRes("A");
                        } catch (JSONException e) {
                            showError("JSON mapping error.");
                            Log.e("EXCEPTION", e.toString());

                            overallSuccess = false;
                        }
                    }
                    if (roomB.isChecked() && overallSuccess) {
                        checkCount++;
                        try {
                            logBreakoutRes("B");
                        } catch (JSONException e) {
                            showError("JSON mapping error.");
                            Log.e("EXCEPTION", e.toString());

                            overallSuccess = false;
                        }
                    }
                    if (roomC.isChecked() && overallSuccess) {
                        checkCount++;
                        try {
                            logBreakoutRes("C");
                        } catch (JSONException e) {
                            showError("JSON mapping error.");
                            Log.e("EXCEPTION", e.toString());

                            overallSuccess = false;
                        }
                    }
                    if (roomD.isChecked() && overallSuccess) {
                        checkCount++;
                        try {
                            logBreakoutRes("D");
                        } catch (JSONException e) {
                            showError("JSON mapping error.");
                            Log.e("EXCEPTION", e.toString());

                            overallSuccess = false;
                        }
                    }
                    calendarView.setVisibility(View.INVISIBLE);
                    times.setVisibility(View.INVISIBLE);
                    printName.setVisibility(View.INVISIBLE);
                    employeeExt.setVisibility(View.INVISIBLE);
                    startsDate.setVisibility(View.INVISIBLE);
                    endsDate.setVisibility(View.INVISIBLE);
                    endsTimeHour.setVisibility(View.INVISIBLE);
                    endsTimeMin.setVisibility(View.INVISIBLE);
                    startsTimeHour.setVisibility(View.INVISIBLE);
                    startsTimeMin.setVisibility(View.INVISIBLE);
                    resDetails.setVisibility(View.INVISIBLE);
                    guestNumber.setVisibility(View.INVISIBLE);
                    roomA.setVisibility(View.INVISIBLE);
                    roomB.setVisibility(View.INVISIBLE);
                    roomC.setVisibility(View.INVISIBLE);
                    roomD.setVisibility(View.INVISIBLE);
                    submitRes.setVisibility(View.INVISIBLE);
                    resDetails.setVisibility(View.INVISIBLE);
                    startsAt.setVisibility(View.INVISIBLE);
                    endsAt.setVisibility(View.INVISIBLE);
                    colonTop.setVisibility(View.INVISIBLE);
                    colonBottom.setVisibility(View.INVISIBLE);
                    on.setVisibility(View.INVISIBLE);
                    breakoutCancel.setVisibility(View.INVISIBLE);

                    homeButton.setVisibility(View.VISIBLE);
                    success.setVisibility(View.VISIBLE);
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent cancel = new Intent(BreakoutReservation.this, PickReservationType.class);
                BreakoutReservation.this.startActivity(cancel);
            }
        });
        breakoutCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent cancel = new Intent(BreakoutReservation.this, PickReservationType.class);
                BreakoutReservation.this.startActivity(cancel);
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
                    String starts = startsDate.getText().toString();
                    String ends = endsDate.getText().toString();
                    String thisString = Integer.toString(monthD) + "/" + Integer.toString(dayD) + "/" + Integer.toString(yearD);

                    Log.d("getText",starts);

                    int time = view.getId();

                    if(starts.equals(thisString)){
                        startsTimeHour.setValue(time);
                        Log.d("this",thisString);

                    }else if(ends.equals(thisString)){
                        endsTimeHour.setValue(time);

                    }

                }
            });


            m++;

            times.addView(slot[count]);
        }

    }

    public void logBreakoutRes(String reservableId) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        String fullStartDate = startsDate.getText().toString() + " " + Integer.toString(startsTimeHour.getValue()) + ":" + Integer.toString(startsTimeMin.getValue());
        String fullEndDate = endsDate.getText().toString() + " " + Integer.toString(endsTimeHour.getValue()) + ":" + Integer.toString(endsTimeMin.getValue());
        SimpleDateFormat timeDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Timestamp startTime;
        Timestamp endTime;
        try {
            Date formattedStartDate = timeDateFormat.parse(fullStartDate);
            Date formattedEndDate = timeDateFormat.parse(fullEndDate);
            startTime = new Timestamp(formattedStartDate.getTime());
            endTime = new Timestamp(formattedEndDate.getTime());
        }
        catch(Exception e){
            Log.d("EXCEPTION", e.toString());
            overallSuccess = false;
            showError("Failed to parse start date. Please make sure it is formatted correctly.");
            return;
        }

        JSONObject body = new JSONObject();

        body.put("userExt", employeeExt.getText().toString());
        //currently is either "Printer" or "Breakout", should eventually be enum
        body.put("reservableType", "Breakout");
        body.put("reservableId", reservableId);
        body.put("resDescription", printName.getText().toString());
        body.put("resStart", startTime.toString());
        body.put("resEnd", endTime.toString());
        body.put("numPeople", Integer.toString(numberGuests));
        body.put("additionalCom", details);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/newBreakoutReservation",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            Log.d("RESPONSE", response.toString());
                            ResponseObject responseObject = mapper.readValue(response.toString(), ResponseObject.class);
                            verifyResponse(responseObject);
                        }
                        catch(Exception e){
                            showError("User logging object mapping error, please check logs.");
                            Log.d("EXCEPTION", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.getMessage());
                        overallSuccess = false;
                        showError(error.getMessage());
                    }
                }
        );

        requestQueue.add(request);
    }
    public void verifyResponse(ResponseObject response){
        if(!response.isSuccess()){
            calendarView.setVisibility(View.VISIBLE);
            times.setVisibility(View.VISIBLE);
            printName.setVisibility(View.VISIBLE);
            employeeExt.setVisibility(View.VISIBLE);
            startsDate.setVisibility(View.VISIBLE);
            endsDate.setVisibility(View.VISIBLE);
            endsTimeHour.setVisibility(View.VISIBLE);
            endsTimeMin.setVisibility(View.VISIBLE);
            startsTimeHour.setVisibility(View.VISIBLE);
            startsTimeMin.setVisibility(View.VISIBLE);
            resDetails.setVisibility(View.VISIBLE);
            guestNumber.setVisibility(View.VISIBLE);
            roomA.setVisibility(View.VISIBLE);
            roomB.setVisibility(View.VISIBLE);
            roomC.setVisibility(View.VISIBLE);
            roomD.setVisibility(View.VISIBLE);
            submitRes.setVisibility(View.VISIBLE);

            homeButton.setVisibility(View.INVISIBLE);
            success.setVisibility(View.INVISIBLE);
            showError(response.getMessage());

            overallSuccess = false;
        }
    }
    public void showError(String message){
        Log.d("MESSAGE", message);
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