public class Test2 {

    public static String registerCode() {
        String code;
        int num;
        while (true) {
            num = (int) (Math.random() * 1000000);
            String num1 = num + "";
            if (!num1.equals("0") && num1.length() == 6) {
                break;
            }
        }
        code = "【大连口岸物流网】验证码：" + num + "，为了保障您的账户安全，请勿向任何单位或个人泄漏验证码信息";
        return code;
    }

    public static String registerCode2() {
        StringBuilder s = new StringBuilder();
        while (s.length() < 6) {
            s.append((int) (Math.random() * 10));
        }
        return s.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(registerCode());
            System.out.println(registerCode2());
        }
    }
}
