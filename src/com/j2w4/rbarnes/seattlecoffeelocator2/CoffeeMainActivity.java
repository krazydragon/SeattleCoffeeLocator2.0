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

import com.j2w4.rbarnes.seattlecoffeelocator2.CoffeeDetailFragment.CallListener;
import com.j2w4.rbarnes.seattlecoffeelocator2.CoffeeListFragment.OnLocationSelectedListener;

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


public class CoffeeMainActivity extends FragmentActivity implements OnLocationSelectedListener, CallListener {

	
	String _phoneStr = "";
	Button _callButton;
	CoffeeDetailFragment _fragment;
	static int serviceStarted = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		if(serviceStarted == 0){
			//Start Service load data
			startService(new Intent(this, CoffeeService.class));	
			serviceStarted ++;
			
		}
		
		Crouton.makeText(this, "this works!", Style.ALERT).show();
		
		setContentView(R.layout.fragment_coffee_main);
		_callButton = (Button)findViewById(R.id.callButton);
		_fragment = (CoffeeDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);
		
		
		//If detail page is in layout hide button
		if ((_fragment != null)&& _fragment.isInLayout()){
			
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
	public void onlocationSelected(String number) {
		final Intent detailIntent = new Intent(this, CoffeeDetailActivity.class);
		//check layout
		
		if ((_fragment != null)&& _fragment.isInLayout()){
			
			_phoneStr = "tel:" + number;
			_callButton.setVisibility(View.VISIBLE);
			
		} else {
			//Save color and launch picker activity
			detailIntent.putExtra("phone_number", number);
			
			
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

	

}
