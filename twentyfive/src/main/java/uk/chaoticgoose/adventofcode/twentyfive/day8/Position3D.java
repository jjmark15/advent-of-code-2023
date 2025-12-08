package uk.chaoticgoose.adventofcode.twentyfive.day8;

record Position3D(long x, long y, long z) {
    public long distanceTo(Position3D other) {
        return squareDiff(x, other.x) + squareDiff(y, other.y) + squareDiff(z, other.z);
    }

    private long squareDiff(long a, long b) {
        long diff = a - b;
        return diff * diff;
    }
}
