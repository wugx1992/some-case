package indi.gxwu.other;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @Author: gx.wu
 * @Date: 2020/3/13 10:56
 * @Description: code something to describe this module what it is
 */
public class TimeZoneTest {
    public static void main(String[] args) throws Exception {
        Calendar calendar = Calendar.getInstance();
        System.out.println("目前时间：" + calendar.getTime());
        System.out.println("Calendar时区：：" + calendar.getTimeZone().getID());
        System.out.println("user.timezone：" + System.getProperty("user.timezone"));
        System.out.println("user.country：" + System.getProperty("user.country"));
        System.out.println("默认时区：" + TimeZone.getDefault().getID());
    }
}
