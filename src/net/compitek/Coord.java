package net.compitek;

/**
 * ��������������� �����, �������� ����� ��� ������������ x � y ���������
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
