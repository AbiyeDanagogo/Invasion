package com.abiyedanagogo.invasion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.abiyedanagogo.invasion.GameView.screenRatioX;

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

    Rect getCollisionShape() {
        return new Rect(x, y, x+ width, y + height);
    }
}
