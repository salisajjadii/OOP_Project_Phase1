package Functions;
import Classes.*;

import java.io.Serializable;
import java.util.ArrayList;

public class StaticArrayLists implements Serializable {

    // USER STATIC ARRAYLIST AND METHODS
    public ArrayList<User> userStaticArrayList = new ArrayList<User>() ;
    public int counterIDUser;

    // ADMIN STATIC ARRAYLIST AND METHODS
    public ArrayList<Admin> adminStaticArrayList = new ArrayList<Admin>() ;
    public int counterIDAdmin ;

    // COMMENT STATIC METHOD
    public int counterIDComment ;

    // DELIVERY STATIC ARRAYLIST AND METHODS
    public int counterIDDelivery ;
    public ArrayList<Delivery> deliveriesArraylist = new ArrayList<Delivery>() ;

    // FOOD STATIC ARRAYLIST AND METHODS
    public ArrayList<Food> allFoodsArrayList = new ArrayList<Food>() ;
    public int counterIDFood ;

    // ORDER STATIC METHOD
    public int counterIDOrder;

    // RATING STATIC METHOD
    public int counterIDRating ;

    // RESTAURANT STATIC METHOD AND ARRAYLIST
    public ArrayList<Restaurant> allRestaurantsArrayList = new ArrayList<Restaurant>();
    public int counterIDRestaurant ;
    public ArrayList<Integer> LocationArrayList = new ArrayList<Integer>() ;
    public ArrayList<Order> OrdersWithoutDeliveryArrayList = new ArrayList<Order>() ;
    public Order StaticOrderDelivery ;
}