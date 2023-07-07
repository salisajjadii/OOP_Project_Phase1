package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class User extends Role implements Serializable {
    // public static ArrayList<User> userArrayList = new ArrayList<User>();
    public ArrayList<Order> userOrders = new ArrayList<Order>();
    public Cart userCart = new Cart();
    private String userName ;
    private String password;
    public String userID ;
    public Integer userLocation ;
    private double accountCharge = 0;
    //public static int counterIDUser ;
    public User(String userName, String password,String userID) {
        this.userName = userName;
        this.password = password;
        this.userID = userID ;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAccountCharge() {
        return accountCharge;
    }
    public void setAccountCharge(double accountCharge) {
        this.accountCharge = accountCharge;
    }
}