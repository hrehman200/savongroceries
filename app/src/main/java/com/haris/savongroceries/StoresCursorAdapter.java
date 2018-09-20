package com.haris.savongroceries;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CursorAdapter;

public class StoresCursorAdapter extends CursorAdapter {

    public StoresCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_store, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtStore = (TextView) view.findViewById(R.id.txtStore);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        txtStore.setText(name);
    }
}
