package com.haris.savongroceries;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class StoresActivity extends ListActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stores);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor storeCursor = db.rawQuery("SELECT id AS _id, name FROM stores", null);

        StoresCursorAdapter cursorAdapter = new StoresCursorAdapter(this, storeCursor);
        setListAdapter(cursorAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(this, "Clicked row " + id, Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("store_id", id);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}

