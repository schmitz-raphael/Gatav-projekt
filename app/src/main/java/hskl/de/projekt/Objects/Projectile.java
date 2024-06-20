package hskl.de.projekt.Objects;

import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Projectile implements Drawable {
    private float x;
    private float y;

    private Direction direction;

    // form of a narrow rectangle
    private float[] vertices = {
            // Front face
            -0.01f, -0.05f,  0.05f,
            0.01f, -0.05f,  0.05f,
            -0.01f,  0.05f,  0.05f,
            0.01f,  0.05f,  0.05f,
            // Back face
            -0.01f, -0.05f, -0.05f,
            -0.01f,  0.05f, -0.05f,
            0.01f, -0.05f, -0.05f,
            0.01f,  0.05f, -0.05f,
            // Left face
            -0.01f, -0.05f, -0.05f,
            -0.01f, -0.05f,  0.05f,
            -0.01f,  0.05f, -0.05f,
            -0.01f,  0.05f,  0.05f,
            // Right face
            0.01f, -0.05f, -0.05f,
            0.01f, -0.05f,  0.05f,
            0.01f,  0.05f, -0.05f,
            0.01f,  0.05f,  0.05f,
            // Top face
            -0.01f,  0.05f, -0.05f,
            0.01f,  0.05f, -0.05f,
            -0.01f,  0.05f,  0.05f,
            0.01f,  0.05f,  0.05f,
            // Bottom face
            -0.01f, -0.05f, -0.05f,
            -0.01f, -0.05f,  0.05f,
            0.01f, -0.05f, -0.05f,
            0.01f, -0.05f,  0.05f
    };

    private float[] color;

    private FloatBuffer vertexBuffer;

    public float[] transformationMatrix;
    private float leftBoundary = -3.0f;
    private float rightBoundary = 3.0f;
    private float bottomBoundary = -4.0f;
    private float topBoundary = 5.0f;

    public Projectile(float x, float y, Direction direction, float[] color) {
        // initialize the position
        this.x = x;
        this.y = y;
        //initialize the direction of the projectile
        this.direction = direction;
        //initialize the color
        this.color = color;

        // initialize the transformation matrix and set its identity
        transformationMatrix = new float[16];
        Matrix.setIdentityM(transformationMatrix, 0);

        // Initialize the vertex buffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4); // 4 bytes per float
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    /**
     * Draws the projectile on the screen
     * @param gl -> takes an instance of the GL10 interface as parameter and draws the projectile on there
     */
    @Override
    public void draw(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();  // Save the current model view matrix

        // Apply the transformation matrix
        gl.glMultMatrixf(transformationMatrix, 0);

        // Set the color of the projectile
        gl.glColor4f(color[0], color[1], color[2], color[3]);

        // Enable vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // Draw the rectangle faces using GL_TRIANGLE_STRIP for simplicity
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);

        // Disable vertex arrays
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glPopMatrix();  // Restore the previous model view matrix
    }

    /**
     * updates the projectile position based on the elapsed time since the last call of the function
     * @param fracSec: float -> elapsed time in seconds
     */
    public void updateProjectile(float fracSec) {
        //use a projectile speed to accelerate the particles
        float PROJECTILESPEED = 5;
        //adjust the position
        switch (direction) {
            case UP:
                y += PROJECTILESPEED * fracSec;
                break;
            case DOWN:
                y -= PROJECTILESPEED * fracSec;
                break;
        }
        //reset the matrix identity and translate it
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
    }

    /**
     * checks if the projectile is out of bounds
     * @return true if out of bounds, false otherwise
     */
    public boolean isOutOfBounds() {
        return x < leftBoundary || x > rightBoundary || y < bottomBoundary || y > topBoundary;
    }
}
