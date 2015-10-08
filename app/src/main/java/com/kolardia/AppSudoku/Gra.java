package com.kolardia.AppSudoku;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;



/**
 * Created by Kolardia
 */
public class Gra extends Activity {
    private Zestawy zestaw;
    private Gra gra;
    private View wstrzons;
    private int puzzle[] = new int[9 * 9];
    private WidokPuzzle widokPuzzle;
    private final int wykorzystane[][][] = new int[9][9][];

    public static final String TRUDNOSC_KLUCZ =
            "com.kolardia.AppSudoku.util";
    public static final int TRUDNOSC_LATWY = 0;
    public static final int TRUDNOSC_SREDNI = 1;
    public static final int TRUDNOSC_TRUDNY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int trud = getIntent().getIntExtra(TRUDNOSC_KLUCZ, TRUDNOSC_LATWY);
        puzzle = wezPuzzle(trud);
        obliczUzytePola();

        widokPuzzle = new WidokPuzzle(this);
        setContentView(widokPuzzle);
        widokPuzzle.requestFocus();

    }

    /******************************* class WidokPuzzle, class Zestawy (Generowanie planszy poziomów)
     */

    private int[] wezPuzzle ( int trud){
        String puz;
        zestaw = new Zestawy();

        switch (trud) {
            case TRUDNOSC_TRUDNY:
                puz = zestaw.losujTrudny();
                break;
            case TRUDNOSC_SREDNI:
                puz = zestaw.losujSredni();
                break;
            case TRUDNOSC_LATWY:
            default:
                puz = zestaw.losujLatwy();
                break;
        }

        return odZnakowPuzzle(puz);
    }

    /**
     * ************************************************************ class Klawiatura (aktywnoœæ)
     */

    protected void pokazKlaviatureLubBlad(int x, int y) {
        int pola[] = wezUzytePola(x, y);
        if (pola.length == 9) {
            Toast toast = Toast.makeText(this, R.string.etykieta_brak_ruchow, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            wstrzons = new View(this);
            gra = new Gra();
            wstrzons.startAnimation(AnimationUtils.loadAnimation(gra, R.anim.wstrzasy));

            //startActionMode(AnimationUtils.loadAnimation(this, R.anim.wstrzasy), hashCode() );
            // wstrzons.onTouchEvent(toast.show(), onActionModeStarted(AnimationUtils.loadAnimation(vidok, R.anim.wstrzasy)));

        } else {
            Dialog v = new Klawiatura(this, pola, widokPuzzle);
            v.show();

        }

    }

    /**
     * ********************************************************************************** system
     */
    protected boolean ustawPoleJesliPoprawne(int x, int y, int wartosc) {
        int pola[] = wezUzytePola(x, y);
        if (wartosc != 0) {
            for (int pole : pola) {
                if (pole == wartosc)
                    return false;
            }
        }
        ustawPole(x, y, wartosc);
        obliczUzytePola();
        return true;
    }

    protected int[] wezUzytePola(int x, int y) {
        return wykorzystane[x][y];
    }

    private void obliczUzytePola() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                wykorzystane[x][y] = obliczUzytePola(x, y);
            }
        }
    }

    private int[] obliczUzytePola(int x, int y) {
        int c[] = new int[9];
        //poziomo
        for (int i = 0; i < 9; i++) {
            if (i == y)
                continue;
            int t = wezPole(x, i);
            if (t != 0)
                c[t - 1] = t;
        }
        //poziomo
        for (int i = 0; i < 9; i++) {
            if (i == x)
                continue;
            int t = wezPole(i, y);
            if (t != 0)
                c[t - 1] = t;
        }
        //w zakresie bloku
        int startx = (x / 3) * 3;
        int starty = (y / 3) * 3;
        for (int i = startx; i < startx + 3; i++) {
            for (int j = starty; j < starty + 3; j++) {
                if (i == x && j == y)
                    continue;
                int t = wezPole(i, j);
                if (t != 0)
                    c[t - 1] = t;
            }
        }
        //kompozycja
        int nuzyty = 0;
        for (int t : c) {
            if (t != 0)
                nuzyty++;
        }
        int cl[] = new int[nuzyty];
        nuzyty = 0;
        for (int t : c) {
            if (t != 0)
                cl[nuzyty++] = t;
        }
        return cl;
    }


    private int wezPole(int x, int y) {
        return puzzle[y * 9 + x];
    }

    private void ustawPole(int x, int y, int wartosc) {
        puzzle[y * 9 + x] = wartosc;
    }

    protected String wezZnakPola(int x, int y) {

        int v = wezPole(x, y);
        if (v == 0)
            return "";
        else
            return String.valueOf(v);
    }

    /**
     * ********************************************************************Dostosowanie tablicy
     * Zmiana tablicy liczb na ci¹g znaków i odwrotnie
     */

    static private String doZnakowPuzzle(int[] puz) {
        StringBuilder buf = new StringBuilder();
        for (int element : puz) {
            buf.append(element);
        }
        return buf.toString();
    }

    static protected int[] odZnakowPuzzle(String string) {
        int[] puz = new int[string.length()];
        for (int i = 0; i < puz.length; i++) {
            puz[i] = string.charAt(i) - '0';
        }
        return puz;
    }

}




