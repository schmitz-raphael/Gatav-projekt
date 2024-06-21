package hskl.de.projekt.Objects;

import android.opengl.Matrix;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Alien implements Drawable {
    private float x, y, size;
    private float velocityX;
    private int lives;
    private FloatBuffer vertexBuffer;
    private float[] color;
    private float[] transformationMatrix;
    private static final float leftBoundary = -2.4f;
    private static final float rightBoundary = 2.4f;
    private static final float rowDown = 0.5f;

    private float[] vertices = {
            -1.000000f, 5.000000f, 9.000000f,
            -1.000000f, 5.000000f, 7.000000f,
            1.000000f, 5.000000f, 7.000000f,
            1.000000f, 5.000000f, 9.000000f,
            -1.000000f, 3.000000f, 9.000000f,
            -1.000000f, 3.000000f, 7.000000f,
            1.000000f, 3.000000f, 7.000000f,
            1.000000f, 3.000000f, 9.000000f,
            -1.000000f, 1.000000f, 9.000000f,
            -1.000000f, 1.000000f, 7.000000f,
            1.000000f, 1.000000f, 7.000000f,
            1.000000f, 1.000000f, 9.000000f,
            -1.000000f, -1.000000f, 9.000000f,
            -1.000000f, -1.000000f, 7.000000f,
            1.000000f, -1.000000f, 7.000000f,
            1.000000f, -1.000000f, 9.000000f,
    };

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Random random;

    public Alien(float x, float y, float size, float velocityX, int lives) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityX = velocityX;
        this.lives = lives;
        this.color = new float[]{1.0f, 0.0f, 0.0f, 1.0f}; // Red color

        // Initialize the transformation matrix
        transformationMatrix = new float[16];
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
        Matrix.scaleM(transformationMatrix, 0, size, size, size);

        // Initialize the vertex buffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4); // 4 bytes per float
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

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
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // Draw the cube faces using GL_TRIANGLES for simplicity
        for (int i = 0; i < vertices.length / 3; i += 4) {
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i, 4);
        }

        // Disable vertex arrays
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glPopMatrix();  // Restore the previous model view matrix

        for (Projectile projectile : projectiles) {
            projectile.draw(gl);
        }
    }
}
