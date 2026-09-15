package DeliveryMangment.src;


import java.time.LocalDateTime;

public class Entity {
    protected String name;
    protected int id;
    protected LocalDateTime registrationTime;
    

    public Entity(){

    }
    

    public Entity(String name, int id){
        this.name = name;
        this.id = id;
        registrationTime = LocalDateTime.now();
    }
}
