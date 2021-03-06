package com.bedulin.dots.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.bedulin.dots.R;

import static com.bedulin.dots.Constants.MENU_MODE;
import static com.bedulin.dots.Constants.MENU_MODE_PAUSE;

/**
 * @author Alexandr Bedulin
 */
public class GameFieldActivity extends Activity {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field_screen);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        TODO save game here
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showPauseMenu();
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private void showPauseMenu(){
        Intent intent = new Intent(this,MenuActivity.class);
        intent.putExtra(MENU_MODE, MENU_MODE_PAUSE);
        startActivity(intent);
    }


    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
