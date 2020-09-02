package com.abiyedanagogo.invasion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.abiyedanagogo.invasion.GameView.screenRatioX;

/*
 * Created by Abiye Danagogo on 17/05/2020.
 * The bullets that are being fired by the rocket are in itialised in this class
 * */

public class Bullet {
    int x, y, width, height;
    Bitmap bullet;

    Bullet(Resources resources) {
        bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width / screenRatioX);
        height = (int) (height / screenRatioX);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);

    }

    /*
     * A rectangle is created around the sprite to detect collisions.
     * */
    Rect getCollisionShape() {
        return new Rect(x, y, x+ width, y + height);
    }
}
