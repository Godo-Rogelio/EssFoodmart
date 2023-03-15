package com.essfoodmart.BigCanteen.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.essfoodmart.BigCanteen.adapter.BCMenuAdapter;
import com.essfoodmart.Dashboard.activity.Dashboard;
import com.essfoodmart.Database.DBHelper;
import com.essfoodmart.Model.MenuModel;
import com.essfoodmart.Model.OrderModel;
import com.essfoodmart.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BigCanteenActivity extends AppCompatActivity implements BCMenuAdapter.onClickItemListener {

    RecyclerView bcRecyclerMenu;
    BCMenuAdapter adapter;

    ArrayList<MenuModel> menuItems;
    ArrayList<OrderModel> orderedItem;
    int orderNumber = 0;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_canteen);

        initializations();
    }

    private void initializations()
    {
        bcRecyclerMenu = findViewById(R.id.bcRecyclerMenu);

        menuItems = new ArrayList<MenuModel>();

        menuItems.add(new MenuModel("PINAKBET", "P85", R.drawable.pinakbet));
        menuItems.add(new MenuModel("PORK BBQ", "P95", R.drawable.pork_bbq));
        menuItems.add(new MenuModel("SPAGHETTI", "P65", R.drawable.spaghetti));
        menuItems.add(new MenuModel("BEEF TAPA", "P85", R.drawable.beef_tapa));
        menuItems.add(new MenuModel("FRIED CHICKEN", "P80", R.drawable.fried_chicken));
        menuItems.add(new MenuModel("PORK", "P85", R.drawable.pork));

        adapter = new BCMenuAdapter(BigCanteenActivity.this,    menuItems, this);
        bcRecyclerMenu.setLayoutManager(new GridLayoutManager(this, 2));
        bcRecyclerMenu.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position)
    {
        String foodName = menuItems.get(position).getFoodName();
        String foodPrice = menuItems.get(position).getFoodPrice();
        int foodImage = menuItems.get(position).getFoodImage();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.take_order_dialog, null);

        ImageView imgMinus  = dialogView.findViewById(R.id.imgMinus);
        ImageView imgPlus  = dialogView.findViewById(R.id.imgPlus);
        TextView txtOrderNumber  = dialogView.findViewById(R.id.txtOrderNumber);
        Button btnAddOrder  = dialogView.findViewById(R.id.btnAddOrder);
        TextView txtFood  = dialogView.findViewById(R.id.txtFood);
        TextView txtPrice  = dialogView.findViewById(R.id.txtPrice);
        ImageView imageView  = dialogView.findViewById(R.id.imageView);

        txtFood.setText(foodName);
        txtPrice.setText(foodPrice);
        imageView.setImageResource(foodImage);

        orderNumber = Integer.parseInt(txtOrderNumber.getText().toString());
        //Events
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(orderNumber != 1)
                {
                    orderNumber = orderNumber - 1;
                    txtOrderNumber.setText(String.valueOf(orderNumber));
                }
            }
        });
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                orderNumber = orderNumber + 1;
                txtOrderNumber.setText(String.valueOf(orderNumber));
            }
        });


        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                orderedItem = new ArrayList<OrderModel>();


                orderedItem.add(new OrderModel(menuItems.get(position).getFoodName(),
                        menuItems.get(position).getFoodPrice(),
                        menuItems.get(position).getFoodImage(),
                        orderNumber));


                //SAVING IN DB
                OrderModel order = new OrderModel(menuItems.get(position).getFoodName(),
                        menuItems.get(position).getFoodPrice(),
                        menuItems.get(position).getFoodImage(),
                        orderNumber);
                dbHelper = new DBHelper(BigCanteenActivity.this);
                dbHelper.addOrder(order);

                Toast.makeText(BigCanteenActivity.this, "Added " + menuItems.get(position).getFoodName() + orderNumber + " to Cart", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}