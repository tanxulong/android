package com.example.testfragmentsplit;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

import com.example.testfragmentsplit.FragmentDown.FragmentDownListener;
import com.example.testfragmentsplit.FragmentWeather.FragmentWeatherListener;

public class MainActivity extends Activity implements FragmentDownListener, FragmentWeatherListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragmentUpDefault();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onFragmentWeatherViewClicked(int viewId) {
		showDefaultFragment();	
		
	}

	@Override
	public void onFragmentDownViewClicked(int position) {
		showWeatherFragment();		
	}
    
	private void showFragmentUpDefault()
    {
		final FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
		final FragmentUp mFragmentUp = FragmentUp.getInstance();
    	
    	ft.replace(R.id.fragment_up_layout, mFragmentUp);
    	ft.commit();
    }
	
	private void showDefaultFragment()
	{
		final FragmentTransaction ft = getFragmentManager().beginTransaction();
    	ft.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_out_down);
    	final FragmentUp mFragmentUp = FragmentUp.getInstance();
    	ft.replace(R.id.fragment_up_layout, mFragmentUp);
    	ft.commit();
	}
	
	private void showWeatherFragment()
	{
	    	final FragmentTransaction ft = getFragmentManager().beginTransaction();
	    	ft.setCustomAnimations(R.animator.slide_in_down, R.animator.slide_out_up);
	    	final FragmentWeather fw = FragmentWeather.getInstance();
	    	ft.replace(R.id.fragment_up_layout, fw);
	    	ft.commit();
    }
	
}
