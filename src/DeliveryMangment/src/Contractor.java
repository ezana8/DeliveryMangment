package DeliveryMangment.src;


import DeliveryMangment.src.*;



public class Contractor extends Entity{
    private int phoneNum;
    private String dateOfBirth;
    private int vehicle;
    private String status;


    Contractor(){}
    Contractor(String name, int id,int vehicle, String status){
        super(name, id);
        this.vehicle=vehicle;
        this.status=status;
        
    }
    
    Contractor(String name, int id, int phoneNum, String dateOfBirth,int vehicle, String status){
        super(name, id);
        this.phoneNum = phoneNum;
        this.dateOfBirth = dateOfBirth;
        this.vehicle=vehicle;
    }
  
}
