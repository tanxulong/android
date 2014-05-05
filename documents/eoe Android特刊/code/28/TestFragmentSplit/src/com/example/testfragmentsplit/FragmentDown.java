package com.example.testfragmentsplit;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author (作者名) chen jian
 * @version (版本标识) 1.0
 * @since (最早使用该方法/类/接口的JDK版本)
 *
 */
public final class FragmentDown extends Fragment{
	private View mRootView = null;
	
	private FragmentDownListener mHostListener = null;
	private Button mBtn = null;
	
	public interface FragmentDownListener
	{
		void onFragmentDownViewClicked(int position); 
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mRootView = inflater.inflate(R.layout.fragment_down, container, false);
		return mRootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try
		{
			mHostListener = (FragmentDownListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + " must implement FragmentWeatherListener"); 
		}
		finally
		{
			Log.v("test", "onAttach() finally");
		}
	}
	
	private void initView()
	{
		mBtn = (Button) mRootView.findViewById(R.id.show_weather_btn); 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
		initData();
		initAction();
	}
	
	private void initData()
	{
		
	}
	
	private void initAction()
	{
		mBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != mHostListener)
				{
					mHostListener.onFragmentDownViewClicked(v.getId());
				}
			}
		});
	}
}
