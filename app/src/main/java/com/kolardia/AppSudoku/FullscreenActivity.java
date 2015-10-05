package com.kolardia.AppSudoku;

import com.kolardia.AppSudoku.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.view.MenuInflater;
import android.view.MotionEvent;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.app.AlertDialog;
import android.content.DialogInterface;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
//gitHub.kolardia

public class FullscreenActivity extends Activity{
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */

    private WidokPuzzle widokPuzzle;
    private static final String ZNACZNIK = "Sudoku";


    private void otwurzDialogNowaGra(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.tytul_nowa_gra)
                .setItems(R.array.trudnosc,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialoginterface, int i){
                                uruchomGre(i);
                            }
                        }
                        )
                .show();
    }

    private void uruchomGre(int i){
        Intent intentcja = new Intent(FullscreenActivity.this, Gra.class);
        intentcja.putExtra(Gra.TRUDNOSC_KLUCZ, i);
        startActivity(intentcja);
    }


    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        /*View przyciskInformacje = findViewById(R.id.dummy_button_informacje);
        przyciskInformacje.setOnClickListener(this);*/

        //View dummy_button_informacje = findViewById(R.id.dummy_button_informacje);
       // dummy_button_informacje.setOnClickListener((OnClickListener) this);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TOGGLE_ON_CLICK)

                {
                    mSystemUiHider.toggle();


                    Intent intent = new Intent(getApplicationContext(), Informacje.class);
                    startActivity(intent);
                    /*Intent intent = new Intent(context, Informacje.class);
                    Intent i = new Intent(context , Informacje.class);*/
                    //finish();
                    /*switch (view.getId()) {
                        case R.id.dummy_button_wyjscie:
                            finish();
                            break;}*/

                }
            else

            {
                mSystemUiHider.show();
            }

        }
    });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button_nowa_gra).setOnTouchListener(mDelayHideTouchListener);
        findViewById(R.id.dummy_button_kontynuacja).setOnTouchListener(mDelayHideTouchListener);
        findViewById(R.id.dummy_button_wyjscie).setOnTouchListener(mDelayHideTouchListener);
    }
   /* protected void onStop(){
        findViewById(R.id.dummy_button_wyjscie).setOnTouchListener(mDelayHideTouchListener);
        super.onStop();
    };*/
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener;

    {
        mDelayHideTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (AUTO_HIDE) {
                    delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    switch (view.getId()) {
                        case R.id.dummy_button_wyjscie:
                            finish();
                            break;
                        case R.id.dummy_button_nowa_gra:
                            otwurzDialogNowaGra();
                            break;

                        case R.id.dummy_button_kontynuacja:
                            Intent intent = new Intent(getApplicationContext(), Gra.class);
                            startActivity(intent);
                            break;

                    }
                }
                return false;
            }
        };
    }

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater wypelniacz = getMenuInflater();
        wypelniacz.inflate(R.menu.menu_fullscreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.ustawienia:
                startActivity(new Intent(this, Ustawienia.class));
                return true;
        }

        return false;
    }
}
