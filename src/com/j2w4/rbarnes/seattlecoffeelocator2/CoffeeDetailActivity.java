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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class CoffeeDetailActivity extends FragmentActivity implements CallListener{
  
	
	String _phoneStr = "";
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        
        Intent _mainIntent = getIntent();
        _phoneStr = "tel:" + (_mainIntent.getStringExtra("phone_number"));
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