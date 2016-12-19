package com.abit.han.pda.sendpush.umeng.pushtype;


import com.abit.han.pda.sendpush.umeng.AndroidNotification;

/**
 * 列播
 */
public class AndroidListcast extends AndroidNotification {
	public AndroidListcast(String appkey, String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "listcast");
	}
	
	public void setDeviceToken(String... token) throws Exception {
		if(token==null||token.length==0){
			setPredefinedKeyValue("device_tokens", "ttttt");
			return;
		}
		StringBuilder sb = new StringBuilder(token[0]);
		for (int i = 1; i < token.length; i++) {
			sb.append(",");
			sb.append(token[i]);
		}
		setPredefinedKeyValue("device_tokens", sb.toString());
    }

}