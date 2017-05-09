package com.example.brandonnguyen.mortgagecalc;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    public static final String HOUSE_PRICE = "HOUSE_PRICE";
    public static final String DOWN_PAYMENT_AMOUNT = "DOWN_PAYMENT_AMOUNT";
    public static final String ANNUAL_INTEREST_RATE = "ANNUAL_INTEREST_RATE";
    public static final String MORTGAGE_LOAN_LENGTH = "MORTGAGE_LOAN_LENGTH";

    public double housePrice;
    public double downPaymentAmount;
    public double annualInterestRate;
    public int lengthOfMortgageLoan;

    private EditText housePriceEditText;
    private EditText downPaymentAmountEditText;
    private EditText annualInterestRateEditText;
    private EditText lengthOfMortgageLoanEditText;
    private EditText monthlyPaymentEditText;
    private EditText totalPaymentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_calculator);

        if(savedInstanceState == null){
            housePrice = 0.0;
            downPaymentAmount = 0.0;
            annualInterestRate = 0.0;
            lengthOfMortgageLoan = 0;
        }
        else
        {
            housePrice = savedInstanceState.getDouble(HOUSE_PRICE);
            downPaymentAmount = savedInstanceState.getDouble(DOWN_PAYMENT_AMOUNT);
            annualInterestRate = savedInstanceState.getDouble(ANNUAL_INTEREST_RATE);
            lengthOfMortgageLoan = savedInstanceState.getInt(MORTGAGE_LOAN_LENGTH);

        }

        housePriceEditText = (EditText)findViewById(R.id.housePriceEditText);
        downPaymentAmountEditText = (EditText)findViewById(R.id.downPaymentAmountEditText);
        annualInterestRateEditText = (EditText)findViewById(R.id.annualInterestRateEditText);
        lengthOfMortgageLoanEditText = (EditText)findViewById(R.id.lengthOfMortgageLoanEditText);
        monthlyPaymentEditText = (EditText)findViewById(R.id.monthlyPaymentEditText);
        totalPaymentEditText =(EditText)findViewById(R.id.totalPaymentEditText);

        Button calculateButton = (Button)findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(calculateButtonListener);
        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelButtonListener);

    }

    public OnClickListener calculateButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            ((InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    housePriceEditText.getWindowToken(), 0);

            calculate();
        }
    };

    private void calculate(){

        //calculate monthly and total payment
        double monthlyIntRate = 0.0, loanAmount = 0.0, monthlyPayment = 0.0, totalPayment = 0.0;
        int months = 0;

        housePrice = Double.parseDouble(housePriceEditText.getText().toString());
        downPaymentAmount = Double.parseDouble(downPaymentAmountEditText.getText().toString());
        annualInterestRate = Double.parseDouble(annualInterestRateEditText.getText().toString());
        lengthOfMortgageLoan = Integer.parseInt(lengthOfMortgageLoanEditText.getText().toString());

        //put checks for 0 or  out of range values
        if(housePrice != 0.0 && downPaymentAmount != 0.0 && annualInterestRate != 0.0 && lengthOfMortgageLoan !=0) {

            monthlyIntRate = annualInterestRate / (12 * 100);
            months = lengthOfMortgageLoan * 12;
            loanAmount = housePrice - downPaymentAmount;

            monthlyPayment = ((loanAmount * monthlyIntRate) / (1 - Math.pow(1 + monthlyIntRate, -months)));
            monthlyPaymentEditText.setText(String.format("%.02f", monthlyPayment));

            totalPayment = monthlyPayment * months;
            totalPaymentEditText.setText(String.format("%.02f", totalPayment));
        }
        else
        {
            //alert
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);

            builder.setTitle(R.string.missingEntries); // title bar string

            // provide an OK button that simply dismisses the dialog
            builder.setPositiveButton(R.string.OK, null);

            // set the message to display
            builder.setMessage(R.string.provideEntries);

            // create AlertDialog from the AlertDialog.Builder
            AlertDialog errorDialog = builder.create();
            errorDialog.show();
        }


    }

    public OnClickListener cancelButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // hide the soft keyboard
            ((InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    housePriceEditText.getWindowToken(), 0);

            cancel();
        }
    };


    private void cancel(){

        //reset every field
        housePriceEditText.setText(String.format("0.0"));// Is this right??
        downPaymentAmountEditText.setText("0.0");
        annualInterestRateEditText.setText("0.0");
        lengthOfMortgageLoanEditText.setText("0");
        monthlyPaymentEditText.setText("0.0");
        totalPaymentEditText.setText("0.0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mortgage_calculator, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putDouble(HOUSE_PRICE, housePrice);
        outState.putDouble(DOWN_PAYMENT_AMOUNT, downPaymentAmount);
        outState.putDouble(ANNUAL_INTEREST_RATE, annualInterestRate);
        outState.putInt(MORTGAGE_LOAN_LENGTH, lengthOfMortgageLoan);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
