package com.abit.han.pda.sendpush.umeng.pushtype;


import com.abit.han.pda.sendpush.umeng.AndroidNotification;

/**
 * 文件播(多个device_token可通过文件形式批量发送）
 */
public class AndroidFilecast extends AndroidNotification {
	public AndroidFilecast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "filecast");	
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}