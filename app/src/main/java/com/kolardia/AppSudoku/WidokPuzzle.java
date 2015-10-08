package com.kolardia.AppSudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;


public class WidokPuzzle extends View {

   private final Gra gra;

    public WidokPuzzle(Context kontekst) {
        super(kontekst);
        this.gra = (Gra) kontekst;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    private float szerokosc;
    private float wysokosc;
    protected int wybX;
    protected int wybY;
    private final Rect wybProst = new Rect();

    @Override
    protected void onSizeChanged(int s, int w, int staras, int staraw) {
        szerokosc = s / 9f;
        wysokosc = w / 9f;
        wezProst(wybX, wybY, wybProst);
        super.onSizeChanged(s, w, staras, staraw);
    }

    private void wezProst(int x, int y, Rect prost) {
        prost.set((int) (x * szerokosc), (int) (y * wysokosc), (int) (x * szerokosc + szerokosc), (int) (y * wysokosc + wysokosc));
    }

    private void wybierz(int x, int y) {
        invalidate(wybProst);
        wybX = Math.min(Math.max(x, 0), 8);
        wybY = Math.min(Math.max(y, 0), 8);
        wezProst(wybX, wybY, wybProst);
        invalidate(wybProst);
    }

    @Override
    protected void onDraw(Canvas plotno) {

        Paint tlo = new Paint();
        tlo.setColor(getResources().getColor(R.color.puzzle_tlo));
        plotno.drawRect(0, 0, getWidth(), getHeight(), tlo);

        Paint ciemny = new Paint();
        ciemny.setColor(getResources().getColor(R.color.puzzle_ciemny));

        Paint podswietlenie = new Paint();
        podswietlenie.setColor(getResources().getColor(R.color.puzzle_podswietlenie));

        Paint jasny = new Paint();
        jasny.setColor(getResources().getColor(R.color.puzzle_jasny));

        // rysuje siatke
        for (int i = 0; i < 9; i++) {
            plotno.drawLine(0, i * wysokosc, getWidth(), i * wysokosc, ciemny);
            plotno.drawLine(0, i * wysokosc + 1, getWidth(), i * wysokosc + 1, ciemny);
            plotno.drawLine(i * szerokosc, 0, i * szerokosc, getHeight(), ciemny);
            plotno.drawLine(i * szerokosc + 1, 0, i * szerokosc + 1, getHeight(), ciemny);
        }
        // rysuje bloki
        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0)
                continue;
            plotno.drawLine(0, i * wysokosc, getWidth(), i * wysokosc, ciemny);
            plotno.drawLine(0, i * wysokosc + 1, getWidth(), i * wysokosc + 1, ciemny);
            plotno.drawLine(i * szerokosc, 0, i * szerokosc, getHeight(), ciemny);
            plotno.drawLine(i * szerokosc + 1, 0, i * szerokosc + 1, getHeight(), ciemny);
        }

        Paint zaznaczony = new Paint();
        zaznaczony.setColor(getResources().getColor(R.color.puzzle_zaznaczony));
        plotno.drawRect(wybProst, zaznaczony);



        Paint pierwszy_plan = new Paint(Paint.ANTI_ALIAS_FLAG);
        pierwszy_plan.setColor(getResources().getColor(R.color.puzzle_pierwszy_plan));
        pierwszy_plan.setStyle(Paint.Style.FILL);

        pierwszy_plan.setTextSize(wysokosc * 0.75f);
        pierwszy_plan.setTextScaleX(szerokosc / wysokosc);
        pierwszy_plan.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fm = pierwszy_plan.getFontMetrics();
        float x = szerokosc / 2;
        float y = wysokosc / 2 - (fm.ascent + fm.descent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                plotno.drawText(this.gra.wezZnakPola(i, j), i * szerokosc + x, j * wysokosc + y, pierwszy_plan);
            }
        }



        Paint podpowiedz = new Paint();
        int c[] = {
                getResources().getColor(R.color.puzzle_podpowiedz_0),
                getResources().getColor(R.color.puzzle_podpowiedz_1),
                getResources().getColor(R.color.puzzle_podpowiedz_2),
        };

        Rect r = new Rect();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int pozostaleruchy = 9 - gra.wezUzytePola(i, j).length;
                if (pozostaleruchy < c.length) {
                    wezProst(i, j, r);
                    podpowiedz.setColor(c[pozostaleruchy]);
                    plotno.drawRect(r, podpowiedz);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent zdarzenie) {
        if (zdarzenie.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(zdarzenie);
        wybierz((int) (zdarzenie.getX() / szerokosc), (int) (zdarzenie.getY() / wysokosc));

        gra.pokazKlaviatureLubBlad(wybX, wybY);
        return true;
    }

    public void ustawWybranePole(int pole) {
        if (gra.ustawPoleJesliPoprawne(wybX, wybY, pole)) {
            invalidate();
        } else {
            startAnimation(AnimationUtils.loadAnimation(gra, R.anim.wstrzasy));
        }
    }




}


