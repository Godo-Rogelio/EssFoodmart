package com.essfoodmart.Dashboard.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.essfoodmart.Beverages.activity.BeveragesActivity;
import com.essfoodmart.BigCanteen.activity.BigCanteenActivity;
import com.essfoodmart.Database.DBHelper;
import com.essfoodmart.MyCart.activity.MyCart;
import com.essfoodmart.MyCart.adapter.MyCartAdapter;
import com.essfoodmart.R;
import com.essfoodmart.SmallCanteen.activity.SmallCanteenActivity;

public class Dashboard extends AppCompatActivity {

    CardView cardBigCanteen, cardSmallCanteen, cardBeverages;
    ImageView btnCart;
    DBHelper dbHelper;

    String[] PERMISSIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializePermission();



    }

    private void initializePermission()
    {
            PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
        else
        {
            initializations();
        }
    }

    private void initializations()
    {

        cardBigCanteen = findViewById(R.id.cardBigCanteen);
        cardSmallCanteen = findViewById(R.id.cardSmallCanteen);
        cardBeverages = findViewById(R.id.cardBeverages);
        btnCart = findViewById(R.id.btnCart);

        dbHelper = new DBHelper(Dashboard.this);

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

    public static boolean hasPermissions(Context context, String... permissions) { //Permission
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==1)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                initializations();

        }
    }
}