package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    boolean hasWhippedCream;
    boolean hasChocolate;
    String summary;
    String personName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /**
     * This method is called when the order button is clicked.
     * @return
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.Whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity","Has Chocolate" + hasChocolate);

        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.Chocolate_check_box);
        boolean hasChocolate = ChocolateCheckBox.isChecked();
        Log.v("MainActivity","Has Whipped Cream" + hasWhippedCream);

        EditText personNameEditText = (EditText) findViewById(R.id.personName_EditText);
        String personName = personNameEditText.getText().toString();


        int price = calculatePrice(hasChocolate, hasWhippedCream);
        summary = orderSummary(price, hasWhippedCream, hasChocolate, personName);
        displayMessage(summary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary of  "+ personName);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    //code to write summary of order

    private String orderSummary(int price,boolean hasWhippedCream, boolean hasChocolate, String personName) {

        String summary = getString(R.string.name)+ personName + "\r\n"+ getString(R.string.Whipped_Cream)+ hasWhippedCream + "\r\n"+ getString(R.string.Added_Chocolate)+ hasChocolate + "\r\n" + getString(R.string.Quantity)+ quantity +"\r\n"+ "Total :"+ price +"\r\n"+ getString(R.string.Thank_you);
        return summary;
    }

    /**
     * Calculates the price of the order.
     *
     //* @param quantity is the number of cups of coffee ordered
     */
    public int calculatePrice(boolean hasChocolate, boolean hasWhippedCream){

        int basePrice =  5;

        // price of chocolate is 2
        if (hasChocolate){
            basePrice= basePrice + 2;
        }
        // price of whippedCream is 1
        if (hasWhippedCream){
            basePrice = basePrice + 1;
        }
        int price = basePrice*quantity;

        return price;
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (quantity >99){
            Toast.makeText(this, "You cannot order more than 100 cups of coffe", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void decrement(View view) {
        if (quantity <2){
            Toast.makeText(this, "You cannot order less than 1 cup of coffe", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number2) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number2);
    }
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    
}