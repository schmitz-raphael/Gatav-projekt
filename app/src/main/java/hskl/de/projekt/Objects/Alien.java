package hskl.de.projekt.Objects;

import android.opengl.Matrix;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Alien implements Drawable {
    private float x, y, size;
    private float velocityX;
    private static FloatBuffer alienVerticesBuffer;
    private static ShortBuffer alienFacesBuffer;
    private float[] color;
    private float[] transformationMatrix;
    private static final float leftBoundary = -2.4f;
    private static final float rightBoundary = 2.4f;
    private static final float rowDown = 0.5f;


    private static final float[] alienModelVertices = {
            -1.0f, 5.0f, 9.0f,
            -1.0f, 11.0f, 9.0f,
            -1.0f, 5.0f, 7.0f,
            -1.0f, 11.0f, 7.0f,
            1.0f, 5.0f, 9.0f,
            1.0f, 11.0f, 9.0f,
            1.0f, 5.0f, 7.0f,
            1.0f, 11.0f, 7.0f,
            -1.0f, 3.0f, 3.0f,
            -1.0f, 7.0f, 3.0f,
            -1.0f, 3.0f, 1.0f,
            -1.0f, 7.0f, 1.0f,
            1.0f, 3.0f, 3.0f,
            1.0f, 7.0f, 3.0f,
            1.0f, 3.0f, 1.0f,
            1.0f, 7.0f, 1.0f,
            -1.0f, -1.0f, 3.0f,
            -1.0f, 1.0f, 3.0f,
            -1.0f, -1.0f, -3.0f,
            -1.0f, 1.0f, -3.0f,
            1.0f, -1.0f, 3.0f,
            1.0f, 1.0f, 3.0f,
            1.0f, -1.0f, -3.0f,
            1.0f, 1.0f, -3.0f,
            -1.0f, 1.0f, 3.0f,
            -1.0f, 3.0f, 3.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 3.0f, -1.0f,
            1.0f, 1.0f, 3.0f,
            1.0f, 3.0f, 3.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, 3.0f, -1.0f,
            -1.0f, 1.0f, 9.0f,
            -1.0f, 3.0f, 9.0f,
            -1.0f, 1.0f, 5.0f,
            -1.0f, 3.0f, 5.0f,
            1.0f, 1.0f, 9.0f,
            1.0f, 3.0f, 9.0f,
            1.0f, 1.0f, 5.0f,
            1.0f, 3.0f, 5.0f,
            -1.0f, -1.0f, 11.0f,
            -1.0f, 1.0f, 11.0f,
            -1.0f, -1.0f, 5.0f,
            -1.0f, 1.0f, 5.0f,
            1.0f, -1.0f, 11.0f,
            1.0f, 1.0f, 11.0f,
            1.0f, -1.0f, 5.0f,
            1.0f, 1.0f, 5.0f,
            -1.0f, 3.0f, 7.0f,
            -1.0f, 7.0f, 7.0f,
            -1.0f, 3.0f, 5.0f,
            -1.0f, 7.0f, 5.0f,
            1.0f, 3.0f, 7.0f,
            1.0f, 7.0f, 7.0f,
            1.0f, 3.0f, 5.0f,
            1.0f, 7.0f, 5.0f,
            -1.0f, 5.0f, 1.0f,
            -1.0f, 11.0f, 1.0f,
            -1.0f, 5.0f, -1.0f,
            -1.0f, 11.0f, -1.0f,
            1.0f, 5.0f, 1.0f,
            1.0f, 11.0f, 1.0f,
            1.0f, 5.0f, -1.0f,
            1.0f, 11.0f, -1.0f,
            -1.0f, 7.0f, 7.0f,
            -1.0f, 11.0f, 7.0f,
            -1.0f, 7.0f, 1.0f,
            -1.0f, 11.0f, 1.0f,
            1.0f, 7.0f, 7.0f,
            1.0f, 11.0f, 7.0f,
            1.0f, 7.0f, 1.0f,
            1.0f, 11.0f, 1.0f,
            -1.0f, 23.0f, 11.0f,
            -1.0f, 25.0f, 11.0f,
            -1.0f, 23.0f, -3.0f,
            -1.0f, 25.0f, -3.0f,
            1.0f, 23.0f, 11.0f,
            1.0f, 25.0f, 11.0f,
            1.0f, 23.0f, -3.0f,
            1.0f, 25.0f, -3.0f,
            -1.0f, 17.0f, -1.0f,
            -1.0f, 25.0f, -1.0f,
            -1.0f, 17.0f, -3.0f,
            -1.0f, 25.0f, -3.0f,
            1.0f, 17.0f, -1.0f,
            1.0f, 25.0f, -1.0f,
            1.0f, 17.0f, -3.0f,
            1.0f, 25.0f, -3.0f,
            -1.0f, 17.0f, 11.0f,
            -1.0f, 25.0f, 11.0f,
            -1.0f, 17.0f, 1.0f,
            -1.0f, 25.0f, 1.0f,
            1.0f, 17.0f, 11.0f,
            1.0f, 25.0f, 11.0f,
            1.0f, 17.0f, 1.0f,
            1.0f, 25.0f, 1.0f,
            -1.0f, 11.0f, -1.0f,
            -1.0f, 17.0f, -1.0f,
            -1.0f, 11.0f, -3.0f,
            -1.0f, 17.0f, -3.0f,
            1.0f, 11.0f, -1.0f,
            1.0f, 17.0f, -1.0f,
            1.0f, 11.0f, -3.0f,
            1.0f, 17.0f, -3.0f,
            -1.0f, 11.0f, 1.0f,
            -1.0f, 17.0f, 1.0f,
            -1.0f, 11.0f, 11.0f,
            -1.0f, 17.0f, 11.0f,
            1.0f, 11.0f, 1.0f,
            1.0f, 17.0f, 1.0f,
            1.0f, 11.0f, 11.0f,
            1.0f, 17.0f, 11.0f,
            -1.0f, 3.0f, -1.0f,
            -1.0f, 5.0f, -1.0f,
            -1.0f, 3.0f, -3.0f,
            -1.0f, 5.0f, -3.0f,
            1.0f, 3.0f, -1.0f,
            1.0f, 5.0f, -1.0f,
            1.0f, 3.0f, -3.0f,
            1.0f, 5.0f, -3.0f,
            -1.0f, 3.0f, 7.0f,
            -1.0f, 5.0f, 7.0f,
            -1.0f, 3.0f, 5.0f,
            -1.0f, 5.0f, 5.0f,
            1.0f, 3.0f, 7.0f,
            1.0f, 5.0f, 7.0f,
            1.0f, 3.0f, 5.0f,
            1.0f, 5.0f, 5.0f,
            -1.0f, -1.0f, -5.0f,
            -1.0f, 1.0f, -5.0f,
            -1.0f, -1.0f, -11.0f,
            -1.0f, 1.0f, -11.0f,
            1.0f, -1.0f, -5.0f,
            1.0f, 1.0f, -5.0f,
            1.0f, -1.0f, -11.0f,
            1.0f, 1.0f, -11.0f,
            -1.0f, 1.0f, -7.0f,
            -1.0f, 3.0f, -7.0f,
            -1.0f, 1.0f, -11.0f,
            -1.0f, 3.0f, -11.0f,
            1.0f, 1.0f, -7.0f,
            1.0f, 3.0f, -7.0f,
            1.0f, 1.0f, -11.0f,
            1.0f, 3.0f, -11.0f,
            -1.0f, 7.0f, -1.0f,
            -1.0f, 11.0f, -1.0f,
            -1.0f, 7.0f, -5.0f,
            -1.0f, 11.0f, -5.0f,
            1.0f, 7.0f, -1.0f,
            1.0f, 11.0f, -1.0f,
            1.0f, 7.0f, -5.0f,
            1.0f, 11.0f, -5.0f,
            -1.0f, 7.0f, -7.0f,
            -1.0f, 11.0f, -7.0f,
            -1.0f, 7.0f, -11.0f,
            -1.0f, 11.0f, -11.0f,
            1.0f, 7.0f, -7.0f,
            1.0f, 11.0f, -7.0f,
            1.0f, 7.0f, -11.0f,
            1.0f, 11.0f, -11.0f,
            -1.0f, 5.0f, 3.0f,
            -1.0f, 11.0f, 3.0f,
            -1.0f, 5.0f, -3.0f,
            -1.0f, 11.0f, -3.0f,
            1.0f, 5.0f, 3.0f,
            1.0f, 11.0f, 3.0f,
            1.0f, 5.0f, -3.0f,
            1.0f, 11.0f, -3.0f,
            -1.0f, 7.0f, 3.0f,
            -1.0f, 11.0f, 3.0f,
            -1.0f, 7.0f, -3.0f,
            -1.0f, 11.0f, -3.0f,
            1.0f, 7.0f, 3.0f,
            1.0f, 11.0f, 3.0f,
            1.0f, 7.0f, -3.0f,
            1.0f, 11.0f, -3.0f,
            -1.0f, -1.0f, 7.0f,
            -1.0f, 1.0f, 7.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 7.0f,
            1.0f, 1.0f, 7.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -3.0f,
            -1.0f, 1.0f, -3.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, -3.0f,
            1.0f, 1.0f, -3.0f,
            -1.0f, -3.0f, -7.0f,
            -1.0f, -1.0f, -7.0f,
            -1.0f, -3.0f, -11.0f,
            -1.0f, -1.0f, -11.0f,
            1.0f, -3.0f, -7.0f,
            1.0f, -1.0f, -7.0f,
            1.0f, -3.0f, -11.0f,
            1.0f, -1.0f, -11.0f,
            -1.0f, -7.0f, -7.0f,
            -1.0f, -3.0f, -7.0f,
            -1.0f, -7.0f, -11.0f,
            -1.0f, -3.0f, -11.0f,
            1.0f, -7.0f, -7.0f,
            1.0f, -3.0f, -7.0f,
            1.0f, -7.0f, -11.0f,
            1.0f, -3.0f, -11.0f,
            -1.0f, -7.0f, -3.0f,
            -1.0f, -1.0f, -3.0f,
            -1.0f, -7.0f, -5.0f,
            -1.0f, -1.0f, -5.0f,
            1.0f, -7.0f, -3.0f,
            1.0f, -1.0f, -3.0f,
            1.0f, -7.0f, -5.0f,
            1.0f, -1.0f, -5.0f,
            -1.0f, -7.0f, 7.0f,
            -1.0f, -3.0f, 7.0f,
            -1.0f, -7.0f, 3.0f,
            -1.0f, -3.0f, 3.0f,
            1.0f, -7.0f, 7.0f,
            1.0f, -3.0f, 7.0f,
            1.0f, -7.0f, 3.0f,
            1.0f, -3.0f, 3.0f,
            -1.0f, -11.0f, 7.0f,
            -1.0f, -3.0f, 7.0f,
            -1.0f, -11.0f, 3.0f,
            -1.0f, -3.0f, 3.0f,
            1.0f, -11.0f, 7.0f,
            1.0f, -3.0f, 7.0f,
            1.0f, -11.0f, 3.0f,
            1.0f, -3.0f, 3.0f,
            -1.0f, -11.0f, -7.0f,
            -1.0f, -7.0f, -7.0f,
            -1.0f, -11.0f, -11.0f,
            -1.0f, -7.0f, -11.0f,
            1.0f, -11.0f, -7.0f,
            1.0f, -7.0f, -7.0f,
            1.0f, -11.0f, -11.0f,
            1.0f, -7.0f, -11.0f,
            -1.0f, -17.0f, -5.0f,
            -1.0f, -11.0f, -5.0f,
            -1.0f, -17.0f, -7.0f,
            -1.0f, -11.0f, -7.0f,
            1.0f, -17.0f, -5.0f,
            1.0f, -11.0f, -5.0f,
            1.0f, -17.0f, -7.0f,
            1.0f, -11.0f, -7.0f,
            -1.0f, -17.0f, -1.0f,
            -1.0f, -11.0f, -1.0f,
            -1.0f, -17.0f, -3.0f,
            -1.0f, -11.0f, -3.0f,
            1.0f, -17.0f, -1.0f,
            1.0f, -11.0f, -1.0f,
            1.0f, -17.0f, -3.0f,
            1.0f, -11.0f, -3.0f,
            -1.0f, -11.0f, 3.0f,
            -1.0f, -7.0f, 3.0f,
            -1.0f, -11.0f, -1.0f,
            -1.0f, -7.0f, -1.0f,
            1.0f, -11.0f, 3.0f,
            1.0f, -7.0f, 3.0f,
            1.0f, -11.0f, -1.0f,
            1.0f, -7.0f, -1.0f,
            -1.0f, -1.0f, -3.0f,
            -1.0f, 1.0f, -3.0f,
            -1.0f, -1.0f, -5.0f,
            -1.0f, 1.0f, -5.0f,
            1.0f, -1.0f, -3.0f,
            1.0f, 1.0f, -3.0f,
            1.0f, -1.0f, -5.0f,
            1.0f, 1.0f, -5.0f,
            -1.0f, -1.0f, 7.0f,
            -1.0f, 1.0f, 7.0f,
            -1.0f, -1.0f, 3.0f,
            -1.0f, 1.0f, 3.0f,
            1.0f, -1.0f, 7.0f,
            1.0f, 1.0f, 7.0f,
            1.0f, -1.0f, 3.0f,
            1.0f, 1.0f, 3.0f,
            -1.0f, 1.0f, 5.0f,
            -1.0f, 3.0f, 5.0f,
            -1.0f, 1.0f, 3.0f,
            -1.0f, 3.0f, 3.0f,
            1.0f, 1.0f, 5.0f,
            1.0f, 3.0f, 5.0f,
            1.0f, 1.0f, 3.0f,
            1.0f, 3.0f, 3.0f,
            -1.0f, 3.0f, -7.0f,
            -1.0f, 5.0f, -7.0f,
            -1.0f, 3.0f, -11.0f,
            -1.0f, 5.0f, -11.0f,
            1.0f, 3.0f, -7.0f,
            1.0f, 5.0f, -7.0f,
            1.0f, 3.0f, -11.0f,
            1.0f, 5.0f, -11.0f,
            -1.0f, 3.0f, -3.0f,
            -1.0f, 5.0f, -3.0f,
            -1.0f, 3.0f, -7.0f,
            -1.0f, 5.0f, -7.0f,
            1.0f, 3.0f, -3.0f,
            1.0f, 5.0f, -3.0f,
            1.0f, 3.0f, -7.0f,
            1.0f, 5.0f, -7.0f,
            -1.0f, -1.0f, 3.0f,
            -1.0f, 3.0f, 3.0f,
            -1.0f, -1.0f, -3.0f,
            -1.0f, 3.0f, -3.0f,
            1.0f, -1.0f, 3.0f,
            1.0f, 3.0f, 3.0f,
            1.0f, -1.0f, -3.0f,
            1.0f, 3.0f, -3.0f,
    };

    private static final short[] alienModelFaces = {
            2, 3, 1,
            2, 4, 3,
            6, 5, 7,
            8, 7, 5,
            10, 11, 9,
            10, 12, 11,
            14, 13, 15,
            16, 15, 13,
            18, 19, 17,
            18, 20, 19,
            22, 21, 23,
            24, 23, 21,
            25, 27, 26,
            25, 28, 27,
            30, 29, 31,
            32, 31, 29,
            33, 35, 34,
            33, 36, 35,
            38, 37, 39,
            40, 39, 37,
            42, 43, 41,
            42, 44, 43,
            46, 45, 47,
            48, 47, 45,
            50, 51, 49,
            50, 52, 51,
            54, 53, 55,
            56, 55, 53,
            58, 59, 57,
            58, 60, 59,
            62, 61, 63,
            64, 63, 61,
            66, 67, 65,
            66, 68, 67,
            70, 69, 71,
            72, 71, 69,
            74, 75, 73,
            74, 76, 75,
            78, 77, 79,
            80, 79, 77,
            82, 83, 81,
            82, 84, 83,
            86, 85, 87,
            88, 87, 85,
            90, 91, 89,
            90, 92, 91,
            94, 93, 95,
            96, 95, 93,
            98, 99, 97,
            98, 100, 99,
            102, 101, 103,
            104, 103, 101,
            106, 107, 105,
            106, 108, 107,
            110, 109, 111,
            112, 111, 109,
            114, 115, 113,
            114, 116, 115,
            118, 117, 119,
            120, 119, 117,
            122, 123, 121,
            122, 124, 123,
            126, 125, 127,
            128, 127, 125,
            130, 131, 129,
            130, 132, 131,
            134, 133, 135,
            136, 135, 133,
            138, 139, 137,
            138, 140, 139,
            142, 141, 143,
            144, 143, 141,
            146, 147, 145,
            146, 148, 147,
            150, 149, 151,
            152, 151, 149,
            154, 155, 153,
            154, 156, 155,
            158, 157, 159,
            160, 159, 157,
            162, 163, 161,
            162, 164, 163,
            166, 165, 167,
            168, 167, 165,
            170, 171, 169,
            170, 172, 171,
            174, 173, 175,
            176, 175, 173,
            178, 179, 177,
            178, 180, 179,
            182, 181, 183,
            184, 183, 181,
    };



    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Random random;


    public Alien(float x, float y, float size, float velocityX) {

        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityX = velocityX;
        this.color = new float[]{1.0f, 0.0f, 0.0f, 1.0f}; // Red color

        // Initialize the transformation matrix
        transformationMatrix = new float[16];
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
        Matrix.scaleM(transformationMatrix, 0, size, size, size);

        // Initialize the vertex buffer
        ByteBuffer alienVerticesBB = ByteBuffer.allocateDirect(alienModelVertices.length * 4); // 4 bytes per float
        alienVerticesBB.order(ByteOrder.nativeOrder());
        FloatBuffer alienVerticesBuffer = alienVerticesBB.asFloatBuffer();
        alienVerticesBuffer.put(alienModelVertices);
        alienVerticesBuffer.position(0);

        ByteBuffer alienFacesBB = ByteBuffer.allocateDirect(alienModelFaces.length * 2);
        alienFacesBB.order(ByteOrder.nativeOrder());
        ShortBuffer alienFacesBuffer = alienFacesBB.asShortBuffer();
        alienFacesBuffer.put(alienModelFaces);
        alienFacesBuffer.position(0);

        // Initialize projectiles and random generator
        projectiles = new ArrayList<>();
        random = new Random();
    }

    public void update(float deltaTime) {
        // Update position
        x += velocityX * deltaTime;
        //check boundaries
        if (x <= leftBoundary) {
            x = leftBoundary;
            velocityX *= -1;
            y -= rowDown;
        }
        if (x >= rightBoundary) {
            x = rightBoundary;
            velocityX *= -1;
            y -= rowDown;
        }
        // Update transformation matrix with new position
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
        Matrix.scaleM(transformationMatrix, 0, size, size, size);

        List<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            projectile.updateProjectile(deltaTime);
            if (projectile.isOutOfBounds()) {
                projectilesToRemove.add(projectile);
            }
        }
        projectiles.removeAll(projectilesToRemove);

        // Randomly shoot a projectile
        if (random.nextInt(8000) < 10) { // 1/100 chance every frame
            shoot();
        }
    }

    public void shoot() {
        float[] color = {1.0f, 0.0f, 0.0f, 1.0f};
        projectiles.add(new Projectile(x, y, Direction.DOWN, color));
    }

    @Override
    public void draw(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();  // Save the current model view matrix

        // Apply the transformation matrix
        gl.glMultMatrixf(transformationMatrix, 0);

        // Set the color of the alien
        gl.glColor4f(color[0], color[1], color[2], color[3]);

        // Enable vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, alienVerticesBuffer);

        // Draw the cube faces using GL_TRIANGLES for simplicity
        for (int i = 0; i < (alienModelFaces.length / 3); i += 4) {
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i, 4);
        }

        // Disable vertex arrays
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glPopMatrix();  // Restore the previous model view matrix

        for (Projectile projectile : projectiles) {
            projectile.draw(gl);
        }
    }
    public float getX() {
        return transformationMatrix[12];
    }
    public float getY() {
        return transformationMatrix[13];
    }
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
