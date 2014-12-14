package com.example.deadpool.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ItemDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        updateProductDetails();
    }

    private void updateProductDetails() {
        Intent intent = getIntent();

        String productName = intent.getStringExtra("product_name");
        String productDescription = intent.getStringExtra("product_description");

        TextView productNameView = (TextView) findViewById(R.id.product_name_view);
        TextView productDescriptionView = (TextView) findViewById(R.id.product_description_view);

        productNameView.setText(productName);
        productDescriptionView.setText(productDescription);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
