package com.stacrux.paceclock.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.stacrux.paceclock.R;
import com.stacrux.paceclock.ServiceProvider;
import com.stacrux.paceclock.model.SoundSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RotationHelper {

    private final static int periodDuration = 15000;

    /**
     * given a view and the context, this method setup a full 360 rotation, split
     * in 4 separated 90 degrees rotations, it is possible then to perform specific actions
     * for each quarter of rotation thanks to a listener.
     *
     * @param viewToRotate
     * @param ctx
     */
    public static void setupQuarterRotations(final View viewToRotate, final Context ctx) {

        final List<Animation> quarterRotations = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            float fromDegrees = (float) 90 * i;
            float toDegrees = (float) 90 * (i + 1);
            RotateAnimation anim = new RotateAnimation(
                    fromDegrees,
                    toDegrees,
                    Animation.RELATIVE_TO_SELF,
                    .5f,
                    Animation.RELATIVE_TO_SELF,
                    .5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setDuration(periodDuration);
            quarterRotations.add(anim);
        }
        for (int i = 0; i < 4; i++) {
            final int index = i;
            quarterRotations.get(i).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (ServiceProvider.settingsService.isSoundEnabled()) {
                        getSoundArray(ctx).get(index).start();
                    }
                    startNextAnimation(viewToRotate, quarterRotations, index);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        viewToRotate.startAnimation(quarterRotations.get(0));
    }

    private static void startNextAnimation(View viewToRotate,
                                           List<Animation> quarterRotations,
                                           int index) {

        if (index + 1 == quarterRotations.size()) {
            viewToRotate.startAnimation(quarterRotations.get(0));
        } else {
            viewToRotate.startAnimation(quarterRotations.get(index + 1));
        }
    }

    private static List<MediaPlayer> getSoundArray(Context ctx) {
        SoundSet soundSet = ServiceProvider.settingsService.getSoundSet();
        if (soundSet.equals(SoundSet.NOTES)) {
            return Arrays.asList(
                    MediaPlayer.create(ctx, R.raw.c3),
                    MediaPlayer.create(ctx, R.raw.c4),
                    MediaPlayer.create(ctx, R.raw.c5),
                    MediaPlayer.create(ctx, R.raw.c6)
            );
        }
        if (soundSet.equals(SoundSet.EFFECTS)) {
            return Arrays.asList(
                    MediaPlayer.create(ctx, R.raw.glass),
                    MediaPlayer.create(ctx, R.raw.ting),
                    MediaPlayer.create(ctx, R.raw.tone),
                    MediaPlayer.create(ctx, R.raw.star)
            );
        }
        return Collections.emptyList();
    }
}
