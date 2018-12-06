package com.dpn.jt808.util;

import com.dpn.jt808.common.TPMSConsts;
import com.dpn.jt808.domain.base.MsgHeader;
import com.dpn.jt808.domain.PackageData;
import com.dpn.jt808.domain.message.body.MsgBody0200;
import com.dpn.jt808.domain.message.body.MsgBody0704;

import java.util.ArrayList;
import java.util.List;

/**
 * jt808解析工具类
 */
public class MsgHandlerUtils {

    private static MsgHandlerUtils instance;

    public static MsgHandlerUtils getInstance() {
        if (instance == null) {
            instance = new MsgHandlerUtils();
        }
        return instance;
    }

    /**
     * 获取数据包信息
     *
     * @param hexString 16进制BCD字符串
     * @return PackageData
     * @throws Exception
     */
    public PackageData getPackageData(String hexString) throws Exception {
        if (hexString == null || hexString.length() == 0) {
            throw new Exception("param is illegal");
        }
        byte[] bytes = BitUtils.hexStringToByte(hexString);
        if (bytes.length < 13) {
            throw new Exception("param is illegal");
        }
        PackageData packageData = new PackageData();
        MsgHeader msgHeader = getMsgHeader(bytes);
        assert msgHeader != null;
        packageData.setMsgHeader(msgHeader);
        if (msgHeader.isHasSubPackage()) {
            packageData.setMsgBody(BitUtils.subBytes(bytes, 17, msgHeader.getMsgBodyLength()));
        } else {
            packageData.setMsgBody(BitUtils.subBytes(bytes, 13, msgHeader.getMsgBodyLength()));
        }
        return packageData;
    }

    /**
     * 解析0200定位信息
     *
     * @param packageData 初始化消息体
     * @return MsgBody0200
     */
    public MsgBody0200 getMsgBody0200(PackageData packageData) throws Exception {
        if (packageData == null) {
            throw new Exception("param is null");
        }
        MsgHeader msgHeader = packageData.getMsgHeader();
        if (msgHeader == null) {
            throw new Exception("msgHeader is null");
        }
        if (TPMSConsts.LOCATION_INFO_UPLOAD != msgHeader.getMsgId()) {
            throw new Exception("msgId is illegal");
        }
        byte[] msgBody = packageData.getMsgBody();
        if (msgBody == null || msgBody.length != msgHeader.getMsgBodyLength()) {
            throw new Exception("msgBody length is error");
        }
        MsgBody0200 locationInfoMsg = new MsgBody0200();
        locationInfoMsg.setWarningFlagField(BitUtils.parseIntFromBytes(msgBody, 0, 4));
        locationInfoMsg.setStatusField(BitUtils.parseIntFromBytes(msgBody, 4, 4));
        locationInfoMsg.setLatitude(BitUtils.parseIntFromBytes(msgBody, 8, 4) / 1000000f);
        locationInfoMsg.setLongitude(BitUtils.parseIntFromBytes(msgBody, 12, 4) / 1000000f);
        locationInfoMsg.setElevation(BitUtils.parseIntFromBytes(msgBody, 16, 2));
        locationInfoMsg.setSpeed(BitUtils.parseIntFromBytes(msgBody, 18, 2));
        locationInfoMsg.setDirection(BitUtils.parseIntFromBytes(msgBody, 20, 2));
        locationInfoMsg.setTime(BitUtils.parseBcdStringFromBytes(msgBody, 22, 6));
        return locationInfoMsg;
    }

    /**
     * 解析0704定位信息批量上传解析数据
     *
     * @param packageData 初始化消息体
     * @return MsgBody0704
     * @throws Exception
     */
    public MsgBody0704 getMsgBody0704(PackageData packageData) throws Exception {
        if (packageData == null) {
            throw new Exception("param is null");
        }
        MsgHeader msgHeader = packageData.getMsgHeader();
        if (msgHeader == null) {
            throw new Exception("msgHeader is null");
        }
        if (TPMSConsts.LOCATION_INFO_UPLOAD_BATCH != msgHeader.getMsgId()) {
            throw new Exception("msgId is illegal");
        }
        byte[] msgBody = packageData.getMsgBody();
        if (msgBody == null || msgBody.length != msgHeader.getMsgBodyLength()) {
            throw new Exception("msgBody length is error");
        }
        MsgBody0704 result = new MsgBody0704();

        result.setInfoCount(BitUtils.parseIntFromBytes(msgBody, 0, 2));
        result.setType(BitUtils.parseIntFromBytes(msgBody, 2, 1));
        List<MsgBody0200> list = new ArrayList<MsgBody0200>(result.getInfoCount());
        // 为什么消息体长度-3：数据项个数2字节，位置数据类型1字节
        int oneLength = (msgHeader.getMsgBodyLength() - 3) / result.getInfoCount();
        for (int i = 0; i < result.getInfoCount(); i++) {
            list.add(getMsgBody0200Body(BitUtils.subBytes(msgBody, 3 + i * oneLength, oneLength)));
        }
        result.setMsgBody0200List(list);
        return result;
    }

    /**
     * 获取数据头信息
     *
     * @param bytes
     * @return MsgHeader
     * @throws Exception
     */
    private MsgHeader getMsgHeader(byte[] bytes) throws Exception {
        if (bytes.length < 13) {
            throw new Exception("bytes length is error");
        }
        MsgHeader msgHeader = new MsgHeader();
        msgHeader.setMsgId(BitUtils.parseIntFromBytes(bytes, 1, 2));

        int msgBodyProps = BitUtils.parseIntFromBytes(bytes, 3, 2);
        msgHeader.setMsgBodyPropsField(msgBodyProps);
        // [ 0-9 ] 0000,0011,1111,1111(3FF)(消息体长度)
        msgHeader.setMsgBodyLength(msgBodyProps & 0x3ff);
        // [10-12] 0001,1100,0000,0000(1C00)(加密类型)
        msgHeader.setEncryptionType((msgBodyProps & 0x1c00) >> 10);
        // [ 13_ ] 0010,0000,0000,0000(2000)(是否有子包)
        msgHeader.setHasSubPackage(((msgBodyProps & 0x2000) >> 13) == 1);
        // [14-15] 1100,0000,0000,0000(C000)(保留位)
        msgHeader.setReservedBit(((msgBodyProps & 0xc000) >> 14) + "");
        // 消息体属性 word(16)<=================

        // 3. 终端手机号 bcd[6]
        msgHeader.setTerminalPhone(BitUtils.parseBcdStringFromBytes(bytes, 5, 6));

        // 4. 消息流水号 word(16) 按发送顺序从 0 开始循环累加
        msgHeader.setFlowId(BitUtils.parseIntFromBytes(bytes, 11, 2));

        // 5. 消息包封装项
        // 有子包信息
        if (msgHeader.isHasSubPackage()) {
            // 消息包封装项字段
            msgHeader.setPackageInfoField(BitUtils.parseIntFromBytes(bytes, 13, 4));
            // byte[0-1] 消息包总数(word(16))
            msgHeader.setTotalSubPackage(BitUtils.parseIntFromBytes(bytes, 13, 2));
            // byte[2-3] 包序号(word(16)) 从 1 开始
            msgHeader.setSubPackageSeq(BitUtils.parseIntFromBytes(bytes, 15, 2));
        }
        return msgHeader;
    }

    /**
     * 解析定位信息
     *
     * @param msgBody 消息体字节数组
     * @return MsgBody0200
     */
    private MsgBody0200 getMsgBody0200Body(byte[] msgBody) {
        if (msgBody.length == 52) {
            // 定位批量上传子数据 前两个字节为数据长度 截掉处理
            msgBody = BitUtils.subBytes(msgBody, 2, 50);
        }
        MsgBody0200 msgBody0200 = new MsgBody0200();
        msgBody0200.setWarningFlagField(BitUtils.parseIntFromBytes(msgBody, 0, 4));
        msgBody0200.setStatusField(BitUtils.parseIntFromBytes(msgBody, 4, 4));
        msgBody0200.setLatitude(BitUtils.parseIntFromBytes(msgBody, 8, 4) / 1000000f);
        msgBody0200.setLongitude(BitUtils.parseIntFromBytes(msgBody, 12, 4) / 1000000f);
        msgBody0200.setElevation(BitUtils.parseIntFromBytes(msgBody, 16, 2));
        msgBody0200.setSpeed(BitUtils.parseIntFromBytes(msgBody, 18, 2));
        msgBody0200.setDirection(BitUtils.parseIntFromBytes(msgBody, 20, 2));
        msgBody0200.setTime(BitUtils.parseBcdStringFromBytes(msgBody, 22, 6));
        return msgBody0200;
    }

//    /**
//     * 获取0200 定位信息数据
//     *
//     * @param packageData 解析后的数据包
//     * @return MsgResult0200
//     * @throws Exception
//     */
//    public MsgResult0200 getMsgResult0200(PackageData packageData) throws Exception {
//        if (packageData == null) {
//            throw new Exception("param is null");
//        }
//        MsgHeader msgHeader = packageData.getMsgHeader();
//        if (msgHeader == null) {
//            throw new Exception("msgHeader is null");
//        }
//        if (TPMSConsts.LOCATION_INFO_UPLOAD != msgHeader.getMsgId()) {
//            throw new Exception("msgId is illegal");
//        }
//        byte[] msgBody = packageData.getMsgBody();
//        if (msgBody == null || msgBody.length != msgHeader.getMsgBodyLength()) {
//            throw new Exception("msgBody length is error");
//        }
//        MsgResult0200 result = new MsgResult0200();
//        result.setMsgHeader(msgHeader);
//        result.setMsgBody(msgBody);
//        LocationInfoMsg locationInfoMsg = getLocationInfo(msgBody);
//        result.setLocationInfoMsg(locationInfoMsg);
//        return result;
//    }
//
//    /**
//     * 获取0704定位信息批量上传解析数据
//     *
//     * @param packageData 解析数据包
//     * @return MsgResult0704
//     * @throws Exception
//     */
//    public MsgResult0704 getMsgResult0704(PackageData packageData) throws Exception {
//        if (packageData == null) {
//            throw new Exception("param is null");
//        }
//        MsgHeader msgHeader = packageData.getMsgHeader();
//        if (msgHeader == null) {
//            throw new Exception("msgHeader is null");
//        }
//        if (TPMSConsts.LOCATION_INFO_UPLOAD_BATCH != msgHeader.getMsgId()) {
//            throw new Exception("msgId is illegal");
//        }
//        byte[] msgBody = packageData.getMsgBody();
//        if (msgBody == null || msgBody.length != msgHeader.getMsgBodyLength()) {
//            throw new Exception("msgBody length is error");
//        }
//        MsgResult0704 result = new MsgResult0704();
//        result.setMsgHeader(msgHeader);
//        result.setMsgBody(msgBody);
//
//        result.setInfoCount(BitUtils.parseIntFromBytes(msgBody, 0, 2));
//        result.setType(BitUtils.parseIntFromBytes(msgBody, 2, 1));
//        List<LocationInfoMsg> list = new ArrayList<LocationInfoMsg>(result.getInfoCount());
//        // 为什么消息体长度-3：数据项个数2字节，位置数据类型1字节
//        int oneLength = (msgHeader.getMsgBodyLength() - 3) / result.getInfoCount();
//        for (int i = 0; i < result.getInfoCount(); i++) {
//            list.add(getLocationInfo(BitUtils.subBytes(msgBody, 3 + i * oneLength, oneLength)));
//        }
//        result.setLocationInfoMsgList(list);
//        return result;
//    }

}
