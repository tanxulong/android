package com.example.ztq_count;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	// public static SurfaceView sfv ;
	static int width;
	int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// sfv =(SurfaceView)findViewById(R.id.SurfaceView01);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		Log.i("系统信息", "该设备的分辨是：" + width + "*" + height);
		Geomark.right = width - 35;
		Geomark.gapX = (width - 45) / 23;

		Rain.right = width - 42;// 17*24=408
		Rain.gapX = (width - 45) / 25;

		RainAnimotion.right = width - 42;// 17*24=408
		RainAnimotion.gapX = (width - 45) / 25;

		final LinearLayout ll1 = (LinearLayout) findViewById(R.id.linechart1);
		LinearLayout tl1 = (LinearLayout) findViewById(R.id.templl);

		final Geomark geomark1 = (Geomark) findViewById(R.id.geomark_view1);

		tl1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ll1.getVisibility() == 0) {
					ll1.setVisibility(View.GONE);
					geomark1.setVisibility(View.GONE);
				} else {
					ll1.setVisibility(View.VISIBLE);// 设置可见
					geomark1.setVisibility(View.VISIBLE);
				}

			}
		});
		final LinearLayout ll2 = (LinearLayout) findViewById(R.id.linechart2);
		LinearLayout tl2 = (LinearLayout) findViewById(R.id.windll);

		final Geomark geomark2 = (Geomark) findViewById(R.id.geomark_view2);

		tl2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ll2.getVisibility() == 0) {
					ll2.setVisibility(View.GONE);
					geomark2.setVisibility(View.GONE);
				} else {
					ll2.setVisibility(View.VISIBLE);// 设置可见
					geomark2.setVisibility(View.VISIBLE);
				}

			}
		});
		final LinearLayout ll3 = (LinearLayout) findViewById(R.id.linechart3);
		LinearLayout tl3 = (LinearLayout) findViewById(R.id.rainll);

		final RainAnimotion geomark3 = (RainAnimotion) findViewById(R.id.geomark_view3);

		tl3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ll3.getVisibility() == 0) {
					ll3.setVisibility(View.GONE);
					geomark3.setVisibility(View.GONE);
				} else {
					ll3.setVisibility(View.VISIBLE);// 设置可见
					geomark3.setVisibility(View.VISIBLE);
				}

			}
		});
		final LinearLayout ll4 = (LinearLayout) findViewById(R.id.linechart4);
		LinearLayout tl4 = (LinearLayout) findViewById(R.id.wetll);
		final PinChart geomark4 = (PinChart) findViewById(R.id.geomark_view4);
		// final PinChart geomark4 = (PinChart)
		// findViewById(R.id.geomark_view4);

		tl4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// float[] init = {};
				if (ll4.getVisibility() == 0) {
					ll4.setVisibility(View.GONE);
					geomark4.setVisibility(View.GONE);
					for (int i = 0; i < PinChart.humidity.length; i++) {
						geomark4.mSweep[i] -= PinChart.humidity[i];
					}
				} else {
					ll4.setVisibility(View.VISIBLE);// 设置可见
					geomark4.setVisibility(View.VISIBLE);
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
