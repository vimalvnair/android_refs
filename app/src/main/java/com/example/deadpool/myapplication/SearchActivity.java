package com.example.deadpool.myapplication;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class SearchActivity extends Activity {
    TextView textView = null;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_search);
        textView = (TextView) findViewById(R.id.search_text_view);
        findProductsAndUpdate(R.id.search_list_view, getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        findProductsAndUpdate(R.id.search_list_view, getIntent());
    }

    private void findProductsAndUpdate(int listViewID, Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            textView.setText("Searched for: " + query);
            ListView listView = (ListView) findViewById(listViewID);
            ProductListHelper productListHelper = new ProductListHelper(SearchActivity.this, listView);


            Cursor cursor = databaseHelper.getSqLiteDatabase().rawQuery("select * from "+ DatabaseHelper.TABLE_NAME +
                    " where " + DatabaseHelper.NAME +
                    " like ? or " +
                    DatabaseHelper.CATEGORY +
                    " like ?"
                    , new String[]{"%"+query+"%", "%"+query+"%"});

            productListHelper.setupListView(cursor);
        }else if( Intent.ACTION_VIEW.equals(intent.getAction())) {
            System.out.println("view action hit " + intent.getDataString());
            Cursor cursor = databaseHelper.getSqLiteDatabase().rawQuery("select * from " + DatabaseHelper.TABLE_NAME + " where " + BaseColumns._ID + " = " + intent.getDataString(), null);
            System.out.println("count: " + cursor.getCount());
            cursor.moveToFirst();
            //System.out.println(cursor.getString(0));
            Intent itemDetailIntent = new Intent(this, ItemDetailActivity.class);

            itemDetailIntent.putExtra("product_name", cursor.getString(1));
            itemDetailIntent.putExtra("product_description", cursor.getString(2));
            startActivity(itemDetailIntent);
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
