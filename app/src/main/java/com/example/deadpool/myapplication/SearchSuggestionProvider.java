package com.example.deadpool.myapplication;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by deadpool on 18/12/14.
 */
public class SearchSuggestionProvider extends ContentProvider{

    public static String AUTHORITY = "com.example.deadpool.myapplication.SearchSuggestionProvider";

    private UriMatcher uriMatcher = buildUriMatcher();
    private HashMap<String, String> aliasMap;
    private static final int SEARCH_SUGGEST = 0;


    private UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        aliasMap = new HashMap<String, String>();
        aliasMap.put(BaseColumns._ID, BaseColumns._ID);
        aliasMap.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA, BaseColumns._ID + " as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA);
        aliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_2, DatabaseHelper.CATEGORY + " as " + SearchManager.SUGGEST_COLUMN_TEXT_2);
        aliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_1, DatabaseHelper.NAME + " as " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)){
            case SEARCH_SUGGEST:
                if(selectionArgs != null){
                    selectionArgs[0] = "%"+selectionArgs[0]+"%";
                }
                String [] selectionString = new String[] {selectionArgs[0], selectionArgs[0]};
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
                selection = DatabaseHelper.NAME + " like ? or " + DatabaseHelper.CATEGORY + " like ? ";
                SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
                sqLiteQueryBuilder.setProjectionMap(aliasMap);
                sqLiteQueryBuilder.setTables(DatabaseHelper.TABLE_NAME);
                Cursor cursor =  sqLiteQueryBuilder.query(sqLiteDatabase, new String[]{BaseColumns._ID, SearchManager.SUGGEST_COLUMN_INTENT_DATA, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_TEXT_2}, selection, selectionString, null, null, null, "10");
                System.out.println("--------------: " + Arrays.toString(cursor.getColumnNames()));
                return  cursor;
            default:
                System.out.println("no match");
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
