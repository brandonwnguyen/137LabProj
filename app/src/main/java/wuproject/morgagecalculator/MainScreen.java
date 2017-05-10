package wuproject.morgagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

public class MainScreen extends AppCompatActivity {

    private static final String Home_Value = "Home_Value";
    private static final String Down_Payment = "Down_Payment";
    private static final String Interest_Rate = " Interest_Rate";
    private static final String Morgage_Loan = "Morgage_Loan";
    private static final String Property_tax = "Property_tax";


    //Amount being inserted
    private double HomeValue;//user input
    private double DownPayment;
    private double InterestRate;
    private int MorgageLoanLength;
    private int PropertyTaxes;
    //Accepts User Input
    private EditText homeInput; //user input for Home Payment
    private EditText DownInput; //user input for Down Payment
    private EditText InterestInput;
    private EditText PropertyInput; // custom rate
    private EditText MorgageLoanInput; // custom rate
    private EditText FirstTerm;
    private EditText TotalPayment;
    //amount of terms
    //declaring the amount of terms


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if(savedInstanceState == null){
            HomeValue = 0.0;
            DownPayment = 0.0;
            InterestRate = 0.0;
            MorgageLoanLength = 0;
            PropertyTaxes = 0;
        }
        else
        {
            HomeValue = savedInstanceState.getDouble(Home_Value);
            DownPayment = savedInstanceState.getDouble(Down_Payment);
            InterestRate = savedInstanceState.getDouble(Interest_Rate);
            PropertyTaxes = savedInstanceState.getInt(Property_tax);
            MorgageLoanLength = savedInstanceState.getInt(Morgage_Loan);

        }

        homeInput = (EditText)findViewById(R.id.homeInput);
        DownInput = (EditText)findViewById(R.id.DownInput);
        InterestInput = (EditText)findViewById(R.id.InterestInput);
        PropertyInput = (EditText)findViewById(R.id.PropertyInput);
        MorgageLoanInput = (EditText)findViewById(R.id.MorgageLoanInput);
        TotalPayment =(EditText)findViewById(R.id.TotalPayment);

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
                    homeInput.getWindowToken(), 0);

            calculate();
        }
    };
    private void calculate(){

        //calculate monthly and total payment
        double monthlyIntRate = 0.0, loanAmount = 0.0, monthlyPayment = 0.0, totalPayment = 0.0;
        int months = 0;

        HomeValue = Double.parseDouble(homeInput.getText().toString());
        DownPayment = Double.parseDouble(DownInput.getText().toString());
        InterestRate = Double.parseDouble(InterestInput.getText().toString());
        PropertyTaxes = Integer.parseInt(PropertyInput.getText().toString());

        //put checks for 0 or  out of range values
        if(HomeValue != 0.0 && DownPayment != 0.0 && InterestRate != 0.0 && PropertyTaxes !=0) {

            monthlyIntRate = InterestRate / (12 * 100);
            months = MorgageLoanLength * 12;
            loanAmount = HomeValue - DownPayment;

            monthlyPayment = ((loanAmount * monthlyIntRate) / (1 - Math.pow(1 + monthlyIntRate, -months)));
            MorgageLoanInput.setText(String.format("%.02f", monthlyPayment));

            totalPayment = monthlyPayment * months;
            TotalPayment.setText(String.format("%.02f", totalPayment));
        }


    }
    public View.OnClickListener ResetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // hide the soft keyboard
            ((InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    homeInput.getWindowToken(), 0);

            Reset();
        }
    };
    private void Reset(){

        //reset every field
        homeInput.setText(String.format("0.0"));// Is this right??
        DownInput.setText("0.0");
        InterestInput.setText("0.0");
        PropertyInput.setText("0");
        FirstTerm.setText("0.0");
        TotalPayment.setText("0.0");
    }




}
