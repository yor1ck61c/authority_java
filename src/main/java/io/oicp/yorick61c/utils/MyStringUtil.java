package io.oicp.yorick61c.utils;

import java.util.ArrayList;
import java.util.List;

public class MyStringUtil {

    public static String StringArray2String(String[] arr){
        StringBuilder res = new StringBuilder();
        for (String s : arr) {
            res.append(s);
            res.append(" ");
        }
        return res.toString();
    }

    public static String[] String2StringArray(String s) {
        List<String> res = new ArrayList<>();
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c == ' ') {
                res.add(sb.toString());
                sb.delete(0, sb.length()); //清空StringBuilder实例
            }
            if (c != ' ')
                sb.append(c);
        }
        return res.toArray(new String[0]);
        // 当预期的换算参数arr长度比列表中的元素数量更多或一致时
        // arr的值会转换列表中的元素值，后面如果有多余的空间，则剩余位置的值替换为null。
        // 为了节省空间同时获取返回值为String[]的数组，我们使用toArray(new String[0])
    }

    public static void main(String[] args) {
        String[] s = {"test", "test", "test"};
        String s2 = StringArray2String(s);
        System.out.println(s2);
        String[] strings = String2StringArray(s2);
        for (String string : strings) {
            System.out.print(string);
        }
    }
}
