package com.bedulin.dots.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bedulin.dots.R;

import static com.bedulin.dots.Constants.*;

/**
 * @author Alexandr Bedulin
 */
public class MenuActivity extends Activity implements View.OnClickListener {
    // ===========================================================
    // Constants
    // ===========================================================
    public static final String LOG = MenuActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================
    private Button btnResume, btnNewGame, btnSave, btnLoad, btnSettings, btnExit;
    private TextView tvVersion;

    private int mMenuMode;

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
        setContentView(R.layout.game_menu_screen);

        findViews();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent == null) {
            mMenuMode = MENU_MODE_FIRST_START;
        } else {
            mMenuMode = intent.getIntExtra(MENU_MODE, MENU_MODE_CHECK);
        }
        Log.d(LOG, "mMenuMode: " + mMenuMode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnResume:
                resumeLastGame();
                break;
            case R.id.btnNewGame:
                if (mMenuMode == MENU_MODE_PAUSE) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setMessage(getString(R.string.new_game_warring));
                    dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startNewGame();
                        }
                    });
                    dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                } else {
                    startNewGame();
                }
                break;
            case R.id.btnSave:
                saveGame();
                break;
            case R.id.btnLoad:
                loadGame();
                break;
            case R.id.btnSettings:
                showSettings();
                break;
            case R.id.btnExit:
                if (mMenuMode == MENU_MODE_PAUSE) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setMessage(getString(R.string.exit_game_warring));
                    dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mMenuMode == MENU_MODE_PAUSE) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(getString(R.string.exit_game_warring));
                dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private void findViews() {
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        btnResume = (Button) findViewById(R.id.btnResume);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnExit = (Button) findViewById(R.id.btnExit);
    }

    private void setListeners() {
        btnResume.setOnClickListener(this);
        btnNewGame.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    private void resumeLastGame() {
        finish();
//        TODO need to load last saved game here
//        Intent intent = new Intent(this, GameFieldActivity.class);
//        startActivity(intent);
        Toast.makeText(this, "Загрузака последней игры", Toast.LENGTH_SHORT).show();
    }

    private void startNewGame() {
        finish();
        Intent intent = new Intent(this, GameFieldActivity.class);
        startActivity(intent);
    }

    private void saveGame() {
//        TODO need to make saving algorithm here
        Intent intent = new Intent(this, SaveGameActiity.class);
        startActivity(intent);
    }

    private void loadGame() {
        Intent intent = new Intent(this, LoadGameActivity.class);
        startActivity(intent);
    }

    private void showSettings() {
        Intent intent = new Intent(this, PreferenceActivity.class);
        startActivity(intent);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
