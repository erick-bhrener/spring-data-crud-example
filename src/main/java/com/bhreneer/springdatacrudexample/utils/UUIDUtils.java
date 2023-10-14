package com.bhreneer.springdatacrudexample.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class UUIDUtils {

    private static final Integer uuidLen = 12;
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public static String getUUID() {
        StringBuilder sb = new StringBuilder(uuidLen);
        for(int i = 0; i < uuidLen; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static void main(String[] args) {
        String actors = "Logan Browning, Brandon P. Bell, DeRon Horton, Antoinette Robertson, John Patrick Amedori, Ashley Blaine Featherson, Marque Richardson, Giancarlo Esposito\n";
        String[] cast = actors.trim().split(",");
        for (String actor: cast) {
            System.out.println(actor.trim());
        }
        System.out.println("end");
    }

}
