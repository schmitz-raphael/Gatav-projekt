package hskl.de.projekt.Objects.Aliens;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import hskl.de.projekt.Objects.Direction;
import hskl.de.projekt.Objects.Drawable;
import hskl.de.projekt.Objects.Projectile;

public class Alien2 extends AlienMaster implements Drawable {
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
            -1.000000f, -1.000000f, -1.000000f,
            -1.000000f, -1.000000f, 1.000000f,
            -1.000000f, 1.000000f, -1.000000f,
            -1.000000f, 1.000000f, 1.000000f,
            1.000000f, -1.000000f, -1.000000f,
            1.000000f, -1.000000f, 1.000000f,
            1.000000f, 1.000000f, -1.000000f,
            1.000000f, 1.000000f, 1.000000f,
            1.000000f, -1.100000f, -3.333333f,
            1.000000f, -1.100000f, -4.666667f,
            1.000000f, -9.100000f, -3.333333f,
            1.000000f, -9.100000f, -4.666667f,
            -4.333333f, -1.100000f, -3.333333f,
            -4.333333f, -1.100000f, -4.666667f,
            -4.333333f, -9.100000f, -3.333333f,
            -4.333333f, -9.100000f, -4.666667f,
            2.333333f, 0.900000f, 2.000000f,
            2.333333f, 0.900000f, -2.000000f,
            2.333333f, -11.100000f, 2.000000f,
            2.333333f, -11.100000f, -2.000000f,
            -5.666667f, 0.900000f, 2.000000f,
            -5.666667f, 0.900000f, -2.000000f,
            -5.666667f, -11.100000f, 2.000000f,
            -5.666667f, -11.100000f, -2.000000f,
            1.666667f, -0.100000f, -2.000000f,
            1.666667f, -0.100000f, -3.333333f,
            1.666667f, -10.100000f, -2.000000f,
            1.666667f, -10.100000f, -3.333333f,
            -5.000000f, -0.100000f, -2.000000f,
            -5.000000f, -0.100000f, -3.333333f,
            -5.000000f, -10.100000f, -2.000000f,
            -5.000000f, -10.100000f, -3.333333f,
            1.666667f, -0.100000f, 3.333333f,
            1.666667f, -0.100000f, 2.000000f,
            1.666667f, -10.100000f, 3.333333f,
            1.666667f, -10.100000f, 2.000000f,
            -5.000000f, -0.100000f, 3.333333f,
            -5.000000f, -0.100000f, 2.000000f,
            -5.000000f, -10.100000f, 3.333333f,
            -5.000000f, -10.100000f, 2.000000f,
            1.000000f, -1.100000f, 4.000000f,
            1.000000f, -1.100000f, 2.666667f,
            1.000000f, -9.100000f, 4.000000f,
            1.000000f, -9.100000f, 2.666667f,
            -4.333333f, -1.100000f, 4.000000f,
            -4.333333f, -1.100000f, 2.666667f,
            -4.333333f, -9.100000f, 4.000000f,
            -4.333333f, -9.100000f, 2.666667f,
            0.333333f, -2.100000f, -4.666667f,
            0.333333f, -2.100000f, -6.000000f,
            0.333333f, -8.100000f, -4.666667f,
            0.333333f, -8.100000f, -6.000000f,
            -3.666667f, -2.100000f, -4.666667f,
            -3.666667f, -2.100000f, -6.000000f,
            -3.666667f, -8.100000f, -4.666667f,
            -3.666667f, -8.100000f, -6.000000f,
            -2.333333f, -6.100000f, -8.000000f,
            -2.333333f, -6.100000f, -5.333333f,
            -2.333333f, -4.100000f, -8.000000f,
            -2.333333f, -4.100000f, -5.333333f,
            -1.000000f, -6.100000f, -8.000000f,
            -1.000000f, -6.100000f, -5.333333f,
            -1.000000f, -4.100000f, -8.000000f,
            -1.000000f, -4.100000f, -5.333333f,
            3.000000f, -5.600000f, 2.666667f,
            3.000000f, -5.600000f, 3.333333f,
            3.000000f, -4.600000f, 2.666667f,
            3.000000f, -4.600000f, 3.333333f,
            3.666667f, -5.600000f, 2.666667f,
            3.666667f, -5.600000f, 3.333333f,
            3.666667f, -4.600000f, 2.666667f,
            3.666667f, -4.600000f, 3.333333f,
            -6.333333f, -5.600000f, 2.000000f,
            -6.333333f, -5.600000f, 2.666667f,
            -6.333333f, -4.600000f, 2.000000f,
            -6.333333f, -4.600000f, 2.666667f,
            -5.666667f, -5.600000f, 2.000000f,
            -5.666667f, -5.600000f, 2.666667f,
            -5.666667f, -4.600000f, 2.000000f,
            -5.666667f, -4.600000f, 2.666667f,
            2.333334f, -5.600000f, 2.000000f,
            2.333334f, -5.600000f, 2.666667f,
            2.333334f, -4.600000f, 2.000000f,
            2.333334f, -4.600000f, 2.666667f,
            3.000000f, -5.600000f, 2.000000f,
            3.000000f, -5.600000f, 2.666667f,
            3.000000f, -4.600000f, 2.000000f,
            3.000000f, -4.600000f, 2.666667f,
            -8.000000f, -6.100000f, 3.000000f,
            -8.000000f, -6.100000f, 4.333333f,
            -8.000000f, -4.100000f, 3.000000f,
            -8.000000f, -4.100000f, 4.333333f,
            -6.666667f, -6.100000f, 3.000000f,
            -6.666667f, -6.100000f, 4.333333f,
            -6.666667f, -4.100000f, 3.000000f,
            -6.666667f, -4.100000f, 4.333333f,
            3.333334f, -6.100000f, 3.000000f,
            3.333334f, -6.100000f, 4.333333f,
            3.333334f, -4.100000f, 3.000000f,
            3.333334f, -4.100000f, 4.333333f,
            4.666667f, -6.100000f, 3.000000f,
            4.666667f, -6.100000f, 4.333333f,
            4.666667f, -4.100000f, 3.000000f,
            4.666667f, -4.100000f, 4.333333f,
            -7.000000f, -5.600000f, 2.666667f,
            -7.000000f, -5.600000f, 3.333333f,
            -7.000000f, -4.600000f, 2.666667f,
            -7.000000f, -4.600000f, 3.333333f,
            -6.333333f, -5.600000f, 2.666667f,
            -6.333333f, -5.600000f, 3.333333f,
            -6.333333f, -4.600000f, 2.666667f,
            -6.333333f, -4.600000f, 3.333333f,
            -4.333333f, -1.000000f, -1.000000f,
            -4.333333f, -1.000000f, 1.000000f,
            -4.333333f, 1.000000f, -1.000000f,
            -4.333333f, 1.000000f, 1.000000f,
            -2.333333f, -1.000000f, -1.000000f,
            -2.333333f, -1.000000f, 1.000000f,
            -2.333333f, 1.000000f, -1.000000f,
            -2.333333f, 1.000000f, 1.000000f,
            -3.500000f, 0.010000f, 0.000000f,
            -3.500000f, 0.010000f, 0.666667f,
            -3.500000f, 1.010000f, 0.000000f,
            -3.500000f, 1.010000f, 0.666667f,
            -2.833333f, 0.010000f, 0.000000f,
            -2.833333f, 0.010000f, 0.666667f,
            -2.833333f, 1.010000f, 0.000000f,
            -2.833333f, 1.010000f, 0.666667f,
            2.333334f, -5.600000f, 2.000000f,
            2.333334f, -5.600000f, 2.666667f,
            2.333334f, -4.600000f, 2.000000f,
            2.333334f, -4.600000f, 2.666667f,
            3.000000f, -5.600000f, 2.000000f,
            3.000000f, -5.600000f, 2.666667f,
            3.000000f, -4.600000f, 2.000000f,
            3.000000f, -4.600000f, 2.666667f,
            -0.666667f, 0.010000f, 0.000000f,
            -0.666667f, 0.010000f, 0.666667f,
            -0.666667f, 1.010000f, 0.000000f,
            -0.666667f, 1.010000f, 0.666667f,
            0.000000f, 0.010000f, 0.000000f,
            0.000000f, 0.010000f, 0.666667f,
            0.000000f, 1.010000f, 0.000000f,
            0.000000f, 1.010000f, 0.666667f

    };

    private static final short[] alienModelFaces = {
            0, 1, 3, 2,
            2, 3, 7, 6,
            6, 7, 5, 4,
            4, 5, 1, 0,
            2, 6, 4, 0,
            7, 3, 1, 5,
            8, 12, 14, 10,
            11, 10, 14, 15,
            15, 14, 12, 13,
            13, 9, 11, 15,
            9, 8, 10, 11,
            13, 12, 8, 9,
            16, 20, 22, 18,
            19, 18, 22, 23,
            23, 22, 20, 21,
            21, 17, 19, 23,
            17, 16, 18, 19,
            21, 20, 16, 17,
            24, 28, 30, 26,
            27, 26, 30, 31,
            31, 30, 28, 29,
            29, 25, 27, 31,
            25, 24, 26, 27,
            29, 28, 24, 25,
            32, 36, 38, 34,
            35, 34, 38, 39,
            39, 38, 36, 37,
            37, 33, 35, 39,
            33, 32, 34, 35,
            37, 36, 32, 33,
            40, 44, 46, 42,
            43, 42, 46, 47,
            47, 46, 44, 45,
            45, 41, 43, 47,
            41, 40, 42, 43,
            45, 44, 40, 41,
            48, 52, 54, 50,
            51, 50, 54, 55,
            55, 54, 52, 53,
            53, 49, 51, 55,
            49, 48, 50, 51,
            53, 52, 48, 49,
            56, 57, 59, 58,
            58, 59, 63, 62,
            62, 63, 61, 60,
            60, 61, 57, 56,
            58, 62, 60, 56,
            63, 59, 57, 61,
            64, 65, 67, 66,
            66, 67, 71, 70,
            70, 71, 69, 68,
            68, 69, 65, 64,
            66, 70, 68, 64,
            71, 67, 65, 69,
            72, 73, 75, 74,
            74, 75, 79, 78,
            78, 79, 77, 76,
            76, 77, 73, 72,
            74, 78, 76, 72,
            79, 75, 73, 77,
            80, 81, 83, 82,
            82, 83, 87, 86,
            86, 87, 85, 84,
            84, 85, 81, 80,
            82, 86, 84, 80,
            87, 83, 81, 85,
            88, 89, 91, 90,
            90, 91, 95, 94,
            94, 95, 93, 92,
            92, 93, 89, 88,
            90, 94, 92, 88,
            95, 91, 89, 93,
            96, 97, 99, 98,
            98, 99, 103, 102,
            102, 103, 101, 100,
            100, 101, 97, 96,
            98, 102, 100, 96,
            103, 99, 97, 101,
            104, 105, 107, 106,
            106, 107, 111, 110,
            110, 111, 109, 108,
            108, 109, 105, 104,
            106, 110, 108, 104,
            111, 107, 105, 109,
            112, 113, 115, 114,
            114, 115, 119, 118,
            118, 119, 117, 116,
            116, 117, 113, 112,
            114, 118, 116, 112,
            119, 115, 113, 117,
            120, 121, 123, 122,
            122, 123, 127, 126,
            126, 127, 125, 124,
            124, 125, 121, 120,
            122, 126, 124, 120,
            127, 123, 121, 125,
            128, 129, 131, 130,
            130, 131, 135, 134,
            134, 135, 133, 132,
            132, 133, 129, 128,
            130, 134, 132, 128,
            135, 131, 129, 133,
            136, 137, 139, 138,
            138, 139, 143, 142,
            142, 143, 141, 140,
            140, 141, 137, 136,
            138, 142, 140, 136,
            143, 139, 137, 141
    };



    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Random random;


    public Alien2(float x, float y, float size, float velocityX) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityX = velocityX;
        this.color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};

        // Initialize the transformation matrix
        transformationMatrix = new float[16];
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
        Matrix.scaleM(transformationMatrix, 0, size, size, size);

        // Initialize the vertex buffer
        ByteBuffer alienVerticesBB = ByteBuffer.allocateDirect(alienModelVertices.length * 4);
        alienVerticesBB.order(ByteOrder.nativeOrder());
        alienVerticesBuffer = alienVerticesBB.asFloatBuffer();
        alienVerticesBuffer.put(alienModelVertices);
        alienVerticesBuffer.position(0);

        ByteBuffer alienFacesBB = ByteBuffer.allocateDirect(alienModelFaces.length * 2);
        alienFacesBB.order(ByteOrder.nativeOrder());
        alienFacesBuffer = alienFacesBB.asShortBuffer();
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
        if (random.nextInt(8000) < 10) { // 1/800 chance every frame
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

        // Enable vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, alienVerticesBuffer);

        // Scale, Rotate, Color
        gl.glScalef(0.10f,0.10f,0.10f);
        gl.glRotatef(25f,1,0,0);
        gl.glColor4f(color[0],color[1],color[2],color[3]);

        // Draw the cubes using triangles
        for (int i = 0; i < (alienModelFaces.length / 3); i++) {
            alienFacesBuffer.position(3 * i);
            gl.glDrawElements(GL10.GL_TRIANGLES, 3, GL10.GL_UNSIGNED_SHORT, alienFacesBuffer);
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
