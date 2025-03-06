package src.events;

public enum MachineType {
    FAST(1);
    SLOW(2);

    private final int value;

    MachineType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}


