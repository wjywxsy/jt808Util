package com.dpn.jt808.domain.message.body;

import com.dpn.jt808.domain.base.MsgBody;
import java.util.List;

public class MsgBody0704 extends MsgBody {

    /**
     * 位置数据项个数
     */
    private int infoCount;

    /**
     * 位置数据类型 0：正常位置批量汇报 1：盲区补报
     */
    private int type;

    /**
     * 位置汇报信息
     */
    private List<MsgBody0200> msgBody0200List = null;

    public int getInfoCount() {
        return infoCount;
    }

    public void setInfoCount(int infoCount) {
        this.infoCount = infoCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<MsgBody0200> getMsgBody0200List() {
        return msgBody0200List;
    }

    public void setMsgBody0200List(List<MsgBody0200> msgBody0200List) {
        this.msgBody0200List = msgBody0200List;
    }
}
