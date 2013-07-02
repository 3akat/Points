package com.bedulin.dots.ui.views;

import android.content.Context;
import android.content.pm.PackageManager;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bedulin.dots.R;

/**
 * @author Alexandr Bedulin
 */
public class CustomPreference extends Preference {
    // ===========================================================
    // Constants
    // ===========================================================
    private static final String LOG = CustomPreference.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================
    private LayoutInflater mLayoutInflater;
    private View mPreferenceView;
    private TextView tvVersion;

    // ===========================================================
    // Constructors
    // ===========================================================
    public CustomPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPreference(Context context) {
        this(context, null);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected View onCreateView(ViewGroup parent) {
        mPreferenceView = mLayoutInflater.inflate(R.layout.prefs_header, null, false);
        tvVersion = (TextView) mPreferenceView.findViewById(R.id.tvVersion);
        try {
            tvVersion.setText(getContext().getString(R.string.ver) + getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(LOG, "tvVersion init: " + e.toString());
        }

        return mPreferenceView;
    }


    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
