package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.market.classes.connectionDB;

import java.sql.Array;
import java.util.ArrayList;

public class list_users extends AppCompatActivity {

    //Call DataBase class connection
    connectionDB market;
    //Create ListView variable
    ListView userlist;
    //Create an ArrayList variable
    ArrayList<String> listItem;
    //Create an Array adapter variabel
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        //Instant DB Connection
        market = new connectionDB(this, "market",
                null, 1);
        //Create an empty array
        listItem = new ArrayList<>();
        //Call ListView id
        userlist = findViewById(R.id.idUserList);

        //Call viewData method
        viewData();
    }

    private void viewData() {
        Cursor cursor = market.SelectUserData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,
                    "There aren't users", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                listItem.add(cursor.getString(1));
                listItem.add(cursor.getString(2));
            }
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, listItem);
            userlist.setAdapter(adapter);
        }
    }
}
