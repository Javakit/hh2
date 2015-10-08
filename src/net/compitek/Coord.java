package net.compitek;

/**
 * ¬спомогательный класс, сущность точки как совокупности x и y координат
 */
public class Coord {

    public Coord(long x, long y) {
        this.x = x;
        this.y = y;
    }

    long x;

    long y;

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}
