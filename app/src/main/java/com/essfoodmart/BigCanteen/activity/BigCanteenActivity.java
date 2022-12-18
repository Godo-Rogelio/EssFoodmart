package com.essfoodmart.BigCanteen.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.essfoodmart.BigCanteen.adapter.BCMenuAdapter;
import com.essfoodmart.BigCanteen.model.BCMenuModel;
import com.essfoodmart.R;

import java.util.ArrayList;

public class BigCanteenActivity extends AppCompatActivity implements BCMenuAdapter.onClickItemListener {

    RecyclerView bcRecyclerMenu;
    BCMenuAdapter adapter;

    ArrayList<BCMenuModel> menuItems;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_canteen);

        initializations();
    }

    private void initializations()
    {
        bcRecyclerMenu = findViewById(R.id.bcRecyclerMenu);

        menuItems = new ArrayList<BCMenuModel>();
        menuItems.add(new BCMenuModel("PINAKBET", "P85", R.drawable.pinakbet));
        menuItems.add(new BCMenuModel("PORK BBQ", "P95", R.drawable.pork_bbq));
        menuItems.add(new BCMenuModel("SPAGHETTI", "65", R.drawable.spaghetti));
        menuItems.add(new BCMenuModel("BEEF TAPA", "P85", R.drawable.beef_tapa));
        menuItems.add(new BCMenuModel("FRIED CHICKEN", "P80", R.drawable.fried_chicken));
        menuItems.add(new BCMenuModel("PORK", "P85", R.drawable.pork));

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

        final int[] orderNumber = {Integer.parseInt(txtOrderNumber.getText().toString())};
        //Events
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(orderNumber[0] != 1)
                {
                    orderNumber[0] = orderNumber[0] - 1;
                    txtOrderNumber.setText(String.valueOf(orderNumber[0]));
                }
            }
        });
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                orderNumber[0] = orderNumber[0] + 1;
                txtOrderNumber.setText(String.valueOf(orderNumber[0]));
            }
        });


        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}