import com.dpn.jt808.common.TPMSConsts;
import com.dpn.jt808.domain.base.MsgBody;
import com.dpn.jt808.domain.base.MsgHeader;
import com.dpn.jt808.domain.PackageData;
import com.dpn.jt808.domain.base.Tj808BaseMsg;
import com.dpn.jt808.domain.message.Tj808Msg0200;
import com.dpn.jt808.domain.message.Tj808Msg0704;
import com.dpn.jt808.domain.message.body.MsgBody0200;
import com.dpn.jt808.domain.message.body.MsgBody0704;
import com.dpn.jt808.util.MsgHandlerUtils;


public class Test {

    public static void main(String[] args) throws Exception {
//        String hexString = "7e0200003206481010505500bc00000000000c00030253b3cd0741ea2a000e0000000018071613493201040003284703020000250400000000300117310113427e";
//        byte[] bytes = BitUtils.hexStringToByte(hexString);
//        byte[] head = BitUtils.subBytes(bytes, 0, 13);//数据头
//
//        byte[] bytes1 = BitUtils.subBytes(head, 0, 1);//标示位
//        byte[] bytes2 = BitUtils.subBytes(head, 1, 2);// 消息ID
//        byte[] bytes3 = BitUtils.subBytes(head, 3, 2);// 消息体属性
//        byte[] bytes4 = BitUtils.subBytes(head, 5, 6);// 终端手机号
//        byte[] bytes5 = BitUtils.subBytes(head, 11, 2);// 消息流水号
//
//        byte[] bytes6 = BitUtils.subBytes(bytes, 13, 4);// 报警状态
//        byte[] bytes7 = BitUtils.subBytes(bytes, 17, 4);// 状态
//        byte[] bytes8 = BitUtils.subBytes(bytes, 21, 4);// 纬度
//        byte[] bytes9 = BitUtils.subBytes(bytes, 25, 4);// 经度
//        byte[] bytes10 = BitUtils.subBytes(bytes, 29, 2);// 高度
//        byte[] bytes11 = BitUtils.subBytes(bytes, 31, 2);// 速度
//        byte[] bytes12 = BitUtils.subBytes(bytes, 33, 2);// 方向
//        byte[] bytes13 = BitUtils.subBytes(bytes, 35, 6);// 时间
//
//
//        System.out.println("-----数据头开始------");
//        System.out.println("消息ID："+BitUtils.byteToInteger(bytes2));
//        System.out.println("消息体属性："+BitUtils.byteToInteger(bytes3));
//        System.out.println("终端手机号："+BitUtils.bcd2String(bytes4));
//        System.out.println("消息流水号："+BitUtils.byteToInteger(bytes5));
//
//        System.out.println("-----数据体开始------");
//        System.out.println("报警状态：" + BitUtils.byteToInteger(bytes6));
//        System.out.println("状态：" + BitUtils.byteToInteger(bytes7));
//        System.out.println("纬度：" + BitUtils.byteToInteger(bytes8)/1000000f);
//        System.out.println("经度：" + BitUtils.byteToInteger(bytes9)/1000000f);
//        System.out.println("高度：" + BitUtils.byteToInteger(bytes10));
//        System.out.println("速度：" + BitUtils.byteToInteger(bytes11));
//        System.out.println("方向：" + BitUtils.byteToInteger(bytes12));
//        System.out.println("时间：" + BitUtils.bcd2String(bytes13));


        System.out.println("------------start----------------");

        // 0200
        String hexString = "7e0200003206481010505500bc00000000000c00030253b3cd0741ea2a000e0000000018071613493201040003284703020000250400000000300117310113427e";
        // 0704
        String hexString2 = "7e0704009f06481013656600dd000301003200000000000c00030253514d07433cd10003002800c6180716134831010400054c570302001025040000000030010931010e003200000000000c0003025350ad0743409e000601170030180716134856010400054c580302011425040000000030010c31010e003200000000000c0003025354bd074346ac0006013e0031180716134921010400054c5a0302014e25040000000030010931010e547e";
        PackageData data = MsgHandlerUtils.getInstance().getPackageData(hexString);
        if (data.getMsgHeader().getMsgId() == TPMSConsts.LOCATION_INFO_UPLOAD) {
            Tj808BaseMsg tj808Msg = new Tj808Msg0200(data);
            MsgHeader msgHeader = tj808Msg.getMsgHeader();
            MsgBody msgBody = (MsgBody0200)tj808Msg.getMsgBody();
        }

        PackageData data2 = MsgHandlerUtils.getInstance().getPackageData(hexString2);
        if (data.getMsgHeader().getMsgId() == TPMSConsts.LOCATION_INFO_UPLOAD_BATCH) {
            Tj808BaseMsg tj808Msg = new Tj808Msg0704(data2);
            MsgHeader msgHeader = tj808Msg.getMsgHeader();
            MsgBody msgBody = (MsgBody0704)tj808Msg.getMsgBody();
        }
        System.out.println("------------end----------------");

    }
}
