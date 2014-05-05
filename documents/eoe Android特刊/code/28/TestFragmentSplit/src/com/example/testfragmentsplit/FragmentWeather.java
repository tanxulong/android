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
public final class FragmentWeather extends Fragment{
	private static String sTAG = "FragmentWeather>> ";
	private static FragmentWeather sInstance = null;
	
	private View mRootView = null;
	private Button mBtn = null;
	private TextView mTv = null;
	private FragmentWeatherListener mHostListener = null;
	
	/**
	 * interface which activity should realize it
	 * @author chenjj
	 *
	 */
	public interface FragmentWeatherListener
	{
		void onFragmentWeatherViewClicked(int viewId); 
	}
	
	/** 
     * readResolve方法应对单例对象被序列化时候 
     */  
    private Object readResolve() {  
        return getInstance();  
    }  
   
    public static FragmentWeather getInstance() {  
    	
    	 if (null == sInstance) 
         {  
             synchronized (FragmentWeather.class) 
             {  
                 if (sInstance == null) 
                 {  
                 	sInstance = new FragmentWeather();  
                 }  
             }  
         }  
         return sInstance;  
    }  
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		if (null == container)
		{
			return null;
		}
		
		mRootView = inflater.inflate(R.layout.fragment_weather, container, false); 
		
		return mRootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initView();
		initData();
		initAction();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try
		{
			mHostListener = (FragmentWeatherListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + " must implement FragmentWeatherListener"); 
		}
		finally
		{
			Log.v(sTAG, "onAttach() finally");
		}
	}

	private void initView()
	{
		mBtn = (Button) mRootView.findViewById(R.id.weather_btn); 
		mTv = (TextView) mRootView.findViewById(R.id.weather_tv);
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
					mHostListener.onFragmentWeatherViewClicked(v.getId());
				}
			}
		});
	}
}
