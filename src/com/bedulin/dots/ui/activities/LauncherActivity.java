package com.bedulin.dots.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.bedulin.dots.R;

/**
 * @author Alexandr Bedulin
 */
public class LauncherActivity extends Activity implements Animation.AnimationListener {
    // ===========================================================
    // Constants
    // ===========================================================

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
