package hskl.de.projekt.Objects.Aliens;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import hskl.de.projekt.Objects.Projectile;

public abstract class AlienMaster {
    public abstract void draw(GL10 gl);

    public abstract void update(float deltaTime);

    public abstract float getX();

    public abstract float getY();

    public abstract ArrayList<Projectile> getProjectiles();
}
