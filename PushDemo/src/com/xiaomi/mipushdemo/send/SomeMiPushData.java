package com.xiaomi.mipushdemo.send;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihanb on 2016/12/21.
 */

public class SomeMiPushData extends MiPushData {
    public final List<String> regIds ;

    SomeMiPushData(MiPostHandler postHandler) {
        super(postHandler);
        this.regIds = new ArrayList<>();
    }


    public SomeMiPushData addRegID(String regID){
        regIds.add(regID);
        return this;
    }

}
