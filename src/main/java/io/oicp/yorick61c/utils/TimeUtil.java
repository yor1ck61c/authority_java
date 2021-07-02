package io.oicp.yorick61c.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String formatTime(String time) {
        char[] chars = time.toCharArray();
        chars[10] = ' ';
        chars[12] += 8;
        int hours = Character.getNumericValue(chars[12]);
        if ( hours > 9 || hours == -1) {
            chars[12] -= 10;
            chars[11]++;
        }

        return new String(chars).substring(0, 19);
    }

    public static String getPresentFormatTimeString() {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(formatTime("2021-06-29T23:51:41.000Z"));
        // System.out.println(Character.getNumericValue('.'));
    }


}
