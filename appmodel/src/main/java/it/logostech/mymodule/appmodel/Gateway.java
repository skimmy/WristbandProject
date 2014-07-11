package it.logostech.mymodule.appmodel;

/**
 * Created by michele.schimd on 26/06/2014.
 */
public class Gateway {
    private long id;
    private int type;
    private float[] position;

    public Gateway(long id, int type, float[] position) {
        this.id = id;
        this.type = type;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float lat, float lon) {
        this.position = new float[2];
        this.position[0] = lat;
        this.position[1] = lon;
    }
}
