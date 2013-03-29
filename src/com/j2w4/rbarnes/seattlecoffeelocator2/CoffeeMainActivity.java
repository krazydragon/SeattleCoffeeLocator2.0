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

import com.j2w4.rbarnes.seattlecoffeelocator2.CoffeeDetailFragment.CallListener;
import com.j2w4.rbarnes.seattlecoffeelocator2.CoffeeListFragment.OnLocationSelectedListener;
import com.rbarnes.other.LocationDB;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.net.Uri;
import android.os.Bundle;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CoffeeMainActivity extends FragmentActivity implements OnLocationSelectedListener, CallListener {

	
	String _phoneStr = "";
	Button _callButton;
	CoffeeDetailFragment _fragment;
	static HashMap<String, String> _currentLocation = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		
		
		if(LocationDB.checkDataBase() == 0){
			Crouton.makeText(this, "Thank you for installing my app!", Style.INFO).show();
			startService(new Intent(this, CoffeeService.class));
		}
		
		setContentView(R.layout.fragment_coffee_main);
		_callButton = (Button)findViewById(R.id.callButton);
		_fragment = (CoffeeDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);
		
		
		//If detail page is in layout hide button
		if ((_fragment != null)&& _fragment.isInLayout() && (_currentLocation != null)){
			displayFragment();
			_callButton.setVisibility(View.GONE);
			
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.coffee_main, menu);
		return true;
	}

	@Override
	public void onlocationSelected(HashMap<String, String> currentLocation) {
		final Intent detailIntent = new Intent(this, CoffeeDetailActivity.class);
		//check layout
		
		if ((_fragment != null)&& _fragment.isInLayout()){
			_currentLocation = new HashMap<String, String>();
			_currentLocation = currentLocation;
			displayFragment();
			
		} else {
			//Save color and launch picker activity
			detailIntent.putExtra("current_location", currentLocation);
			
			
			startActivityForResult(detailIntent,0);
		}
		
	}

	@Override
	public void onButtonPress() {
		try {
		    Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse(_phoneStr));
		    startActivity(callIntent);
		    } catch (ActivityNotFoundException e) {
		    Log.e("CALL", "Call failed", e);
		}
		
	}

	private void displayFragment(){
		((TextView)findViewById(R.id.titleValue)).setText(_currentLocation.get("Title").toString());
		((TextView)findViewById(R.id.addressValue)).setText(_currentLocation.get("Address").toString());
		((TextView)findViewById(R.id.cityValue)).setText(_currentLocation.get("City").toString());
		((TextView)findViewById(R.id.stateValue)).setText(_currentLocation.get("State").toString());
		_phoneStr = "tel:" + (_currentLocation.get("Phone").toString());
		_callButton.setVisibility(View.VISIBLE);
	}
	
	public void runService(){
		startService(new Intent(this, CoffeeService.class));
	}

}
