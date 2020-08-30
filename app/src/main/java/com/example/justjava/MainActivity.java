package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increment(View view)
    {

        quantity=quantity+1;
        if(quantity>100)
        {
            quantity=100;
            Toast.makeText(this,"You cannot order more than 100 coffees",Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);

    }
    public void decrement(View view)
    {

        quantity = quantity-1;
        if(quantity<0)
        {
            quantity=0;
            Toast.makeText(this,"You cannot order less than 1 coffees",Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);

    }
    public void submitOrder(View view)
    {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("MainActivity","Has whipped scream: "+hasWhippedCream);
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(name,price,hasWhippedCream,hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity((getPackageManager()))!=null)
        {
            startActivity(intent);
        }

    }

    /**
     * Calculate the price of the order
     * @return price of quantity * 5
     */
    private int calculatePrice(boolean addWhippedCream,boolean addChocolate)
    {
        int price = 5;
        if(addChocolate)
        {
            price+=2;
        }
        if(addWhippedCream)
        {
            price+=1;
        }
        return quantity*price;
    }
    /**
     * Create summary of the order.
     *
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */

    private String createOrderSummary(String name,int price,boolean hasWhippedCream,boolean hasChocolate)
    {
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage +="\n"+getString(R.string.order_summary_whipped_cream,hasWhippedCream);
        priceMessage +="\n"+getString(R.string.order_summary_chocolate,hasChocolate);
        priceMessage +="\n"+getString(R.string.order_summary_quantity,quantity);
        priceMessage+="\n"+getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(price));
        priceMessage+="\n"+getString(R.string.thank_you);
        return priceMessage;
    }


    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+numberOfCoffees);
    }
    /**
     * This method displays the given price on the screen.
     */



}