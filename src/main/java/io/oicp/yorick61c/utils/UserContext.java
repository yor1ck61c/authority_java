package io.oicp.yorick61c.utils;

public class UserContext {

    private static final ThreadLocal<String> userInfo = new ThreadLocal<>();

    public static void add(String userName) {
        userInfo.set(userName);
    }

    public static void remove() {
        userInfo.remove();
    }

    /**
     * @return 当前登录用户的用户名
     */
    public static String getCurrentUserName() {
        return userInfo.get();
    }

}
