package com.abit.han.pda.sendpush.umeng.pushtype;

import com.abit.han.pda.sendpush.umeng.AndroidNotification;

import org.json.JSONObject;

/**
 * 组播
 * (按照filter条件筛选特定用户群, 具体请参照filter参数)
 */
public class AndroidGroupcast extends AndroidNotification {
	public AndroidGroupcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");	
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
