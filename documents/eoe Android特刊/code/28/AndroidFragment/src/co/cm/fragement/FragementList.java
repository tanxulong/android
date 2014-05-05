package co.cm.fragement;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class FragementList extends Fragment {

	TextView wifi;
	ToggleButton toggleButton;
	TextView saveBut;
	FragementDetails frag_detail;
	boolean isChecked = false;
	boolean butIsRunning = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 在这里初始化fragment的页面
		return inflater.inflate(R.layout.frag_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 由于fragment不是activity，不是oncreated，而是onActivityCreated
		setView();
		setListener();

		startThread();// 启动控制button的线程，当wifi状态不是在1或者3的时候，不可点击，
		// if (frag != null && frag.isInLayout()) {
		// switch (arg2) {
		// case 0:
		// frag.setText("0000");

	}

	public void setListener() {
		saveBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				frag_detail.setSaveShow();

			}
		});
		wifi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				frag_detail.setWifiShow();
				Log.i("111", WifiAdmin.getWifiAdmin().checkState() + "===-=-");
				checktoggleButton();// 当点回到wifi界面时，刷新button的状态
			}
		});

		toggleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("111", isChecked + "/"
						+ WifiAdmin.getWifiAdmin().checkState());
				if (isChecked) {
					WifiAdmin.getWifiAdmin().OpenWifi();
					frag_detail.setWifiShow();
					// toggleButton.setText("关闭");
					toggleButton.setChecked(false);
					isChecked = false;
				} else {
					WifiAdmin.getWifiAdmin().CloseWife();
					frag_detail.setWifiShow();
					// toggleButton.setText("打开");
					toggleButton.setChecked(true);
					isChecked = true;
				}

				toggleButton.setClickable(false);
			}
		});

	}

	//
	public void checktoggleButton() {
		if (WifiAdmin.getWifiAdmin().checkState() == 1) {
			toggleButton.setChecked(true);
			isChecked = true;
		}
		if (WifiAdmin.getWifiAdmin().checkState() == 3) {
			toggleButton.setChecked(false);
			isChecked = false;
		}
	}

	public void setView() {
		wifi = (TextView) getView().findViewById(R.id.wifi);
		toggleButton = (ToggleButton) getView().findViewById(R.id.toggleButton);
		saveBut = (TextView) getView().findViewById(R.id.saveBut);
		// 实例化右面界面，以便操纵里面的方法F
		frag_detail = (FragementDetails) getFragmentManager().findFragmentById(
				R.id.frag_detail);
		// 初始化button的装态
		if (WifiAdmin.getWifiAdmin().checkState() == 3) {
			toggleButton.setChecked(false);
			isChecked = false;
		}
		if (WifiAdmin.getWifiAdmin().checkState() == 1) {
			toggleButton.setChecked(true);
			isChecked = true;
		}
		toggleButton.setClickable(true);

	}

	@Override
	public void onDestroy() {
		frag_detail.stopWifiThread();
		butIsRunning = false;
		super.onDestroy();
	}

	private void startThread() {
		butIsRunning = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (butIsRunning) {
					if (WifiAdmin.getWifiAdmin().checkState() == 3) {
						if (!isChecked) {
							toggleButton.setClickable(true);
						}

					} else if (WifiAdmin.getWifiAdmin().checkState() == 1) {
						if (isChecked) {
							toggleButton.setClickable(true);
						}
					}
				}
			}
		}).start();
	}

}
