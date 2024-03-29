package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.market.classes.connectionDB;

public class Signup extends AppCompatActivity {

    EditText Fname, Lname, Email, Passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Fname = findViewById(R.id.idFname);
        Lname = findViewById(R.id.idLname);
        Email = findViewById(R.id.idEmail);
        Passwd = findViewById(R.id.idPassword);
    }
    public void Register(View view){
        connectionDB manager = new connectionDB(this,
                "market", null, 1);
        SQLiteDatabase market = manager.getWritableDatabase();

        String FNAME = Fname.getText().toString();
        String LNAME = Lname.getText().toString();
        String EMAIL = Email.getText().toString();
        String PASSWD = Passwd.getText().toString();

        if(!FNAME.isEmpty() && !LNAME.isEmpty()
                && !EMAIL.isEmpty() && !PASSWD.isEmpty()) {
            //Validation: Don't repeat email if exists.
            Cursor row = market.rawQuery
                    ("SELECT email FROM users " +
                            "WHERE email = '" + EMAIL + "'",null);
            //if(row.moveToFirst())
            if(row.getCount() > 0) {
                Toast.makeText(this,
                        "User already exists.", Toast.LENGTH_SHORT).show();
            }else {
                ContentValues DATA = new ContentValues();
                DATA.put("firstname", FNAME);
                DATA.put("lastname", LNAME);
                DATA.put("email", EMAIL);
                DATA.put("password", PASSWD);
                market.insert("users", null, DATA);
                market.close();
                Fname.setText("");
                Lname.setText("");
                Email.setText("");
                Passwd.setText("");
                Toast.makeText(this, "The user has been created", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "There are empty fields.", Toast.LENGTH_SHORT).show();
            Fname.setError("Field can't be empty");
            Lname.setError("Field can't be empty");
            Email.setError("Field can't be empty");
            Passwd.setError("Field can't be empty");
        }
    }
}
