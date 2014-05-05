package co.cm.fragement;

import java.util.ArrayList;
import java.util.List;
import co.cm.fragement.R;
import android.app.Fragment;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragementDetails extends Fragment {
	TextView mac_address, bssid, ip_address, id, info, wifiText;
	ListView listView;
	LinearLayout wifiLinear;
	RelativeLayout save, wifi;
	boolean ThreadFlag = false;
	WifiAdapter wifiAdapter;
	private List<ScanResult> mWifiList = new ArrayList<ScanResult>();// ɨ��������������б�
	private List<WifiConfiguration> mWifiConfiguration = null;// ���������б�
	private int nowWifiState = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_detail, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setView();
		// setListener();
		setWifiShow();

	}

	public void setWifiShow() {
		save.setVisibility(View.GONE);
		wifi.setVisibility(View.VISIBLE);
		stopWifiThread();
		refreshWifi();

	}

	public void setSaveShow() {
		stopWifiThread();
		save.setVisibility(View.VISIBLE);
		wifi.setVisibility(View.GONE);
	}

	public void setView() {
		// -----------------wifi-----------------
		wifiText = (TextView) getView().findViewById(R.id.wifiText);
		mac_address = (TextView) getView().findViewById(R.id.mac_address);
		bssid = (TextView) getView().findViewById(R.id.bssid);
		ip_address = (TextView) getView().findViewById(R.id.ip_address);
		id = (TextView) getView().findViewById(R.id.id);
		info = (TextView) getView().findViewById(R.id.info);
		listView = (ListView) getView().findViewById(R.id.listview);
		wifiLinear = (LinearLayout) getView().findViewById(R.id.wifiLinear);
		save = (RelativeLayout) getView().findViewById(R.id.save);
		wifi = (RelativeLayout) getView().findViewById(R.id.wifi);
		wifiAdapter = new WifiAdapter();
		listView.setAdapter(wifiAdapter);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			nowWifiState = WifiAdmin.getWifiAdmin().checkState();
			// ��wifi��ʱ��ˢ��wifi�б������
			if (nowWifiState == 3) {
				mWifiList = WifiAdmin.getWifiAdmin().GetWifiList();
				// ����տ�ʼ����wifi�б�Ϊ�գ��򴴽�һ��ʵ������wifi������null���������adpter���汨��
				if (mWifiList != null) {
					// ���wifi�б����ı䣬����£�else��������s
					if (!mWifiList.toString().equals(
							WifiAdmin.getWifiAdmin().getLastWifiList()
									.toString())) {
						WifiAdmin.getWifiAdmin().setLastWifiList(mWifiList);
						wifiAdapter.notifyDate();
					}
				} else {
					mWifiList = new ArrayList<ScanResult>();
				}

			}
			refreshMeathod();

			super.handleMessage(msg);
		}

	};

	// ˢ��wifi��״̬
	public void refreshWifi() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				ThreadFlag = true;
				while (ThreadFlag) {
					// Log.i("111", WifiAdmin.getWifiAdmin().checkState() +
					// "!!!");
					Message msg = handler.obtainMessage();
					handler.sendMessage(msg);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();

	}

	public void refreshMeathod() {
		// Log.i("111", "refreshMeathod");
		// �˴�����switch
		if (nowWifiState == 3) {
			// Log.i("111", "checkState==3");
			// stopWifiThread();
			wifiLinear.setVisibility(View.VISIBLE);
			wifiText.setVisibility(View.INVISIBLE);
			mac_address.setText(WifiAdmin.getWifiAdmin().GetMacAddress() + "");
			bssid.setText(WifiAdmin.getWifiAdmin().GetBSSID() + "");
			ip_address.setText(WifiAdmin.getWifiAdmin().GetIPAddress() + "");
			id.setText(WifiAdmin.getWifiAdmin().GetNetworkId() + "");
			info.setText(WifiAdmin.getWifiAdmin().GetWifiInfo() + "");
			// WifiAdmin.getWifiAdmin().StartScan();
			// if (WifiAdmin.getWifiAdmin().GetWifiList() != null) {
			// mWifiList = WifiAdmin.getWifiAdmin().GetWifiList();
			// }
			// mWifiConfiguration = WifiAdmin.getWifiAdmin()
			// .getmWifiConfiguration();

			// Log.i("111", mWifiList + "===");

		} else if (nowWifiState == 1) {
			// Log.i("111", "checkState==1");
			wifiText.setVisibility(View.VISIBLE);
			wifiLinear.setVisibility(View.INVISIBLE);
			wifiText.setText("Ҫ�鿴���õ����磬���wifi");
		} else if (nowWifiState == 2) {
			// Log.i("111", "checkState==2");
			wifiText.setVisibility(View.VISIBLE);
			wifiLinear.setVisibility(View.INVISIBLE);
			wifiText.setText("wifi���ڴ�");
		} else if (nowWifiState == 4) {
			// Log.i("111", "checkState==4");
			wifiText.setVisibility(View.VISIBLE);
			wifiLinear.setVisibility(View.INVISIBLE);
			wifiText.setText("wifi���ڹر�");
		} else {
			// Log.i("111", "checkState==0");
			wifiText.setVisibility(View.VISIBLE);
			wifiLinear.setVisibility(View.INVISIBLE);
			wifiText.setText("�Ҳ�֪��wifi������ʲô");
		}
	}

	public void stopWifiThread() {
		ThreadFlag = false;
	}

	public class WifiAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			// return mWifiList.size();
			return mWifiList.size();

		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mWifiList.get(position);
			// return mWifiList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;

			final ChatViewHolder vh;

			if (convertView == null) {
				vh = new ChatViewHolder();
				view = View.inflate(WifiAdmin.getWifiAdmin().getmContext(),
						R.layout.wifi_list, null);
				vh.wifi_name = (TextView) view.findViewById(R.id.wifi_name);

				vh.wifi_name_state = (TextView) view
						.findViewById(R.id.wifi_name_state);

				view.setTag(vh);
			} else {
				vh = (ChatViewHolder) view.getTag();
			}
			vh.wifi_name.setText(mWifiList.get(position).SSID.toString());// ��������֣�Ψһ����WIFI���������
			vh.wifi_name_state.setText(mWifiList.get(position).level + "");
			return view;
		}

		public void notifyDate() {
			notifyDataSetChanged();
		}

	}

	public class ChatViewHolder {
		TextView wifi_name;// ��������֣�Ψһ����WIFI���������
		TextView wifi_name_state;// �����ֵ�WIFI�����ź�ǿ��
	}

}
