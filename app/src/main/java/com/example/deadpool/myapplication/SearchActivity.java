package com.example.deadpool.myapplication;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class SearchActivity extends Activity {
    TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textView = (TextView) findViewById(R.id.search_text_view);
        findProductsAndUpdate(R.id.search_list_view, getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        System.out.println("calling new intent.............");
        setIntent(intent);
        findProductsAndUpdate(R.id.search_list_view, getIntent());
    }

    private void findProductsAndUpdate(int listViewID, Intent intent) {

        System.out.println("finding prod.....");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);
            textView.setText("Searched for: " + query);
            System.out.println("has search............."+ query);

            ListView listView = (ListView) findViewById(listViewID);
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

            Cursor cursor = databaseHelper.getSqLiteDatabase().rawQuery("select * from "+ DatabaseHelper.TABLE_NAME +
                    " where " + DatabaseHelper.NAME +
                    " like ? or " +
                    DatabaseHelper.CATEGORY +
                    " like ?"
                    , new String[]{"%"+query+"%", "%"+query+"%"});
            String [] fromColumns = {DatabaseHelper.NAME, DatabaseHelper.CATEGORY};
            int [] viewIDS = {R.id.product_name, R.id.product_category};
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.product_list_layout, cursor, fromColumns, viewIDS, 0);
            System.out.println("adapter  count ---------------- " + simpleCursorAdapter.getCount());

            listView.setAdapter(simpleCursorAdapter);
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
