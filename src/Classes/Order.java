package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    //public static int counterIDOrder;
    public Order(String orderID){
        this.orderID = orderID ;
    }
    public static Order LoggedInOrderForUser;
    public ArrayList<Food> orderFoods = new ArrayList<Food>();
    public String orderID;
    public Restaurant orderedRestaurant;
    public Delivery deliveryOfOrder ;
    public STATUS orderStatus ;
    public double orderCost ;
    public Long deliveryTimeRemains;
    public User orderedUser;
    public double getOrderCost(){
        double totalCost = 0;
        for(int i=0;i<orderFoods.size();i++)
            totalCost += orderFoods.get(i).foodCost;
        return totalCost;
    }
}