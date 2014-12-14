package com.example.deadpool.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by vimal on 14/12/14.
 */
public class ProductListHelper {
    Activity activity;
    ListView listView;

    public ProductListHelper(Activity activity, ListView listView) {
        this.activity = activity;
        this.listView = listView;
    }

    public void setupListView(Cursor cursor){
        String[] fromColumns = {DatabaseHelper.NAME, DatabaseHelper.CATEGORY};
        int[] viewIDS = {R.id.product_name, R.id.product_description};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(activity, R.layout.product_list_layout,
                cursor, fromColumns, viewIDS, 0);
        listView.setAdapter(simpleCursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout = (LinearLayout) view;
                TextView productName = (TextView) view.findViewById(R.id.product_name);
                TextView productCategory = (TextView) view.findViewById(R.id.product_description);
                Intent intent = new Intent(activity, ItemDetailActivity.class);

                intent.putExtra("product_name", productName.getText());
                intent.putExtra("product_description", productCategory.getText());
                activity.startActivity(intent);
            }
        });


    }

}
