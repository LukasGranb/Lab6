package src.events;

public class Car {
    // om jag fatta det rätt ska detta alltid börja på noll.
    private int id = 0;

    public Car(int id){
        this.id = id;
    }

    public int getCarId(){
        return id;
    }
}
