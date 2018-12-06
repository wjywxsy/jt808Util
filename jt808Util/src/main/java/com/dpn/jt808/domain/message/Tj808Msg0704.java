package com.dpn.jt808.domain.message;

import com.dpn.jt808.domain.base.MsgHeader;
import com.dpn.jt808.domain.PackageData;
import com.dpn.jt808.domain.base.Tj808BaseMsg;
import com.dpn.jt808.domain.message.body.MsgBody0704;
import com.dpn.jt808.util.MsgHandlerUtils;

public class Tj808Msg0704 extends Tj808BaseMsg {

    private MsgBody0704 msgBody;

    public Tj808Msg0704(PackageData packageData) throws Exception {
        this.msgHeader = packageData.getMsgHeader();
        this.msgBody = MsgHandlerUtils.getInstance().getMsgBody0704(packageData);
    }

    @Override
    public MsgHeader getMsgHeader() {
        return this.msgHeader;
    }

    @Override
    public MsgBody0704 getMsgBody() {
        return msgBody;
    }
}
