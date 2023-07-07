package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Delivery extends Role implements Serializable {
    //public static int counterIDDelivery ;
    //public static ArrayList<Delivery> deliveriesArraylist = new ArrayList<Delivery>() ;
    public ArrayList<Order> deliveryOrdersHistory = new ArrayList<Order>() ;
    public Order activeOrder ;
    private String userName ;
    private String password ;
    public String deliveryID ;
    public Integer deliveryLocation ;
    public boolean activeOrderBoolean ;
    public Delivery(String userName,String password,String deliveryID){
        this.userName = userName ;
        this.password = password ;
        this.deliveryID = deliveryID ;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}