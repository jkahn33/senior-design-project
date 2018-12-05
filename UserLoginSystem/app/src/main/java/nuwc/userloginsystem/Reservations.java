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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.util.RequestUtil;


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

    private RadioGroup printersGroup;
    private RadioButton printerA;
    private RadioButton printerB;
    private RadioButton printerC;
    private RadioButton printerD;
    private RadioButton printerE;

    private TextView startsAtLabel;
    private TextView endsAtLabel;
    private TextView onLabel;
    private TextView hoursLabel;
    private TextView colonLabel;
    private TextView minutesLabel;
    private TextView printForLabel;
    private TextView success;

    private Button homeButton;


    Button [] slot = new Button[24];

    Button submitRes;
    Button printCancel;

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

    SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat fullTimeFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    Date startDate = new Date();
    Date endDate = new Date();

    boolean overallSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        //All fields in reservation page
        homeButton = (Button) findViewById(R.id.printerHomeButton);
        success = (TextView) findViewById(R.id.txtPrintSuccess);
        homeButton.setVisibility(View.INVISIBLE);
        success.setVisibility(View.INVISIBLE);

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

        printersGroup = (RadioGroup) findViewById(R.id.radioPrinters);
        printerA = (RadioButton) findViewById(R.id.radioA);
        printerB = (RadioButton) findViewById(R.id.radioB);
        printerC = (RadioButton) findViewById(R.id.radioC);
        printerD = (RadioButton) findViewById(R.id.radioD);
        printerE = (RadioButton) findViewById(R.id.radioE);

        startsAtLabel = (TextView) findViewById(R.id.startsAt);
        endsAtLabel = (TextView) findViewById(R.id.endDate);
        onLabel = (TextView) findViewById(R.id.endsAt7);
        hoursLabel = (TextView) findViewById(R.id.endsAt3);
        colonLabel = (TextView) findViewById(R.id.endsAt5);
        minutesLabel = (TextView) findViewById(R.id.endsAt4);
        printForLabel = (TextView) findViewById(R.id.endsAt);

        printCancel = (Button) findViewById(R.id.btnPrintResCancel);

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
                    startDate = mdyFormat.parse(dateString);
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
                overallSuccess = true;

                sTimeHour = startsTimeHour.getValue();
                sTimeMin = startsTimeMin.getValue();

                eTimeHour = endsTimeHour.getValue();
                eTimeMin = endsTimeMin.getValue();

                details = printDetails.getText().toString();

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
                    employeeExt.setHint("Enter employee extension");
                    ready = false;
                }

                try {
                    startDate = mdyFormat.parse(startsDate.getText().toString());

                }catch (ParseException e){
                    startsDate.getText().clear();
                    startsDate.setHintTextColor(getResources().getColor(R.color.red));
                    startsDate.setHint("MM/dd/yyyy");
                    ready = false;
                }

                try {
                    endDate = fullTimeFormat.parse(endsDate.getText().toString());
                    Log.d("TIME", endsTimeHour.getValue() + ":" + endsTimeMin.getValue());

                }catch (ParseException e){
                    endsDate.getText().clear();
                    endsDate.setHintTextColor(getResources().getColor(R.color.red));
                    endsDate.setHint("MM/dd/yyyy");
                    ready = false;
                }
                RadioGroup printersGroup = (RadioGroup) findViewById(R.id.radioPrinters);
                printersGroup.getCheckedRadioButtonId();
                try {
                    logPrintRes(printersGroup.getCheckedRadioButtonId());
                }
                catch(JSONException e) {
                    showError(e.getMessage());
                    Log.e("EXCEPTION", e.toString());
                }
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent home = new Intent(Reservations.this, PickReservationType.class);
                Reservations.this.startActivity(home);
            }
        });

        printCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent home = new Intent(Reservations.this, PickReservationType.class);
                Reservations.this.startActivity(home);
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
    public void logPrintRes(int radioId) throws JSONException {
        String reservableId;
        switch(radioId){
            case R.id.radioA:
                reservableId = "A";
                break;
            case R.id.radioB:
                reservableId = "B";
                break;
            case R.id.radioC:
                reservableId = "C";
                break;
            case R.id.radioD:
                reservableId = "D";
                break;
            case R.id.radioE:
                reservableId = "E";
                break;
            default:
                showError("Cannot determine printer ID");
                return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        String fullStartDate = startsDate.getText().toString() + " " + Integer.toString(startsTimeHour.getValue()) + ":" + Integer.toString(startsTimeMin.getValue());
        SimpleDateFormat startDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Timestamp currentTime;
        try {
            Date testDate = startDateFormat.parse(fullStartDate);
            currentTime = new Timestamp(testDate.getTime());
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
        body.put("reservableType", "Printer");
        body.put("reservableId", reservableId);
        body.put("jobDescription", printName.getText().toString());
        body.put("jobSchedule", currentTime.toString());
        body.put("jobDuration", Integer.toString(endsTimeHour.getValue()) + ":" + Integer.toString(endsTimeMin.getValue()));
        body.put("additionalCom", details);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/newPrinterReservation",
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
        if(response.isSuccess()){
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
            printDetails.setVisibility(View.INVISIBLE);
            printersGroup.setVisibility(View.INVISIBLE);
            startsAtLabel.setVisibility(View.INVISIBLE);
            endsAtLabel.setVisibility(View.INVISIBLE);
            onLabel.setVisibility(View.INVISIBLE);
            hoursLabel.setVisibility(View.INVISIBLE);
            colonLabel.setVisibility(View.INVISIBLE);
            minutesLabel.setVisibility(View.INVISIBLE);
            printForLabel.setVisibility(View.INVISIBLE);
            submitRes.setVisibility(View.INVISIBLE);
            printCancel.setVisibility(View.INVISIBLE);

            homeButton.setVisibility(View.VISIBLE);
            success.setVisibility(View.VISIBLE);
            Toast.makeText(Reservations.this, "Reservation for " + pName + " has been made! ", Toast.LENGTH_SHORT).show();
        }
        else{
            showError(response.getMessage());
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
