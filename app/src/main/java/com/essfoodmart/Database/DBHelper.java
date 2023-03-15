package com.essfoodmart.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.essfoodmart.Model.OrderModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper
{

    private static final String DB_NAME = "canteenDB.sqlite";
    private static final int DB_VERSION = 1;
    private static final String orderTable = "orders";

    public static String DB_PATH = "";

    private Context context;
    private static SQLiteDatabase database;

    public DBHelper (Context context)
    {
        this.context = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            DB_PATH = context.getExternalFilesDir(null) + "/DB";
        }

        File _file = new File(DB_PATH);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            if(!_file.exists())
            {
                _file.mkdir();
                InputStream myInput;
                OutputStream outputStream;
                try
                {
                    myInput = context.getAssets().open(DB_NAME);
                    String file = DB_PATH + "/" + DB_NAME;
                    outputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int length = 0;
                    while ((length = myInput.read(buffer)) >= 0)
                    {
                        outputStream.write(buffer, 0, length);
                    }
                    outputStream.flush();
                    myInput.close();
                    outputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void addOrder(OrderModel orderModel)
    {
        SQLiteDatabase db = openDB();
        ContentValues values = new ContentValues();

        values.put("foodName", orderModel.getFoodName());
        values.put("foodPrice", orderModel.getFoodPrice());
        values.put("foodImage", orderModel.getFoodImage());
        values.put("quantity", orderModel.getQuantity());

        db.insert(orderTable, null, values);
        db.close();
    }

    public static List<OrderModel> getCartItems()
    {
        List<OrderModel> foodList = new ArrayList<>();

        SQLiteDatabase db = openDB();
        String query = "SELECT * FROM orders";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String foodName = cursor.getString(1);
                String foodPrice = cursor.getString(2);
                int foodImage = cursor.getInt(3);
                int quantity = cursor.getInt(4);
                OrderModel model = new OrderModel(foodName, foodPrice, foodImage, quantity);
                foodList.add(model);
                // Do something with the values
            } while (cursor.moveToNext());
        }

        // Close the cursor and the database
        cursor.close();
        db.close();

        return foodList;
    }

    public static SQLiteDatabase openDB()
    {
        try
        {
            try
            {
                String PATH = DB_PATH  + "/" + DB_NAME;
                database = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READWRITE);
            }
            catch (Exception ex)
            {
                Log.d("OPENDATABASE", ex.getMessage());
            }
        }
        catch (Exception ex)
        {
            Log.d("OPENDATABASE", ex.getMessage());
        }

        return database;
    }
}
