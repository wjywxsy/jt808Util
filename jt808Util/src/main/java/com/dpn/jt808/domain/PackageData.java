package com.dpn.jt808.domain;

import com.dpn.jt808.domain.base.MsgHeader;

import java.io.Serializable;

public class PackageData implements Serializable {

    private MsgHeader msgHeader;

    private byte[] msgBody;

    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public byte[] getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(byte[] msgBody) {
        this.msgBody = msgBody;
    }
}
