package wuproject.morgagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainScreen extends AppCompatActivity {

    private static final String Home_Value = "Home_Value";
    private static final String Down_Payment = "Down_Payment";
    private static final String Interest_Rate = " Interest_Rate";
    private static final String Property_tax = "Property_tax";


    //Amount being inserted
    private double HomeValue;//user input
    private double DownPayment;
    private double InterestRate;
    private int PropertyTaxes;
    //Accepts User Input
    private EditText homeInput; //user input for Home Payment
    private EditText DownInput; //user input for Down Payment
    private EditText InterestInput;
    private EditText PropertyInput; // custom rate

    //amount of terms
    private EditText FirstTerm; // 15 yr monthly
    //declaring the amount of terms


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if(savedInstanceState == null){
            HomeValue = 0.0;
            DownPayment = 0.0;
            InterestRate = 0.0;
            PropertyTaxes = 0;
        }
        else
        {
            HomeValue = savedInstanceState.getDouble(Home_Value);
            DownPayment = savedInstanceState.getDouble(Down_Payment);
            InterestRate = savedInstanceState.getDouble(Interest_Rate);
            PropertyTaxes = savedInstanceState.getInt(Property_tax);

        }

        homeInput = (EditText)findViewById(R.id.homeInput);
        DownInput = (EditText)findViewById(R.id.DownInput);
        InterestInput = (EditText)findViewById(R.id.InterestInput);
        PropertyInput = (EditText)findViewById(R.id.PropertyInput);

        Button Calculate = (Button)findViewById(R.id.Calculate);
        Calculate.setOnClickListener(CalculateListener);
        Button Reset = (Button)findViewById(R.id.Reset);
        Reset.setOnClickListener(ResetListener);



    }
    public View.OnClickListener CalculateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    housePriceEditText.getWindowToken(), 0);

            Calculate();
        }
    };
    public View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // hide the soft keyboard
            ((InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    housePriceEditText.getWindowToken(), 0);

            Reset();
        }
    };
    private void Reset(){

        //reset every field
        homeInput.setText(String.format("0.0"));// Is this right??
        DownInput.setText("0.0");
        InterestInput.setText("0.0");
        PropertyInput.setText("0");

    }
    // calculate monthly payment
    private double formula(double loan, double rate, int term)
    {
        double c = rate/100/12.;
        double n = term *12 ;
        return loan * (c * Math.pow(1 + c, n )) / ( Math.pow(1 + c,n)-1);
    }

    private void updateMonthlyPayment()
    {
        // calculate monthly payment
        double FirstTerm =
                formula(homeInput,InterestRate, 15);

        // 10, 15 and 30 yr monthly payment EditTexts
        FirstTerm.setText("$" + String.format("%.0f", FirstTerm));

    }




}
