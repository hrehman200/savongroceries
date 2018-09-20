package com.haris.savongroceries;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;

public class StoresActivity extends ListActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stores);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor storeCursor = db.rawQuery("SELECT id AS _id, name FROM stores", null);

        StoresCursorAdapter cursorAdapter = new StoresCursorAdapter(this, storeCursor);
        setListAdapter(cursorAdapter);
    }
}

