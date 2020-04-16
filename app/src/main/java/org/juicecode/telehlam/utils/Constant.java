package org.juicecode.telehlam.utils;

public class Constant {
    public static boolean isRegistered = false;
    private static String baseURL = "http://35.228.43.188:8081";
    private static String UserLogin = "";
    public static String getUserLogin() {
        return UserLogin;
    }

    public static void setUserLogin(String userLogin) {
        UserLogin = userLogin;
    }


    public static String getBaseURL() {
        return baseURL;
    }

    public static void setBaseURL(String baseURL) {
        Constant.baseURL = baseURL;
    }

}
