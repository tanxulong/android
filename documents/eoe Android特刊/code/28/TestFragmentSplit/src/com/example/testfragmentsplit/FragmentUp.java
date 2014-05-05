package com.example.testfragmentsplit;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
	
/**
 * 
 * @author (作者名) chen jian
 * @version (版本标识) 1.0
 * @since (最早使用该方法/类/接口的JDK版本)
 *
 */
public final  class FragmentUp extends Fragment{
		
	private static FragmentUp sInstance = null;  
	private View mRootView = null;
	
	private FragmentUp()
	{
		
	}
	
	public static FragmentUp getInstance() 
	{
        if (null == sInstance) 
        {  
            synchronized (FragmentUp.class) 
            {  
                if (sInstance == null) 
                {  
                	sInstance = new FragmentUp();  
                }  
            }  
        }  
        return sInstance;  
    }  
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_up, container, false);
		return mRootView;
	}
}