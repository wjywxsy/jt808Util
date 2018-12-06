package com.dpn.jt808.domain.message;

import com.dpn.jt808.domain.base.MsgHeader;
import com.dpn.jt808.domain.PackageData;
import com.dpn.jt808.domain.base.Tj808BaseMsg;
import com.dpn.jt808.domain.message.body.MsgBody0200;
import com.dpn.jt808.util.MsgHandlerUtils;

public class Tj808Msg0200 extends Tj808BaseMsg {

    private MsgBody0200 msgBody;

    public Tj808Msg0200(PackageData packageData) throws Exception {
        this.msgHeader = packageData.getMsgHeader();
        this.msgBody = MsgHandlerUtils.getInstance().getMsgBody0200(packageData);
    }

    @Override
    public MsgHeader getMsgHeader() {
        return this.msgHeader;
    }

    @Override
    public MsgBody0200 getMsgBody() {
        return msgBody;
    }

}
