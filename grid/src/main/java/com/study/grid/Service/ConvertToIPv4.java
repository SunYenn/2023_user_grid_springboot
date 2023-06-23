package com.study.grid.Service;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ConvertToIPv4 {

    public static String convert(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            if (inetAddress instanceof Inet6Address) {
                byte[] addressBytes = inetAddress.getAddress();
                InetAddress ipv4Address = InetAddress.getByAddress(Arrays.copyOfRange(addressBytes, 12, 16));
                return ipv4Address.getHostAddress();
            }
        } catch (UnknownHostException e) {
            // Handle the exception
        }
        return ipAddress;
    }
}
