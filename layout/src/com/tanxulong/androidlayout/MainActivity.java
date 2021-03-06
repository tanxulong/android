package com.tanxulong.androidlayout;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		ActionBar.OnNavigationListener, OnClickListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_section1),
								getString(R.string.title_section2),
								getString(R.string.title_section3), }), this);
		super.findViewById(R.id.toastBtn).setOnClickListener(this);
		super.findViewById(R.id.notificationBtn0).setOnClickListener(this);
		super.findViewById(R.id.notificationBtn1).setOnClickListener(this);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
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

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
		return true;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.toastBtn:
			showToast();
			break;
		case R.id.notificationBtn0:
			showNormalNotification();
			break;
		case R.id.notificationBtn1:
			showBigNotification();
			break;

		}
	}

	/**
	 * Customized toast
	 */
	private void showToast() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.custom_toast,
				(ViewGroup) findViewById(R.id.custom_toast_layout_id));
		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);

		TextView text = (TextView) layout.findViewById(R.id.textView);
		text.setText("From Russia with love");

		Toast toast = new Toast(getApplicationContext());
		// set toast location
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	/**
	 * normal notification
	 */
	private void showNormalNotification() {
		// Context mContext = getApplicationContext();
		Intent intent = new Intent(this, NotificationReceiverActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		Notification notification = new Notification.Builder(this)
				.setContentTitle("This is Notification title")
				.setContentText("This is Notification Text.")
				.setSmallIcon(R.drawable.icon).setContentIntent(pIntent)
				.setAutoCancel(true)
				.setDeleteIntent(pIntent)
				.addAction(R.drawable.icon, "Call", pIntent)
				.addAction(R.drawable.icon, "More", pIntent)
				.addAction(R.drawable.icon, "And More", pIntent).build();
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);

	}

	private void showBigNotification() {
		// Context mContext = getApplicationContext();
		Intent intent = new Intent(this, NotificationReceiverActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		String longText = "This is Big Notification Text.This is Big Notification Text."
				+ " This is Big Notification Text.This is Big Notification Text."
				+ "This is Big Notification Text.This is Big Notification Text."
				+ "This is Big Notification Text.This is Big Notification Text."
				+ "This is Big Notification Text.This is Big Notification Text."
				+ "This is Big Notification Text.";
		
		Notification notification = new Notification.Builder(this)
				.setContentTitle("This is Big Notification title")
				.setStyle(new Notification.BigTextStyle().bigText(longText))
				.setSmallIcon(R.drawable.icon).setContentIntent(pIntent)
				.setAutoCancel(true)
				.addAction(R.drawable.icon, "Call", pIntent)
				.addAction(R.drawable.icon, "More", pIntent)
				.addAction(R.drawable.icon, "And More", pIntent).build();
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);

	}
}
