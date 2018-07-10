package com.klo.kieran.runpaceconversion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.klo.kieran.runpaceconversion.R;

public class MainActivity extends AppCompatActivity {


    EditText secondsInput;
    EditText minutesInput;
    TextView output;
    TextView toText;
    TextView fromText;
    Button converter;
    Boolean mode = false; //False for km, true for mi


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise all inputs
        secondsInput = findViewById(R.id.secondsInput);
        minutesInput = findViewById(R.id.minutesInput);
        output = findViewById(R.id.output);
        toText = findViewById(R.id.toText);
        fromText = findViewById(R.id.fromText);
        converter = findViewById(R.id.converter);

        //Waits for text from inputs to change
        secondsInput.addTextChangedListener(watcher);
        minutesInput.addTextChangedListener(watcher);

        //Switch button listener
        converter.setOnClickListener(listener);


    }

    //Used to watch the inputs
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        //Called after inputs are changed
        public void afterTextChanged(Editable s) {
            double minutes = 0.0;
            double seconds = 0.0;

            //If input is valid, otherwise 0.0
            if(!minutesInput.getText().toString().equals("") && !minutesInput.getText().toString().equals(".")){
                minutes =  Double.parseDouble(minutesInput.getText().toString());
                if(minutes>30){
                    Toast.makeText(MainActivity.this, "Enter a valid number!", Toast.LENGTH_SHORT).show();
                    minutes = 0.0;
                    minutesInput.setText("0");
                }
            }

            //If input is valid, otherwise 0.0
            if(!secondsInput.getText().toString().equals("") && !secondsInput.getText().toString().equals(".")) {
                seconds = Double.parseDouble(secondsInput.getText().toString());
                if(seconds>60){
                    Toast.makeText(MainActivity.this, "Enter a valid number!", Toast.LENGTH_SHORT).show();
                    seconds = 0.0;
                    secondsInput.setText("0");
                }
            }

            //After inputs are decided, send to conversion for multiplication or division
            conversion(minutes, seconds);
        }
    };

    //Sets a global boolean depending on mode from button
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mode){
                mode = false;
                fromText.setText("From mins/km");
                toText.setText("To mins/mi");
                textUpdate(); //Called to update times after button change

            }else{
                mode = true;
                fromText.setText("From mins/mi");
                toText.setText("To mins/km");
                textUpdate(); //Called to update times after button change
            }
        }
    };

    //Mathematical conversion of the time, also outputs the converted pace
    private void conversion(double minutes, double seconds){
        if(mode){ //Convert to km
            double end;

            end = minutes * 60 + seconds;
            end /= 1.60934;

            minutes = (end % 3600) / 60;
            seconds = end % 60;

            output.setText(Double.toString(minutes).split("\\.")[0] + " mins " +String.format( "%.0f", seconds) + " seconds");

        }else{ //Convert to mi
            double end;

            end = minutes * 60 + seconds;
            end *= 1.60934;

            minutes = (end % 3600) / 60;
            seconds = end % 60;
            output.setText(Double.toString(minutes).split("\\.")[0] + " mins " +String.format( "%.0f", seconds) + " seconds");
        }
    }

    //Called on button switch to update times, similar job as text watcher
    private void textUpdate(){
        double minutes = 0.0;
        double seconds = 0.0;
        if(!minutesInput.getText().toString().equals("") && !minutesInput.getText().toString().equals(".")){
            minutes =  Double.parseDouble(minutesInput.getText().toString());
            if(minutes>30){
                Toast.makeText(MainActivity.this, "Enter a valid number!", Toast.LENGTH_SHORT).show();
                minutes = 0.0;
                minutesInput.setText("0");
            }
        }

        if(!secondsInput.getText().toString().equals("") && !secondsInput.getText().toString().equals(".")) {
            seconds = Double.parseDouble(secondsInput.getText().toString());
            if(seconds>60){
                Toast.makeText(MainActivity.this, "Enter a valid number!", Toast.LENGTH_SHORT).show();
                seconds = 0.0;
                secondsInput.setText("0");
            }
        }
        conversion(minutes, seconds);
    }
    
}
