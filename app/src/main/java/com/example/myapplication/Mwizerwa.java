package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Mwizerwa extends AppCompatActivity {

    private TextInputLayout txtusername;
    private TextInputLayout txtpassword;
    private Button register;
    private TextView clickhere;

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mwizerwa);
        txtusername = findViewById(R.id.username);
        txtpassword = findViewById(R.id.password);
        register =(Button)findViewById(R.id.register);
        openHelper = new DatabaseHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                submit();
            }
        });
    }

    private boolean validateusername() {
        String fname = txtusername.getEditText().getText().toString().trim();

        if (fname.isEmpty()) {
            txtusername.setError("field can't be empty");
            return false;
        } else if (fname.length() > 12) {
            txtusername.setError("username too long");

            return false;
        } else {
            txtusername.setError(null);

            return true;
        }
    }
    private boolean validatepassword() {
        String lname = txtpassword.getEditText().getText().toString().trim();
        if (lname.isEmpty()) {
            txtpassword.setError("field can't be empty");
            return false;
        } else if (lname.length() > 9) {
            txtpassword.setError("password too long");
            return false;
        } else {
            txtpassword.setError(null);
            return true;
        }
    }


    public void submit(){
        if ( !validateusername() | !validatepassword() ){
            return;
        }
        db = openHelper.getWritableDatabase();
        String username = txtusername.getEditText().getText().toString().trim();
        String password = txtpassword.getEditText().getText().toString().trim();
        Cursor c = db.rawQuery(" SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_2 + " =? AND " + DatabaseHelper.COL_3 + " =? ", new String[]{username,password});
        if (c != null) {
            if (c.getCount() > 0) {
                Toast.makeText(getApplicationContext(), username + "  " + "already exists", Toast.LENGTH_SHORT).show();
            }
        }
        if (c.getCount() == 0){

            insertdata(username,password);
        }

    }

    private void insertdata(String username, String password)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2,username);
        contentValues.put(DatabaseHelper.COL_3,password);

        db.insert(DatabaseHelper.TABLE_NAME , null,contentValues);
        Toast.makeText(getApplicationContext(), "registration successful", Toast.LENGTH_SHORT).show();

    }



}
