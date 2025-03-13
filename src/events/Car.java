package src.events;

/**
 * @author Lukas Granberg, Amund Knutsson
 * The Car class creates a car that will go throguh the simulation
 */

public class Car {
    private int id;

    /**
     *Constructor that creates cars
     * @param id
     */

    public Car(int id){
        this.id = id;
    }

    /**
     * Getter for car objects id
     * @return id
     */

    public int getCarId(){
        return id;
    }
}
