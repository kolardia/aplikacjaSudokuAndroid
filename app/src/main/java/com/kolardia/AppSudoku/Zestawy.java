package com.kolardia.AppSudoku;

import android.app.Activity;

import java.util.Random;
import java.util.TreeMap;

/**
 * Created by Kolardia
 *
 * Listy dla trzech poziomów gry
 */
public class Zestawy extends Activity{

    private TreeMap<Integer, String> kluczZestawu;
    private Random generator;
    private String zestaw;

    protected String losujLatwy() {
        generator = new Random();
        zestaw = new String();
        kluczZestawu =
                new TreeMap<Integer, String>();
        // Lista dla pozimou ³atwego
        kluczZestawu.put(1, "372461598619852374845739261934185627586327419721946853153294786467518932298673145");
        kluczZestawu.put(2, "372461598619852374845739261934185627586327419721946853153294786467518932298673145");
        kluczZestawu.put(3, "372461598619852374845739261934185627586327419721946853153294786467518932298673145");

        switch (generator.nextInt(2)) {

            case 0: zestaw = kluczZestawu.get(1);
                break;
            case 1: zestaw = kluczZestawu.get(2);
                break;
            case 2: zestaw = kluczZestawu.get(3);
                break;
        }
        return zestaw;
    }

    protected String losujSredni() {
        generator = new Random();
        zestaw = new String();
        kluczZestawu =
                new TreeMap<Integer, String>();
        // lista dla poziomu œredniego
        kluczZestawu.put(1, "430091200870000100102007600003100000200050000001000700020000001010080000008210006");
        kluczZestawu.put(2, "245001638000304000030000400020059810007000206901007000000106000003002060000030000");
        kluczZestawu.put(3, "697000013040100070021700465410090000000004021002060900070000000000000004000010030");

        switch (generator.nextInt(2)) {

            case 0: zestaw = kluczZestawu.get(1);
                break;
            case 1: zestaw = kluczZestawu.get(2);
                break;
            case 2: zestaw = kluczZestawu.get(3);
                break;
        }
        return zestaw;
    }

    protected String losujTrudny() {
        generator = new Random();
        zestaw = new String();
        kluczZestawu =
                new TreeMap<Integer, String>();
        //Lista dla poziomu trudnego
        kluczZestawu.put(1, "009000000080605020501078000000000700706040102004000000000720903090301080000000600");
        kluczZestawu.put(2, "500019000000800400030000072200004630000075000960000000000300507008000006070020001");
        kluczZestawu.put(3, "800907030000000025710000000006780042503001000000000900000000084070040300060020050");

        switch (generator.nextInt(2)) {

            case 0: zestaw = kluczZestawu.get(1);
                break;
            case 1: zestaw = kluczZestawu.get(2);
                break;
            case 2: zestaw = kluczZestawu.get(3);
                break;
        }
        return zestaw;
    }

}
