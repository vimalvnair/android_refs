package com.example.deadpool.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.SQLException;


public class MainActivity extends Activity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        getActionBar().setTitle("Robo test");
        setContentView(R.layout.activity_main);
        System.out.println("will create..................");
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        //databaseHelper.seedTheDB();

        ListView listView = (ListView) findViewById(R.id.list_view);
        Cursor cursor = databaseHelper.getSqLiteDatabase().rawQuery("select * from "+ DatabaseHelper.TABLE_NAME, new String[]{});
        System.out.println("the count ---------------- " + cursor.getColumnName(2));
        cursor.getCount();


        String [] fromColumns = {DatabaseHelper.NAME, DatabaseHelper.CATEGORY};
        int [] viewIDS = {R.id.product_name, R.id.product_category};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.product_list_layout, cursor, fromColumns, viewIDS, 0);
        System.out.println("adapter  count ---------------- " + simpleCursorAdapter.getCount());

        listView.setAdapter(simpleCursorAdapter);


    /*    final EditText editText = (EditText) findViewById(R.id.edit_box);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView = (TextView) findViewById(R.id.text_field);
                textView.setText(editText.getText());
            }
        });*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        System.out.println("main act single top.....");
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

        System.out.println( " seeeee----"  + searchManager.getSearchableInfo(getComponentName()));
        System.out.println("comp nammmme " + getComponentName());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {

  //          return true;
    //    }

        switch (item.getItemId()){
            case R.id.extras:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
