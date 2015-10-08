package com.kolardia.AppSudoku;

import android.app.Dialog;
import android.view.View;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;



/**
 * Created by Kolardia
 */
public class Klawiatura extends Dialog {

    private final View klawisze[] = new View[9];
    private View klawiatura;

    protected void znajdzWidoki() {
        klawiatura = findViewById(R.id.klawiatura);
        klawisze[0] = findViewById(R.id.klawiatura_1);
        klawisze[1] = findViewById(R.id.klawiatura_2);
        klawisze[2] = findViewById(R.id.klawiatura_3);
        klawisze[3] = findViewById(R.id.klawiatura_4);
        klawisze[4] = findViewById(R.id.klawiatura_5);
        klawisze[5] = findViewById(R.id.klawiatura_6);
        klawisze[6] = findViewById(R.id.klawiatura_7);
        klawisze[7] = findViewById(R.id.klawiatura_8);
        klawisze[8] = findViewById(R.id.klawiatura_9);

    }

    private final int uzyte[];
    private final WidokPuzzle widokPuzzle;

    public Klawiatura(Context kontekst, int uzyte[], WidokPuzzle widokPuzzle) {
        super(kontekst);
        this.uzyte = uzyte;
        this.widokPuzzle = widokPuzzle;
    }

    private void ustawObiektyNasuchujace() {
        for (int i = 0; i < klawisze.length; i++) {
            final int t = i + 1;
            klawisze[i].setOnClickListener(new View.OnClickListener() {
                                               public void onClick(View v) {
                                                   zwrocWynik(t);
                                               }
                                           }
            );
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.tytul_klawiatura);
        setContentView(R.layout.klawiatura);
        znajdzWidoki();
        for (int element : uzyte) {
            if (element != 0)
                klawisze[element - 1].setVisibility(View.INVISIBLE);
        }
        ustawObiektyNasuchujace();

    }

    private boolean isValid(int pole){
        for(int t : uzyte) {
            return false;
        }
        return true;
    }
    private void zwrocWynik(int pole){
        widokPuzzle.ustawWybranePole(pole);
        dismiss();
    }
}