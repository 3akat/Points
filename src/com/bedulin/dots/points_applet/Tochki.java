//package com.bedulin.dots.points_applet;// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 27.08.2013 11:33:12
//// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
//// Decompiler options: packimports(3)
//// Source File Name:   Tochki.java
//
//import java.applet.Applet;
//import java.applet.AppletContext;
//import java.awt.*;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class Tochki extends Applet
//        implements Runnable {
//
//    public static void main(String args[]) {
//        Tochki tochki = new Tochki(args);
//        Frame frame = new Frame("Tochki");
//        frame.resize(board_width + 200, board_height + 74);
//        frame.add("Center", tochki);
//        frame.show();
//        tochki.start();
//    }
//
//    public Tochki(String as[]) {
//        restart = 1;
//        click_mouse_x = -1;
//        click_mouse_y = -1;
//        oval_size = 4;
//        step_1_of_2 = 10;
//        red_exp_str = " ";
//        green_exp_str = " ";
//        loc_mas_numb = 1;
//        start_second = "-------------------";
//        click_range_x_h = 100;
//        click_range_y_h = 100;
//        OLD_last_dot_x = 11;
//        OLD_last_dot_y = 11;
//        last_dot_str = 'r';
//        kavichki = '"';
//        repai = 1;
//        is_programm = 1;
//        init();
//    }
//
//    public void init() {
//        setLayout(new BorderLayout());
//        add("Center", my_tochki_draw = new Tochki());
//        Panel panel = new Panel();
//        Panel panel1 = new Panel();
//        Panel panel2 = new Panel();
//        Color color = new Color(255, 252, 208);
//        setBackground(color);
//        panel.setLayout(new FlowLayout());
//        add("North", panel1);
//        add("West", panel2);
//        add("South", panel);
//        str_button = new Button(start_second);
//        panel.add(str_button);
//        if (is_programm == 1)
//            panel.add(new Button("EXIT"));
//        if (is_programm == 0)
//            panel.add(new Button("Go to http://beloostrov-spb.narod.ru"));
//        my_text_field = new TextField(5);
//        panel.add(my_text_field);
//        count_time = new CTime(this);
//        count_time.resize(1, 1);
//        panel.add(count_time);
//        theThread = new Thread(count_time);
//        theThread.start();
//        my_tochki_draw.lining();
//    }
//
//    public boolean lining() {
//        int i = board_height - step - (board_height - step) % step;
//        gor_lines = i / step;
//        gor_lines = gor_lines;
//        int j = board_width - step - (board_width - step) % step;
//        vert_lines = j / step;
//        vert_lines = vert_lines;
//        int k = step % 3;
//        int l = (step - k) / 3 + 3;
//        oval_size = l;
//        int i1 = step % 2;
//        int j1 = (step - i1) / 2 + 2;
//        step_1_of_2 = j1;
//        mass_board = new char[gor_lines][vert_lines];
//        dot_condition = new char[gor_lines][vert_lines];
//        local_mas_temp = new int[gor_lines][vert_lines];
//        my_test = new char[gor_lines][vert_lines];
//        expanded_dots = new int[gor_lines][vert_lines];
//        one = 1 << vert_lines - 1;
//        x_board = new int[vert_lines];
//        x_board = x_board;
//        y_board = new int[gor_lines];
//        y_board = y_board;
//        int k1 = gor_lines * 10;
//        int l1 = vert_lines * 20;
//        poligons = new int[k1][2][l1];
//        poligons = poligons;
//        click_range_x_l = step_1_of_2 + 1;
//        click_range_y_l = step_1_of_2 + 1;
//        click_range_x_h = board_width - (step_1_of_2 + 1);
//        click_range_y_h = board_height - (step_1_of_2 + 1);
//        message = new String[2];
//        message[0] = "              ";
//        message[1] = "              ";
//        message = message;
//        set_mass();
//        while (single_danger <= 0)
//            try {
//                my_odin = new Odin(dot_condition, expanded_dots, gor_lines, vert_lines, single_danger);
//                single_danger = my_odin.clear_danger();
//            } catch (Exception _ex) {
//                try {
//                    Thread.sleep(500L);
//                } catch (InterruptedException _ex2) {
//                    System.out.println("sleep error");
//                }
//            }
//        return true;
//    }
//
//    public boolean set_mass() {
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < gor_lines; j++) {
//                int k = j;
//                for (int i1 = 0; i1 < vert_lines; i1++) {
//                    mass_board[k][i1] = f;
//                    dot_condition[k][i1] = f;
//                    my_test[k][i1] = f;
//                }
//
//            }
//
//            mass_board = mass_board;
//            dot_condition = dot_condition;
//            my_test = my_test;
//            for (int l = 0; l < vert_lines; l++)
//                x_board[l] = step * (l + 1);
//
//            x_board = x_board;
//            for (int j1 = 0; j1 < gor_lines; j1++)
//                y_board[j1] = step * (j1 + 1);
//
//            y_board = y_board;
//            for (int k1 = 0; k1 < gor_lines * 10; k1++) {
//                byte byte0 = -1;
//                poligons[k1][0][0] = byte0;
//            }
//
//            poligons = poligons;
//            message_vert_line = x_board[vert_lines - 1] + step * 2 + 3;
//        }
//
//        return true;
//    }
//
//    public Tochki() {
//        restart = 1;
//        click_mouse_x = -1;
//        click_mouse_y = -1;
//        oval_size = 4;
//        step_1_of_2 = 10;
//        red_exp_str = " ";
//        green_exp_str = " ";
//        loc_mas_numb = 1;
//        start_second = "-------------------";
//        click_range_x_h = 100;
//        click_range_y_h = 100;
//        OLD_last_dot_x = 11;
//        OLD_last_dot_y = 11;
//        last_dot_str = 'r';
//        kavichki = '"';
//        repai = 1;
//    }
//
//    public void update(Graphics g) {
//        offScreenImage = createImage(board_width + 200, board_height);
//        Graphics g1 = offScreenImage.getGraphics();
//        g1.setColor(getBackground());
//        g1.setColor(g.getColor());
//        paint(g1);
//        g.drawImage(offScreenImage, 0, 0, this);
//    }
//
//    public void paint(Graphics g) {
//        Color color = new Color(255, 252, 208);
//        setBackground(color);
//        Color color1 = new Color(255, 0, 0);
//        Color color2 = new Color(0, 143, 35);
//        Color color3 = new Color(0, 100, 251);
//        Color color4 = new Color(0, 0, 0);
//        Color color5 = new Color(0, 0, 255);
//        g.setColor(color);
//        if (restart == 1)
//            g.fillRect(0, 0, board_width + 200, board_height + 74);
//        g.fillOval(OLD_last_dot_x - oval_size, OLD_last_dot_y - oval_size, oval_size * 2, oval_size * 2);
//        g.fillRect(board_width, 50, board_width + 200, board_height + 74);
//        g.setColor(color5);
//        for (int i = 0; i < gor_lines; i++)
//            g.drawLine(0, step + step * i, board_width, step + step * i);
//
//        for (int j = 0; j < vert_lines; j++)
//            g.drawLine(step + step * j, 0, step + step * j, board_height);
//
//        int k = 0;
//        int l = 0;
//        g.setColor(color3);
//        for (int i1 = 3; i1 >= 0; i1--) {
//            g.drawRect(k, 0, board_width - 3, board_height - 3);
//            k++;
//            for (int j1 = 3; j1 >= 0; j1--) {
//                g.drawRect(k - 1, j1, board_width - 3, board_height - 3);
//                l++;
//            }
//
//        }
//
//        for (int k1 = gor_lines * 10 - 1; k1 >= 0; k1--) {
//            int l1 = k1;
//            if (poligons[l1][0][0] != -1) {
//                if (poligons[l1][0][0] == 1)
//                    g.setColor(color1);
//                if (poligons[l1][0][0] == 2)
//                    g.setColor(color2);
//                for (int j2 = 1; poligons[l1][0][j2 + 1] > 0 && poligons[l1][1][j2 + 1] > 0; j2 += 2) {
//                    g.drawLine(poligons[l1][0][j2], poligons[l1][1][j2], poligons[l1][0][j2 + 1], poligons[l1][1][j2 + 1]);
//                    g.drawLine(poligons[l1][0][j2], poligons[l1][1][j2] + 1, poligons[l1][0][j2 + 1], poligons[l1][1][j2 + 1] + 1);
//                    g.drawLine(poligons[l1][0][j2] - 1, poligons[l1][1][j2], poligons[l1][0][j2 + 1] - 1, poligons[l1][1][j2 + 1]);
//                }
//
//            }
//        }
//
//        for (int i2 = 0; i2 < gor_lines; i2++) {
//            int k2 = i2;
//            for (int l2 = 0; l2 < vert_lines; l2++) {
//                if (mass_board[k2][l2] == r) {
//                    g.setColor(color1);
//                    g.fillOval(x_board[l2] - 4, y_board[k2] - 4, oval_size, oval_size);
//                    if (expanded_dots[k2][l2] == 1) {
//                        g.setColor(color);
//                        g.fillOval(x_board[l2] - 4, y_board[k2] - 4, oval_size, oval_size);
//                        g.setColor(color1);
//                        g.drawOval(x_board[l2] - 4, y_board[k2] - 4, oval_size, oval_size);
//                        g.drawOval(x_board[l2] - 3, y_board[k2] - 4, oval_size, oval_size);
//                        g.drawOval(x_board[l2] - 4, y_board[k2] - 3, oval_size, oval_size);
//                    }
//                }
//                if (mass_board[k2][l2] == G) {
//                    g.setColor(color2);
//                    g.fillOval(x_board[l2] - 4, y_board[k2] - 4, oval_size, oval_size);
//                    if (expanded_dots[k2][l2] == 1) {
//                        g.setColor(color);
//                        g.fillOval(x_board[l2] - 4, y_board[k2] - 4, oval_size, oval_size);
//                        g.setColor(color2);
//                        g.drawOval(x_board[l2] - 4, y_board[k2] - 4, oval_size, oval_size);
//                        g.drawOval(x_board[l2] - 3, y_board[k2] - 4, oval_size, oval_size);
//                        g.drawOval(x_board[l2] - 4, y_board[k2] - 3, oval_size, oval_size);
//                    }
//                }
//            }
//
//        }
//
//        if (restart != 1) {
//            if (last_dot_str == r)
//                g.setColor(color1);
//            if (last_dot_str == G)
//                g.setColor(color2);
//            g.fillOval(last_dot_x - oval_size, last_dot_y - oval_size, oval_size * 2, oval_size * 2);
//        }
//        Font font = new Font("TimesRoman", 1, 24);
//        g.setFont(font);
//        if (restart != 1) {
//            g.setColor(color1);
//            g.drawString(red_exp_str, message_vert_line + step * 2, y_board[6] + step_1_of_2);
//            g.setColor(color2);
//            g.drawString(green_exp_str, message_vert_line + step * 2, y_board[9] + step_1_of_2);
//            g.drawLine(message_vert_line + step, y_board[5], message_vert_line + step * 5, y_board[5]);
//            g.drawLine(message_vert_line + step, y_board[7], message_vert_line + step * 5, y_board[7]);
//            g.drawLine(message_vert_line + step, y_board[5], message_vert_line + step, y_board[7]);
//            g.drawLine(message_vert_line + step + step * 4, y_board[5], message_vert_line + step * 5, y_board[7]);
//            g.fillOval((message_vert_line + step) - 4, y_board[5] - 4, oval_size, oval_size);
//            g.fillOval((message_vert_line + step + step * 4) - 4, y_board[5] - 4, oval_size, oval_size);
//            g.fillOval((message_vert_line + step) - 4, y_board[7] - 4, oval_size, oval_size);
//            g.fillOval((message_vert_line + step + step * 4) - 4, y_board[7] - 4, oval_size, oval_size);
//            g.setColor(color1);
//            g.drawLine(message_vert_line + step, y_board[8], message_vert_line + step * 5, y_board[8]);
//            g.drawLine(message_vert_line + step, y_board[10], message_vert_line + step * 5, y_board[10]);
//            g.drawLine(message_vert_line + step, y_board[8], message_vert_line + step, y_board[10]);
//            g.drawLine(message_vert_line + step + step * 4, y_board[8], message_vert_line + step * 5, y_board[10]);
//            g.fillOval((message_vert_line + step) - 4, y_board[8] - 4, oval_size, oval_size);
//            g.fillOval((message_vert_line + step + step * 4) - 4, y_board[8] - 4, oval_size, oval_size);
//            g.fillOval((message_vert_line + step) - 4, y_board[10] - 4, oval_size, oval_size);
//            g.fillOval((message_vert_line + step + step * 4) - 4, y_board[10] - 4, oval_size, oval_size);
//        }
//        Font font1 = new Font("TimesRoman", 1, 18);
//        g.setFont(font1);
//        g.setColor(color4);
//        Font font2 = new Font("TimesRoman", 1, 16);
//        g.setFont(font2);
//        g.drawString("Dvortsoft Tochki", message_vert_line, y_board[1]);
//        Font font3 = new Font("TimesRoman", 2, 16);
//        g.setFont(font3);
//        g.drawString("version 2.5", message_vert_line + 18, y_board[2]);
//        if (restart == 1) {
//            g.drawString("Dmitri Dvortsov", message_vert_line + step_1_of_2, y_board[17]);
//            g.drawString("    copyright.", message_vert_line + step_1_of_2, y_board[18]);
//            g.drawString("(\u041A.\u041F\u0440\u0443\u0442\u043A\u043E\u0432).", message_vert_line + step * 3, y_board[11]);
//            Font font4 = new Font("TimesRoman", 1, 16);
//            g.setFont(font4);
//            g.drawString(kavichki + "\u0429\u0451\u043B\u043A\u043D\u0438 \u043A\u043E\u0431\u044B\u043B\u0443 \u0432 \u043D\u043E\u0441", (message_vert_line - step) + 4, y_board[9]);
//            g.drawString("- \u043E\u043D\u0430 \u043C\u0430\u0445\u043D\u0451\u0442 \u0445\u0432\u043E\u0441\u0442\u043E\u043C." + kavichki, (message_vert_line - step) + 4, y_board[10]);
//        }
//        Font font5 = new Font("TimesRoman", 1, 16);
//        g.setFont(font5);
//        g.drawString(message[0], message_vert_line, y_board[14]);
//        g.drawString(message[1], message_vert_line, y_board[16]);
//        message[0] = " ";
//        message[1] = " ";
//        if (game_over != 0)
//            g.drawString("\u041A\u041E\u041D\u0415\u0426 \u0418\u0413\u0420\u042B !", message_vert_line, y_board[4]);
//        repai = 1;
//    }
//
//    public void my_set_cursor(int i) {
//        my_cursor = new Cursor(i);
//        setCursor(my_cursor);
//    }
//
//    public void write_message(String s) {
//        if (message[0] == " ") {
//            message[0] = s;
//            return;
//        } else {
//            message[1] = s;
//            return;
//        }
//    }
//
//    public void my_repaint() {
//        repaint();
//    }
//
//    public boolean set_active() {
//        for (int i = 0; active != i; active = i) ;
//        return true;
//    }
//
//    public void set_clear_field(int i) {
//        to_clear_field = i;
//    }
//
//    public boolean stand_pr_dot(int i, int j) {
//        for (; OLD_last_dot_x != last_dot_x; OLD_last_dot_x = last_dot_x) ;
//        for (; OLD_last_dot_y != last_dot_y; OLD_last_dot_y = last_dot_y) ;
//        mass_board[j][i] = G;
//        dot_condition[j][i] = G;
//        last_dot_str = G;
//        last_dot_str = last_dot_str;
//        relative_last_x = i;
//        relative_last_x = relative_last_x;
//        last_dot_x = x_board[i];
//        last_dot_x = last_dot_x;
//        relative_last_y = j;
//        relative_last_y = relative_last_y;
//        last_dot_y = y_board[j];
//        last_dot_y = last_dot_y;
//        return true;
//    }
//
//    public void set_restart(int i, int j, int k) {
//        restart = i;
//        click_mouse_y = 20;
//        user_done = j;
//        to_clear_field = k;
//        red_expanded = 0;
//        green_expanded = 0;
//        red_exp_str = " ";
//        green_exp_str = " ";
//        game_over = 0;
//        my_repaint();
//    }
//
//    public boolean counter() {
//        red_expanded = 0;
//        green_expanded = 0;
//        for (int i = 0; i < gor_lines; i++) {
//            int j = i;
//            for (int k = 0; k < vert_lines; k++) {
//                if (mass_board[j][k] == r && expanded_dots[j][k] == 1)
//                    red_expanded++;
//                if (mass_board[j][k] == G && expanded_dots[j][k] == 1)
//                    green_expanded++;
//            }
//
//        }
//
//        String s = " ";
//        String s1 = " ";
//        red_exp_str = String.valueOf(red_expanded);
//        green_exp_str = String.valueOf(green_expanded);
//        return true;
//    }
//
//    public void set_click(int i, int j, int k, int l, int ai[], int ai1[]) {
//        if (active == 0) {
//            int i1 = -1;
//            int j1 = -1;
//            for (int k1 = 0; k1 < vert_lines; k1++)
//                if (k > ai[k1] - (step_1_of_2 - 2) && k < ai[k1] + step_1_of_2 + 3) {
//                    click_mouse_x = ai[k1];
//                    j1 = k1;
//                }
//
//            for (int l1 = 0; l1 < gor_lines; l1++)
//                if (l > ai1[l1] - (step_1_of_2 - 2) && l < ai1[l1] + step_1_of_2 + 3) {
//                    click_mouse_y = ai1[l1];
//                    i1 = l1;
//                }
//
//            if (i1 != -1 || j1 != -1) {
//                if (mass_board[i1][j1] == f && dot_condition[i1][j1] == f) {
//                    mass_board[i1][j1] = r;
//                    dot_condition[i1][j1] = r;
//                    for (; OLD_last_dot_x != last_dot_x; OLD_last_dot_x = last_dot_x) ;
//                    for (; OLD_last_dot_y != last_dot_y; OLD_last_dot_y = last_dot_y) ;
//                    last_dot_str = r;
//                    last_dot_str = last_dot_str;
//                    relative_last_x = j1;
//                    relative_last_x = relative_last_x;
//                    last_dot_x = x_board[j1];
//                    last_dot_x = last_dot_x;
//                    last_dot_y = y_board[i1];
//                    last_dot_y = last_dot_y;
//                    relative_last_y = i1;
//                    relative_last_y = relative_last_y;
//                    int i2 = 1;
//                    active = i2;
//                    return;
//                }
//                if (dot_condition[i1][j1] == n) {
//                    write_message(" \u0410 \u043E\u043D\u043E \u0432\u0430\u043C \u043D\u0430\u0434\u043E ?!");
//                    my_repaint();
//                    return;
//                }
//                write_message("       \u0417\u0430\u043D\u044F\u0442\u043E !     ");
//                my_repaint();
//            }
//        }
//    }
//
//    public boolean yes_free() {
//        for (int i = 0; i < gor_lines; i++) {
//            int j = i;
//            for (int k = 0; k < vert_lines; k++)
//                if (dot_condition[j][k] == f)
//                    return true;
//
//        }
//
//        return false;
//    }
//
//    public boolean progr_set_dot() {
//        int ai[] = new int[2];
//        ai[1] = -1;
//        ai[0] = -1;
//        my_odin = new Odin(dot_condition, expanded_dots, gor_lines, vert_lines, single_danger);
//        ai = my_odin.make_hod();
//        if (ai[0] >= 0 && ai[1] >= 0) {
//            if (dot_condition[ai[1]][ai[0]] == f) {
//                stand_pr_dot(ai[0], ai[1]);
//                set_last_dot();
//            } else {
//                my_odin2 = new Odin(dot_condition, expanded_dots, gor_lines, vert_lines, single_danger);
//                int ai1[] = my_odin2.accessories_random();
//                stand_pr_dot(ai1[0], ai1[1]);
//                set_last_dot();
//            }
//        } else {
//            my_odin2 = new Odin(dot_condition, expanded_dots, gor_lines, vert_lines, single_danger);
//            int ai2[] = my_odin2.accessories_random();
//            stand_pr_dot(ai2[0], ai2[1]);
//            set_last_dot();
//        }
//        return true;
//    }
//
//    public boolean set_last_dot() {
//        dot_condition[relative_last_y][relative_last_x] = last_dot_str;
//        return true;
//    }
//
//    public boolean second_polig(char c) {
//        search_poligons(c);
//        return true;
//    }
//
//    public int[][] chang_mas_num(int ai[][], int i, int j) {
//        for (int k = 0; k < gor_lines; k++) {
//            int l = k;
//            for (int i1 = 0; i1 < vert_lines; i1++)
//                if (ai[l][i1] == j)
//                    ai[l][i1] = i;
//
//        }
//
//        return ai;
//    }
//
//    public int mas_expand(int ai[][]) {
//        int i = 0;
//        int j = (gor_lines + vert_lines) * 2;
//        int ai1[] = new int[j];
//        for (int k = 0; k < j; k++)
//            ai1[k] = 0;
//
//        for (int l = 0; l < vert_lines * 2; l++) {
//            int i1 = l;
//            if (i1 < vert_lines)
//                ai1[i1] = ai[0][i1];
//            if (vert_lines <= i1 && i1 < vert_lines * 2)
//                ai1[i1] = ai[gor_lines - 1][i1 - vert_lines];
//        }
//
//        for (int j1 = vert_lines * 2; vert_lines * 2 <= j1 && j1 < j; j1++) {
//            int k1 = j1;
//            if (vert_lines * 2 <= k1 && k1 < vert_lines * 2 + gor_lines)
//                ai1[k1] = ai[k1 - vert_lines * 2][0];
//            if (vert_lines * 2 + gor_lines <= k1 && k1 < vert_lines * 2 + gor_lines * 2)
//                ai1[k1] = ai[k1 - (vert_lines * 2 + gor_lines)][vert_lines - 1];
//        }
//
//        for (int l1 = 1; l1 < gor_lines - 1; l1++) {
//            int i2 = l1;
//            for (int j2 = 1; j2 < vert_lines - 1; j2++) {
//                int k2 = j2;
//                for (int l2 = 0; l2 < j; l2++) {
//                    if (ai[i2][k2] != ai1[l2] && l2 == j - 1 && mass_board[i2][k2] != f && (dot_condition[i2][k2] == r || dot_condition[i2][k2] == G))
//                        return ai[i2][k2];
//                    if (ai[i2][k2] == ai1[l2] || ai[i2][k2] == 0)
//                        break;
//                }
//
//            }
//
//        }
//
//        return i;
//    }
//
//    public boolean add_poligon(int ai[][], int i, char c) {
//        int j = 0;
//        char c1 = r;
//        char c2 = r;
//        char c3 = r;
//        byte byte0 = 1;
//        if (c == r) {
//            c1 = G;
//            c2 = G;
//            byte0 = 2;
//            char c4 = G;
//        }
//        for (int k = 0; k < gor_lines * 10; k++) {
//            j = k;
//            if (poligons[j][0][0] == -1)
//                break;
//        }
//
//        for (int l = 0; l < 3; l++) {
//            int i1 = 1;
//            poligons[j][0][0] = byte0;
//            for (int j1 = 1; j1 < gor_lines - 1; j1++) {
//                int k1 = j1;
//                for (int l1 = 1; l1 < vert_lines - 1; l1++) {
//                    int i2 = l1;
//                    if (ai[k1][i2] == i && dot_condition[k1][i2] == c)
//                        while (dot_condition[k1][i2] != c2) {
//                            dot_condition[k1][i2] = c2;
//                            expanded_dots[k1][i2] = 1;
//                        }
//                    if (ai[k1][i2] == i && dot_condition[k1][i2] == f)
//                        for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                    if (ai[k1][i2] == i && ai[k1 - 1][i2] != i && ai[k1][i2 - 1] != i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        if (ai[k1 - 1][i2 - 1] != i) {
//                            for (; dot_condition[k1][i2 - 1] != c1; dot_condition[k1][i2 - 1] = c1) ;
//                            for (; dot_condition[k1 - 1][i2] != c1; dot_condition[k1 - 1][i2] = c1) ;
//                            for (; poligons[j][0][i1] != x_board[i2]; poligons[j][0][i1] = x_board[i2]) ;
//                            for (; poligons[j][1][i1] != y_board[k1 - 1]; poligons[j][1][i1] = y_board[k1 - 1]) ;
//                            for (; poligons[j][0][i1 + 1] != x_board[i2 - 1]; poligons[j][0][i1 + 1] = x_board[i2 - 1])
//                                ;
//                            for (; poligons[j][1][i1 + 1] != y_board[k1]; poligons[j][1][i1 + 1] = y_board[k1]) ;
//                            i1 += 2;
//                        }
//                    }
//                    if (ai[k1][i2] == i && ai[k1 - 1][i2] != i && ai[k1][i2 + 1] != i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        if (ai[k1 - 1][i2 + 1] != i) {
//                            for (; dot_condition[k1][i2 + 1] != c1; dot_condition[k1][i2 + 1] = c1) ;
//                            for (; dot_condition[k1 - 1][i2] != c1; dot_condition[k1 - 1][i2] = c1) ;
//                            for (; poligons[j][0][i1] != x_board[i2]; poligons[j][0][i1] = x_board[i2]) ;
//                            for (; poligons[j][1][i1] != y_board[k1 - 1]; poligons[j][1][i1] = y_board[k1 - 1]) ;
//                            for (; poligons[j][0][i1 + 1] != x_board[i2 + 1]; poligons[j][0][i1 + 1] = x_board[i2 + 1])
//                                ;
//                            for (; poligons[j][1][i1 + 1] != y_board[k1]; poligons[j][1][i1 + 1] = y_board[k1]) ;
//                            i1 += 2;
//                        }
//                    }
//                    if (ai[k1][i2] == i && ai[k1 + 1][i2] != i && ai[k1][i2 + 1] != i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        if (ai[k1 + 1][i2 + 1] != i) {
//                            for (; dot_condition[k1][i2 + 1] != c1; dot_condition[k1][i2 + 1] = c1) ;
//                            for (; dot_condition[k1 + 1][i2] != c1; dot_condition[k1 + 1][i2] = c1) ;
//                            for (; poligons[j][0][i1] != x_board[i2]; poligons[j][0][i1] = x_board[i2]) ;
//                            for (; poligons[j][1][i1] != y_board[k1 + 1]; poligons[j][1][i1] = y_board[k1 + 1]) ;
//                            for (; poligons[j][0][i1 + 1] != x_board[i2 + 1]; poligons[j][0][i1 + 1] = x_board[i2 + 1])
//                                ;
//                            for (; poligons[j][1][i1 + 1] != y_board[k1]; poligons[j][1][i1 + 1] = y_board[k1]) ;
//                            i1 += 2;
//                        }
//                    }
//                    if (ai[k1][i2] == i && ai[k1 + 1][i2] != i && ai[k1][i2 - 1] != i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        if (ai[k1 + 1][i2 - 1] != i) {
//                            for (; dot_condition[k1][i2 - 1] != c1; dot_condition[k1][i2 - 1] = c1) ;
//                            for (; dot_condition[k1 + 1][i2] != c1; dot_condition[k1 + 1][i2] = c1) ;
//                            for (; poligons[j][0][i1] != x_board[i2]; poligons[j][0][i1] = x_board[i2]) ;
//                            for (; poligons[j][1][i1] != y_board[k1 + 1]; poligons[j][1][i1] = y_board[k1 + 1]) ;
//                            for (; poligons[j][0][i1 + 1] != x_board[i2 - 1]; poligons[j][0][i1 + 1] = x_board[i2 - 1])
//                                ;
//                            for (; poligons[j][1][i1 + 1] != y_board[k1]; poligons[j][1][i1 + 1] = y_board[k1]) ;
//                            i1 += 2;
//                        }
//                    }
//                    if (ai[k1][i2] == i && ai[k1][i2 - 1] != i && ai[k1 - 1][i2 - 1] != i && ai[k1 - 1][i2] == i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        for (; dot_condition[k1][i2 - 1] != c1; dot_condition[k1][i2 - 1] = c1) ;
//                        for (; dot_condition[k1 - 1][i2 - 1] != c1; dot_condition[k1 - 1][i2 - 1] = c1) ;
//                        for (; poligons[j][0][i1] != x_board[i2 - 1]; poligons[j][0][i1] = x_board[i2 - 1]) ;
//                        for (; poligons[j][1][i1] != y_board[k1]; poligons[j][1][i1] = y_board[k1]) ;
//                        for (; poligons[j][0][i1 + 1] != x_board[i2 - 1]; poligons[j][0][i1 + 1] = x_board[i2 - 1]) ;
//                        for (; poligons[j][1][i1 + 1] != y_board[k1 - 1]; poligons[j][1][i1 + 1] = y_board[k1 - 1]) ;
//                        i1 += 2;
//                    }
//                    if (ai[k1][i2] == i && ai[k1][i2 - 1] != i && ai[k1 + 1][i2 - 1] != i && ai[k1 + 1][i2] == i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        for (; dot_condition[k1][i2 - 1] != c1; dot_condition[k1][i2 - 1] = c1) ;
//                        for (; dot_condition[k1 + 1][i2 - 1] != c1; dot_condition[k1 + 1][i2 - 1] = c1) ;
//                        for (; poligons[j][0][i1] != x_board[i2 - 1]; poligons[j][0][i1] = x_board[i2 - 1]) ;
//                        for (; poligons[j][1][i1] != y_board[k1]; poligons[j][1][i1] = y_board[k1]) ;
//                        for (; poligons[j][0][i1 + 1] != x_board[i2 - 1]; poligons[j][0][i1 + 1] = x_board[i2 - 1]) ;
//                        for (; poligons[j][1][i1 + 1] != y_board[k1 + 1]; poligons[j][1][i1 + 1] = y_board[k1 + 1]) ;
//                        i1 += 2;
//                    }
//                    if (ai[k1][i2] == i && ai[k1][i2 + 1] != i && ai[k1 - 1][i2 + 1] != i && ai[k1 - 1][i2] == i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        for (; dot_condition[k1 - 1][i2 + 1] != c1; dot_condition[k1 - 1][i2 + 1] = c1) ;
//                        for (; dot_condition[k1][i2 + 1] != c1; dot_condition[k1][i2 + 1] = c1) ;
//                        for (; poligons[j][0][i1] != x_board[i2 + 1]; poligons[j][0][i1] = x_board[i2 + 1]) ;
//                        for (; poligons[j][1][i1] != y_board[k1 - 1]; poligons[j][1][i1] = y_board[k1 - 1]) ;
//                        for (; poligons[j][0][i1 + 1] != x_board[i2 + 1]; poligons[j][0][i1 + 1] = x_board[i2 + 1]) ;
//                        for (; poligons[j][1][i1 + 1] != y_board[k1]; poligons[j][1][i1 + 1] = y_board[k1]) ;
//                        i1 += 2;
//                    }
//                    if (ai[k1][i2] == i && ai[k1][i2 + 1] != i && ai[k1 + 1][i2 + 1] != i && ai[k1 + 1][i2] == i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        for (; dot_condition[k1][i2 + 1] != c1; dot_condition[k1][i2 + 1] = c1) ;
//                        for (; dot_condition[k1 + 1][i2 + 1] != c1; dot_condition[k1 + 1][i2 + 1] = c1) ;
//                        for (; poligons[j][0][i1] != x_board[i2 + 1]; poligons[j][0][i1] = x_board[i2 + 1]) ;
//                        for (; poligons[j][1][i1] != y_board[k1]; poligons[j][1][i1] = y_board[k1]) ;
//                        for (; poligons[j][0][i1 + 1] != x_board[i2 + 1]; poligons[j][0][i1 + 1] = x_board[i2 + 1]) ;
//                        for (; poligons[j][1][i1 + 1] != y_board[k1 + 1]; poligons[j][1][i1 + 1] = y_board[k1 + 1]) ;
//                        i1 += 2;
//                    }
//                    if (ai[k1][i2] == i && ai[k1 - 1][i2 - 1] != i && ai[k1 - 1][i2] != i && ai[k1][i2 - 1] == i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        for (; dot_condition[k1 - 1][i2 - 1] != c1; dot_condition[k1 - 1][i2 - 1] = c1) ;
//                        for (; dot_condition[k1 - 1][i2] != c1; dot_condition[k1 - 1][i2] = c1) ;
//                        for (; poligons[j][0][i1] != x_board[i2 - 1]; poligons[j][0][i1] = x_board[i2 - 1]) ;
//                        for (; poligons[j][1][i1] != y_board[k1 - 1]; poligons[j][1][i1] = y_board[k1 - 1]) ;
//                        for (; poligons[j][0][i1 + 1] != x_board[i2]; poligons[j][0][i1 + 1] = x_board[i2]) ;
//                        for (; poligons[j][1][i1 + 1] != y_board[k1 - 1]; poligons[j][1][i1 + 1] = y_board[k1 - 1]) ;
//                        i1 += 2;
//                    }
//                    if (ai[k1][i2] == i && ai[k1 + 1][i2 - 1] != i && ai[k1 + 1][i2] != i && ai[k1][i2 - 1] == i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        for (; dot_condition[k1 + 1][i2 - 1] != c1; dot_condition[k1 + 1][i2 - 1] = c1) ;
//                        for (; dot_condition[k1 + 1][i2] != c1; dot_condition[k1 + 1][i2] = c1) ;
//                        for (; poligons[j][0][i1] != x_board[i2 - 1]; poligons[j][0][i1] = x_board[i2 - 1]) ;
//                        for (; poligons[j][1][i1] != y_board[k1 + 1]; poligons[j][1][i1] = y_board[k1 + 1]) ;
//                        for (; poligons[j][0][i1 + 1] != x_board[i2]; poligons[j][0][i1 + 1] = x_board[i2]) ;
//                        for (; poligons[j][1][i1 + 1] != y_board[k1 + 1]; poligons[j][1][i1 + 1] = y_board[k1 + 1]) ;
//                        i1 += 2;
//                    }
//                    if (ai[k1][i2] == i && ai[k1 - 1][i2] != i && ai[k1 - 1][i2 + 1] != i && ai[k1][i2 + 1] == i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        for (; dot_condition[k1 - 1][i2] != c1; dot_condition[k1 - 1][i2] = c1) ;
//                        for (; dot_condition[k1 - 1][i2 + 1] != c1; dot_condition[k1 - 1][i2 + 1] = c1) ;
//                        for (; poligons[j][0][i1] != x_board[i2]; poligons[j][0][i1] = x_board[i2]) ;
//                        for (; poligons[j][1][i1] != y_board[k1 - 1]; poligons[j][1][i1] = y_board[k1 - 1]) ;
//                        for (; poligons[j][0][i1 + 1] != x_board[i2 + 1]; poligons[j][0][i1 + 1] = x_board[i2 + 1]) ;
//                        for (; poligons[j][1][i1 + 1] != y_board[k1 - 1]; poligons[j][1][i1 + 1] = y_board[k1 - 1]) ;
//                        i1 += 2;
//                    }
//                    if (ai[k1][i2] == i && ai[k1 + 1][i2] != i && ai[k1 + 1][i2 + 1] != i && ai[k1][i2 + 1] == i) {
//                        if (dot_condition[k1][i2] == c)
//                            while (dot_condition[k1][i2] != c2) {
//                                dot_condition[k1][i2] = c2;
//                                expanded_dots[k1][i2] = 1;
//                            }
//                        if (dot_condition[k1][i2] == f)
//                            for (; dot_condition[k1][i2] != n; dot_condition[k1][i2] = n) ;
//                        for (; dot_condition[k1 + 1][i2] != c1; dot_condition[k1 + 1][i2] = c1) ;
//                        for (; dot_condition[k1 + 1][i2 + 1] != c1; dot_condition[k1 + 1][i2 + 1] = c1) ;
//                        for (; poligons[j][0][i1] != x_board[i2]; poligons[j][0][i1] = x_board[i2]) ;
//                        for (; poligons[j][1][i1] != y_board[k1 + 1]; poligons[j][1][i1] = y_board[k1 + 1]) ;
//                        for (; poligons[j][0][i1 + 1] != x_board[i2 + 1]; poligons[j][0][i1 + 1] = x_board[i2 + 1]) ;
//                        for (; poligons[j][1][i1 + 1] != y_board[k1 + 1]; poligons[j][1][i1 + 1] = y_board[k1 + 1]) ;
//                        i1 += 2;
//                    }
//                }
//
//            }
//
//        }
//
//        return true;
//    }
//
//    public boolean search_poligons(char c) {
//        for (int i = 0; i < 4; i++) {
//            loc_mas_numb = 1;
//            loc_mas_numb = loc_mas_numb;
//            int ai[][] = new int[gor_lines][vert_lines];
//            local_mas_temp = ai;
//            for (int j = 0; j < vert_lines; j++) {
//                int k = j;
//                for (int i1 = 0; i1 < gor_lines; i1++) {
//                    int l1 = i1;
//                    if (dot_condition[l1][k] == c || dot_condition[l1][k] == f || dot_condition[l1][k] == n) {
//                        if (l1 == 0 && k == 0)
//                            local_mas_temp[l1][k] = loc_mas_numb;
//                        if (l1 != 0 && k == 0)
//                            local_mas_temp[l1][k] = loc_mas_numb;
//                        if (k > 0)
//                            if (local_mas_temp[l1][k - 1] != 0)
//                                local_mas_temp[l1][k] = local_mas_temp[l1][k - 1];
//                            else
//                                local_mas_temp[l1][k] = loc_mas_numb;
//                    } else {
//                        loc_mas_numb = loc_mas_numb + 1;
//                    }
//                }
//
//            }
//
//            for (int l = 0; l < gor_lines; l++) {
//                int j1 = l;
//                for (int i2 = 0; i2 < vert_lines; i2++) {
//                    int j2 = i2;
//                    if (local_mas_temp[j1][j2] != 0) {
//                        int k2 = local_mas_temp[j1][j2];
//                        if (j1 + 1 < gor_lines) {
//                            int l2 = local_mas_temp[j1 + 1][j2];
//                            if (l2 != 0 && l2 != k2) {
//                                int ai1[][] = chang_mas_num(local_mas_temp, k2, l2);
//                                local_mas_temp = ai1;
//                            }
//                        }
//                    }
//                }
//
//            }
//
//            int k1 = mas_expand(local_mas_temp);
//            if (k1 != 0)
//                add_poligon(local_mas_temp, k1, c);
//        }
//
//        return true;
//    }
//
//    public boolean mouseDown(Event event, int i, int j) {
//        if (active == 0) {
//            if (i < click_range_x_h && j < click_range_y_h && i > click_range_x_l && j > click_range_y_l)
//                set_click(click_mouse_x, click_mouse_y, i, j, x_board, y_board);
//            if (i < board_width - (step_1_of_2 - 7) && j < board_height - (step_1_of_2 - 7) && i > step_1_of_2 - 7 && j > step_1_of_2 - 7) {
//                int k = 1;
//                user_done = k;
//                int l = 0;
//                restart = l;
//            }
//        }
//        return false;
//    }
//
//    public boolean keyDown(Event event, int i) {
//        int j = i;
//        char c = '0';
//        c = (char) j;
//        if (c == 'v')
//            System.out.println("version 2.53");
//        return true;
//    }
//
//    public boolean action(Event event, Object obj) {
//        if ("Go to http://beloostrov-spb.narod.ru".equals(obj))
//            try {
//                URL url = new URL("http://beloostrov-spb.narod.ru");
//                AppletContext appletcontext = getAppletContext();
//                appletcontext.showDocument(url);
//            } catch (MalformedURLException _ex) {
//            }
//        if ("RESTART".equals(obj)) {
//            restart = 1;
//            to_clear_field = 1;
//            user_done = 0;
//            my_tochki_draw.set_restart(restart, user_done, to_clear_field);
//        }
//        if ("EXIT".equals(obj))
//            System.exit(0);
//        return true;
//    }
//
//    public void start() {
//        tochki_thread = new Thread(this);
//        tochki_thread.start();
//    }
//
//    public void run() {
//        do {
//            String s = str_button.getLabel();
//            if (restart == 0 && s != "RESTART")
//                str_button.setLabel("RESTART");
//            if (restart == 1 && s != "-------------------")
//                str_button.setLabel("-------------------");
//            if (my_tochki_draw.to_clear_field == 1 && my_tochki_draw.lining()) {
//                my_tochki_draw.my_repaint();
//                int i = 0;
//                my_tochki_draw.set_clear_field(i);
//            }
//            if (my_tochki_draw.odin_load == 0) {
//                my_odin = new Odin();
//                int j = 0;
//                j = my_odin.loading(my_tochki_draw.odin_load);
//                my_tochki_draw.odin_load = j;
//            }
//            if (my_tochki_draw.active == 1)
//                if (!my_tochki_draw.yes_free()) {
//                    my_tochki_draw.game_over = 1;
//                    my_tochki_draw.my_repaint();
//                    try {
//                        Thread.sleep(1500L);
//                    } catch (InterruptedException _ex) {
//                        System.out.println("sleep error");
//                    }
//                    my_tochki_draw.active = 0;
//                } else {
//                    count_time.set_ind(1);
//                    my_set_cursor(3);
//                    my_tochki_draw.set_last_dot();
//                    if (my_tochki_draw.search_poligons(G)) {
//                        my_tochki_draw.repai = 0;
//                        my_tochki_draw.my_repaint();
//                        while (my_tochki_draw.repai != 1)
//                            try {
//                                Thread.sleep(17L);
//                            } catch (InterruptedException _ex) {
//                                System.out.println("sleep error");
//                            }
//                        while (!my_tochki_draw.progr_set_dot()) ;
//                        count_time.set_ind(0);
//                    }
//                    my_tochki_draw.set_last_dot();
//                    if (my_tochki_draw.second_polig(r)) {
//                        my_tochki_draw.counter();
//                        my_tochki_draw.repai = 0;
//                        if (!my_tochki_draw.yes_free())
//                            my_tochki_draw.game_over = 1;
//                        my_tochki_draw.my_repaint();
//                        while (my_tochki_draw.repai != 1)
//                            try {
//                                Thread.sleep(17L);
//                            } catch (InterruptedException _ex) {
//                                System.out.println("sleep error");
//                            }
//                        my_set_cursor(0);
//                        while (!my_tochki_draw.set_active()) ;
//                    }
//                }
//            try {
//                Thread.sleep(47L);
//            } catch (InterruptedException _ex) {
//                System.out.println("sleep error");
//            }
//        } while (true);
//    }
//
//    public static int board_width = 496;
//    public static int board_height;
//    public static int step;
//    Tochki my_tochki_draw;
//    int restart;
//    int to_clear_field;
//    int gor_lines;
//    int vert_lines;
//    int active;
//    int user_done;
//    int click_mouse_x;
//    int click_mouse_y;
//    int oval_size;
//    int step_1_of_2;
//    int game_over;
//    int message_vert_line;
//    int red_expanded;
//    int green_expanded;
//    String red_exp_str;
//    String green_exp_str;
//    String message[];
//    public static char r = 'r';
//    public static char G = 'G';
//    public static char f = 'f';
//    public static char n = 'n';
//    int loc_mas_numb;
//    Thread tochki_thread;
//    public int is_programm;
//    Cursor my_cursor;
//    String start_second;
//    Button str_button;
//    int click_range_x_l;
//    int click_range_y_l;
//    int click_range_x_h;
//    int click_range_y_h;
//    char mass_board[][];
//    int OLD_last_dot_x;
//    int OLD_last_dot_y;
//    int x_board[];
//    int y_board[];
//    char last_dot_str;
//    int last_dot_x;
//    int last_dot_y;
//    int relative_last_x;
//    int relative_last_y;
//    char dot_condition[][];
//    char my_test[][];
//    int expanded_dots[][];
//    int local_mas_temp[][];
//    int poligons[][][];
//    int gor_random;
//    int vert_random;
//    char kavichki;
//    int repai;
//    int one;
//    int odin_load;
//    Odin my_odin;
//    Odin my_odin2;
//    Thread theThread;
//    CTime count_time;
//    TextField my_text_field;
//    int single_danger;
//    Image offScreenImage;
//
//    static {
//        board_height = 336;
//        step = board_height / 21;
//    }
//}
