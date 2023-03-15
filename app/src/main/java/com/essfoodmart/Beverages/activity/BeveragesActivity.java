package com.essfoodmart.Beverages.activity;

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

import com.essfoodmart.Beverages.adapter.BeveragesMenuAdapter;
import com.essfoodmart.BigCanteen.activity.BigCanteenActivity;
import com.essfoodmart.BigCanteen.adapter.BCMenuAdapter;
import com.essfoodmart.Model.MenuModel;
import com.essfoodmart.Model.OrderModel;
import com.essfoodmart.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class BeveragesActivity extends AppCompatActivity implements BeveragesMenuAdapter.onClickItemListener{

    RecyclerView bcRecyclerMenu;
    BeveragesMenuAdapter adapter;

    ArrayList<MenuModel> menuItems;
    ArrayList<OrderModel> orderedItem;
    int orderNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverages);

        initializations();
    }

    private void initializations()
    {
        bcRecyclerMenu = findViewById(R.id.bcRecyclerMenu);

        menuItems = new ArrayList<MenuModel>();
        menuItems.add(new MenuModel("CHUCKIE", "P35", R.drawable.chuckie));
        menuItems.add(new MenuModel("FRESH MILK", "P45", R.drawable.fresh_milk));
        menuItems.add(new MenuModel("NESTEA CUPS 12 OZ", "P25", R.drawable.nestea_cups));
        menuItems.add(new MenuModel("MILO CUPS 12 OZ", "P30", R.drawable.milo_cups));
        menuItems.add(new MenuModel("GULAMAN", "P25", R.drawable.gulaman));
        menuItems.add(new MenuModel("JUNGLE JUICE SMALL", "P15", R.drawable.jungle_juice));
        menuItems.add(new MenuModel("SOLA IN CAN LEMON", "P40", R.drawable.sola_in_can_lemon));
        menuItems.add(new MenuModel("SOLA IN CAN RASPBERRY", "P40", R.drawable.sola_in_can_raspberry));
        menuItems.add(new MenuModel("SOLA BOTTLE LEMON", "P60", R.drawable.sola_bottle_lemon));
        menuItems.add(new MenuModel("SOLA BOTTLE RASPBERRY", "P60", R.drawable.sola_bottle_raspberry));
        menuItems.add(new MenuModel("SOLA ICE CREAM VANILLA", "P25", R.drawable.sola_ice_cream_vanilla));
        menuItems.add(new MenuModel("SOLA ICE CREAM CHOCO", "P25", R.drawable.sola_ice_cream_choco));
        menuItems.add(new MenuModel("MINERAL WATER REFILL", "P15", R.drawable.mineral_water));

        adapter = new BeveragesMenuAdapter(BeveragesActivity.this,    menuItems, this);
        bcRecyclerMenu.setLayoutManager(new GridLayoutManager(this, 2));
        bcRecyclerMenu.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {
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
                Toast.makeText(BeveragesActivity.this, "Added " + menuItems.get(position).getFoodName() + orderNumber + " to Cart", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}