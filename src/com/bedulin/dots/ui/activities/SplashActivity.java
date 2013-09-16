package com.bedulin.dots.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.bedulin.dots.R;
import com.crittercism.app.Crittercism;

/**
 * @author Alexandr Bedulin
 */
public class SplashActivity extends Activity implements Animation.AnimationListener {
    // ===========================================================
    // Constants
    // ===========================================================
    private static final String LOG_TAG = SplashActivity.class.getName();

    private static final String DEBUG_VERSION = "debug";

    // ===========================================================
    // Fields
    // ===========================================================
    private ImageView ivLauncher;

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
        try {
            String appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            if (!DEBUG_VERSION.equals(appVersion))
                Crittercism.initialize(getApplicationContext(), "5235d18cd0d8f77d48000001");

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG, e.toString());
        }

        setContentView(R.layout.launcher_screen);

        ivLauncher = (ImageView) findViewById(R.id.ivLauncher);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.launcher_anim);
        anim.setAnimationListener(this);
        ivLauncher.setAnimation(anim);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        ivLauncher.setBackgroundColor(getResources().getColor(android.R.color.black));
        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }


    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
