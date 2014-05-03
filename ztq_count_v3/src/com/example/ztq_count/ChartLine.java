/**
 * 
 */
package com.example.ztq_count;

import android.app.Activity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author Administrator
 * 
 */
public class ChartLine extends Activity
{

	//SurfaceView sfv;

	//SurfaceHolder sfh;

	int Y_axis[] = new int[getWindowManager().getDefaultDisplay().getWidth()];

	int[] X_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];

	//int centerY = (getWindowManager().getDefaultDisplay().getHeight() - sfv.getTop()) / 2;// 中心线

	int oldX, oldY;// 上一个XY点

	int currentX;// 当前绘制到的X轴上的点

}
