package hskl.de.projekt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Main activity class
 * Diese Klasse dient als Wrapper für das ganze Spiel
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //declaration of certain variables
    private GameView gameView;
    private WindowManager mWindowManager;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Display mDisplay;


    /**
     * Diese Funktion dient dazu, alle nötigen Sensoren und Manager zu initialisieren
     * Außerdem wird hier die Gameview erstellt um in den Gameloop rein zu kommen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        gameView = new GameView(this);

        gameView.context = this;

        setContentView(gameView);

    }

    /**
     * bei Neustart, den Sensor wieder neu registrieren
     */
    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * bei Pause, den Sensor aus dem Register nehmen
     */
    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    /**
     *
     */
    @Override
    public void onSensorChanged (SensorEvent event){
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        float accelerationX = event.values[0];
        float accelerationY = event.values[1];
        float velocity;

        switch (mDisplay.getRotation()) {
            case Surface.ROTATION_0:
                velocity = -accelerationX;
                break;
            case Surface.ROTATION_90:
                velocity = accelerationY;
                break;
            case Surface.ROTATION_180:
                velocity = accelerationX;
                break;
            case Surface.ROTATION_270:
                velocity = -accelerationY;
                break;
            default:
                velocity = 0;
                break;
        }
        gameView.setShipVelocity(velocity);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}