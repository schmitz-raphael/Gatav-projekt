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
    private float[] transformationMatrix;
    private static final float leftBoundary = -2.4f;
    private static final float rightBoundary = 2.4f;
    private static final float rowDown = 0.5f;

    private static final float[] alienModelVertices = {
            4.0f, -5.0f, -4.0f,
            4.0f, -7.0f, -4.0f,
            4.0f, -5.0f, 4.0f,
            4.0f, -7.0f, 4.0f,
            -4.0f, -5.0f, -4.0f,
            -4.0f, -7.0f, -4.0f,
            -4.0f, -5.0f, 4.0f,
            -4.0f, -7.0f, 4.0f,
            6.0f, 3.0f, -6.0f,
            6.0f, -3.0f, -6.0f,
            6.0f, 3.0f, 6.0f,
            6.0f, -3.0f, 6.0f,
            -6.0f, 3.0f, -6.0f,
            -6.0f, -3.0f, -6.0f,
            -6.0f, 3.0f, 6.0f,
            -6.0f, -3.0f, 6.0f,
            5.0f, -3.0f, -5.0f,
            5.0f, -5.0f, -5.0f,
            5.0f, -3.0f, 5.0f,
            5.0f, -5.0f, 5.0f,
            -5.0f, -3.0f, -5.0f,
            -5.0f, -5.0f, -5.0f,
            -5.0f, -3.0f, 5.0f,
            -5.0f, -5.0f, 5.0f,
            5.0f, 5.0f, -5.0f,
            5.0f, 3.0f, -5.0f,
            5.0f, 5.0f, 5.0f,
            5.0f, 3.0f, 5.0f,
            -5.0f, 5.0f, -5.0f,
            -5.0f, 3.0f, -5.0f,
            -5.0f, 5.0f, 5.0f,
            -5.0f, 3.0f, 5.0f,
            4.0f, 6.0f, -4.0f,
            4.0f, 4.0f, -4.0f,
            4.0f, 6.0f, 4.0f,
            4.0f, 4.0f, 4.0f,
            -4.0f, 6.0f, -4.0f,
            -4.0f, 4.0f, -4.0f,
            -4.0f, 6.0f, 4.0f,
            -4.0f, 4.0f, 4.0f,
            3.0f, -7.0f, -3.0f,
            3.0f, -9.0f, -3.0f,
            3.0f, -7.0f, 3.0f,
            3.0f, -9.0f, 3.0f,
            -3.0f, -7.0f, -3.0f,
            -3.0f, -9.0f, -3.0f,
            -3.0f, -7.0f, 3.0f,
            -3.0f, -9.0f, 3.0f,
            -1.0f, -12.0f, 1.0f,
            -1.0f, -8.0f, 1.0f,
            -1.0f, -12.0f, -1.0f,
            -1.0f, -8.0f, -1.0f,
            1.0f, -12.0f, 1.0f,
            1.0f, -8.0f, 1.0f,
            1.0f, -12.0f, -1.0f,
            1.0f, -8.0f, -1.0f,
            7.0f, 4.0f, 0.5f,
            7.0f, 5.0f, 0.5f,
            7.0f, 4.0f, -0.5f,
            7.0f, 5.0f, -0.5f,
            8.0f, 4.0f, 0.5f,
            8.0f, 5.0f, 0.5f,
            8.0f, 4.0f, -0.5f,
            8.0f, 5.0f, -0.5f,
            -7.0f, 3.0f, 0.5f,
            -7.0f, 4.0f, 0.5f,
            -7.0f, 3.0f, -0.5f,
            -7.0f, 4.0f, -0.5f,
            -6.0f, 3.0f, 0.5f,
            -6.0f, 4.0f, 0.5f,
            -6.0f, 3.0f, -0.5f,
            -6.0f, 4.0f, -0.5f,
            6.0f, 3.0f, 0.5f,
            6.0f, 4.0f, 0.5f,
            6.0f, 3.0f, -0.5f,
            6.0f, 4.0f, -0.5f,
            7.0f, 3.0f, 0.5f,
            7.0f, 4.0f, 0.5f,
            7.0f, 3.0f, -0.5f,
            7.0f, 4.0f, -0.5f,
            -9.5f, 4.5f, 1.0f,
            -9.5f, 6.5f, 1.0f,
            -9.5f, 4.5f, -1.0f,
            -9.5f, 6.5f, -1.0f,
            -7.5f, 6.5f, 1.0f,
            -7.5f, 6.5f, -1.0f,
            -8.5f, 5.5f, -1.0f,
            -8.5f, 4.5f, -1.0f,
            -7.5f, 5.5f, -1.0f,
            -8.5f, 5.5f, 1.0f,
            -7.5f, 5.5f, 1.0f,
            -8.5f, 4.5f, 1.0f,
            7.5f, 6.5f, 1.0f,
            7.5f, 6.5f, -1.0f,
            9.5f, 4.5f, 1.0f,
            9.5f, 6.5f, 1.0f,
            9.5f, 4.5f, -1.0f,
            9.5f, 6.5f, -1.0f,
            8.5f, 5.5f, -1.0f,
            7.5f, 5.5f, -1.0f,
            8.5f, 4.5f, -1.0f,
            8.5f, 5.5f, 1.0f,
            7.5f, 5.5f, 1.0f,
            8.5f, 4.5f, 1.0f,
            -8.0f, 4.0f, 0.5f,
            -8.0f, 5.0f, 0.5f,
            -8.0f, 4.0f, -0.5f,
            -8.0f, 5.0f, -0.5f,
            -7.0f, 4.0f, 0.5f,
            -7.0f, 5.0f, 0.5f,
            -7.0f, 4.0f, -0.5f,
            -7.0f, 5.0f, -0.5f,
            -4.0f, -1.5f, -4.1f,
            -4.0f, 1.5f, -4.1f,
            -4.0f, -1.5f, -6.1f,
            -4.0f, 1.5f, -6.1f,
            -1.0f, -1.5f, -4.1f,
            -1.0f, 1.5f, -4.1f,
            -1.0f, -1.5f, -6.1f,
            -1.0f, 1.5f, -6.1f,
            1.0f, -1.5f, -4.1f,
            1.0f, 1.5f, -4.1f,
            1.0f, -1.5f, -6.1f,
            1.0f, 1.5f, -6.1f,
            4.0f, -1.5f, -4.1f,
            4.0f, 1.5f, -4.1f,
            4.0f, -1.5f, -6.1f,
            4.0f, 1.5f, -6.1f,
            -2.75f, 0.0f, -5.11f,
            -2.75f, 1.0f, -5.11f,
            -2.75f, 0.0f, -6.11f,
            -2.75f, 1.0f, -6.11f,
            -1.75f, 0.0f, -5.11f,
            -1.75f, 1.0f, -5.11f,
            -1.75f, 0.0f, -6.11f,
            -1.75f, 1.0f, -6.11f,
            6.0f, 3.0f, 0.5f,
            6.0f, 4.0f, 0.5f,
            6.0f, 3.0f, -0.5f,
            6.0f, 4.0f, -0.5f,
            7.0f, 3.0f, 0.5f,
            7.0f, 4.0f, 0.5f,
            7.0f, 3.0f, -0.5f,
            7.0f, 4.0f, -0.5f,
            1.5f, 0.0f, -5.11f,
            1.5f, 1.0f, -5.11f,
            1.5f, 0.0f, -6.11f,
            1.5f, 1.0f, -6.11f,
            2.5f, 0.0f, -5.11f,
            2.5f, 1.0f, -5.11f,
            2.5f, 0.0f, -6.11f,
            2.5f, 1.0f, -6.11f,
    };

    private static final short[] alienModelFaces = {
            0, 4, 6, 2, 3, 2, 6, 7, 7, 6, 4, 5, 5, 1, 3, 7, 5, 4, 0, 1, 7, 3, 0, 1,
            8, 12, 14, 10, 11, 10, 14, 15, 15, 14, 12, 13, 13, 9, 11, 15, 13, 12, 8, 9, 15, 11, 8, 9,
            16, 20, 22, 18, 19, 18, 22, 23, 23, 22, 20, 21, 21, 17, 19, 23, 21, 20, 16, 17, 23, 19, 16, 17,
            24, 28, 30, 26, 27, 26, 30, 31, 31, 30, 28, 29, 29, 25, 27, 31, 29, 28, 24, 25, 31, 27, 24, 25,
            32, 36, 38, 34, 35, 34, 38, 39, 39, 38, 36, 37, 37, 33, 35, 39, 37, 36, 32, 33, 39, 35, 32, 33,
            40, 44, 46, 42, 43, 42, 46, 47, 47, 46, 44, 45, 45, 41, 43, 47, 45, 44, 40, 41, 47, 43, 40, 41,
            48, 50, 52, 51, 51, 52, 56, 55, 55, 56, 54, 53, 53, 54, 50, 49, 51, 55, 53, 49, 56, 52, 50, 54,
            57, 58, 60, 59, 59, 60, 64, 63, 63, 64, 62, 61, 61, 62, 58, 57, 59, 63, 61, 57, 64, 60, 58, 62,
            65, 66, 68, 67, 67, 68, 72, 71, 71, 72, 70, 69, 69, 70, 66, 65, 67, 71, 69, 65, 72, 68, 66, 70,
            73, 74, 76, 75, 75, 76, 80, 79, 79, 80, 78, 77, 77, 78, 74, 73, 75, 79, 77, 73, 80, 76, 74, 78,
            81, 82, 84, 83, 83, 84, 86, 89, 87, 88, 89, 86, 85, 91, 91, 85, 82, 81, 92, 90, 92, 81, 83, 88, 86, 84, 82, 85, 88, 87, 90, 92, 91, 90, 87, 89,
            93, 94, 100, 103, 100, 94, 98, 97, 101, 99, 97, 98, 96, 95, 95, 96, 93, 103, 102, 104, 102, 103, 97, 95, 104, 101, 98, 94, 93, 96, 102, 99, 101, 104, 100, 99, 102, 103,
            105, 106, 108, 107, 107, 108, 112, 111, 111, 112, 110, 109, 109, 110, 106, 105, 107, 111, 109, 105, 112, 108, 106, 110,
            113, 114, 116, 115, 115, 116, 120, 119, 119, 120, 118, 117, 117, 118, 114, 113, 115, 119, 117, 113, 120, 116, 114, 118,
            121, 122, 124, 123, 123, 124, 128, 127, 127, 128, 126, 125, 125, 126, 122, 121, 123, 127, 125, 121, 128, 124, 122, 126,
            129, 130, 132, 131, 131, 132, 136, 135, 135, 136, 134, 133, 133, 134, 130, 129, 131, 135, 133, 129, 136, 132, 130, 134,
            137, 138, 140, 139, 139, 140, 144, 143, 143, 144, 142, 141, 141, 142, 138, 137, 139, 143, 141, 137, 144, 140, 138, 142,
            145, 146, 148, 147, 147, 148, 152, 151, 151, 152, 150, 149, 149, 150, 146, 145, 147, 151, 149, 145, 152, 148, 146, 150,
    };

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Random random;


    public Alien(float x, float y, float size, float velocityX) {

        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityX = velocityX;

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

        // Draw the cube faces using GL_TRIANGLES for simplicity
        for (int i = 0; i < (alienModelFaces.length / 3); i++) {
            alienFacesBuffer.position(3 * i);
            gl.glDrawElements(GL10.GL_LINE_LOOP, 3,GL10.GL_UNSIGNED_SHORT, alienFacesBuffer);
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
