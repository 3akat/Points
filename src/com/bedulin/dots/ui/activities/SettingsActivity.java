package com.bedulin.dots.ui.activities;

import android.os.Bundle;
import android.preference.*;
import android.widget.TextView;
import android.widget.Toast;
import com.bedulin.dots.R;

import static com.bedulin.dots.ui.constants.MenuAndPrefsConstants.*;

/**
 * @author Alexandr Bedulin
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    // ===========================================================
    // Constants
    // ===========================================================
    private static final String LOG = "T_" + SettingsActivity.class.getName();

    // ===========================================================
    // Fields
    // ===========================================================
    private EditTextPreference etpFirstPlayerName, etpSecondPlayerName, etpThinkingTime, etpFieldCellsInWidth, etpFieldCellsInHeight;
    private CheckBoxPreference cbpSecondClick, cbpPlayWithAndroid;
    private ListPreference lpFirstPlayerColor, lpSecondPlayerColor, lpFieldLinesColor;
    private TextView tvVersion;

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
        addPreferencesFromResource(R.xml.preference);
        findPreferences();
        setListeners();
//        try {
//            tvVersion.setText(getString(R.string.ver) + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d(LOG, "tvVersion init: " + e.toString());
//        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case PREFERENCE_FIRST_PLAYER_COLOR:
                switch (newValue.toString()) {
                    case COLOR_BLACK:
                        lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_black));
                        lpFirstPlayerColor.setSummary(getString(R.string.color_black));
                        break;
                    case COLOR_RED:
                        lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_red));
                        lpFirstPlayerColor.setSummary(getString(R.string.color_red));
                        break;
                    case COLOR_GREEN:
                        lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_green));
                        lpFirstPlayerColor.setSummary(getString(R.string.color_green));
                        break;
                    case COLOR_BLUE:
                        lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_blue));
                        lpFirstPlayerColor.setSummary(getString(R.string.color_blue));
                        break;
                    case COLOR_YELLOW:
                        lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_yellow));
                        lpFirstPlayerColor.setSummary(getString(R.string.color_yellow));
                        break;
                }
                break;
            case PREFERENCE_SECOND_PLAYER_COLOR:
                if (!newValue.equals(lpFirstPlayerColor.getValue()) && !newValue.equals(lpFieldLinesColor.getValue())) {
                    switch (newValue.toString()) {
                        case COLOR_BLACK:
                            lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_black));
                            lpSecondPlayerColor.setSummary(getString(R.string.color_black));
                            break;
                        case COLOR_RED:
                            lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_red));
                            lpSecondPlayerColor.setSummary(getString(R.string.color_red));
                            break;
                        case COLOR_GREEN:
                            lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_green));
                            lpSecondPlayerColor.setSummary(getString(R.string.color_green));
                            break;
                        case COLOR_BLUE:
                            lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_blue));
                            lpSecondPlayerColor.setSummary(getString(R.string.color_blue));
                            break;
                        case COLOR_YELLOW:
                            lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_yellow));
                            lpSecondPlayerColor.setSummary(getString(R.string.color_yellow));
                            break;
                    }
                } else {
                    Toast.makeText(this, getString(R.string.same_colors), Toast.LENGTH_SHORT).show();
                }
                break;
            case PREFERENCE_FIELD_LINES_COLOR:
                if (!newValue.equals(lpFirstPlayerColor.getValue()) && !newValue.equals(lpSecondPlayerColor.getValue())) {
                    switch (newValue.toString()) {
                        case COLOR_LIGHT_BLUE:
                            lpFieldLinesColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_light_blue));
                            lpFieldLinesColor.setSummary(getString(R.string.color_light_blue));
                            break;
                        case COLOR_LIGHT_PURPLE:
                            lpFieldLinesColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_light_purple));
                            lpFieldLinesColor.setSummary(getString(R.string.color_light_purple));
                            break;
                    }
                } else {
                    Toast.makeText(this, getString(R.string.same_colors), Toast.LENGTH_SHORT).show();
                }
                break;
            case PREFERENCE_FIRST_PLAYER_NAME:
                etpFirstPlayerName.setSummary(newValue.toString());
                break;
            case PREFERENCE_SECOND_PLAYER_NAME:
                if (!newValue.equals(etpFirstPlayerName.getText())) {
                    etpSecondPlayerName.setSummary(newValue.toString());
                } else {
                    Toast.makeText(this, getString(R.string.same_names), Toast.LENGTH_SHORT).show();
                }
                break;
            case PREFERENCE_THINKING_TIME:
                etpThinkingTime.setSummary(newValue + SPACE+ getString(R.string.minutes));
                break;
            case PREFERENCE_PLAY_WITH_ANDROID:
                boolean enabled = Boolean.getBoolean(newValue.toString());
                etpThinkingTime.setEnabled(!enabled);
                break;
//            TODO cells
//            case PREFERENCE_FIELD_CELLS_IN_HEIGHT:
//                etpFieldCellsInHeight.setSummary(newValue.toString()+SPACE+getString(R.string.cells));
//                break;
//            case PREFERENCE_FIELD_CELLS_IN_WIDTH:
//                etpFieldCellsInWidth.setSummary(newValue.toString()+SPACE+getString(R.string.cells));
//                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPreference();
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private void findPreferences() {
        etpFirstPlayerName = (EditTextPreference) findPreference(PREFERENCE_FIRST_PLAYER_NAME);
        etpSecondPlayerName = (EditTextPreference) findPreference(PREFERENCE_SECOND_PLAYER_NAME);
        etpThinkingTime = (EditTextPreference) findPreference(PREFERENCE_THINKING_TIME);
        //            TODO cells
//        etpFieldCellsInHeight = (EditTextPreference) findPreference(PREFERENCE_FIELD_CELLS_IN_HEIGHT);
//        etpFieldCellsInWidth = (EditTextPreference) findPreference(PREFERENCE_FIELD_CELLS_IN_WIDTH);
        cbpSecondClick = (CheckBoxPreference) findPreference(PREFERENCE_SECOND_CLICK);
        cbpPlayWithAndroid = (CheckBoxPreference) findPreference(PREFERENCE_PLAY_WITH_ANDROID);
        lpFirstPlayerColor = (ListPreference) findPreference(PREFERENCE_FIRST_PLAYER_COLOR);
        lpSecondPlayerColor = (ListPreference) findPreference(PREFERENCE_SECOND_PLAYER_COLOR);
        lpFieldLinesColor = (ListPreference) findPreference(PREFERENCE_FIELD_LINES_COLOR);
    }

    private void setListeners() {
        etpFirstPlayerName.setOnPreferenceChangeListener(this);
        etpSecondPlayerName.setOnPreferenceChangeListener(this);
        etpThinkingTime.setOnPreferenceChangeListener(this);
        //            TODO cells
//        etpFieldCellsInHeight.setOnPreferenceChangeListener(this);
//        etpFieldCellsInWidth.setOnPreferenceChangeListener(this);
        cbpSecondClick.setOnPreferenceChangeListener(this);
        cbpPlayWithAndroid.setOnPreferenceChangeListener(this);
        lpFirstPlayerColor.setOnPreferenceChangeListener(this);
        lpSecondPlayerColor.setOnPreferenceChangeListener(this);
        lpFieldLinesColor.setOnPreferenceChangeListener(this);
    }

    private void initPreference() {
        //            TODO cells
//        String temp = etpFieldCellsInHeight.getText();
//        if (temp!=null && !temp.equals("")) {
//            etpFieldCellsInHeight.setSummary(temp +SPACE+ getString(R.string.cells));
//        }
//        temp = etpFieldCellsInWidth.getText();
//        if (temp!=null && !temp.equals("")) {
//            etpFieldCellsInWidth.setSummary(temp +SPACE+ getString(R.string.cells));
//        }
        String temp  = etpThinkingTime.getText();
        if (temp!=null && !temp.equals("")) {
            etpThinkingTime.setSummary(temp +SPACE+ getString(R.string.minutes));
        }
        etpFirstPlayerName.setSummary(etpFirstPlayerName.getText());
        etpSecondPlayerName.setSummary(etpSecondPlayerName.getText());
        switch (lpFirstPlayerColor.getValue()) {
            case COLOR_BLACK:
                lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_black));
                lpFirstPlayerColor.setSummary(getString(R.string.color_black));
                break;
            case COLOR_RED:
                lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_red));
                lpFirstPlayerColor.setSummary(getString(R.string.color_red));
                break;
            case COLOR_GREEN:
                lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_green));
                lpFirstPlayerColor.setSummary(getString(R.string.color_green));
                break;
            case COLOR_BLUE:
                lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_blue));
                lpFirstPlayerColor.setSummary(getString(R.string.color_blue));
                break;
            case COLOR_YELLOW:
                lpFirstPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_yellow));
                lpFirstPlayerColor.setSummary(getString(R.string.color_yellow));
                break;
        }
        switch (lpSecondPlayerColor.getValue()) {
            case COLOR_BLACK:
                lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_black));
                lpSecondPlayerColor.setSummary(getString(R.string.color_black));
                break;
            case COLOR_RED:
                lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_red));
                lpSecondPlayerColor.setSummary(getString(R.string.color_red));
                break;
            case COLOR_GREEN:
                lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_green));
                lpSecondPlayerColor.setSummary(getString(R.string.color_green));
                break;
            case COLOR_BLUE:
                lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_blue));
                lpSecondPlayerColor.setSummary(getString(R.string.color_blue));
                break;
            case COLOR_YELLOW:
                lpSecondPlayerColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_yellow));
                lpSecondPlayerColor.setSummary(getString(R.string.color_yellow));
                break;
        }
        switch (lpFieldLinesColor.getValue()) {
            case COLOR_LIGHT_BLUE:
                lpFieldLinesColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_light_blue));
                lpFieldLinesColor.setSummary(getString(R.string.color_light_blue));
                break;
            case COLOR_LIGHT_PURPLE:
                lpFieldLinesColor.setDialogIcon(getResources().getDrawable(R.drawable.icon_light_purple));
                lpFieldLinesColor.setSummary(getString(R.string.color_light_purple));
                break;
        }
    }
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
