/*
 * project	SeattleCoffeeLocator2.0
 * 
 * package	com.j2w4.rbarnes.seattlecoffeelocator2
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Mar 26, 2013
 */
package com.j2w4.rbarnes.seattlecoffeelocator2;

import java.util.HashMap;

import com.rbarnes.other.LocationContentProvider;
import com.rbarnes.other.LocationDB;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class CoffeeListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private OnLocationSelectedListener locationSelectedListener;
    private static final int LOCATION_LIST_LOADER = 2;

    private SimpleCursorAdapter adapter;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String projection[] = { LocationDB.COL_TITLE, LocationDB.COL_ADDRESS, LocationDB.COL_CITY, LocationDB.COL_STATE, LocationDB.COL_PHONE, LocationDB.COL_COORDS};
        Cursor locationCursor = getActivity().getContentResolver().query(
                Uri.withAppendedPath(LocationContentProvider.CONTENT_URI,
                        String.valueOf(id)), projection, null, null, null);
        if (locationCursor.moveToFirst()) {
        	HashMap<String, String> currentLocation = new HashMap<String, String>();
        	
        	currentLocation.put("Title",  locationCursor.getString(0));
			currentLocation.put("Address", locationCursor.getString(1));
			currentLocation.put("City", locationCursor.getString(2));
			currentLocation.put("State", locationCursor.getString(3));
			currentLocation.put("Phone", locationCursor.getString(4));
			currentLocation.put("Coords", locationCursor.getString(5));
        	
        	
        	
        	Log.i("ITEM",locationCursor.getString(0)+locationCursor.getString(1)+locationCursor.getString(2) );
        	
            locationSelectedListener.onlocationSelected(currentLocation);
        }
        locationCursor.close();
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] uiBindFrom = { LocationDB.COL_TITLE };
        int[] uiBindTo = { R.id.title };

        getLoaderManager().initLoader(LOCATION_LIST_LOADER, null, this);

        adapter = new SimpleCursorAdapter(
                getActivity().getApplicationContext(), R.layout.list_item,
                null, uiBindFrom, uiBindTo,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        setListAdapter(adapter);
        setHasOptionsMenu(true);
        
        
    }

    public interface OnLocationSelectedListener {
        public void onlocationSelected(HashMap<String, String> currentLocation);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            locationSelectedListener = (OnLocationSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLocationSelectedListener");
        }
    }

    

    // LoaderManager.LoaderCallbacks<Cursor> methods

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { LocationDB.ID, LocationDB.COL_TITLE };

        CursorLoader cursorLoader = new CursorLoader(getActivity(),
        		LocationContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
