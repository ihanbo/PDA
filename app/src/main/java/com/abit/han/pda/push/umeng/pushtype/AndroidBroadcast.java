package com.abit.han.pda.push.umeng.pushtype;


import com.abit.han.pda.push.umeng.AndroidNotification;

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
