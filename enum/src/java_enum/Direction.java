package java_enum;

enum Direction {
    EAST(1), WEST(-1), SOUTH(5), NORTH(10);

    private final int value;
    private Direction(int value){
        this.value = value;
    }
    public int getValue() { return value; }
}