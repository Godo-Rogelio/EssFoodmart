package com.essfoodmart.Dashboard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.essfoodmart.Beverages.activity.BeveragesActivity;
import com.essfoodmart.BigCanteen.activity.BigCanteenActivity;
import com.essfoodmart.MyCart.activity.MyCart;
import com.essfoodmart.MyCart.adapter.MyCartAdapter;
import com.essfoodmart.R;
import com.essfoodmart.SmallCanteen.activity.SmallCanteenActivity;

public class Dashboard extends AppCompatActivity {

    CardView cardBigCanteen, cardSmallCanteen, cardBeverages;
    ImageView btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializations();
    }

    private void initializations()
    {
        cardBigCanteen = findViewById(R.id.cardBigCanteen);
        cardSmallCanteen = findViewById(R.id.cardSmallCanteen);
        cardBeverages = findViewById(R.id.cardBeverages);
        btnCart = findViewById(R.id.btnCart);

        //events
        cardBigCanteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, BigCanteenActivity.class);
                startActivity(i);
            }
        });
        cardSmallCanteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, SmallCanteenActivity.class);
                startActivity(i);
            }
        });
        cardBeverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, BeveragesActivity.class);
                startActivity(i);
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, MyCart.class);
                startActivity(i);
            }
        });
    }
}