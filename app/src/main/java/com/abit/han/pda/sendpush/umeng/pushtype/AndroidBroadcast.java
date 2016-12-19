package com.abit.han.pda.sendpush.umeng.pushtype;


import com.abit.han.pda.sendpush.umeng.AndroidNotification;

/**
 * 广播
 */
public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
	}
}
