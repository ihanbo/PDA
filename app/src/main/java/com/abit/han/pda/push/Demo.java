package com.abit.han.pda.push;

import com.abit.han.pda.event.NewSmsEvent;
import com.abit.han.pda.push.pushtype.AndroidBroadcast;
import com.abit.han.pda.push.pushtype.AndroidCustomizedcast;
import com.abit.han.pda.push.pushtype.AndroidFilecast;
import com.abit.han.pda.push.pushtype.AndroidGroupcast;
import com.abit.han.pda.push.pushtype.AndroidListcast;
import com.abit.han.pda.push.pushtype.AndroidUnicast;
import com.abit.han.pda.util.ll;

import org.json.JSONArray;
import org.json.JSONObject;

public class Demo {
    private static String appkey = "5802099de0f55aca6a000e45";
    private static String appMasterSecret = "yvs5icmg9sihzaharfao7q3d8zgsde4i";
    private static String timestamp = null;
    private static PushClient client = new PushClient();



    public Demo(String key, String secret) {
        try {
            appkey = key;
            appMasterSecret = secret;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 广播
     * @throws Exception
     */
    public static void sendAndroidBroadcast(String ticker,String title,String content,PushSendListener listener)  {
        try {
            AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);
            broadcast.setTicker(ticker);
            broadcast.setTitle(title);
            broadcast.setText(content);
            broadcast.goAppAfterOpen();
            broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            // TODO Set 'production_mode' to 'false' if it's a test device.
            // 设置生产模式
            broadcast.setProductionMode();
            // Set customized fields
            broadcast.setExtraField("test", "helloworld");
            client.send(broadcast,listener);
        } catch (Exception e) {
            if(listener!=null){
                listener.onFail(e);
            }
        }
    }

    /**
     * 单播，给个人发
     *
     * @throws Exception
     */
    public static void sendAndroidUnicast() throws Exception {
        AndroidUnicast unicast = new AndroidUnicast(appkey, appMasterSecret);
        // TODO Set your device token
        unicast.setDeviceToken("your device token");
        unicast.setTicker("Android unicast ticker");
        unicast.setTitle("中文的title");
        unicast.setText("Android unicast text");
        unicast.goAppAfterOpen();
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        unicast.setProductionMode();
        // Set customized fields
        unicast.setExtraField("test", "helloworld");
        client.send(unicast);
    }

    /**
     * 列播
     * @param ticker
     * @param title
     * @param content
     * @param listener
     * @param deviceToken
     */
    public static void sendAndroidListcast(String ticker,String title,String content,PushSendListener listener,String... deviceToken) {
        try {
            AndroidListcast listcast = new AndroidListcast(appkey, appMasterSecret);
            // TODO Set your device token
            listcast.setDeviceToken(deviceToken);
            listcast.setTicker(ticker);
            listcast.setTitle(title);
            listcast.setText(content);
            listcast.goAppAfterOpen();
            listcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            // TODO Set 'production_mode' to 'false' if it's a test device.
            // For how to register a test device, please see the developer doc.
            listcast.setProductionMode();
            // Set customized fields
            listcast.setExtraField("test", "helloworld");
            client.send(listcast,listener);
        } catch (Exception e) {
            e.printStackTrace();
            if(listener!=null){
                listener.onFail(e);
            }
        }
    }

    /**
     * 组播
     * @throws Exception
     */
    public static void sendAndroidGroupcast(String ticker,String title,String content,PushSendListener listener,String... tag)  {
        if(tag==null||tag.length==0){
            if(listener!=null){
                listener.onFail(new RuntimeException("tag 不能为空"));
            }
            return;
        }
        try {
            AndroidGroupcast groupcast = new AndroidGroupcast(appkey, appMasterSecret);
        /*  例子
		 *  Construct the filter condition:
		 *  "where":
		 *	{
    	 *		"and":
    	 *		[
      	 *			{"tag":"test"},
      	 *			{"tag":"Test"}
    	 *		]
		 *	}
		 */
            JSONObject filterJson = new JSONObject();
            JSONObject whereJson = new JSONObject();
            JSONArray tagArray = new JSONArray();
            for (int i = 0; i <tag.length ; i++) {
                JSONObject tt = new JSONObject();
                tt.put("tag", tag[i]);
                tagArray.put(tt);
            }
            whereJson.put("and", tagArray);
            filterJson.put("where", whereJson);
            ll.i("组播tag: "+filterJson.toString());

            groupcast.setFilter(filterJson);
            groupcast.setTicker(ticker);
            groupcast.setTitle(title);
            groupcast.setText(content);
            groupcast.goAppAfterOpen();
            groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            // TODO Set 'production_mode' to 'false' if it's a test device.
            // For how to register a test device, please see the developer doc.
            groupcast.setProductionMode();
            client.send(groupcast,listener);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFail(e);
        }
    }

    /**
     * 自定义-直接发送
     * @throws Exception
     */
    public static void sendAndroidCustomizedcast(String alias,String title,String content,PushSendListener sendListener)  {
        AndroidCustomizedcast customizedcast = null;
        try {
            customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
            // TODO Set your alias here, and use comma to split them if there are multiple alias.
            // And if you have many alias, you can also upload a file containing these alias, then
            // use file_id to send customized notification.
            customizedcast.setAlias("alias", alias);
            customizedcast.setTicker(title);
            customizedcast.setTitle(title);
            customizedcast.setText(content);
            customizedcast.goAppAfterOpen();
            customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            // TODO Set 'production_mode' to 'false' if it's a test device.
            // For how to register a test device, please see the developer doc.
            customizedcast.setProductionMode();
            client.send(customizedcast,sendListener);
        } catch (Exception e) {
            e.printStackTrace();
            if(sendListener!=null){
                sendListener.onFail(e);
            }
        }

    }

    /**
     * 自定义-文件id发送方式
     * @throws Exception
     */
    public static void sendAndroidCustomizedcastFile() throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
        // TODO Set your alias here, and use comma to split them if there are multiple alias.
        // And if you have many alias, you can also upload a file containing these alias, then
        // use file_id to send customized notification.
        String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb" + "\n" + "alias");
        customizedcast.setFileId(fileId, "alias_type");
        customizedcast.setTicker("Android customizedcast ticker");
        customizedcast.setTitle("中文的title");
        customizedcast.setText("Android customizedcast text");
        customizedcast.goAppAfterOpen();
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        customizedcast.setProductionMode();
        client.send(customizedcast);
    }

    public static void sendAndroidFilecast() throws Exception {
        AndroidFilecast filecast = new AndroidFilecast(appkey, appMasterSecret);
        // TODO upload your device tokens, and use '\n' to split them if there are multiple tokens
        String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
        filecast.setFileId(fileId);
        filecast.setTicker("Android filecast ticker");
        filecast.setTitle("中文的title");
        filecast.setText("Android filecast text");
        filecast.goAppAfterOpen();
        filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        client.send(filecast);
    }

    public static void main(String[] args) {
        // TODO set your appkey and master secret here
        Demo demo = new Demo("your appkey", "the app master secret");
        try {
            demo.sendAndroidUnicast();
			/* TODO these methods are all available, just fill in some fields and do the test
			 * demo.sendAndroidCustomizedcastFile();
			 * demo.sendAndroidBroadcast();
			 * demo.sendAndroidGroupcast();
			 * demo.sendAndroidCustomizedcast();
			 * demo.sendAndroidFilecast();
			 * 
			 * demo.sendIOSBroadcast();
			 * demo.sendIOSUnicast();
			 * demo.sendIOSGroupcast();
			 * demo.sendIOSCustomizedcast();
			 * demo.sendIOSFilecast();
			 */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
