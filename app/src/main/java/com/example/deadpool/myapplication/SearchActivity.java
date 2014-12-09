package com.example.deadpool.myapplication;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class SearchActivity extends Activity {
    TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textView = (TextView) findViewById(R.id.search_text_view);

        findProducts();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        System.out.println("calling new intent.............");
        findProducts();
    }

    private void findProducts() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);


            ListView listView = (ListView) findViewById(R.id.search_list_view);
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

            textView.setText("You Searched for: " +query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
