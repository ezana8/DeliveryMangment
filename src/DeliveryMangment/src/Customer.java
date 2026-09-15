package DeliveryMangment.src;


import DeliveryMangment.src.*;



public class Customer extends Entity{
    private int phoneNum;
    private String dateOfBirth;

    boolean isfound() {
       //check if the phone number exists in the database 
       return true;
    }

    Customer(){}
    Customer(String name, int id){
        super(name, id);
        
    }
    
    Customer(String name, int id, int phoneNum, String dateOfBirth){
        super(name, id);
        this.phoneNum = phoneNum;
        this.dateOfBirth = dateOfBirth;
    }
  
}
