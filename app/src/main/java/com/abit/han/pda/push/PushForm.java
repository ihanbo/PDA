package com.abit.han.pda.push;

import java.util.HashMap;

/**
 * Created by ihanb on 2016/11/11.
 */

public class PushForm {



    private HashMap<String,String> pushForm = new HashMap<>();
    /**
     * appkey : 你的appkey
     * timestamp : 你的timestamp
     * type : listcast
     * device_tokens : device1,device2,…
     * payload : {"display_type":"notification","body":{"ticker":"测试提示文字","title":"测试标题","text":"测试文字描述","after_open":"go_app"}}
     * policy : {"expire_time":"2013-10-30 12:00:00"}
     * description : 测试列播通知-Android
     */

    public String appkey = "5802099de0f55aca6a000e45";
    public String timestamp;
    public String type = "listcast";
    public String device_tokens;
    public PayloadBean payload;
    public PolicyBean policy;
    public String description;

    public PushForm() {
        pushForm.put("appkey","5802099de0f55aca6a000e45");
        pushForm.put("appMasterSecret","yvs5icmg9sihzaharfao7q3d8zgsde4i");
        pushForm.put("Umeng Message Secret","5c4f5c48be92b3d4b5a67dc461beec0c");
        pushForm.put("timestamp",""+System.currentTimeMillis());

        pushForm.put("type","listcast");
        //pushForm.put("device_tokens","xx,xx");
        pushForm.put("alias_type","1357504");
        pushForm.put("alias","");
        pushForm.put("","");
        pushForm.put("","");
        pushForm.put("","");
        pushForm.put("","");
        pushForm.put("","");

    }

    public static class PayloadBean {
        /**
         * display_type : notification
         * body : {"ticker":"测试提示文字","title":"测试标题","text":"测试文字描述","after_open":"go_app"}
         */

        private String display_type;
        private BodyBean body;

        public String getDisplay_type() {
            return display_type;
        }

        public void setDisplay_type(String display_type) {
            this.display_type = display_type;
        }

        public BodyBean getBody() {
            return body;
        }

        public void setBody(BodyBean body) {
            this.body = body;
        }

        public static class BodyBean {
            /**
             * ticker : 测试提示文字
             * title : 测试标题
             * text : 测试文字描述
             * after_open : go_app
             */

            private String ticker;
            private String title;
            private String text;
            private String after_open;

            public String getTicker() {
                return ticker;
            }

            public void setTicker(String ticker) {
                this.ticker = ticker;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getAfter_open() {
                return after_open;
            }

            public void setAfter_open(String after_open) {
                this.after_open = after_open;
            }
        }
    }

    public static class PolicyBean {
        /**
         * expire_time : 2013-10-30 12:00:00
         */

        private String expire_time;

        public String getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(String expire_time) {
            this.expire_time = expire_time;
        }
    }
}
