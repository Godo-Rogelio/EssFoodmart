package com.essfoodmart.MyCart.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import com.essfoodmart.Database.DBHelper;
import com.essfoodmart.Model.OrderModel;
import com.essfoodmart.MyCart.adapter.MyCartAdapter;
import com.essfoodmart.MyCart.adapter.OrderSummaryAdapter;
import com.essfoodmart.R;
import com.google.gson.Gson;

import java.util.List;

public class MyCart extends AppCompatActivity implements MyCartAdapter.onClickItemListener{

    RecyclerView bcRecyclerMenu;
    Button btnPayment;
    MyCartAdapter adapter;
    List<OrderModel> orderItems;

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

        orderItems = DBHelper.getCartItems();

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
        for(int a = 0; a < orderItems.size(); a++)
        {
            String itemPriceFirstCharRemoved = orderItems.get(a).getFoodPrice().substring(1);
            int itemPrice = Integer.parseInt(itemPriceFirstCharRemoved);
            int itemQty = orderItems.get(a).getQuantity();
            totalAmount += itemPrice * itemQty;
        }

        //int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        //int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);


        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_NoTitleBar_Fullscreen);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_payment, null);

        TextView txtOrderTotal  = dialogView.findViewById(R.id.txtOrderTotal);
        Button btnPay  = dialogView.findViewById(R.id.btnPay);
        RecyclerView recyclerOrders  = dialogView.findViewById(R.id.recyclerOrders);

        txtOrderTotal.setText(String.valueOf(totalAmount));

        OrderSummaryAdapter adapter = new OrderSummaryAdapter(MyCart.this, orderItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerOrders.setLayoutManager(layoutManager);
        recyclerOrders.addItemDecoration(new DividerItemDecoration(MyCart.this, LinearLayoutManager.VERTICAL));
        recyclerOrders.setAdapter(adapter);


        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        //alertDialog.getWindow().setLayout(width, height);

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

    @Override
    public void onClickItem(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Message");
        builder.setMessage("Do you want to remove " + orderItems.get(position).getFoodName() +" " + orderItems.get(position).getQuantity());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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