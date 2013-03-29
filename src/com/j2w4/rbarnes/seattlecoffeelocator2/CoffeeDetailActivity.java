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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

public class CoffeeDetailActivity extends FragmentActivity implements CallListener{
  
	
	String _phoneStr = "";
	
	
	
    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        ///set text for detail view
        Intent mainIntent = getIntent();
        HashMap<String, String> location = null;
        location = (HashMap<String, String>) mainIntent.getSerializableExtra("current_location");
        
        
        ((TextView)findViewById(R.id.titleValue)).setText(location.get("Title").toString());
		((TextView)findViewById(R.id.addressValue)).setText(location.get("Address").toString());
		((TextView)findViewById(R.id.cityValue)).setText(location.get("City").toString());
		((TextView)findViewById(R.id.stateValue)).setText(location.get("State").toString());
		_phoneStr = "tel:" + (location.get("Phone").toString());
        
    }
		
	

    // call location
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