import Classes.*;
import Functions.Functions;
import java.io.*;
import java.sql.* ;
import java.util.Scanner;
import Functions.StaticArrayLists ;
import Map.Map;
public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        StaticArrayLists staticArrayLists = new StaticArrayLists() ;
        Map map = new Map("F:\\CP Projects\\OOP\\Phase1\\src\\graph.txt");
        //deSerializeObjects(staticArrayLists);
        try {
            FileInputStream fileIn = new FileInputStream("F:\\CP Projects\\OOP\\Phase1\\src\\StaticArrayLists.txt") ;
            ObjectInputStream in = new ObjectInputStream(fileIn) ;
            staticArrayLists = (StaticArrayLists) in.readObject() ;
            in.close();
            fileIn.close();
            System.out.println("WELCOME TO THE KOSSHER PROGRAM !");
        }catch (IOException | ClassNotFoundException e ){
            System.out.println("DeSerialize failed !");
        }
        Scanner scanner = new Scanner(System.in);
        boolean inputValidation = true;
        while (inputValidation) {
            String input = scanner.nextLine();
            if (input.equals("END") && !Role.loggedInRoleExistance) {
                inputValidation = false ;
                break;
            } else {
                String[] inputArray = input.split(" ") ;
                if (inputArray[0].equals("ADD") && (inputArray[1].equals("USER") || inputArray[1].equals("ADMIN") || inputArray[1].equals("DELIVERY")) && inputArray.length == 4 && !Role.loggedInRoleExistance) {
                    if (!inputArray[1].equals("ADMIN") && !inputArray[1].equals("USER") && !inputArray[1].equals("DELIVERY")) {
                        System.out.println("Choosen Role is Invalid!");
                    } else {
                        Functions.checkPassword(inputArray[3], inputArray[1], inputArray[2],staticArrayLists);
                    }
                } else if (inputArray[0].equals("LOGIN") && inputArray.length == 4 && !Role.loggedInRoleExistance) {
                    Functions.LogIn(inputArray[3], inputArray[1], inputArray[2],staticArrayLists,map);
                } else if (input.equals("LOGOUT") && Role.loggedInRoleExistance && Restaurant.loggedInRestaurantForAdmin == null && Restaurant.loggedInRestaurantForUser == null) {
                    if (Role.loggedInRoleExistance) {
                        Role.loggedInRole = null;
                        Role.loggedInRoleExistance = false;
                        System.out.println("Logged out sucessfully!");
                    } else {
                        System.out.println("There is no LoggedIn account!");
                    }
                } else if (inputArray[0].equals("FORGET") && inputArray[1].equals("PASSWORD") && inputArray.length == 4 && !Role.loggedInRoleExistance) {
                    String Role = inputArray[2];
                    String userName = inputArray[3];
                    Functions.ForgetPassword(Role, userName,staticArrayLists);
                } else if (inputArray[0].equals("SELECT") && !inputArray[1].equals("MENU") && inputArray.length == 2 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin == null) {
                    Admin admin = (Admin) Role.loggedInRole ;
                    Functions.searchRestaurant(admin, inputArray[1]);
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("RESTAURANT") && inputArray.length == 3 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin == null) {
                    Admin admin = (Admin) Role.loggedInRole;
                    String foodtype = inputArray[2] ;
                    System.out.println("please enter yout restaurant name : ");
                    String restaurantName = scanner.nextLine();
                    boolean restaurantExistance = false ;
                    for (int i = 0 ; i < admin.adminRestaurants.size() ; i++){
                        if(admin.adminRestaurants.get(i).restaurantName.equals(restaurantName)){
                            restaurantExistance = true ;
                        }
                    }
                    if (restaurantExistance){
                        System.out.println("you have another restaurant with this name !");
                    }else {
                        Restaurant restaurant = new Restaurant(restaurantName);
                        restaurant.restaurantOwner = admin;
                        restaurant.restaurantLocation = Functions.setRandomLocation(staticArrayLists) ;
                        restaurant.restaurantFoodType.add(foodtype) ;
                        restaurant.restaurantID = Functions.setID("restaurant",staticArrayLists) ;
                        admin.adminRestaurants.add(restaurant);
                        staticArrayLists.allRestaurantsArrayList.add(restaurant);
                        System.out.println("restaurant created successfully!");
                    }
                } else if (inputArray[0].equals("SHOW") && inputArray[1].equals("RESTAURANT") && inputArray[2].equals("LIST") && inputArray.length == 3 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin == null && Food.selectedFoodForAdmin == null) {
                    Admin admin = (Admin) Role.loggedInRole;
                    Functions.showRestaurantList(admin);
                } else if (inputArray[0].equals("SHOW") && inputArray[1].equals("FOODTYPE") && inputArray.length == 2 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin == null) {
                    Functions.showFoodType();
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("FOODTYPE") && inputArray.length == 2 && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin == null) {
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    System.out.println("Add number of foodtypes you want to add : ");
                    int numberOfFoodTypes = Integer.parseInt(scanner.nextLine()) ;
                    for (int i = 0; i < numberOfFoodTypes; i++) {
                        String foodtype = scanner.nextLine();
                        boolean foodtypeExistance = false ;
                        for (int j = 0 ; j < restaurant.restaurantFoodType.size() ; j++){
                            if (foodtype.equals(restaurant.restaurantFoodType.get(j))){
                                foodtypeExistance = true ;
                            }
                        }
                        if (foodtypeExistance){
                            System.out.println("this food type exist now !");
                        }else {
                            restaurant.restaurantFoodType.add(foodtype);
                            System.out.println("added successfully !");
                        }
                    }
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("FOODTYPE") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin == null) {
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    if (restaurant.restaurantOrders.size() > 0) {
                        System.out.println("You can't edit food type when restaurant has active order!");
                    } else {
                        System.out.println("ARE YOU SURE YOU WANT TO CHANGE YOUR RESTAURANT TYPE? (enter yes or no)");
                        String answer = scanner.nextLine();
                        if (answer.toLowerCase().equals("yes")) {
                            restaurant.restaurantMenu.clear();
                            restaurant.restaurantFoodType.clear();
                            for (int i = 0; i < inputArray.length - 2; i++) {
                                restaurant.restaurantFoodType.add(inputArray[i + 2]);
                            }
                            System.out.println("food type edited succesfully , now you have to recreate your restaurant menu!");
                        } else if (answer.toLowerCase().equals("no")) {

                        } else {
                            System.out.println("invalid command");
                        }
                    }
                } else if (inputArray[0].equals("SELECT") && inputArray[1].equals("MENU") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 2 && Food.selectedFoodForAdmin == null) {
                    Functions.showMenuForAdmin();
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 5 && Food.selectedFoodForAdmin == null) {
                    String foodID = inputArray[2];
                    String changingParameters = inputArray[3];
                    String newValue = inputArray[4];
                    Functions.editFood(foodID, changingParameters, newValue);
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin == null) {
                    String foodName = new String("");
                    for (int i = 2; i < inputArray.length - 1; i++) {
                        foodName += inputArray[i] + " ";
                    }
                    foodName = foodName.trim();
                    int foodCost = Integer.parseInt(inputArray[inputArray.length - 1]);
                    Functions.addFood(foodName, foodCost,staticArrayLists);
                } else if (inputArray[0].equals("DELETE") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 3 && Food.selectedFoodForAdmin == null) {
                    String foodID = inputArray[2];
                    Functions.deleteFood(foodID,staticArrayLists);
                } else if (inputArray[0].equals("DEACTIVE") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 3 && Food.selectedFoodForAdmin == null) {
                    String foodID = inputArray[2];
                    Functions.deactiveFood(foodID);
                } else if (inputArray[0].equals("ACTIVE") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 3 && Food.selectedFoodForAdmin == null) {
                    String foodID = inputArray[2];
                    Functions.activeFood(foodID);
                } else if (inputArray[0].equals("DISCOUNT") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 5 && Food.selectedFoodForAdmin == null) {
                    String foodID = inputArray[2];
                    int discountPercent = Integer.parseInt(inputArray[3]);
                    int timestampHour = Integer.parseInt(inputArray[4]);
                    Functions.discountFood(foodID, discountPercent, timestampHour);
                } else if (inputArray[0].equals("SHOW") && inputArray[1].equals("LOCATION") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 2 && Food.selectedFoodForAdmin == null) {
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    if (restaurant.restaurantLocation == null) {
                        System.out.println("Location doesn't exist !");
                    } else {
                        System.out.println("Restaurant Location is : " + restaurant.restaurantLocation);
                    }
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("LOCATION") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 2 && Food.selectedFoodForAdmin == null) {
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    System.out.println("Please enter the restaurant location : ");
                    Integer location = scanner.nextInt();
                    restaurant.restaurantLocation = location;
                    System.out.println("Location edited successfully!");
                } else if (inputArray[0].equals("SELECT") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 3 && Food.selectedFoodForAdmin == null) {
                    String foodID = inputArray[2] ;
                    Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin;
                    boolean foodIDExistance = Functions.foodIDExistanceChecker(restaurant, foodID);
                    if (foodIDExistance) {
                        Food food = Functions.foodIDfounder(restaurant, foodID);
                        Food.selectedFoodForAdmin = food;
                        System.out.println("the food id of : "+foodID+" selected successfully !") ;
                    } else {
                        System.out.println("this foodID doesn't exist in this restaurant!");
                    }
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin != null && inputArray.length == 2) {
                    Food food = Food.selectedFoodForAdmin;
                    System.out.println(food.foodRatedNumber + " people rate this food and rating is : " + food.getRating() + " of 5 .");
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin != null && inputArray.length == 2) {
                    Food food = Food.selectedFoodForAdmin;
                    if (food.foodCommentsArrayList.size() == 0) {
                        System.out.println("There is no comment yet!");
                    } else {
                        for (int i = 0; i < food.foodCommentsArrayList.size(); i++) {
                            Comment comment = food.foodCommentsArrayList.get(i);
                            System.out.println("User " + comment.commentedUser.getUserName() + " commented : " + comment.comment + " , comment ID : " + comment.commentID);
                        }
                    }
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("NEW") && inputArray[2].equals("RESPONSE") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin != null) {
                    String commentID = inputArray[3] ;
                    String response = new String("") ;
                    if (inputArray.length == 4) {
                        System.out.println("Please enter the message you want : ");
                        response = scanner.nextLine();
                        Functions.commentResponse(commentID, response);
                    } else {
                        for (int i = 4; i < inputArray.length; i++) {
                            response += inputArray[i];
                        }
                        Functions.commentResponse(commentID, response);
                    }
                }else if(inputArray[0].equals("EDIT") && inputArray[1].equals("RESPONSE") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin != null && inputArray.length == 4){
                    String commentID = inputArray[2] ;
                    String response = new String("") ;
                    for (int i = 3 ; i < inputArray.length; i++){
                        response += inputArray[i];
                    }
                    Functions.commentResponse(commentID, response) ;
                }else if(inputArray[0].equals("DISPLAY") && inputArray[1].equals("OPEN") && inputArray[2].equals("ORDERS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 3 && Food.selectedFoodForAdmin == null){
                    Functions.displayOpenOrders() ;
                }else if(inputArray[0].equals("EDIT") && inputArray[1].equals("ORDER") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && inputArray.length == 5){
                    String orderID = inputArray[2] ;
                    String parameter = inputArray[3] ;
                    String value = inputArray[4] ;
                    Functions.editOrder(orderID,parameter,value,staticArrayLists,map);
                }else if (inputArray[0].equals("SHOW") && inputArray[1].equals("ORDER") && inputArray[2].equals("HISTORY") && Role.loggedInRoleExistance && Role.loggedInRole instanceof Admin && Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin == null && inputArray.length == 3){
                    Functions.showOrderHistory();
                } else if (input.equals("RETURN") && Role.loggedInRoleExistance) {
                    if (Role.loggedInRole instanceof Admin){
                        if (Food.selectedFoodForAdmin != null){
                            Food.selectedFoodForAdmin = null ;
                        }else if (Restaurant.loggedInRestaurantForAdmin != null && Food.selectedFoodForAdmin == null){
                            Restaurant.loggedInRestaurantForAdmin = null ;
                        }
                    }else if (Role.loggedInRole instanceof User){
                        if (Food.selectedFoodForUser != null){
                            Food.selectedFoodForUser = null ;
                        }else if (Food.selectedFoodForUser == null && Restaurant.loggedInRestaurantForUser != null){
                            Restaurant.loggedInRestaurantForUser = null ;
                        } else if (Food.selectedFoodForUser == null && Restaurant.loggedInRestaurantForUser == null && Order.LoggedInOrderForUser != null){
                            Order.LoggedInOrderForUser = null;
                        }
                    }
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("CART") && inputArray[2].equals("STATUS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null){
                    Functions.showCartStatus((User) Role.loggedInRole);
                } else if (inputArray[0].equals("CONFIRM") && inputArray[1].equals("ORDER") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null){
                    String orderID = inputArray[2];
                    Functions.confirmOrder((User) Role.loggedInRole, orderID);
                } else if (inputArray[0].equals("CHARGE") && inputArray[1].equals("ACCOUNT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null){
                    Functions.chargeAccount((User) Role.loggedInRole);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("ACCOUNT") && inputArray[2].equals("CHARGE") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null){
                    Functions.showAccountCharge((User) Role.loggedInRole);
                } else if (inputArray[0].equals("SEARCH") && inputArray[1].equals("RESTAURANT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null) {
                    String restaurantName = inputArray[2];
                    Functions.ShowRelatedRestaurants(restaurantName,staticArrayLists);
                } else if (inputArray[0].equals("SELECT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && inputArray.length == 2 && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null) {
                    String restaurantID = inputArray[1];
                    Functions.selectRestaurant(restaurantID,staticArrayLists);
                } else if (inputArray[0].equals("ACCESS") && inputArray[1].equals("ORDER") && inputArray[2].equals("HISTORY") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Food.selectedFoodForUser == null && Restaurant.loggedInRestaurantForUser == null){
                    Functions.showOrdersHistory((User) Role.loggedInRole);
                } else if (inputArray[0].equals("SELECT") && inputArray[1].equals("ORDER") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null){
                    Functions.selectOrder(inputArray[2]);
                    System.out.println("You also should pay " + map.getCost(Order.LoggedInOrderForUser.orderedRestaurant.restaurantLocation,Order.LoggedInOrderForUser.orderedUser.userLocation) + "$ to delivery man !");
                } else if (inputArray[0].equals("SEARCH") && inputArray[1].equals("FOOD") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    String foodName = inputArray[1];
                    Functions.ShowRelatedFoods(foodName);
                } else if (inputArray[0].equals("SELECT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && inputArray.length == 2 && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    String foodID = inputArray[1];
                    Functions.selectFood(foodID);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    Functions.showRestaurantComments(Restaurant.loggedInRestaurantForUser);
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("NEW") && inputArray[2].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    Functions.getRestaurantComment(Restaurant.loggedInRestaurantForUser,staticArrayLists);
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("COMMENT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    String commentID = inputArray[2];
                    Functions.editRestaurantComment(commentID);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    Functions.displayRestaurantRating((User) Role.loggedInRole,Restaurant.loggedInRestaurantForUser);
                } else if (inputArray[0].equals("SUBMIT") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    Functions.getRestaurantRating(staticArrayLists,(User) Role.loggedInRole,Restaurant.loggedInRestaurantForUser);
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser == null) {
                    String ratingID = inputArray[2];
                    Functions.editRestaurantRating(ratingID);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    Functions.showFoodComments(Food.selectedFoodForUser);
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("NEW") && inputArray[2].equals("COMMENTS") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    Functions.getFoodComment(Food.selectedFoodForUser,staticArrayLists);
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("COMMENT") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    String commentID = inputArray[2];
                    Functions.editFoodComment(commentID);
                } else if (inputArray[0].equals("DISPLAY") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    Functions.displayFoodRating((User) Role.loggedInRole, Food.selectedFoodForUser);
                } else if (inputArray[0].equals("SUBMIT") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    Functions.getFoodRating(staticArrayLists,(User) Role.loggedInRole,Food.selectedFoodForUser);
                } else if (inputArray[0].equals("EDIT") && inputArray[1].equals("RATING") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null) {
                    String ratingID = inputArray[2];
                    Functions.editFoodRating(ratingID);
                } else if (inputArray[0].equals("ADD") && inputArray[1].equals("THIS") && inputArray[2].equals("FOOD") && inputArray[3].equals("TO") && inputArray[4].equals("CART") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser != null && Food.selectedFoodForUser != null){
                    Functions.addFoodToCart( (User) Role.loggedInRole,Restaurant.loggedInRestaurantForUser,Food.selectedFoodForUser,staticArrayLists);
                } else if (inputArray[0].equals("SHOW") && inputArray[1].equals("ESTIMATED") && inputArray[2].equals("DELIVERY") && inputArray[3].equals("TIME") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null && Order.LoggedInOrderForUser != null){
                    System.out.println("Estimated delivery time is : " + map.getTime(Order.LoggedInOrderForUser.orderedRestaurant.restaurantLocation,Order.LoggedInOrderForUser.orderedUser.userLocation) + " (minute/minutes)");
                }else if (inputArray[0].equals("SHOW") && inputArray[0].equals("PATH") && Role.loggedInRoleExistance && Role.loggedInRole instanceof User && Restaurant.loggedInRestaurantForUser == null && Food.selectedFoodForUser == null && Order.LoggedInOrderForUser != null){
                    System.out.println("The path for sending your order to you is " + map.getPath(Order.LoggedInOrderForUser.orderedRestaurant.restaurantLocation,Order.LoggedInOrderForUser.orderedUser.userLocation));
                }else if(inputArray[0].equals("SELECT") && inputArray[1].equals("ORDER") && inputArray.length == 3 && Role.loggedInRole instanceof Delivery){
                    String orderId = inputArray[2] ;
                    Functions.selectOrderForDelivery(staticArrayLists,orderId,map);
                }else if(inputArray[0].equals("ORDER") && inputArray[1].equals("DELIVERED") && Role.loggedInRole instanceof Delivery){
                    Functions.orderDelivered(staticArrayLists);
                } else {
                    System.out.println("invalid command");
                }
            }
        }
        serializeObjects(staticArrayLists);
    }
    public static void serializeObjects(StaticArrayLists staticArrayLists){
        try{
            FileOutputStream fileout = new FileOutputStream("F:\\CP Projects\\OOP\\Phase1\\src\\StaticArrayLists.txt") ;
            ObjectOutputStream out = new ObjectOutputStream(fileout) ;
            out.writeObject(staticArrayLists);
            out.close();
            fileout.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}