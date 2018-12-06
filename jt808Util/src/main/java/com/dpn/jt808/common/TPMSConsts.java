package com.dpn.jt808.common;

import java.nio.charset.Charset;

public class TPMSConsts {

	public static final String STRING_ENCODING = "GBK";

	public static final Charset STRING_CHARSET = Charset.forName(STRING_ENCODING);
	// 标识位
	public static final int PKG_DELIMITER = 0x7e;
	// 客户端发呆15分钟后,服务器主动断开连接
	public static int TCP_CLIENT_IDLE_MINUTES = 30;

	// 终端通用应答
	public static final int COMMON_RESP = 0x0001;
	// 终端心跳
	public static final int HEART_BEAT = 0x0002;
	// 终端注册
	public static final int REGISTER = 0x0100;
	// 终端注销
	public static final int LOG_OUT = 0x0003;
	// 终端鉴权3765
	public static final int AUTHENTICATION = 0x0102;
	// 位置信息汇报
	public static final int LOCATION_INFO_UPLOAD = 0x0200;
	// 位置信息汇报
	public static final int LOCATION_INFO_UPLOAD_BATCH = 0x0704;
}
