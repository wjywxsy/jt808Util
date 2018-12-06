package com.dpn.jt808.domain.base;

public abstract class Tj808BaseMsg {

    protected MsgHeader msgHeader;

    protected MsgBody msgBody;

    public abstract MsgHeader getMsgHeader();

    public abstract Object getMsgBody();

}
