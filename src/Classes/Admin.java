package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Admin extends Role implements Serializable {
    //public static ArrayList<Admin> adminArrayList = new ArrayList<Admin>() ;
    public ArrayList<Restaurant> adminRestaurants = new ArrayList<Restaurant>() ;
    public ArrayList<Restaurant> alphabeticSortOfAdminRestaurants = new ArrayList<Restaurant>() ;
    //public static int counterIDAdmin ;
    private String userName ;
    private String password ;
    public String adminID ;

    public Admin(String userName,String password,String adminID){
        this.userName = userName ;
        this.password = password ;
        this.adminID = adminID ;
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
}