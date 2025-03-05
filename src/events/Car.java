package src.events;

public class Car {
    private static int nextId = 1;
    private int id;

    public Car(){
        this.id = nextId;
        nextId ++;
    }
}
