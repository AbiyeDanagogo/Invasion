package com.abiyedanagogo.invasion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.abiyedanagogo.invasion.GameView.screenRatioX;
import static com.abiyedanagogo.invasion.GameView.screenRatioY;

public class Rocket {

    public boolean isGoingUp = false, isGoingDown = false;
    int toShoot = 0;
    int x, y, width, height, wingCounter = 0, shootCounter = 1;
    Bitmap rocket1, rocket2, shoot1, shoot2, shoot3, shoot4, shoot5, dead;
    private GameView gameView;


    Rocket(GameView gameView, int screenY, Resources resources) {
        this.gameView = gameView;

        rocket1 = BitmapFactory.decodeResource(resources, R.drawable.rocket1);
        rocket2 = BitmapFactory.decodeResource(resources, R.drawable.rocket2);

        width = rocket1.getWidth();
        height = rocket1.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width / screenRatioX);
        height = (int) (height / screenRatioY);

        rocket1 = Bitmap.createScaledBitmap(rocket1, width, height, false);
        rocket2 = Bitmap.createScaledBitmap(rocket2, width, height, false);

        shoot1 = BitmapFactory.decodeResource(resources, R.drawable.rocket1);
        shoot2 = BitmapFactory.decodeResource(resources, R.drawable.rocket1);
        shoot3 = BitmapFactory.decodeResource(resources, R.drawable.rocket2);
        shoot4 = BitmapFactory.decodeResource(resources, R.drawable.rocket2);
        shoot5 = BitmapFactory.decodeResource(resources, R.drawable.rocket1);

        shoot1 = Bitmap.createScaledBitmap(shoot1, width, height, false);
        shoot2 = Bitmap.createScaledBitmap(shoot2, width, height, false);
        shoot3 = Bitmap.createScaledBitmap(shoot3, width, height, false);
        shoot4 = Bitmap.createScaledBitmap(shoot4, width, height, false);
        shoot5 = Bitmap.createScaledBitmap(shoot5, width, height, false);

        dead = BitmapFactory.decodeResource(resources, R.drawable.explosion);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 / screenRatioX);

    }

    Bitmap getFlight() {

        if (toShoot != 0){
            if (shootCounter == 1) {
                shootCounter++;
                return shoot1;
            }

            if (shootCounter == 2) {
                shootCounter++;
                return shoot2;
            }

            if (shootCounter == 3) {
                shootCounter++;
                return shoot3;
            }

            if (shootCounter == 4) {
                shootCounter++;
                return shoot4;
            }

            shootCounter = 1;
            toShoot--;
            gameView.newBullet();

            return shoot5;

        }

        if (wingCounter == 0) {
            wingCounter++;
            return rocket1;
        }
        wingCounter--;
        return rocket2;
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x+ width, y + height);
    }



    Bitmap getDead() {
        return dead;
    }
}
