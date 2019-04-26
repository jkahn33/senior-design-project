package nuwc.userloginsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.util.RequestUtil;

/**
 * Created by Vinny on 11/5/18.
 */
public class reserveOptionsPrint extends AppCompatActivity{

    Button submit;
    Button cancel;

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
    TextView selectedPrinter;

    int year = 1997;
    int month = 0;
    int day = 19;
    String printer = "A";
    String userExt;
    String jobDescription;
    String jobSchedule;
    String jobDuration;
    String additionalCom;


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
    Calendar calStart = Calendar.getInstance();
    Calendar calEnd = calStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_options_printer);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                year= 0;
                month= 0;
                day= 0;
                printer = "A";

            } else {
                year= extras.getInt("year");
                month= extras.getInt("month");
                day= extras.getInt("day");
                printer = extras.getString("printer");

            }
        } else {
            year= (Integer) savedInstanceState.getSerializable("year");
            month= (Integer) savedInstanceState.getSerializable("month");
            day= (Integer) savedInstanceState.getSerializable("day");
            printer= (String) savedInstanceState.getSerializable("printer");

        }

        times = (LinearLayout) findViewById(R.id.times);

        endDate = (TextView) findViewById(R.id.endDate);

        employExt = (EditText) findViewById(R.id.employExt);
        printDetails = (EditText) findViewById(R.id.printDetails);
        printName = (EditText) findViewById(R.id.numberGuests);
        startDate = (EditText) findViewById(R.id.startDate);

        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);

        MDY = (TextView) findViewById(R.id.MDY);
        dayWeek = (TextView) findViewById(R.id.dayWeek);
        selectedPrinter = (TextView) findViewById(R.id.selectedPrinter);
        selectedPrinter.setText("Selected Printer: " + printer);

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
        String[] nums3 = new String[1000];
        for(int i=0; i<nums3.length; i++)
            nums3[i] = Integer.toString(i);

        endTimehourP.setMinValue(0);
        endTimehourP.setMaxValue(999);
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

        startDate.setText(simpleDateFormat.format(calStart.getTime()));
        endDate.setText(simpleDateFormat.format(calStart.getTime()));


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
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userExt = employExt.getText().toString();
                //printer = ;
                jobDescription = printName.getText().toString();

                /*generate the timestamp string according to JDBC timestamp standard*/
                String printDay = String.valueOf(day);
                String printMonth = String.valueOf(month + 1);
                String printHour = String.valueOf(startTimeHourP.getValue());
                String printMin = String.valueOf(startTimeMinP.getValue());
                if(printDay.length() == 1) printDay = "0" + printDay;
                if(printMonth.length() == 1) printMonth = "0"+ printMonth;
                if(printHour.length() == 1) printHour = "0"+ printHour;
                if(printMin.length() == 1) printMin = "0"+ printMin;
                jobSchedule = year + "-" + printMonth + "-" + printDay + " " +  printHour + ":" + printMin + ":00";

                jobDuration = endTimehourP.getValue() + ":" + endTimeMinP.getValue();
                additionalCom = printDetails.getText().toString();

                if(userExt.length() != 5){
                    showError("Extension must be 5 digits.");
                }
                else if(jobDescription == ""){
                    showError("Please enter a print name.");
                }
                else if(jobDuration == "0:0"){
                    showError("Please enter a print duration.");
                }
                else {
                    try {
                        addPrinterReservation();
                    } catch (JSONException e) {
                        showError("JSON Format Error");
                        Log.e("EXCEPTION", e.toString());
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(reserveOptionsPrint.this, Reservations.class);
                reserveOptionsPrint.this.startActivity(myIntent);
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
                    startDate.setText(simpleDateFormat.format(calStart.getTime()));

                    calEnd = calStart;
                    calEnd.add(Calendar.HOUR_OF_DAY,endTimehourP.getValue());
                    endDate.setText(simpleDateFormat.format(calStart.getTime()));




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

    public void addPrinterReservation() throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JSONObject body = new JSONObject();

        body.put("userExt", userExt);
        body.put("reservableType", "Printer");
        body.put("reservableId", printer);
        body.put("jobDescription", jobDescription);
        body.put("jobSchedule", jobSchedule);
        body.put("jobDuration", jobDuration);
        body.put("additionalCom", additionalCom);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/newPrinterReservation",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            ResponseObject responseObject = mapper.readValue(response.toString(), ResponseObject.class);
                            verifyResponse(responseObject);
                        } catch (Exception e) {
                            Log.d("EXCEPTION", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.getMessage());
                        //showError(error.getMessage());
                    }
                }
        );
        requestQueue.add(request);
    }

    public void verifyResponse(ResponseObject response) {
        String title = "Error";
        if(response.isSuccess()) {
            title = "Success";
            Intent myIntent = new Intent(reserveOptionsPrint.this, Reservations.class);
            reserveOptionsPrint.this.startActivity(myIntent);
        }
        Context context = this;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(response.getMessage())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void showError(String message){
        Context context = this;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}