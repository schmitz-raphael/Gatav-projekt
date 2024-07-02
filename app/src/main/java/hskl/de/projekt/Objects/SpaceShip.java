package hskl.de.projekt.Objects;

import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import hskl.de.projekt.R;


public class SpaceShip implements Drawable {
    // Coordinates of the spaceship
    private float x;
    private float y;
    // velocity
    private int lives = 0;
    private float velocity;
    // Color of the spaceship
    private float[] color;
    //transformation matrix
    public float[] transformationMatrix;
    // Vertices for a flat rectangular prism (e.g., width = 0.5, height = 0.25, depth = 0.1)
    private float[] vertices = {
            // Front face
            -0.25f, -0.125f,  0.05f,
            0.25f, -0.125f,  0.05f,
            -0.25f,  0.125f,  0.05f,
            0.25f,  0.125f,  0.05f,
            // Back face
            -0.25f, -0.125f, -0.05f,
            -0.25f,  0.125f, -0.05f,
            0.25f, -0.125f, -0.05f,
            0.25f,  0.125f, -0.05f,
            // Left face
            -0.25f, -0.125f, -0.05f,
            -0.25f, -0.125f,  0.05f,
            -0.25f,  0.125f, -0.05f,
            -0.25f,  0.125f,  0.05f,
            // Right face
            0.25f, -0.125f, -0.05f,
            0.25f, -0.125f,  0.05f,
            0.25f,  0.125f, -0.05f,
            0.25f,  0.125f,  0.05f,
            // Top face
            -0.25f,  0.125f, -0.05f,
            0.25f,  0.125f, -0.05f,
            -0.25f,  0.125f,  0.05f,
            0.25f,  0.125f,  0.05f,
            // Bottom face
            -0.25f, -0.125f, -0.05f,
            -0.25f, -0.125f,  0.05f,
            0.25f, -0.125f, -0.05f,
            0.25f, -0.125f,  0.05f
    };

    private FloatBuffer vertexBuffer;

    //define the boundaries on the left and the right
    private float leftBoundary = -2.4f;
    private float rightBoundary = 2.4f;
    // track the cooldown in a variable
    private float cooldown = 0;
    // create a list for the projectiles
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    /**
     * Constructor for the class
     * doesn't take any arguments
     */
    public SpaceShip() {
        //set the start coordinates to (0,-2)
        this.x = 0;
        this.y = -3.5f;

        //set the initial velocity to 0
        this.velocity = 0;

        //set the color to white
        this.color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};

        //initialize the transformation matrix to an empty array of length 16
        transformationMatrix = new float[16];
        //set the matrix identity
        Matrix.setIdentityM(transformationMatrix, 0);

        // Initialize the vertex buffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4); // 4 bytes per float
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    /**
     * draws the spaceship onto the the gl10 interface given as parameter
     */
    @Override
    public void draw(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();  // Save the current model view matrix

        // Apply the transformation matrix
        gl.glMultMatrixf(transformationMatrix, 0);

        // Set the color of the spaceship
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

        //make a copy of the array to avoid concurrent modifications of the arraylist and draw the projectile
        ArrayList<Projectile> cpy = new ArrayList<>(projectiles);
        for (Projectile projectile : cpy) {
            projectile.draw(gl);
        }
    }

    public void updateShip(float fracSec) {
        //update cooldown
        cooldown -= fracSec;
        // Update position
        x += velocity * fracSec;

        // Collision detection with the boundaries
        if (x < leftBoundary) {
            x = leftBoundary;

        } else if (x > rightBoundary) {
            x = rightBoundary;
        }
        // Update transformation matrix with new position
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
        // rotate the ship to make it look cool
        rotate(0, velocity * 3, 0.0f);

        //update projectiles
        ArrayList<Projectile> cpy = new ArrayList<>(projectiles);
        Log.d("Projectiles", ""+projectiles.size());
        for (Projectile projectile : cpy) {
            projectile.updateProjectile(fracSec);
            if (projectile.isOutOfBounds()) {
                projectiles.remove(projectile);
            }
        }
    }

    /**
     * Function to set the velocity of the spaceship
     * @param vx
     */
    public void setVelocity(float vx) {
        this.velocity = 2*vx;
    }

    /**
     * Function to rotate the ship on the x,y,z axis by the amount given in the parameters
     */
    public void rotate(float angleX, float angleY, float angleZ) {
        // Apply the rotations to the transformation matrix
        Matrix.rotateM(transformationMatrix, 0, angleX, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(transformationMatrix, 0, angleY, 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(transformationMatrix, 0, angleZ, 0.0f, 0.0f, 1.0f);
    }

    /**
     * Function to shoot a projectile
     */
    public void shoot(){
        //check if the ship is off cooldown
        if (cooldown <= 0) {
            //create a new projectile and add it to the list of projectiles
            float[] color = {0.0f, 1.0f, 0.0f, 1.0f};
            projectiles.add(new Projectile(x, y, Direction.UP, color));
            //set the cooldown to 0.1 seconds
            cooldown = 0.1f;
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
    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
}
