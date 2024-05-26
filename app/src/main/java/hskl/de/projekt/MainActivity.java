package hskl.de.projekt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private GameView gameView;
    private WindowManager mWindowManager;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Display mDisplay;

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

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
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
        Log.d("SensorChanged", "Velocity: " + velocity);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}