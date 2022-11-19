package com.essfoodmart.BigCanteen.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

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
        menuItems.add(new BCMenuModel("PINAKBET", "P85"));
        menuItems.add(new BCMenuModel("PORK BBQ", "P95"));
        menuItems.add(new BCMenuModel("SPAGHETTI", "65"));
        menuItems.add(new BCMenuModel("BEEF TAPA", "P85"));
        menuItems.add(new BCMenuModel("FRIED CHICKEN", "P80"));
        menuItems.add(new BCMenuModel("PORK", "P85"));

        adapter = new BCMenuAdapter(BigCanteenActivity.this,    menuItems, this);
        bcRecyclerMenu.setLayoutManager(new GridLayoutManager(this, 2));
        bcRecyclerMenu.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.take_order_dialog, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}