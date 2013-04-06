package com.bedulin.dots.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.bedulin.dots.R;
import com.bedulin.dots.ui.constants.MenuAndPrefsConstants;

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
        intent.putExtra(MenuAndPrefsConstants.MENU_MODE, MenuAndPrefsConstants.MENU_MODE_PAUSE);
        startActivity(intent);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
