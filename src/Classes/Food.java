package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable {
    public static Food selectedFoodForAdmin ;
    public static Food selectedFoodForUser ;
    //public static ArrayList<Food> allFoodsArrayList = new ArrayList<Food>() ;
    public ArrayList<Comment> foodCommentsArrayList = new ArrayList<Comment>() ;
    public ArrayList<Rating> foodRatingsArrayList = new ArrayList<Rating>() ;
    public ArrayList<User> foodOrderHistory = new ArrayList<User>() ;
    //public static int counterIDFood ;
    public String foodName ;
    public String foodID ;
    public double foodCost ;
    public boolean possibilityOfOrdering = true ;
    public boolean discountActivation ;
    public boolean activeOrder ;
    public int discountValue ;
    public int discountTimeStampHour ;
    public int foodRatedNumber ;


    public Food(String foodName,int foodCost){
        this.foodName = foodName ;
        this.foodCost = foodCost ;
    }

    public double getRating(){
        double sum = 0;
        for (int i = 0; i < this.foodRatingsArrayList.size(); i++)
            sum += this.foodRatingsArrayList.get(i).rating;
        return (sum / this.foodRatingsArrayList.size());
    }
}