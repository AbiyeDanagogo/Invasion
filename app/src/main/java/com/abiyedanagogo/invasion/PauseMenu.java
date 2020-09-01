package com.abiyedanagogo.invasion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.abiyedanagogo.invasion.GameView.screenRatioX;
import static com.abiyedanagogo.invasion.GameView.screenRatioY;

public class PauseMenu {
    int menuWidth, menuHeight, quitWidth, quitHeight, arrowHeight, arrowWidth, crossHeight, crossWidth;
    Bitmap menu, quit, gameover, upArrow, downArrow, cross;

    PauseMenu (Resources resources) {
        //For the Pause Menu
        menu = BitmapFactory.decodeResource(resources, R.drawable.pausedmenu);

        menuHeight = menu.getHeight();
        menuWidth = menu.getWidth();

        menuHeight /= 4;
        menuWidth /= 4;

        menuHeight = (int) (menuHeight / screenRatioY);
        menuWidth = (int) (menuWidth / screenRatioX);

        menu = Bitmap.createScaledBitmap(menu, menuWidth, menuHeight, false);

        //For the Game Over Menu
        gameover = BitmapFactory.decodeResource(resources, R.drawable.gameovermenu);
        gameover = Bitmap.createScaledBitmap(gameover, menuWidth, menuHeight, false);

        //For the Quit Button
        quit = BitmapFactory.decodeResource(resources, R.drawable.quitbutton);

        quitHeight = quit.getHeight();
        quitWidth = quit.getWidth();

        quitHeight /= 4;
        quitWidth /= 4;

        quitHeight = (int) (quitHeight / screenRatioY);
        quitWidth = (int) (quitWidth / screenRatioX);

        quit = Bitmap.createScaledBitmap(quit, quitWidth, quitHeight, false);

        //For the Control Arrows
        upArrow = BitmapFactory.decodeResource(resources, R.drawable.uparrow);
        downArrow = BitmapFactory.decodeResource(resources, R.drawable.downarrow);

        arrowHeight = upArrow.getHeight();
        arrowWidth = upArrow.getWidth();

        arrowHeight /= 4;
        arrowWidth /= 4;

        arrowHeight = (int) (arrowHeight / screenRatioY);
        arrowWidth = (int) (arrowWidth / screenRatioX);

        upArrow = Bitmap.createScaledBitmap(upArrow, arrowWidth, arrowHeight, false);
        downArrow = Bitmap.createScaledBitmap(downArrow, arrowWidth, arrowHeight, false);

        //For the Cross Hairs that show you where to shoot
        cross = BitmapFactory.decodeResource(resources, R.drawable.crosshairs);

        crossHeight = cross.getHeight();
        crossWidth = cross.getWidth();

        crossHeight /= 2;
        crossWidth /= 2;

        crossHeight = (int) (crossHeight / screenRatioY);
        crossWidth = (int) (crossWidth / screenRatioX);

        cross = Bitmap.createScaledBitmap(cross, crossHeight, crossHeight, false);

    }
}
