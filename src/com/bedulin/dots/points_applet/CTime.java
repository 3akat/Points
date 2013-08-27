//package com.bedulin.dots.points_applet;// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 27.08.2013 11:40:08
//// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
//// Decompiler options: packimports(3)
//// Source File Name:   CTime.java
//
//import android.graphics.Canvas;
//
//public class CTime extends Canvas
//        implements Runnable {
//
//    public CTime(Tochki tochki) {
//        tochk = tochki;
//    }
//
//    public void set_ind(int i) {
//        count = i;
//        second = i;
//        minute = 0;
//    }
//
//    public void run() {
//        do {
//            if (count == 1) {
//                String s = "";
//                String s1 = String.valueOf(second);
//                if (second < 10)
//                    s1 = "0" + s1;
//                String s2 = "";
//                String s3 = String.valueOf(minute);
//                String s4 = s3 + ":" + s1;
//                tochk.my_text_field.setText(s4);
//                second++;
//                if (second > 59) {
//                    second = 0;
//                    minute++;
//                }
//            }
//            try {
//                Thread.currentThread();
//                Thread.sleep(1000L);
//            } catch (Exception _ex) {
//                System.out.println("Exception on sleep");
//            }
//        } while (true);
//    }
//
//    int count;
//    int second;
//    int minute;
//    Tochki tochk;
//}
