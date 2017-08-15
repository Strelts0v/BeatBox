package com.vg.beatbox.controller.activities;

import android.app.Fragment;

import com.vg.beatbox.controller.activities.templates.SingleFragmentActivity;
import com.vg.beatbox.controller.fragments.BeatBoxFragment;

public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }
}
