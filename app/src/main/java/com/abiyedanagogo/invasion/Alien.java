package com.abiyedanagogo.invasion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.abiyedanagogo.invasion.GameView.screenRatioX;
import static com.abiyedanagogo.invasion.GameView.screenRatioY;

public class Alien {

    private int speed = 20;
    private boolean wasShot = true;
    int x = 0, y, width, height, alienCounter = 1;
    Bitmap alien1, alien2, alien3, alien4;

    Alien(Resources resources) {
        alien1 = BitmapFactory.decodeResource(resources, R.drawable.alien1);
        alien2 = BitmapFactory.decodeResource(resources, R.drawable.alien2);
        alien3 = BitmapFactory.decodeResource(resources, R.drawable.alien3);
        alien4 = BitmapFactory.decodeResource(resources, R.drawable.alien2);

        width = alien1.getWidth();
        height = alien1.getHeight();

        width /=6;
        height /=6;

        width = (int) (width / screenRatioX);
        height = (int) (height / screenRatioY);

        alien1 = Bitmap.createScaledBitmap(alien1, width, height, false);
        alien2 = Bitmap.createScaledBitmap(alien2, width, height, false);
        alien3 = Bitmap.createScaledBitmap(alien3, width, height, false);
        alien4 = Bitmap.createScaledBitmap(alien4, width, height, false);

        y = -height;
    }

    Bitmap getAlien() {
        if (alienCounter == 1) {
            alienCounter++;
            return alien1;
        }
        if (alienCounter == 2) {
            alienCounter++;
            return alien2;
        }
        if (alienCounter == 3) {
            alienCounter++;
            return alien3;
        }

        alienCounter = 1;
        return alien4;
    }

    Rect getCollisionShape() {
        return new Rect((int) (x+(30/screenRatioX)), y, x+ width, y + height);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isWasShot() {
        return wasShot;
    }

    public void setWasShot(boolean wasShot) {
        this.wasShot = wasShot;
    }
}
