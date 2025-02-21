package ru.spshop.service;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class OSInfo {
    public static void main(String[] args) {
        String osName = getOSName();
        String osType = getOSType();
        String osVersion = getOSVersion();

        System.out.println("OS Name: " + osName);
        System.out.println("OS Type: " + osType);
        System.out.println("OS Version: " + osVersion);
    }

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public static String getOSType() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        return osBean.getName();
    }

    public static String getOSVersion() {
        return System.getProperty("os.version");
    }
}
