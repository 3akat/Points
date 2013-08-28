// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 27.08.2013 11:35:18
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Odin.java


public class Odin {

    public Odin(char ac[][], int ai[][], int i, int j, int k) {
        loc_mas_numb = 1;
        min_safety_red = 0x5f5e100;
        min_safety_green = 0x5f5e100;
        safety_test_flag = -1;
        position = 1;
        gor_lines = i;
        vert_lines = j;
        gor_lines_ext = i + (log_shift << 1);
        vert_lines_ext = j + (log_shift << 1);
        odin_condition = new char[i][j];
        odin_condition = ac;
        odin_condition = odin_condition;
        odin_condition_ext = new char[gor_lines_ext][vert_lines_ext];
        for (int l = 0; l < gor_lines_ext; l++) {
            int i1 = l;
            for (int k1 = 0; k1 < vert_lines_ext; k1++)
                odin_condition_ext[i1][k1] = f;

        }

        for (int j1 = 0; j1 < gor_lines; j1++) {
            int l1 = j1;
            for (int i2 = 0; i2 < vert_lines; i2++)
                odin_condition_ext[l1 + log_shift][i2 + log_shift] = odin_condition[l1][i2];

        }

        want_user = new int[i][j];
        want_programm = new int[i][j];
        want_user_value = new int[i][j];
        want_programm_value = new int[i][j];
        safety_green_massiv = new int[i][j];
        safety_red_massiv = new int[i][j];
        danger_all = new int[i][j];
        podstav_tretju = new int[i][j];
        expanded_dots = new int[i][j];
        expanded_dots = ai;
        max_red = new int[2];
        max_green = new int[2];
        single_danger = k;
    }

    public Odin() {
        loc_mas_numb = 1;
        min_safety_red = 0x5f5e100;
        min_safety_green = 0x5f5e100;
        safety_test_flag = -1;
        position = 1;
    }

    public int loading(int i) {
        int j = 0;
        i = 1;
        j = i;
        return j;
    }

    public int[] my_random() {
        int ai[] = new int[2];
        sec_ms = System.currentTimeMillis();
        String s = "";
        String s1 = "";
        String s2 = String.valueOf(sec_ms);
        s = s2.substring(11, 13);
        s1 = s2.substring(10, 12);
        int i = Integer.parseInt(s);
        int j = Integer.parseInt(s1);
        vert_random = (j - j % 5) / 5;
        for (gor_random = (i - i % 3) / 3; gor_random > 29; gor_random = gor_random - 2) ;
        for (; vert_random > 19; vert_random--) ;
        ai[0] = gor_random;
        ai[1] = vert_random;
        return ai;
    }

    public int[] accessories_random() {
        int ai[] = new int[2];
        int ai1[] = my_random();
        int i = ai1[0];
        int j = ai1[1];
        ai = free_search(i, j);
        return ai;
    }

    public int clear_danger() {
        odin_condition_ext[log_shift + 4][log_shift + 4] = G;
        int i = search_dengerous(G);
        return i;
    }

    public int val_sravnenie(int ai[][], int ai1[][], int i) {
        int j = 0;
        for (int k = 0; k < gor_lines; k++) {
            int l = k;
            for (int i1 = 0; i1 < vert_lines; i1++) {
                int j1 = i1;
                if (ai[l][j1] == i && j < ai1[l][j1])
                    j = ai1[l][j1];
            }

        }

        return j;
    }

    public int[] make_hod() {
        int ai[] = new int[2];
        ai[0] = -1;
        ai[1] = -1;
        while (!pleace_to_test()) ;
        for (; !odnazhdy(r); odnazhdy(r)) ;
        while (!dvazhdy(r)) ;
        while (!dvazhdy(G)) ;
        while (!angle(r)) ;
        while (!angle(G)) ;
        while (!max_search(want_user, r)) ;
        while (!max_search(want_programm, G)) ;
        while (!want_sum()) ;
        if (max_red[0] > 0 || max_green[0] > 0) {
            if (max_green[0] < max_red[0] && want_sum_programm < want_sum_user && want_sum_user > 4) {
                while (!sravnenie(want_user, want_user_value, max_red[0], 1)) ;
                if (coord_compare[2] > 1)
                    while (!sravnenie(compare, want_programm, 1, 1)) ;
                if (coord_compare[2] > 1)
                    while (!sravnenie(compare, danger_all, 1, -1)) ;
                if (coord_compare[2] > 0) {
                    ai[0] = coord_compare[0];
                    ai[1] = coord_compare[1];
                    return ai;
                }
            }
            if (max_red[0] <= max_green[0]) {
                while (!sravnenie(want_programm, want_programm_value, max_green[0], 1)) ;
                if (coord_compare[2] > 1)
                    while (!sravnenie(compare, want_user, 1, 1)) ;
                if (coord_compare[2] > 1)
                    while (!sravnenie(compare, danger_all, 1, -1)) ;
                if (coord_compare[2] > 0) {
                    ai[0] = coord_compare[0];
                    ai[1] = coord_compare[1];
                    return ai;
                }
            }
        }
        while (!podstavljaem_tretju(r)) ;
        if (red_tretja == 1)
            while (!dvazhdy(r)) ;
        while (!podstavljaem_tretju(G)) ;
        if (green_tretja == 1)
            while (!dvazhdy(G)) ;
        if (green_tretja == 1 || red_tretja == 1) {
            while (!max_search(want_user, r)) ;
            while (!max_search(want_programm, G)) ;
            while (!want_sum()) ;
            if (max_red[0] > 0 || max_green[0] > 0) {
                if (max_green[0] < max_red[0] && want_sum_programm < want_sum_user && want_sum_user > 4) {
                    while (!sravnenie(want_user, want_user_value, max_red[0], 1)) ;
                    if (coord_compare[2] > 1)
                        while (!sravnenie(compare, want_programm, 1, 1)) ;
                    if (coord_compare[2] > 1)
                        while (!sravnenie(compare, danger_all, 1, -1)) ;
                    if (coord_compare[2] > 0) {
                        ai[0] = coord_compare[0];
                        ai[1] = coord_compare[1];
                        return ai;
                    }
                }
                if (max_red[0] <= max_green[0]) {
                    while (!sravnenie(want_programm, want_programm_value, max_green[0], 1)) ;
                    if (coord_compare[2] > 1)
                        while (!sravnenie(compare, want_user, 1, 1)) ;
                    if (coord_compare[2] > 1)
                        while (!sravnenie(compare, danger_all, 1, -1)) ;
                    if (coord_compare[2] > 0) {
                        ai[0] = coord_compare[0];
                        ai[1] = coord_compare[1];
                        return ai;
                    }
                }
            }
        }
        ai = treezhdy();
        if (ai[0] > -1 && ai[1] > -1) {
            return ai;
        } else {
            int ai1[] = accessories_random();
            return ai1;
        }
    }

    public boolean max_search(int ai[][], char c) {
        for (int i = 0; i < 2; i++) {
            int j = i;
            for (int k = 0; k < gor_lines; k++) {
                int l = k;
                for (int i1 = 0; i1 < vert_lines; i1++) {
                    int j1 = i1;
                    if (j == 0) {
                        if (max_red[0] < ai[l][j1] && c == r)
                            max_red[0] = ai[l][j1];
                        if (max_green[0] < ai[l][j1] && c == G)
                            max_green[0] = ai[l][j1];
                    }
                    if (j == 1) {
                        if (c == r && ai[l][j1] == max_red[0] && ai[l][j1] != 0)
                            max_red[1]++;
                        if (c == G && ai[l][j1] == max_green[0] && ai[l][j1] != 0)
                            max_green[1]++;
                    }
                }

            }

        }

        return true;
    }

    public boolean sravnenie(int ai[][], int ai1[][], int i, int j) {
        int k = 0;
        if (j < 0)
            k = -1;
        int l = 0;
        compare = new int[gor_lines][vert_lines];
        coord_compare = new int[3];
        coord_compare[0] = -1;
        coord_compare[1] = -1;
        coord_compare[2] = 0;
        for (int i1 = 0; i1 < 3; i1++) {
            int j1 = i1;
            for (int k1 = 0; k1 < gor_lines; k1++) {
                int l1 = k1;
                for (int i2 = 0; i2 < vert_lines; i2++) {
                    int j2 = i2;
                    if (j1 == 0 && ai[l1][j2] == i) {
                        if (j > 0 && k < ai1[l1][j2])
                            k = ai1[l1][j2];
                        if (k < 0)
                            k = ai1[l1][j2];
                        if (j < 0 && ai1[l1][j2] <= k)
                            k = ai1[l1][j2];
                    }
                    if (j1 == 1 && ai[l1][j2] == i && k == ai1[l1][j2])
                        l++;
                    if (ai[l1][j2] == i && j1 == 2) {
                        if (j1 == 2 && l == 0)
                            return true;
                        if (ai1[l1][j2] == k) {
                            compare[l1][j2] = 1;
                            coord_compare[0] = j2;
                            coord_compare[1] = l1;
                        }
                        coord_compare[2] = l;
                    }
                }

            }

        }

        return true;
    }

    public boolean odnazhdy(char c) {
        char c1 = r;
        if (c == r)
            c1 = G;
        if (odin_condition[0][0] == f)
            danger_all[0][0] = 1;
        if (odin_condition[gor_lines - 1][0] == f)
            danger_all[gor_lines - 1][0] = 1;
        if (odin_condition[0][vert_lines - 1] == f)
            danger_all[0][vert_lines - 1] = 1;
        if (odin_condition[gor_lines - 1][vert_lines - 1] == f)
            danger_all[gor_lines - 1][vert_lines - 1] = 1;
        for (int i = 0; i < gor_lines; i++) {
            int j = i;
            for (int k = 0; k < vert_lines; k++) {
                int l = k;
                if (odin_mas_temp[j][l] < -1) {
                    char c2;
                    for (c2 = f; c2 != odin_condition[j][l]; c2 = odin_condition[j][l]) ;
                    odin_condition[j][l] = c;
                    boolean flag = true;
                    search_poligons(odin_condition, c1, flag);
                    for (; odin_condition[j][l] != c2; odin_condition[j][l] = c2) ;
                }
            }

        }

        return true;
    }

    public boolean dvazhdy(char c) {
        boolean flag = false;
        char c1 = r;
        if (c == r)
            c1 = G;
        for (int j = 1; j < gor_lines - 1; j++) {
            int k = j;
            for (int i1 = 1; i1 < vert_lines - 1; i1++) {
                int l1 = i1;
                if (podstav_tretju[k][l1] > 20)
                    odin_condition[k][l1] = c;
            }

        }

        for (int l = 0; l < gor_lines; l++) {
            int j1 = l;
            for (int i2 = 0; i2 < vert_lines; i2++) {
                int l2 = i2;
                if (odin_mas_temp[j1][l2] == -2) {
                    for (int k3 = 0; k3 < gor_lines; k3++) {
                        int j4 = k3;
                        for (int j5 = 0; j5 < vert_lines; j5++) {
                            int k5 = j5;
                            if (odin_mas_temp[j4][k5] == -2 && (l2 == k5 && j1 < j4 || l2 < k5)) {
                                char c2 = f;
                                char c3 = f;
                                for (; c2 != odin_condition[j1][l2]; c2 = odin_condition[j1][l2]) ;
                                for (; c3 != odin_condition[j4][k5]; c3 = odin_condition[j4][k5]) ;
                                odin_condition[j1][l2] = c;
                                odin_condition[j4][k5] = c;
                                int i = 0;
                                i = search_poligons(odin_condition, c1, false);
                                if (c == r && i > 0) {
                                    want_user[j1][l2]++;
                                    want_user[j4][k5]++;
                                    if (want_user_value[j1][l2] < i)
                                        want_user_value[j1][l2] = i;
                                    if (want_user_value[j4][k5] < i)
                                        want_user_value[j4][k5] = i;
                                }
                                if (c == G && i > 0) {
                                    want_programm[j1][l2]++;
                                    want_programm[j4][k5]++;
                                    if (want_programm_value[j1][l2] < i)
                                        want_programm_value[j1][l2] = i;
                                    if (want_programm_value[j4][k5] < i)
                                        want_programm_value[j4][k5] = i;
                                }
                                for (; odin_condition[j1][l2] != c2; odin_condition[j1][l2] = c2) ;
                                for (; odin_condition[j4][k5] != c3; odin_condition[j4][k5] = c3) ;
                            }
                        }

                    }

                }
            }

        }

        for (int k1 = 1; k1 < gor_lines - 1; k1++) {
            int j2 = k1;
            for (int i3 = 1; i3 < vert_lines - 1; i3++) {
                int l3 = i3;
                if (podstav_tretju[j2][l3] > 20) {
                    boolean flag1 = false;
                    if (c == G) {
                        int k4 = want_programm[j2 - 1][l3] + want_programm[j2 + 1][l3] + want_programm[j2][l3 - 1] + want_programm[j2][l3 + 1];
                        want_programm[j2][l3] = k4;
                    }
                    if (c == r) {
                        int l4 = want_user[j2 - 1][l3] + want_user[j2 + 1][l3] + want_user[j2][l3 - 1] + want_user[j2][l3 + 1];
                        want_user[j2][l3] = l4;
                    }
                }
            }

        }

        for (int k2 = 1; k2 < gor_lines - 1; k2++) {
            int j3 = k2;
            for (int i4 = 1; i4 < vert_lines - 1; i4++) {
                int i5 = i4;
                if (podstav_tretju[j3][i5] > 20)
                    odin_condition[j3][i5] = f;
            }

        }

        return true;
    }

    public int[] treezhdy() {
        int ai[] = new int[2];
        ai[0] = -1;
        ai[1] = -1;
        int i = 0xfa0a1f00;
        int j = 0xfa0a1f00;
        safety_test_flag = 1;
        int k = search_dengerous(r);
        safety_test_flag = 1;
        int l = search_dengerous(G);
        for (int i1 = 0; i1 < gor_lines; i1++) {
            int j1 = i1;
            for (int k1 = 0; k1 < vert_lines; k1++) {
                int l1 = k1;
                if (danger_all[j1][l1] < 1 && odin_mas_temp[j1][l1] < 0 && odin_condition[j1][l1] == f) {
                    odin_condition[j1][l1] = r;
                    odin_condition_ext[j1 + log_shift][l1 + log_shift] = r;
                    int i2 = search_dengerous(G);
                    int j2 = l - i2;
                    if (j1 > 0 && l1 > 0 && j1 < gor_lines - 1 && l1 < vert_lines - 1 && (odin_condition[j1 - 1][l1] == G && (odin_condition[j1][l1 + 1] == G || odin_condition[j1][l1 - 1] == G) || odin_condition[j1 + 1][l1] == G && (odin_condition[j1][l1 + 1] == G || odin_condition[j1][l1 - 1] == G)))
                        j2 = 0;
                    want_user[j1][l1] = j2;
                    if (j < j2)
                        j = j2;
                    odin_condition[j1][l1] = G;
                    odin_condition_ext[j1 + log_shift][l1 + log_shift] = G;
                    int k2 = search_dengerous(r);
                    int l2 = k - k2;
                    if (j1 > 0 && l1 > 0 && j1 < gor_lines - 1 && l1 < vert_lines - 1 && (odin_condition[j1 - 1][l1] == G && (odin_condition[j1][l1 + 1] == G || odin_condition[j1][l1 - 1] == G) || odin_condition[j1 + 1][l1] == G && (odin_condition[j1][l1 + 1] == G || odin_condition[j1][l1 - 1] == G)))
                        l2 = 0;
                    want_programm[j1][l1] = l2;
                    if (i < l2) {
                        ai[0] = l1;
                        ai[1] = j1;
                        i = l2;
                    }
                    odin_condition[j1][l1] = f;
                    odin_condition_ext[j1 + log_shift][l1 + log_shift] = f;
                }
            }

        }

        if (j >>> 2 < i) {
            while (!sravnenie(want_programm, want_user, i, 1)) ;
            if (coord_compare[2] > 0) {
                ai[0] = coord_compare[0];
                ai[1] = coord_compare[1];
                return ai;
            }
        }
        while (!sravnenie(want_user, want_programm, j, 1)) ;
        if (coord_compare[2] > 0) {
            ai[0] = coord_compare[0];
            ai[1] = coord_compare[1];
            return ai;
        }
        while (!sravnenie(odin_mas_temp, danger_all, -2, -1)) ;
        if (coord_compare[2] > 0) {
            ai[0] = coord_compare[0];
            ai[1] = coord_compare[1];
        }
        return ai;
    }

    public boolean pleace_to_test() {
        odin_mas_temp = new int[gor_lines][vert_lines];
        for (int i = 0; i < gor_lines; i++) {
            int j = i;
            for (int k = 0; k < vert_lines; k++) {
                int l = k;
                if (odin_condition[j][l] == r || odin_condition[j][l] == G)
                    if (j > 0 && j < gor_lines - 1 && l > 0 && l < vert_lines - 1) {
                        if (odin_condition[j - 1][l - 1] == f)
                            odin_mas_temp[j - 1][l - 1] = -2;
                        if (odin_condition[j - 1][l] == f)
                            odin_mas_temp[j - 1][l] = -2;
                        if (odin_condition[j - 1][l + 1] == f)
                            odin_mas_temp[j - 1][l + 1] = -2;
                        if (odin_condition[j][l - 1] == f)
                            odin_mas_temp[j][l - 1] = -2;
                        if (odin_condition[j][l + 1] == f)
                            odin_mas_temp[j][l + 1] = -2;
                        if (odin_condition[j + 1][l - 1] == f)
                            odin_mas_temp[j + 1][l - 1] = -2;
                        if (odin_condition[j + 1][l] == f)
                            odin_mas_temp[j + 1][l] = -2;
                        if (odin_condition[j + 1][l + 1] == f)
                            odin_mas_temp[j + 1][l + 1] = -2;
                    } else {
                        if (l == 0 && j > 0 && j < gor_lines - 1) {
                            if (odin_condition[j - 1][l] == f)
                                odin_mas_temp[j - 1][l] = -2;
                            if (odin_condition[j - 1][l + 1] == f)
                                odin_mas_temp[j - 1][l + 1] = -2;
                            if (odin_condition[j][l + 1] == f)
                                odin_mas_temp[j][l + 1] = -2;
                            if (odin_condition[j + 1][l] == f)
                                odin_mas_temp[j + 1][l] = -2;
                            if (odin_condition[j + 1][l + 1] == f)
                                odin_mas_temp[j + 1][l + 1] = -2;
                        }
                        if (l == vert_lines - 1 && j > 0 && j < gor_lines - 1) {
                            if (odin_condition[j - 1][l - 1] == f)
                                odin_mas_temp[j - 1][l - 1] = -2;
                            if (odin_condition[j - 1][l] == f)
                                odin_mas_temp[j - 1][l] = -2;
                            if (odin_condition[j][l - 1] == f)
                                odin_mas_temp[j][l - 1] = -2;
                            if (odin_condition[j + 1][l - 1] == f)
                                odin_mas_temp[j + 1][l - 1] = -2;
                            if (odin_condition[j + 1][l] == f)
                                odin_mas_temp[j + 1][l] = -2;
                        }
                        if (j == 0 && l > 0 && l < vert_lines - 1) {
                            if (odin_condition[j][l - 1] == f)
                                odin_mas_temp[j][l - 1] = -2;
                            if (odin_condition[j][l + 1] == f)
                                odin_mas_temp[j][l + 1] = -2;
                            if (odin_condition[j + 1][l - 1] == f)
                                odin_mas_temp[j + 1][l - 1] = -2;
                            if (odin_condition[j + 1][l] == f)
                                odin_mas_temp[j + 1][l] = -2;
                            if (odin_condition[j + 1][l + 1] == f)
                                odin_mas_temp[j + 1][l + 1] = -2;
                        }
                        if (j == gor_lines - 1 && l > 0 && l < vert_lines - 1) {
                            if (odin_condition[j - 1][l - 1] == f)
                                odin_mas_temp[j - 1][l - 1] = -2;
                            if (odin_condition[j - 1][l] == f)
                                odin_mas_temp[j - 1][l] = -2;
                            if (odin_condition[j - 1][l + 1] == f)
                                odin_mas_temp[j - 1][l + 1] = -2;
                            if (odin_condition[j][l - 1] == f)
                                odin_mas_temp[j][l - 1] = -2;
                            if (odin_condition[j][l + 1] == f)
                                odin_mas_temp[j][l + 1] = -2;
                        }
                    }
            }

        }

        return true;
    }

    public int[][] chang_mas_num(int ai[][], int i, int j) {
        for (int k = 0; k < gor_lines; k++) {
            int l = k;
            for (int i1 = 0; i1 < vert_lines; i1++)
                if (ai[l][i1] == j)
                    ai[l][i1] = i;

        }

        return ai;
    }

    public int[][] chang_mas_num_2(int ai[][], int ai1[][], int i, int j) {
        for (int k = 0; k < gor_lines; k++) {
            int l = k;
            for (int i1 = 0; i1 < vert_lines; i1++)
                if (ai[l][i1] == j)
                    ai1[l][i1] = i;

        }

        return ai1;
    }

    public int mas_expand(int ai[][], char ac[][], char c, boolean flag) {
        int i = 0;
        int j = (gor_lines + vert_lines) * 2;
        int ai1[] = new int[j];
        position = 1;
        for (int k = 0; k < vert_lines; k++) {
            int l = k;
            for (int j1 = 0; j1 < position; j1++) {
                int i2 = j1;
                if (ai1[i2] == ai[0][l])
                    break;
                if (i2 == position - 1) {
                    ai1[position] = ai[0][l];
                    position++;
                }
            }

        }

        for (int i1 = 0; i1 < vert_lines; i1++) {
            int k1 = i1;
            for (int j2 = 0; j2 < position; j2++) {
                int i3 = j2;
                if (ai1[i3] == ai[gor_lines - 1][k1])
                    break;
                if (i3 == position - 1) {
                    ai1[position] = ai[gor_lines - 1][k1];
                    position++;
                }
            }

        }

        for (int l1 = 0; l1 < gor_lines; l1++) {
            int k2 = l1;
            for (int j3 = 0; j3 < position; j3++) {
                int i4 = j3;
                if (ai1[i4] == ai[k2][0])
                    break;
                if (i4 == position - 1) {
                    ai1[position] = ai[k2][0];
                    position++;
                }
            }

        }

        for (int l2 = 0; l2 < gor_lines; l2++) {
            int k3 = l2;
            for (int j4 = 0; j4 < position; j4++) {
                int l4 = j4;
                if (ai1[l4] == ai[k3][vert_lines - 1])
                    break;
                if (l4 == position - 1) {
                    ai1[position] = ai[k3][vert_lines - 1];
                    position++;
                }
            }

        }

        for (int l3 = 1; l3 < gor_lines - 1; l3++) {
            int k4 = l3;
            for (int i5 = 1; i5 < vert_lines - 1; i5++) {
                int j5 = i5;
                for (int k5 = 0; k5 < position; k5++) {
                    int l5 = k5;
                    if (ai[k4][j5] == ai1[l5] || ai[k4][j5] == 0)
                        break;
                    if (l5 == position - 1) {
                        if (c == G && ac[k4][j5] == G)
                            i++;
                        if (c == r && ac[k4][j5] == r)
                            i++;
                        if (flag && ac[k4][j5] == f)
                            danger_all[k4][j5] = 1;
                    }
                }

            }

        }

        return i;
    }

    public int search_poligons(char ac[][], char c, boolean flag) {
        loc_mas_numb = 1;
        loc_mas_numb = loc_mas_numb;
        search_polig = new int[gor_lines][vert_lines];
        search_polig[0][0] = loc_mas_numb;
        for (int i = 1; i < gor_lines; i++) {
            int j = i;
            if (ac[j][0] == c || ac[j][0] == f || ac[j][0] == n)
                search_polig[j][0] = loc_mas_numb;
        }

        for (int k = 1; k < vert_lines; k++) {
            int l = k;
            for (int j1 = 0; j1 < gor_lines; j1++) {
                int i2 = j1;
                if (ac[i2][l] == c || ac[i2][l] == f || ac[i2][l] == n) {
                    if (search_polig[i2][l - 1] != 0)
                        search_polig[i2][l] = search_polig[i2][l - 1];
                    else
                        search_polig[i2][l] = loc_mas_numb;
                } else {
                    loc_mas_numb = loc_mas_numb + 1;
                }
            }

        }

        for (int i1 = 0; i1 < gor_lines - 1; i1++) {
            int k1 = i1;
            for (int j2 = 0; j2 < vert_lines; j2++) {
                int k2 = j2;
                if (search_polig[k1][k2] != 0) {
                    int l2 = search_polig[k1][k2];
                    int i3 = search_polig[k1 + 1][k2];
                    if (i3 != 0 && i3 != l2) {
                        int ai[][] = chang_mas_num(search_polig, l2, i3);
                        search_polig = ai;
                    }
                }
            }

        }

        int l1 = mas_expand(search_polig, ac, c, flag);
        return l1;
    }

    public int search_dengerous(char c) {
        int i = 0;
        char c1 = r;
        if (c == r)
            c1 = G;
        int ai[][] = new int[gor_lines_ext][vert_lines_ext];
        int ai1[][] = new int[gor_lines][vert_lines];
        for (int j = log_shift; log_shift <= j && j < gor_lines + log_shift; j++) {
            int k = j;
            for (int i1 = log_shift; log_shift <= i1 && i1 < vert_lines + log_shift; i1++) {
                int l1 = i1;
                if (odin_condition_ext[j][i1] != c)
                    continue;
                if (odin_condition_ext[j][i1] == c) {
                    if (i1 - log_shift == 0 || j - log_shift == 0) {
                        ai[k][l1] = single_danger;
                        continue;
                    }
                    if (i1 == (vert_lines + log_shift) - 1 || j == (gor_lines + log_shift) - 1) {
                        ai[k][l1] = single_danger;
                        continue;
                    }
                }
                if (expanded_dots[k - log_shift][l1 - log_shift] != 1) {
                    int l2 = 1;
                    int i4 = log_shift;
                    for (int i5 = k; i5 - 1 >= 0; i5--) {
                        int k6 = i5;
                        if (odin_condition_ext[k6][l1] == c1)
                            break;
                        int i8 = l2;
                        for (int i10 = l1; i10 < vert_lines_ext - 1; i10++) {
                            if (i10 == i4)
                                break;
                            if (odin_condition_ext[k6][i10] == c1) {
                                i4 = i10;
                                break;
                            }
                            if (i8 == log_shift + 1)
                                break;
                            ai[k][l1] += i8;
                            i8++;
                        }

                        if (l2 == log_shift + 1)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    i4 = log_shift;
                    for (int l6 = k; l6 - 1 >= 0; l6--) {
                        int j8 = l6;
                        if (odin_condition_ext[j8][l1] == c1)
                            break;
                        int j10 = l2;
                        for (int j12 = l1; j12 > 0; j12--) {
                            if (j12 == i4)
                                break;
                            if (odin_condition_ext[j8][j12] == c1) {
                                i4 = j12;
                                break;
                            }
                            if (j10 == log_shift + 1)
                                break;
                            ai[k][l1] += j10;
                            j10++;
                        }

                        if (l2 == log_shift + 1)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    i4 = log_shift;
                    for (int k8 = k; k8 < gor_lines_ext - 1; k8++) {
                        int k10 = k8;
                        if (odin_condition_ext[k10][l1] == c1)
                            break;
                        int k12 = l2;
                        for (int l13 = l1; l13 < vert_lines_ext - 1; l13++) {
                            if (l13 == i4)
                                break;
                            if (odin_condition_ext[k10][l13] == c1) {
                                i4 = l13;
                                break;
                            }
                            if (k12 == log_shift + 1)
                                break;
                            ai[k][l1] += k12;
                            k12++;
                        }

                        if (l2 == log_shift + 1)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    i4 = log_shift;
                    for (int l10 = k; l10 < gor_lines_ext - 1; l10++) {
                        int l12 = l10;
                        if (odin_condition_ext[l12][l1] == c1)
                            break;
                        int i14 = l2;
                        for (int j15 = l1; j15 > 0; j15--) {
                            if (j15 == i4)
                                break;
                            if (odin_condition_ext[l12][j15] == c1) {
                                i4 = j15;
                                break;
                            }
                            if (i14 == log_shift + 1)
                                break;
                            ai[k][l1] += i14;
                            i14++;
                        }

                        if (l2 == log_shift + 1)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    i4 = log_shift;
                    for (int i13 = l1; i13 < vert_lines_ext - 1; i13++) {
                        int j14 = i13;
                        if (odin_condition_ext[k][j14] == c1)
                            break;
                        int k15 = l2;
                        for (int j16 = k; j16 - 1 >= 0; j16--) {
                            if (j16 == i4)
                                break;
                            if (odin_condition_ext[j16][j14] == c1) {
                                i4 = j16;
                                break;
                            }
                            if (k15 == log_shift + 1)
                                break;
                            ai[k][l1] += k15;
                            k15++;
                        }

                        if (l2 == log_shift + 1)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    i4 = log_shift;
                    for (int k14 = l1; k14 > 0; k14--) {
                        int l15 = k14;
                        if (odin_condition_ext[k][l15] == c1)
                            break;
                        int k16 = l2;
                        for (int j17 = k; j17 - 1 >= 0; j17--) {
                            if (j17 == i4)
                                break;
                            if (odin_condition_ext[j17][l15] == c1) {
                                i4 = j17;
                                break;
                            }
                            if (k16 == log_shift + 1)
                                break;
                            ai[k][l1] += k16;
                            k16++;
                        }

                        if (l2 == log_shift + 1)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    i4 = log_shift;
                    for (int i16 = l1; i16 < vert_lines_ext - 1; i16++) {
                        int l16 = i16;
                        if (odin_condition_ext[k][l16] == c1)
                            break;
                        int k17 = l2;
                        for (int j18 = k; j18 < gor_lines_ext - 1; j18++) {
                            if (j18 == i4)
                                break;
                            if (odin_condition_ext[j18][l16] == c1) {
                                i4 = j18;
                                break;
                            }
                            if (k17 == log_shift + 1)
                                break;
                            ai[k][l1] += k17;
                            k17++;
                        }

                        if (l2 == log_shift + 1)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    i4 = log_shift;
                    for (int i17 = l1; i17 > 0; i17--) {
                        int l17 = i17;
                        if (odin_condition_ext[k][l17] == c1)
                            break;
                        int k18 = l2;
                        for (int j19 = k; j19 < gor_lines_ext - 1; j19++) {
                            if (j19 == i4)
                                break;
                            if (odin_condition_ext[j19][l17] == c1) {
                                i4 = j19;
                                break;
                            }
                            if (k18 == log_shift + 1)
                                break;
                            ai[k][l1] += k18;
                            k18++;
                        }

                        if (l2 == log_shift + 1)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    for (int i18 = k; i18 - 1 >= 0; i18--) {
                        int l18 = i18;
                        int k19 = l2;
                        for (int j20 = l1; j20 < vert_lines_ext - 1; j20++) {
                            if (odin_condition_ext[l18 - 1][j20] == c1 || odin_condition_ext[l18][j20 + 1] == c1 || k19 == log_shift)
                                break;
                            ai[k][l1] += k19;
                            k19++;
                        }

                        if (l2 == log_shift)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    for (int i19 = k; i19 - 1 >= 0; i19--) {
                        int l19 = i19;
                        int k20 = l2;
                        for (int j21 = l1; j21 > 0; j21--) {
                            if (odin_condition_ext[l19 - 1][j21] == c1 || odin_condition_ext[l19][j21 - 1] == c1 || k20 == log_shift)
                                break;
                            ai[k][l1] += k20;
                            k20++;
                        }

                        if (l2 == log_shift)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    for (int i20 = k; i20 < gor_lines_ext - 1; i20++) {
                        int l20 = i20;
                        int k21 = l2;
                        for (int j22 = l1; j22 < vert_lines_ext - 1; j22++) {
                            if (odin_condition_ext[l20 + 1][j22] == c1 || odin_condition_ext[l20][j22 + 1] == c1 || k21 == log_shift)
                                break;
                            ai[k][l1] += k21;
                            k21++;
                        }

                        if (l2 == log_shift)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    for (int i21 = k; i21 < gor_lines_ext - 1; i21++) {
                        int l21 = i21;
                        int k22 = l2;
                        for (int j23 = l1; j23 > 0; j23--) {
                            if (odin_condition_ext[l21 + 1][j23] == c1 || odin_condition_ext[l21][j23 - 1] == c1 || k22 == log_shift)
                                break;
                            ai[k][l1] += k22;
                            k22++;
                        }

                        if (l2 == log_shift)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    for (int i22 = l1; i22 < vert_lines_ext - 1; i22++) {
                        int l22 = i22;
                        int k23 = l2;
                        for (int j24 = k; j24 - 1 >= 0; j24--) {
                            if (odin_condition_ext[j24 - 1][l22] == c1 || odin_condition_ext[j24][l22 + 1] == c1 || k23 == log_shift)
                                break;
                            ai[k][l1] += k23;
                            k23++;
                        }

                        if (l2 == log_shift)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    for (int i23 = l1; i23 > 0; i23--) {
                        int l23 = i23;
                        int k24 = l2;
                        for (int j25 = k; j25 - 1 >= 0; j25--) {
                            if (odin_condition_ext[j25 - 1][l23] == c1 || odin_condition_ext[j25][l23 - 1] == c1 || k24 == log_shift)
                                break;
                            ai[k][l1] += k24;
                            k24++;
                        }

                        if (l2 == log_shift)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    for (int i24 = l1; i24 < vert_lines_ext - 1; i24++) {
                        int l24 = i24;
                        int k25 = l2;
                        for (int i26 = k; i26 < gor_lines_ext - 1; i26++) {
                            if (odin_condition_ext[i26 + 1][l24] == c1 || odin_condition_ext[i26][l24 + 1] == c1 || k25 == log_shift)
                                break;
                            ai[k][l1] += k25;
                            k25++;
                        }

                        if (l2 == log_shift)
                            break;
                        l2++;
                    }

                    l2 = 1;
                    for (int i25 = l1; i25 > 0; i25--) {
                        int l25 = i25;
                        int j26 = l2;
                        for (int k26 = k; k26 < gor_lines_ext - 1; k26++) {
                            if (odin_condition_ext[k26 + 1][l25] == c1 || odin_condition_ext[k26][l25 - 1] == c1 || j26 == log_shift)
                                break;
                            ai[k][l1] += j26;
                            j26++;
                        }

                        if (l2 == log_shift)
                            break;
                        l2++;
                    }

                }
            }

        }

        for (int l = log_shift; l < gor_lines - log_shift; l++) {
            int j1 = l;
            for (int i2 = log_shift; i2 < vert_lines - log_shift; i2++) {
                int i3 = i2;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1][i3 + 1] == c && odin_condition[j1][i3 - 1] == c)
                    i -= single_danger << 11;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1 + 1][i3] == c && odin_condition[j1 - 1][i3] == c)
                    i -= single_danger << 11;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1 + 1][i3 - 1] == c && odin_condition[j1 - 1][i3] == c)
                    i -= single_danger << 11;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1 + 1][i3] == c && odin_condition[j1 - 1][i3 + 1] == c)
                    i -= single_danger << 11;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1 - 1][i3 - 1] == c && odin_condition[j1][i3 + 1] == c)
                    i -= single_danger << 11;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1][i3 - 1] == c && odin_condition[j1 + 1][i3 + 1] == c)
                    i -= single_danger << 11;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1 + 1][i3 - 1] == c && odin_condition[j1 - 1][i3] == c && odin_condition[j1][i3 - 1] == c)
                    i -= single_danger << 11;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1 + 1][i3] == c && odin_condition[j1 - 1][i3 + 1] == c && odin_condition[j1][i3 + 1] == c)
                    i -= single_danger << 11;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1 - 1][i3 - 1] == c && odin_condition[j1][i3 + 1] == c && odin_condition[j1 - 1][i3] == c)
                    i -= single_danger << 11;
                if (odin_condition[j1][i3] == c1 && odin_condition[j1][i3 - 1] == c && odin_condition[j1 + 1][i3 + 1] == c && odin_condition[j1 + 1][i3] == c)
                    i -= single_danger << 11;
            }

        }

        summary_danger = new int[gor_lines][vert_lines];
        for (int k1 = 0; k1 < gor_lines; k1++) {
            int j2 = k1;
            for (int j3 = 0; j3 < vert_lines; j3++)
                summary_danger[j2][j3] = ai[j2 + log_shift][j3 + log_shift];

        }

        loc_mas_numb = -1;
        loc_mas_numb = loc_mas_numb;
        search_massiv = new int[gor_lines][vert_lines];
        for (int k2 = 0; k2 < vert_lines; k2++) {
            int k3 = k2;
            for (int j4 = 0; j4 < gor_lines; j4++) {
                int j5 = j4;
                if (odin_condition_ext[j5 + log_shift][k3 + log_shift] == c) {
                    if (j5 == 0 && k3 == 0)
                        search_massiv[j5][k3] = loc_mas_numb;
                    if (k3 == 0)
                        search_massiv[j5][k3] = loc_mas_numb;
                    if (k3 > 0)
                        if (search_massiv[j5][k3 - 1] != 0)
                            search_massiv[j5][k3] = search_massiv[j5][k3 - 1];
                        else
                            search_massiv[j5][k3] = loc_mas_numb;
                } else {
                    loc_mas_numb--;
                }
            }

        }

        for (int l3 = 0; l3 < gor_lines; l3++) {
            int k4 = l3;
            for (int k5 = 0; k5 < vert_lines; k5++) {
                int i7 = k5;
                if (search_massiv[k4][i7] != 0) {
                    int l8 = search_massiv[k4][i7];
                    if (k4 + 1 < gor_lines) {
                        int i11 = search_massiv[k4 + 1][i7];
                        if (i11 != 0 && i11 != l8) {
                            int ai2[][] = chang_mas_num(search_massiv, l8, i11);
                            search_massiv = ai2;
                        }
                    }
                }
            }

        }

        for (int l4 = 0; l4 < gor_lines; l4++) {
            int l5 = l4;
            for (int j7 = 0; j7 < vert_lines; j7++) {
                int i9 = j7;
                if (search_massiv[l5][i9] < 0) {
                    int j11 = 0;
                    if (l5 - 1 >= 0 && search_massiv[l5 - 1][i9] != search_massiv[l5][i9])
                        j11++;
                    if (l5 + 1 < gor_lines && search_massiv[l5 + 1][i9] != search_massiv[l5][i9])
                        j11++;
                    if (i9 - 1 >= 0 && search_massiv[l5][i9 - 1] != search_massiv[l5][i9])
                        j11++;
                    if (i9 + 1 < vert_lines && search_massiv[l5][i9 + 1] != search_massiv[l5][i9])
                        j11++;
                    if (j11 == 0)
                        ai1[l5][i9] = summary_danger[l5][i9] << 5;
                    if (j11 != 0) {
                        int j13 = search_massiv[l5][i9];
                        int l14 = val_sravnenie(search_massiv, summary_danger, j13);
                        int ai3[][] = chang_mas_num_2(search_massiv, ai1, l14, j13);
                        chang_mas_num(search_massiv, 0, j13);
                        ai1 = ai3;
                    }
                }
            }

        }

        if (safety_test_flag == 1) {
            for (int i6 = 0; i6 < 2; i6++) {
                for (int k7 = 0; k7 < gor_lines; k7++) {
                    int j9 = k7;
                    for (int k11 = 0; k11 < vert_lines; k11++) {
                        if (i6 == 0) {
                            if (c == r && ai1[j9][k11] < min_safety_red && ai1[j9][k11] > 0)
                                min_safety_red = ai1[j9][k11];
                            if (c == G && ai1[j9][k11] < min_safety_green && ai1[j9][k11] > 0)
                                min_safety_green = ai1[j9][k11];
                        }
                        if (i6 == 1) {
                            if (c == r && ai1[j9][k11] == min_safety_red)
                                safety_red_massiv[j9][k11] = min_safety_red;
                            if (c == G && ai1[j9][k11] == min_safety_green)
                                safety_green_massiv[j9][k11] = min_safety_green;
                        }
                    }

                }

            }

            safety_test_flag = -1;
        }
        int j6 = 0;
        for (int l7 = 0; l7 < gor_lines; l7++) {
            int k9 = l7;
            for (int l11 = 0; l11 < vert_lines; l11++) {
                if (c == r && safety_red_massiv[k9][l11] == min_safety_red)
                    j6 += safety_red_massiv[k9][l11] - ai1[k9][l11];
                if (c == G && safety_green_massiv[k9][l11] == min_safety_green)
                    j6 += safety_green_massiv[k9][l11] - ai1[k9][l11];
            }

        }

        i -= j6 << 7;
        for (int l9 = 0; l9 < gor_lines; l9++) {
            int i12 = l9;
            for (int k13 = 0; k13 < vert_lines; k13++) {
                int i15 = k13;
                if (i12 > 0 && i15 > 0 && odin_condition[i12][i15] == c1 && odin_condition[i12 - 1][i15 - 1] == c1 && odin_condition[i12 - 1][i15] == c && odin_condition[i12][i15 - 1] == c)
                    i -= single_danger << 7;
                if (i12 < gor_lines - 1 && i15 < vert_lines - 1 && odin_condition[i12][i15] == c1 && odin_condition[i12 + 1][i15 + 1] == c1 && odin_condition[i12 + 1][i15] == c && odin_condition[i12][i15 + 1] == c)
                    i -= single_danger << 7;
                if (i12 < gor_lines - 1 && i15 > 0 && odin_condition[i12][i15] == c1 && odin_condition[i12 + 1][i15 - 1] == c1 && odin_condition[i12 + 1][i15] == c && odin_condition[i12][i15 - 1] == c)
                    i -= single_danger << 7;
                if (i12 > 0 && i15 < vert_lines - 1 && odin_condition[i12][i15] == c1 && odin_condition[i12 - 1][i15 + 1] == c1 && odin_condition[i12 - 1][i15] == c && odin_condition[i12][i15 + 1] == c)
                    i -= single_danger << 7;
                i += ai1[i12][i15];
            }

        }

        return i;
    }

    public boolean podstavljaem_tretju(char c) {
        for (int i = 0; i < gor_lines; i++) {
            int j = i;
            for (int k = 0; k < vert_lines; k++) {
                int l = k;
                if (odin_condition[j][l] == f) {
                    if (j > 1 && l > 0 && j < gor_lines - 2 && l < vert_lines - 1 && odin_condition[j - 1][l] == f && (odin_condition[j - 1][l - 1] == f || odin_condition[j - 1][l + 1] == f) && odin_condition[j + 1][l] == f && (odin_condition[j + 1][l - 1] == f || odin_condition[j + 1][l + 1] == f)) {
                        if (c == r && (odin_condition[j - 2][l] == r || odin_condition[j - 2][l - 1] == r || odin_condition[j - 2][l + 1] == r) && (odin_condition[j + 2][l] == r || odin_condition[j + 2][l - 1] == r || odin_condition[j + 2][l + 1] == r)) {
                            podstav_tretju[j][l] = 21;
                            red_tretja = 1;
                        }
                        if (c == G && (odin_condition[j - 2][l] == G || odin_condition[j - 2][l - 1] == G || odin_condition[j - 2][l + 1] == G) && (odin_condition[j + 2][l] == G || odin_condition[j + 2][l - 1] == G || odin_condition[j + 2][l + 1] == G)) {
                            podstav_tretju[j][l] = 21;
                            green_tretja = 1;
                        }
                    }
                    if (j > 0 && l > 1 && j < gor_lines - 1 && l < vert_lines - 2 && odin_condition[j][l - 1] == f && (odin_condition[j - 1][l - 1] == f || odin_condition[j - 1][l + 1] == f) && odin_condition[j][l + 1] == f && (odin_condition[j - 1][l + 1] == f || odin_condition[j + 1][l + 1] == f)) {
                        if (c == r && (odin_condition[j][l - 2] == r || odin_condition[j - 1][l - 2] == r || odin_condition[j + 1][l - 2] == r) && (odin_condition[j][l + 2] == r || odin_condition[j - 1][l + 2] == r || odin_condition[j + 1][l + 2] == r)) {
                            podstav_tretju[j][l] = 22;
                            red_tretja = 1;
                        }
                        if (c == G && (odin_condition[j][l - 2] == G || odin_condition[j - 1][l - 2] == G || odin_condition[j + 1][l - 2] == G) && (odin_condition[j][l + 2] == G || odin_condition[j - 1][l + 2] == G || odin_condition[j + 1][l + 2] == G)) {
                            podstav_tretju[j][l] = 22;
                            green_tretja = 1;
                        }
                    }
                }
            }

        }

        return true;
    }

    public boolean angle(char c) {
        for (int i = 0; i < gor_lines; i++) {
            int j = i;
            for (int k = 0; k < vert_lines; k++) {
                int l = k;
                if (odin_condition[j][l] == f) {
                    if (j > 1 && l < vert_lines - 3 && odin_condition[j - 1][l] == f && odin_condition[j][l + 1] == f) {
                        if (c == r && odin_condition[j - 2][l] == r && odin_condition[j][l + 2] == r) {
                            want_user[j][l] += want_user[j - 1][l];
                            want_user[j][l] += want_user[j][l + 1];
                        }
                        if (c == G && odin_condition[j - 2][l] == G && odin_condition[j][l + 2] == G) {
                            want_programm[j][l] += want_programm[j - 1][l];
                            want_programm[j][l] += want_programm[j][l + 1];
                        }
                    }
                    if (j < gor_lines - 3 && l < vert_lines - 3 && odin_condition[j + 1][l] == f && odin_condition[j][l + 1] == f) {
                        if (c == r && odin_condition[j + 2][l] == r && odin_condition[j][l + 2] == r) {
                            want_user[j][l] += want_user[j + 1][l];
                            want_user[j][l] += want_user[j][l + 1];
                        }
                        if (c == G && odin_condition[j + 2][l] == G && odin_condition[j][l + 2] == G) {
                            want_programm[j][l] += want_programm[j + 1][l];
                            want_programm[j][l] += want_programm[j][l + 1];
                        }
                    }
                    if (j > 1 && l > 1 && odin_condition[j - 1][l] == f && odin_condition[j][l - 1] == f) {
                        if (c == r && odin_condition[j - 2][l] == r && odin_condition[j][l - 2] == r) {
                            want_user[j][l] += want_user[j - 1][l];
                            want_user[j][l] += want_user[j][l - 1];
                        }
                        if (c == G && odin_condition[j - 2][l] == G && odin_condition[j][l - 2] == G) {
                            want_programm[j][l] += want_programm[j - 1][l];
                            want_programm[j][l] += want_programm[j][l - 1];
                        }
                    }
                    if (j < gor_lines - 3 && l > 1 && odin_condition[j][l - 1] == f && odin_condition[j + 1][l] == f) {
                        if (c == r && odin_condition[j][l - 2] == r && odin_condition[j + 2][l] == r) {
                            want_user[j][l] += want_user[j][l - 1];
                            want_user[j][l] += want_user[j + 1][l];
                        }
                        if (c == G && odin_condition[j][l - 2] == G && odin_condition[j + 2][l] == G) {
                            want_programm[j][l] += want_programm[j][l - 1];
                            want_programm[j][l] += want_programm[j + 1][l];
                        }
                    }
                    if (j > 1 && l > 0 && j < gor_lines - 1 && l < vert_lines - 2 && odin_condition[j - 1][l] == f && odin_condition[j - 1][l - 1] == f && odin_condition[j][l + 1] == f && odin_condition[j + 1][l + 1] == f) {
                        if (c == r && odin_condition[j - 2][l - 1] == r && odin_condition[j + 1][l + 2] == r && odin_condition[j - 2][l] != r && odin_condition[j][l + 2] != r) {
                            want_user[j][l] += want_user[j - 1][l];
                            want_user[j][l] += want_user[j][l + 1];
                        }
                        if (c == G && odin_condition[j - 2][l - 1] == G && odin_condition[j + 1][l + 2] == G && odin_condition[j - 2][l] != G && odin_condition[j][l + 2] != G) {
                            want_programm[j][l] += want_programm[j - 1][l];
                            want_programm[j][l] += want_programm[j][l + 1];
                        }
                    }
                    if (j > 1 && l > 1 && j < gor_lines - 1 && l < vert_lines - 1 && odin_condition[j - 1][l] == f && odin_condition[j - 1][l + 1] == f && odin_condition[j][l - 1] == f && odin_condition[j + 1][l - 1] == f) {
                        if (c == r && odin_condition[j - 2][l + 1] == r && odin_condition[j + 1][l - 2] == r && odin_condition[j][l - 2] != r && odin_condition[j - 2][l] != r) {
                            want_user[j][l] += want_user[j - 1][l];
                            want_user[j][l] += want_user[j][l - 1];
                        }
                        if (c == G && odin_condition[j - 2][l + 1] == G && odin_condition[j + 1][l - 2] == G && odin_condition[j][l - 2] != G && odin_condition[j - 2][l] != G) {
                            want_programm[j][l] += want_programm[j - 1][l];
                            want_programm[j][l] += want_programm[j][l - 1];
                        }
                    }
                    if (j > 0 && l > 0 && j < gor_lines - 2 && l < vert_lines - 2 && odin_condition[j][l + 1] == f && odin_condition[j - 1][l + 1] == f && odin_condition[j + 1][l] == f && odin_condition[j + 1][l - 1] == f) {
                        if (c == r && odin_condition[j - 1][l + 2] == r && odin_condition[j + 2][l - 1] == r && odin_condition[j + 2][l] != r && odin_condition[j][l + 2] != r) {
                            want_user[j][l] += want_user[j][l + 1];
                            want_user[j][l] += want_user[j + 1][l];
                        }
                        if (c == G && odin_condition[j - 1][l + 2] == G && odin_condition[j + 2][l - 1] == G && odin_condition[j + 2][l] != G && odin_condition[j][l + 2] != G) {
                            want_programm[j][l] += want_programm[j][l + 1];
                            want_programm[j][l] += want_programm[j + 1][l];
                        }
                    }
                    if (j > 0 && l > 1 && j < gor_lines - 2 && l < vert_lines - 1 && odin_condition[j][l - 1] == f && odin_condition[j - 1][l - 1] == f && odin_condition[j + 1][l] == f && odin_condition[j + 1][l + 1] == f) {
                        if (c == r && odin_condition[j - 1][l - 2] == r && odin_condition[j + 2][l + 1] == r && odin_condition[j][l - 2] != r && odin_condition[j + 2][l] != r) {
                            want_user[j][l] += want_user[j][l - 1];
                            want_user[j][l] += want_user[j + 1][l];
                        }
                        if (c == G && odin_condition[j - 1][l - 2] == G && odin_condition[j + 2][l + 1] == G && odin_condition[j][l - 2] != G && odin_condition[j + 2][l] != G) {
                            want_programm[j][l] += want_programm[j][l - 1];
                            want_programm[j][l] += want_programm[j + 1][l];
                        }
                    }
                }
            }

        }

        return true;
    }

    public boolean want_sum() {
        for (int i = 0; i < gor_lines; i++) {
            int j = i;
            for (int k = 0; k < vert_lines; k++) {
                int l = k;
                want_sum_user += want_user[j][l];
                want_sum_programm += want_programm[j][l];
            }

        }

        return true;
    }

    public int[] free_search(int i, int j) {
        int k = j;
        int l = i;
        int ai[] = new int[2];
        ai[0] = -1;
        ai[1] = -1;
        for (int i1 = 0; i1 < 2; i1++) {
            int j1 = i1;
            for (int k1 = 0; k1 < gor_lines; k1++) {
                for (int l1 = 0; l1 < vert_lines; l1++) {
                    if (odin_condition[k][l] == f && j1 == 0) {
                        ai[0] = l;
                        ai[1] = k;
                        return ai;
                    }
                    if (++l == vert_lines)
                        l = 0;
                    if (k == gor_lines)
                        k = 0;
                    if (l == i) {
                        if (++k == gor_lines)
                            k = 0;
                        break;
                    }
                    if (odin_condition[k][l] == f && j1 == 1) {
                        ai[0] = l;
                        ai[1] = k;
                        return ai;
                    }
                }

                if (k == j)
                    break;
            }

        }

        return ai;
    }

    int gor_lines;
    int vert_lines;
    int gor_lines_ext;
    int vert_lines_ext;
    public static char r = 'r';
    public static char G = 'G';
    public static char f = 'f';
    public static char n = 'n';
    int odin_mas_temp[][];
    int search_massiv[][];
    int summary_danger[][];
    int loc_mas_numb;
    int search_polig[][];
    int want_user[][];
    int want_programm[][];
    int want_user_value[][];
    int want_programm_value[][];
    int safety_green_massiv[][];
    int safety_red_massiv[][];
    int danger_all[][];
    char odin_condition[][];
    int podstav_tretju[][];
    char odin_condition_ext[][];
    long sec_ms;
    int gor_random;
    int vert_random;
    int max_red[];
    int max_green[];
    int compare[][];
    int coord_compare[];
    int expanded_dots[][];
    public static int log_shift = 8;
    public static int glubina_podstanovki = 6;
    int single_danger;
    int min_safety_red;
    int min_safety_green;
    int safety_test_flag;
    int want_sum_user;
    int want_sum_programm;
    int red_tretja;
    int green_tretja;
    int position;

}