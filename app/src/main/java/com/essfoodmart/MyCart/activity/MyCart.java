package com.essfoodmart.MyCart.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.essfoodmart.Model.OrderModel;
import com.essfoodmart.MyCart.adapter.MyCartAdapter;
import com.essfoodmart.R;
import com.google.gson.Gson;

public class MyCart extends AppCompatActivity implements MyCartAdapter.onClickItemListener{

    RecyclerView bcRecyclerMenu;
    Button btnPayment;
    MyCartAdapter adapter;
    OrderModel[] orderItems;

    SharedPreferences sharedPreferences;
    Gson gson;

    int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        initializations();

    }

    private void initializations()
    {
        bcRecyclerMenu = findViewById(R.id.bcRecyclerMenu);
        btnPayment = findViewById(R.id.btnPayment);

        sharedPreferences = getSharedPreferences("my_cart", MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("cart_item", null);
        orderItems = gson.fromJson(json, OrderModel[].class);

        if(orderItems == null)
            orderItems = new OrderModel[0];

        adapter = new MyCartAdapter(MyCart.this, orderItems, this);
        bcRecyclerMenu.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        bcRecyclerMenu.setAdapter(adapter);

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                paymentFunction();
            }
        });
    }

    private void paymentFunction()
    {
        totalAmount = 0;
        for(int a = 0; a < orderItems.length; a++)
        {
            String itemPriceFirstCharRemoved = orderItems[a].getFoodPrice().substring(1);
            int itemPrice = Integer.parseInt(itemPriceFirstCharRemoved);
            int itemQty = orderItems[a].getQuantity();
            totalAmount += itemPrice * itemQty;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_payment, null);

        TextView txtTotalAmount  = dialogView.findViewById(R.id.txtTotalAmount);
        Button btnPay  = dialogView.findViewById(R.id.btnPay);

        txtTotalAmount.setText(String.valueOf(totalAmount));

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                AlertDialog.Builder alert = new AlertDialog.Builder(MyCart.this);
                alert.setTitle("Message")
                        .setMessage("Payment Success!\nThe canteen has received your order.\nPlease proceed to the canteen to receive your order.")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
        alertDialog.show();
    }

    public static OrderModel[] removeTheElement(OrderModel[] arr, int index)
    {

        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (arr == null
                || index < 0
                || index >= arr.length) {

            return arr;
        }

        // Create another array of size one less
        OrderModel[] anotherArray = new OrderModel[arr.length - 1];

        // Copy the elements from starting till index
        // from original array to the other array
        System.arraycopy(arr, 0, anotherArray, 0, index);

        // Copy the elements from index + 1 till end
        // from original array to the other array
        System.arraycopy(arr, index + 1,
                anotherArray, index,
                arr.length - index - 1);

        // return the resultant array
        return anotherArray;
    }

    void refreshPreference(OrderModel[] orderedItem, int position)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(orderedItem);

        editor.putString("cart_item", json);
        editor.apply();

    }

    @Override
    public void onClickItem(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Do you want to remove " + orderItems[position].getFoodName() +" " + orderItems[position].getQuantity());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                orderItems = removeTheElement(orderItems, position);
                refreshPreference(orderItems, position);

                adapter.updateData(orderItems);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}