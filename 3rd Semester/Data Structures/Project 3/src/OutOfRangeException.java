package edu.aueb.ds.exceptions;


/**
 * Out Of Range exception
 */
public class OutOfRangeException extends Exception {
    private int position;
    private int maxPosition;
    private int minPosition;

    public OutOfRangeException(int position, int min, int max) {
        this.position = position;
        maxPosition = max;
        minPosition = min;
    }

    public int getPosition() {
        return position;
    }

    public int getMinPosition() {
        return minPosition;
    }

    public int getMaxPosition() {
        return maxPosition;
    }

    @Override
    public String toString() {
        return String.valueOf(position) + " not in range [" + String.valueOf(minPosition) + ", " + String.valueOf(maxPosition) + "]";
    }
}
