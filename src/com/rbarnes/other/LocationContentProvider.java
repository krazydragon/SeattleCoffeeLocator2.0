/*
 * project	SeattleCoffeeLocator2.0
 * 
 * package	com.rbarnes.other
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Mar 26, 2013
 */
package com.rbarnes.other;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class LocationContentProvider extends ContentProvider{

	public static final String AUTHORITY = "content://com.rbarnes.other.LocationContentProvider";
	private static final String BASE_PATH = "locations";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY + "/" + BASE_PATH);
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/locationDB";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	        + "/locationDB";
	
	
	
	private SQLiteDatabase db;
	
	//URiMatcher to match client URis
	 public static final int LOCATIONS = 1;
	 public static final int LOCATION = 2;
	 
	 
	static final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
	 static{
	  matcher.addURI(AUTHORITY, BASE_PATH , LOCATIONS );
	  matcher.addURI(AUTHORITY, BASE_PATH + "/#", LOCATION);
	 }
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int retVal = db.delete(LocationDB.TABLE_NAME, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);

		return retVal;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues inValues) {
		ContentValues values = new ContentValues(inValues);
		long rowId = db.insert(LocationDB.TABLE_NAME, null, values);
		if(rowId > 0){
		Uri url = ContentUris.withAppendedId(CONTENT_URI, rowId);
		getContext().getContentResolver().notifyChange(url, null);

		return uri;
		}else{
		throw new SQLException("Failed to insert row into " + uri);
		}
	}

	@Override
	public boolean onCreate() {
		db = new LocationDB(getContext()).getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sort) {

		
		Cursor c = db.query(LocationDB.TABLE_NAME, projection, selection, selectionArgs, null, null, sort);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
		

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int retVal = db.update(LocationDB.TABLE_NAME, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);

		return retVal;
	}

}
