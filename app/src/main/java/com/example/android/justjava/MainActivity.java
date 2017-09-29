package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.id.edit;
import static com.example.android.justjava.R.string.chocolate;


public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.add_whipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.add_chocolate);
        boolean hasWhippedCream = whippedCream.isChecked();
        boolean hasChocolate = chocolate.isChecked();
        EditText edit = (EditText) findViewById(R.id.name_field);
        String name = edit.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_subject) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(calculatePrice(hasWhippedCream, hasChocolate), hasWhippedCream, hasChocolate, name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Create the summary of order
     * @param price of the order
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate
     * @param name of the customer
     * @return text summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String summary = getString(R.string.name) + " " + name;

        return summary +
                "\n" + getString(R.string.add_whipped_cream) + " " + hasWhippedCream +
                "\n" + getString(R.string.add_chocolate) + " " + hasChocolate +
                "\n" + getString(R.string.order_quantity) + " " + quantity +
                "\n" + getString(R.string.total) + " " + price +
                "\n" + getString(R.string.thank_you);
    }


    /**
     * This method displays the given text on the screen.
     */

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    public void increment(View view) {
        if (quantity == 10){
            Toast.makeText(this,"You've reached the maximum cups of coffee!", Toast.LENGTH_SHORT).show();
        } else {
            display(++quantity);
        }
    }
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this,"You've reached the minimum cups of coffee!", Toast.LENGTH_SHORT).show();
        } else {
            display(--quantity);
        }
    }
    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param hasWhippedCream is whether the user wants whipped cream topping
     * @param hasChocolate is whether the user wants chocolate topping
     * @return the price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }
        return price * quantity;
    }
}