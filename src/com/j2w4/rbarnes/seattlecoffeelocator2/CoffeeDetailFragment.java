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


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class CoffeeDetailFragment extends Fragment implements OnClickListener {
	
private CallListener listener;
	
	public interface CallListener{
		public void onButtonPress();
		
		
	}
	
	// onCreate
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	   }
	   
	   // onActivityCreated
	   @Override
	   public void onActivityCreated(Bundle savedInstanceState) {
	      super.onActivityCreated(savedInstanceState);
	   }
	   
	   // onCreateView
	   @Override
	   public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		   RelativeLayout view = (RelativeLayout)inflater.inflate(R.layout.fragment_coffee_detail,container,false);
		   
		   Button callButton = (Button)view.findViewById(R.id.callButton);
			
		   	callButton.setOnClickListener(this);
	      return view;
	   }



	@Override
	public void onClick(View v) {
		listener.onButtonPress();
		
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			listener = (CallListener)activity;
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString() + "did not Implemnt Call Listener!");
		}
	}	
}
