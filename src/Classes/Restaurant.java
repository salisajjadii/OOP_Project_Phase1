package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Restaurant implements Serializable {
    //public static ArrayList<Restaurant> allRestaurantsArrayList = new ArrayList<Restaurant>();
    public static Restaurant loggedInRestaurantForAdmin ;
    public static Restaurant loggedInRestaurantForUser ;
    //public static int counterIDRestaurant ;
    public ArrayList<Comment> restaurantCommentsArrayList = new ArrayList<Comment>() ;
    public ArrayList<Rating> restaurantRatingsArrayList = new ArrayList<Rating>() ;
    public ArrayList<String> restaurantFoodType = new ArrayList<String>() ;
    public ArrayList<Order> restaurantOrders = new ArrayList<Order>() ;
    public ArrayList<Food> restaurantMenu = new ArrayList<Food>() ;
    public ArrayList<Order> restaurantOrdersHistory = new ArrayList<Order>() ;
    public String restaurantID ;
    public String restaurantName ;
    public Admin restaurantOwner ;
    public Integer restaurantLocation ;
    public Restaurant(String restaurantName){
        this.restaurantName = restaurantName ;
    }
    public double getRating(){
        double sum = 0;
        for (int i=0;i<this.restaurantRatingsArrayList.size(); i++)
            sum += this.restaurantRatingsArrayList.get(i).rating;
        return (sum / this.restaurantRatingsArrayList.size());
    }

}