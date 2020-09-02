package com.abiyedanagogo.invasion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Created by Abiye Danagogo on 17/05/2020.
 * The GameView class extends the SurfaceView. The SurfaceView provides a dedicated drawing surface. This allows us to
 * draw all of our images for the game. The class also implements the Runnable interface which allows the resources to be run as a thread
 * using its run method.
 * */

public class GameView extends SurfaceView implements Runnable {
    public static float screenRatioX, screenRatioY;

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    private Paint paint;
    private Alien[] aliens;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private int sound, soundHit, soundExplosion;
    private List<Bullet> bullets;
    private Rocket rocket;
    private GameActivity activity;
    private Background background1, background2;
    private boolean playMode;
    private PauseMenu pauseMenu;
    private int speedIncrease;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        //This checks which version of android the device is running and depending on which it runs code to create the soundpool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        sound = soundPool.load(activity, R.raw.shoot, 1);
        soundHit = soundPool.load(activity, R.raw.hitsound, 1);
        soundExplosion  = soundPool.load(activity, R.raw.explodesound, 2);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        pauseMenu = new PauseMenu(getResources());
        rocket = new Rocket(this,screenY, getResources());

        bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128 / screenRatioX);
        paint.setColor(Color.WHITE);

        aliens = new Alien[4];
        for (int i = 0; i < 4; i++) {
            Alien alien = new Alien(getResources());
            aliens[i] = alien;

        }
        random = new Random();
        playMode = true;
        speedIncrease = 0;
    }

    /*
     * The run method is from the Runnable interface and continuously calls the update, draw and sleep methods
     * while the boolean isPlaying is true.
     * */
    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        background1.x -= 10 / screenRatioX;
        background2.x -= 10 / screenRatioX;


        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }
        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }
        if (rocket.isGoingUp) {
            rocket.y -= 30 / screenRatioY;
        }
        if (rocket.isGoingDown){
            rocket.y += 30 / screenRatioY;
        }


        if (rocket.y < 0){
            rocket.y = 0;
        }
        if (rocket.y > screenY - rocket.height){
            rocket.y = screenY - rocket.height;
        }

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.x > screenX) {
                trash.add(bullet);
            }
            bullet.x += 50 / screenRatioX;

            for (Alien alien : aliens) {
                if (Rect.intersects(alien.getCollisionShape(), bullet.getCollisionShape())) {
                    hitAlien();
                    score++;
                    alien.x = -500;
                    bullet.x = screenX + 500;
                    alien.setWasShot(true);
                }
            }

        }

        for (Bullet bullet : trash) {
            bullets.remove(bullet);
        }

        for (Alien alien: aliens) {
            alien.x -= alien.getSpeed();

            if (alien.x + alien.width < 0) {

                if (!alien.isWasShot()) {
                    isGameOver = true;
                    return;
                }


                speedIncrease = score;

                if (speedIncrease > 180) {
                    speedIncrease = 180;
                }


                int bound = (int) (20 / screenRatioX);
                alien.setSpeed((random.nextInt(bound)) + ((speedIncrease/6)));
                //alien.speed = random.nextInt(bound);

                if (alien.getSpeed() < 5 / screenRatioX) {
                    alien.setSpeed((int) (5 / screenRatioX));
                }
                alien.x = screenX;
                alien.y = random.nextInt(screenY - alien.height);


                alien.setWasShot(false);
            }

            if (Rect.intersects(alien.getCollisionShape(), rocket.getCollisionShape())) {
                isGameOver = true;
                return;
            }
        }

    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, 0, 0, paint);
            canvas.drawBitmap(background2.background, screenX/2f, 0, paint);
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (Alien alien : aliens){
                canvas.drawBitmap(alien.getAlien(), alien.x, alien.y, paint);
            }

            canvas.drawText(String.valueOf(score), screenX / 2f, 164, paint);
            canvas.drawText("Pause", (4*screenX)/5f, screenY/5f, paint);

            if (isGameOver) {
                isPlaying = false;
                hitRocket();
                canvas.drawBitmap(rocket.getDead(), rocket.x, rocket.y, paint);
                canvas.drawBitmap(pauseMenu.gameover, (screenX - pauseMenu.menuWidth)/2f,(screenY-pauseMenu.menuHeight)/2f, paint );
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }

            canvas.drawBitmap(rocket.getFlight(), rocket.x, rocket.y, paint);

            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            paint.setColor(Color.argb(100, 255, 255, 255));

            canvas.drawBitmap(pauseMenu.cross, (4*screenX)/5f, screenY-(screenY/3f)-pauseMenu.arrowHeight, paint);
            canvas.drawBitmap(pauseMenu.upArrow, 164/screenRatioX, screenY/9f, paint);
            canvas.drawBitmap(pauseMenu.downArrow, 164/screenRatioX, screenY-(screenY/9f)-pauseMenu.arrowHeight, paint);
            paint.setColor(Color.WHITE);
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, 0, 0, paint);
            canvas.drawBitmap(background2.background, screenX/2f, 0, paint);
            canvas.drawBitmap(pauseMenu.menu, (screenX - pauseMenu.menuWidth)/2f,(screenY-pauseMenu.menuHeight)/2f, paint );
            canvas.drawBitmap(pauseMenu.quit, screenX/2f,(screenY/2f)+pauseMenu.quitHeight, paint);
            canvas.drawText("Resume", (4*screenX)/5f, screenY/5f, paint);
            getHolder().unlockCanvasAndPost(canvas);
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if ((event.getX() < screenX/2f) && (event.getY() < screenY/2f)) {
                rocket.isGoingUp = true;
            }
            else if ((event.getX() < screenX/2f) && (event.getY() > screenY/2f)) {
                rocket.isGoingDown = true;
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            rocket.isGoingUp = false;
            rocket.isGoingDown = false;

            if ((event.getX() > screenX/2f) && (event.getY() > screenY/5f) && playMode){
                rocket.toShoot++;
            }

            else if (!playMode && (event.getX() > screenX/2f) && (event.getX() < (screenX/2f)+pauseMenu.quitWidth) && (event.getY() > (screenY/2f)+pauseMenu.quitHeight) && (event.getY() < (screenY/2f)+pauseMenu.quitHeight+pauseMenu.quitHeight)) {
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }

            else if ((event.getX() > (4*screenX)/5f) && (event.getY() < screenY/5f)) {
                if (playMode){
                    pause();
                }else {
                    resume();
                }
                playMode = !playMode;
            }

        }

        return true;
    }

    public void hitRocket() {
        if (prefs.getBoolean("sound", true)) {
            soundPool.play(soundExplosion, 1,1,1,0,1);
        }
    }


    public void hitAlien() {
        if (prefs.getBoolean("sound", true)) {
            soundPool.play(soundHit, 1,1,0,0,1);
        }
    }

    public void newBullet() {
        if (prefs.getBoolean("sound", true)) {
            soundPool.play(sound, 1,1,0,0,1);
        }
        Bullet bullet = new Bullet(getResources());
        bullet.x = rocket.x + rocket.width;
        bullet.y = rocket.y + (rocket.height / 3);
        bullets.add(bullet);
    }
}
