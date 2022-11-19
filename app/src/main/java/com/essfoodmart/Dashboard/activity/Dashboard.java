package com.essfoodmart.Dashboard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.essfoodmart.BigCanteen.activity.BigCanteenActivity;
import com.essfoodmart.R;

public class Dashboard extends AppCompatActivity {

    CardView cardBigCanteen, cardSmallCanteen, cardBeverages;

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
                Toast.makeText(Dashboard.this, "SMALL CANTEEN", Toast.LENGTH_LONG).show();
            }
        });
        cardBeverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "BEVERAGES", Toast.LENGTH_LONG).show();
            }
        });
    }
}