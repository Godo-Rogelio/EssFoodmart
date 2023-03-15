package com.essfoodmart.SmallCanteen.activity;

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

import com.essfoodmart.BigCanteen.activity.BigCanteenActivity;
import com.essfoodmart.Database.DBHelper;
import com.essfoodmart.Model.MenuModel;
import com.essfoodmart.Model.OrderModel;
import com.essfoodmart.R;
import com.essfoodmart.SmallCanteen.adapter.SMMenuAdapter;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Collections;

public class SmallCanteenActivity extends AppCompatActivity implements SMMenuAdapter.onClickItemListener{

    RecyclerView bcRecyclerMenu;
    SMMenuAdapter adapter;

    ArrayList<MenuModel> menuItems;
    ArrayList<OrderModel> orderedItem;
    int orderNumber = 0;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_canteen);

        initializations();
    }

    private void initializations()
    {
        bcRecyclerMenu = findViewById(R.id.bcRecyclerMenu);

        menuItems = new ArrayList<MenuModel>();
        menuItems.add(new MenuModel("CHICKEN CARBONARA", "P50", R.drawable.chicken_carbonara));
        menuItems.add(new MenuModel("SPAGHETTI MEAT SAUCE", "P50", R.drawable.spaghetti_meat_sauce));
        menuItems.add(new MenuModel("BAKED MAC", "P50", R.drawable.baked_mac));
        menuItems.add(new MenuModel("FRENCH FRIES SALT BOWL 320CC", "P50", R.drawable.fries_bowl_salt));
        menuItems.add(new MenuModel("FRENCH FRIES CHEESE BOWL 320CC", "P50", R.drawable.fries_bowl_cheese));
        menuItems.add(new MenuModel("FRENCH FRIES SALT BOWL 220cc", "P50", R.drawable.fries_bowl_salt));
        menuItems.add(new MenuModel("FRENCH FRIES CHEESE BOWL 220cc", "P50", R.drawable.fries_bowl_cheese));
        menuItems.add(new MenuModel("HOTDOG ON STICK", "P35", R.drawable.hotdog_on_stick));
        menuItems.add(new MenuModel("HOTDOG SANDWICH", "P45", R.drawable.hotdog_sandwhich));
        menuItems.add(new MenuModel("CHICKEN BURGER SANDWICH", "P55", R.drawable.chicken_burger));
        menuItems.add(new MenuModel("HAMBURGER SANDWICH", "P60", R.drawable.hamburger));
        menuItems.add(new MenuModel("CHICKEN & CHEESE ON STICK", "P25", R.drawable.no_image_avail));
        menuItems.add(new MenuModel("CHICKEN & CHEESE SANDWICH", "P35", R.drawable.chicken_cheese_sandwhich));
        menuItems.add(new MenuModel("CORNDOG", "P35", R.drawable.corndog));
        menuItems.add(new MenuModel("PIZZA BACON", "P50", R.drawable.pizza_bacon));
        menuItems.add(new MenuModel("PIZZA PEPPERONI", "P50", R.drawable.pizza_peperoni));
        menuItems.add(new MenuModel("SIOMAI WITH RICE", "P45", R.drawable.siomai_rice));
        menuItems.add(new MenuModel("SHARKSFIN WITH RICE", "P45", R.drawable.sharksfin_with_rice));
        menuItems.add(new MenuModel("SIOPAO BIG", "P35", R.drawable.siopao_big));
        menuItems.add(new MenuModel("SIOMAI", "P25", R.drawable.siomai));
        menuItems.add(new MenuModel("SHARKSFIN", "P30", R.drawable.sharksfin));

        adapter = new SMMenuAdapter(SmallCanteenActivity.this,    menuItems, this);
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
            public void onClick(View view) {

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
                dbHelper = new DBHelper(SmallCanteenActivity.this);
                dbHelper.addOrder(order);

                Toast.makeText(SmallCanteenActivity.this, "Added " + menuItems.get(position).getFoodName() + orderNumber + " to Cart", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}