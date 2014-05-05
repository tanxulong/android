package co.cm.fragement;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

public class WifiAdmin {
	private static WifiAdmin wifiAdmin;
	private WifiManager mWifiManager = null;
	private WifiInfo mWifiInfo = null;
	private List<ScanResult> mWifiList = new ArrayList<ScanResult>();// ɨ��������������б�
	private List<ScanResult> lastWifiList = new ArrayList<ScanResult>();// ɨ��������������б�
	private List<WifiConfiguration> mWifiConfiguration = null;// ���������б�
	private WifiLock mWifiLock = null;
	Context mContext;
	private int lastWifiState = 0;// �ϴ�����״̬

	public List<ScanResult> getLastWifiList() {
		return lastWifiList;
	}

	public void setLastWifiList(List<ScanResult> lastWifiList) {
		this.lastWifiList = lastWifiList;
	}

	public int getLastWifiState() {
		return lastWifiState;
	}

	public void setLastWifiState(int lastWifiState) {
		this.lastWifiState = lastWifiState;
	}

	public static WifiAdmin getWifi() {
		return wifiAdmin;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public static WifiAdmin getWifiAdmin() {
		if (wifiAdmin == null) {
			wifiAdmin = new WifiAdmin();

		}
		return wifiAdmin;
	}

	public void getWifiMeathod() {
		mWifiManager = (WifiManager) mContext
				.getSystemService(mContext.WIFI_SERVICE);
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	public void OpenWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);// ��wifi
		} else {
			Log.i("111", "open ʧ��");
		}
	}

	public void CloseWife() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);// �ر�wifi
		} else {
			Log.i("111", "close ʧ��");
		}
	}

	public void lockWifi() {
		mWifiLock.acquire();// ����wifi
	}

	public void rlockWifi() {
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();// ����wifi
		}
	}

	// ��鵱ǰwifi״̬
	// ����WIFI������״̬����һϵ�е����γ�������ʾ�ġ�
	// ����1.WIFI_STATE_DISABLED : WIFI���������ã�1��
	// ����2.WIFI_STATE_DISABLING : WIFI�������ڹرգ�0��
	// ����3.WIFI_STATE_ENABLED : WIFI�������ã�3��
	// ����4.WIFI_STATE_ENABLING : WIFI�����ڴ򿪣�2�� ��WIFI������Ҫһ��ʱ�䣩
	// ����5.WIFI_STATE_UNKNOWN : δ֪����״̬
	public int checkState() {
		return mWifiManager.getWifiState();
	}

	public void Createwifilock() {
		mWifiLock = mWifiManager.createWifiLock("Testss");// ����һ��wifilock
	}

	public List<WifiConfiguration> GetConfinguration() {
		return mWifiConfiguration;// �õ����úõ�����
	}

	public void ConnectConfiguration(int index) {
		if (index > mWifiConfiguration.size()) {
			return;
		}
		mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
				true);// �������úõ�ָ��ID������
	}

	public void StartScan() {
		mWifiManager.startScan();
		// �õ�ɨ����
		mWifiList = mWifiManager.getScanResults();
		// �õ����úõ���������
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();
	}

	// �õ������б�
	public List<ScanResult> GetWifiList() {
		mWifiManager.startScan();
		// �õ�ɨ����
		mWifiList = mWifiManager.getScanResults();
		return mWifiList;
	}

	public List<WifiConfiguration> getmWifiConfiguration() {
		return mWifiConfiguration;
	}

	// �鿴ɨ����
	public StringBuilder LookUpScan() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < mWifiList.size(); i++) {
			stringBuilder
					.append("Index_" + new Integer(i + 1).toString() + ":");
			// ��ScanResult��Ϣת����һ���ַ�����
			// ���аѰ�����BSSID��SSID��capabilities��frequency��level
			stringBuilder.append((mWifiList.get(i)).toString());
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}

	// �õ�MAC��ַ
	public String GetMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	// �õ�������BSSID
	public String GetBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	// �õ�IP��ַ
	public int GetIPAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	// �õ����ӵ�ID
	public int GetNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	// �õ�WifiInfo��������Ϣ��
	public String GetWifiInfo() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}

	// ���һ�����粢����
	public void AddNetwork(WifiConfiguration wcg) {
		int wcgID = mWifiManager.addNetwork(wcg);
		mWifiManager.enableNetwork(wcgID, true);
	}

	// �Ͽ�ָ��ID������
	public void DisconnectWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}
}