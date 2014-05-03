package com.example.graphexamples;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	public void pieChartHandler(View view) {
		Toast.makeText(this, "pieChartHandler called", Toast.LENGTH_LONG).show();
		PieChart pieChart = new PieChart();
		Intent pieIntent = pieChart.getIntent(this);
		startActivity(pieIntent);
	}

	public void lineChartHandler(View view) {
		Toast.makeText(this, "lineChartHandler called", Toast.LENGTH_LONG).show();
		LineChart lineChart = new LineChart();
		Intent lineIndent = lineChart.getIntent(this);
		startActivity(lineIndent);
	}

	public void histogramHandler(View view) {
		Toast.makeText(this, "histogramHandler called", Toast.LENGTH_LONG).show();
		HistogramChart histogramChart = new HistogramChart();
		Intent histogramIndent = histogramChart.getIntent(this);
		startActivity(histogramIndent);
	}

}
