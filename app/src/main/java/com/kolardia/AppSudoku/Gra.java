package com.kolardia.AppSudoku;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;
/**
 * Created by Kolardia on 2015-10-04.
 */
public class Gra extends Activity {

    public static final String ZNACZNIK = "Sudku";
    public static final String TRUDNOSC_KLUCZ =
            "com.kolardia.AppSudoku.util" ;
    public static final int TRUDNOSC_LATWY = 0;
    public static final int TRUDNOSC_SREDNI = 1;
    public static final int TRUDNOSC_TRUDNY = 2;

    private int puzzle [] = new int [9 * 9];

    private WidokPuzzle widokPuzzle;

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

    private int[] wezPuzzle(int trud){
        String puz;
        switch (trud) {
            case TRUDNOSC_TRUDNY:
                puz = puzzleTrudne;
                break;
            case TRUDNOSC_SREDNI:
                puz = puzzeSrednie;
                break;
            case TRUDNOSC_LATWY:
            default:
                puz = puzzeLatwe;
                break;
        }
        return odZnakowPuzzle(puz);
    }

    private final String puzzeLatwe =
            "360000000004230800000004200" +
                    "070460003820000014500013020" +
                    "001900000007048300000000045" ;
    private final String puzzeSrednie =
            "650000070000506000014000005" +
                    "007009000002314700000700800" +
                    "500000630000201000030000097" ;
    private final String puzzleTrudne =
            "009000000080605020501078000" +
                    "000000700706040102004000000" +
                    "000720903090301080000000600" ;

    static protected int[] odZnakowPuzzle(String string) {
        int[] puz = new int[string.length()];
        for (int i = 0; i < puz.length; i++) {
            puz[i] = string.charAt(i) - '0';
        }
        return puz;
    }


        private int wezPole(int x, int y) {
            return puzzle[y * 9 + x];
        }
        private void ustawPole(int x, int y, int wartosc){
            puzzle[y * 9 + x] = wartosc;
        }

        protected String wezZnakPola(int x, int y){

            int v = wezPole(x, y);
            if(v == 0)
                return "" ;
            else
                return String.valueOf(v);
        }

        private final int wykorzystywane[] [] [] = new int[9][9][];

        protected int[] wezUzytePola(int x, int y){
            return wykorzystywane[x][y];
        }
        private void obliczUzytePola(){
            for(int x = 0; x < 9; x++){
                for(int y = 0; y < 9; y++){
                    wykorzystywane[x][y] = obliczUzytePola(x, y);
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
            int starty = (x / 3) * 3;
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
                for (int t : c){
                    if(t != 0)
                        nuzyty ++;
                }
                int cl[] = new int[nuzyty];
                nuzyty = 0;
                for(int t : c){
                    if(t != 0)
                        cl[nuzyty] = t;
                }
                return cl;
            }



        protected void pokazKlaviatureLubBlad(int x, int y){
            int pola[] = wezUzytePola(x, y);
            if(pola.length == 9){
                Toast toast = Toast.makeText(this, R.string.etykieta_brak_ruchow, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0 , 0);
                toast.show();
            } else {
                Dialog v = new Klawiatura(this, pola, widokPuzzle);
                v.show();
            }
        }




        static private String doZnakowPuzzle(int[] puz){
            StringBuilder buf = new StringBuilder();
            for (int element : puz){
                buf.append(element);
            }
            return buf.toString();
        }

}


