package com.dpn.jt808.util;

/**
 * 字节处理工具类
 */
public class BitUtils {

    /**
     * 处理字节数组转换成16进制字符串
     *
     * @param data       要转换的字节数组
     * @param startIndex 要转换data的起始位置
     * @param length     要转换data的长度
     * @return 返回字符串
     */
    public static String parseBcdStringFromBytes(byte[] data, int startIndex, int length) {
        try {
            byte[] tmp = new byte[length];
            System.arraycopy(data, startIndex, tmp, 0, length);
            return bcd2String(tmp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理字节数组转换成十进制整型
     *
     * @param data       要转换的字节数组
     * @param startIndex 要转换data的起始位置
     * @param length     要转换data的长度
     * @return 返回十进制整型
     */
    public static int parseIntFromBytes(byte[] data, int startIndex, int length) {
        try {
            // 字节数大于4,从起始索引开始向后处理4个字节,其余超出部分丢弃
            final int len = length > 4 ? 4 : length;
            byte[] tmp = new byte[len];
            System.arraycopy(data, startIndex, tmp, 0, len);
            return byteToInteger(tmp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 截取count大小的字节数组 begin为起始位置
     *
     * @param bytes 原始字节数组
     * @param begin 开始截取的位置
     * @param count 新数组大小
     * @return byte[]
     */
    public static byte[] subBytes(byte[] bytes, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(bytes, begin, bs, 0, count);
        return bs;
    }

    /**
     * BCD字节数组===>String
     *
     * @param bytes
     * @return 十进制字符串
     */
    public static String bcd2String(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            // 高四位
            temp.append((bytes[i] & 0xf0) >>> 4);
            // 低四位
            temp.append(bytes[i] & 0x0f);
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
    }

    /**
     * 16进制字符串转换成byte数组
     *
     * @param hex
     * @return byte[]
     */
    public static byte[] hexStringToByte(String hex) {
        hex = hex.toUpperCase();
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] toByteArray(String hexString) {
        if (hexString == null || hexString.length() == 0) {
            throw new IllegalArgumentException("this hexString must not be empty");
        }
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**
     * 字节数组转成16进制表示格式的字符串
     *
     * @param byteArray 需要转换的字节数组
     * @return 16进制表示格式的字符串
     **/
    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1) {
            throw new IllegalArgumentException("this byteArray must not be null or empty");
        }
        final StringBuilder hexString = new StringBuilder();
        for (byte aByteArray : byteArray) {
            if ((aByteArray & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & aByteArray));
        }
        return hexString.toString().toLowerCase();
    }


    /**
     * 把byte[]转化位整形,通常为指令用
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static int byteToInteger(byte[] value) {
        int result;
        if (value.length == 1) {
            result = oneByteToInteger(value[0]);
        } else if (value.length == 2) {
            result = twoBytesToInteger(value);
        } else if (value.length == 3) {
            result = threeBytesToInteger(value);
        } else if (value.length == 4) {
            result = fourBytesToInteger(value);
        } else {
            result = fourBytesToInteger(value);
        }
        return result;
    }

    /**
     * 把一个byte转化位整形,通常为指令用
     *
     * @param value
     * @return
     * @throws Exception
     */
    private static int oneByteToInteger(byte value) {
        return (int) value & 0xFF;
    }

    /**
     * 把一个2位的数组转化位整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    private static int twoBytesToInteger(byte[] value) {
        // if (value.length < 2) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        return ((temp0 << 8) + temp1);
    }

    /**
     * 把一个3位的数组转化位整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    private static int threeBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        return ((temp0 << 16) + (temp1 << 8) + temp2);
    }

    /**
     * 把一个4位的数组转化位整形,通常为指令用
     *
     * @param value
     * @return
     * @throws Exception
     */
    private static int fourBytesToInteger(byte[] value) {
        // if (value.length < 4) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
