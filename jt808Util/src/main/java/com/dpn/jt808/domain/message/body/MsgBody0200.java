package com.dpn.jt808.domain.message.body;

import com.dpn.jt808.domain.base.MsgBody;

public class MsgBody0200 extends MsgBody {
    // 告警信息
    // byte[0-3]
    private int warningFlagField;
    // byte[4-7] 状态(DWORD(32))
    private int statusField;
    // byte[8-11] 纬度(DWORD(32))
    private float latitude;
    // byte[12-15] 经度(DWORD(32))
    private float longitude;
    // byte[16-17] 高程(WORD(16)) 海拔高度，单位为米（ m）
    private int elevation;
    // byte[18-19] 速度(WORD) 1/10km/h
    private int speed;
    // byte[20-21] 方向(WORD) 0-359，正北为 0，顺时针
    private int direction;
    // byte[22-x] 时间(BCD[6]) YYMMDDhhmmss
    private String time;

    public int getWarningFlagField() {
        return warningFlagField;
    }

    public void setWarningFlagField(int warningFlagField) {
        this.warningFlagField = warningFlagField;
    }

    public int getStatusField() {
        return statusField;
    }

    public void setStatusField(int statusField) {
        this.statusField = statusField;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MsgHeader [warningFlagField=" + warningFlagField + ", statusField=" + statusField + ", latitude="
                + latitude + ", longitude=" + longitude + ", elevation=" + elevation
                + ", speed=" + speed + ", direction=" + direction + ", time=" + time + "]";
    }
}
