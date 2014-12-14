package com.example.deadpool.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.SQLException;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Loading...", true);

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

        scheduledThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                //databaseHelper.seedTheDB();

                final Cursor cursor = databaseHelper.getSqLiteDatabase().rawQuery("select * from " + DatabaseHelper.TABLE_NAME, new String[]{});
                cursor.getCount();

                ListView listView = (ListView) findViewById(R.id.list_view);
                final ProductListHelper productListHelper = new ProductListHelper(MainActivity.this, listView);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView listView = (ListView) findViewById(R.id.list_view);
                        productListHelper.setupListView(cursor);
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.extras:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
