package Functions;
import Classes.*;
import java.io.*;
import Map.Map ;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Functions implements Serializable {
    public static Scanner scanner = new Scanner(System.in) ;
    public static void checkPassword(String password,String Role,String userName,StaticArrayLists staticArrayLists){
        boolean numberic = false ;
        boolean capitalLetter = false ;
        boolean smallLetter = false ;
        int passwordLength = password.length() ;
        for (int i = 0 ; i < password.length() ; i++){
            int asciiCode = (int) (password.charAt(i)) ;
            if (asciiCode < 57 && asciiCode > 48){
                numberic = true ;
            }
        }
        for (int i = 0 ; i < password.length() ; i++){
            int asciiCode = (int) (password.charAt(i)) ;
            if (asciiCode < 90 && asciiCode > 65){
                capitalLetter = true ;
            }
        }
        for (int i = 0 ; i < password.length() ; i++){
            int asciiCode = (int) (password.charAt(i)) ;
            if (asciiCode < 122 && asciiCode > 97){
                smallLetter = true ;
            }
        }
        if (!numberic){
            System.out.println("Passowrd must have at least one number!");
        }
        if (!capitalLetter){
            System.out.println("Password must have at least one capital letter!");
        }
        if (!smallLetter){
            System.out.println("Password must have at least one small letter!");
        }
        if (passwordLength < 8){
            System.out.println("Password must have at least 8 charecter!");
        }
        if (numberic && capitalLetter && smallLetter && passwordLength >= 8){
            if (Role.equals("ADMIN")){
                if (isUserNameUnique(userName,Role,staticArrayLists)){
                    Admin admin = new Admin(userName,password,setID("admin",staticArrayLists)) ;
                    staticArrayLists.adminStaticArrayList.add(admin) ;
                    System.out.println("Account created successfully");
                    makeSecurityQuestion(admin);
                }else {
                    System.out.println("An Admin exist with this username!");
                }
            }else if (Role.equals("USER")){
                if (isUserNameUnique(userName,Role,staticArrayLists)){
                    User user = new User(userName,password,setID("user",staticArrayLists)) ;
                    user.userLocation = setRandomLocation(staticArrayLists) ;
                    staticArrayLists.userStaticArrayList.add(user) ;
                    System.out.println("Account created successfully");
                    makeSecurityQuestion(user);
                }else {
                    System.out.println("A User exist with this username!");
                }
            }else if (Role.equals("DELIVERY")){
                if (isUserNameUnique(userName,Role,staticArrayLists)){
                    Delivery delivery = new Delivery(userName,password,setID("delivery",staticArrayLists)) ;
                    staticArrayLists.deliveriesArraylist.add(delivery) ;
                    delivery.deliveryLocation = setRandomLocation(staticArrayLists) ;
                    System.out.println("Account created successfully");
                    makeSecurityQuestion(delivery);
                }else {
                    System.out.println("A Delivery exist with this username!");
                }
            }
        }
    }
    public static void LogIn(String password,String Role,String userName,StaticArrayLists staticArrayLists,Map map){
        if (Role.equals("ADMIN")){
            if (isUserNameUnique(userName,Role,staticArrayLists)){
                System.out.println("This username does not exist!");
            }else {
                Admin admin = staticArrayLists.adminStaticArrayList.get(findRoleIndex(userName,Role,staticArrayLists)) ;
                if (admin.getPassword().equals(password)){
                    Classes.Role.loggedInRole = admin ;
                    Classes.Role.loggedInRoleExistance = true ;
                    System.out.println("you are logged in as ADMIN!");
                    if (admin.adminRestaurants.size() == 1){
                        Restaurant.loggedInRestaurantForAdmin = admin.adminRestaurants.get(0) ;
                        System.out.println("you are at the panel of restaurant with id of : " + admin.adminRestaurants.get(0).restaurantID);
                    }else {
                        showRestaurantList(admin);
                    }
                }else {
                    System.out.println("password is incorrect!");
                }
            }
        }else if (Role.equals("USER")){
            if (isUserNameUnique(userName,Role,staticArrayLists)){
                System.out.println("This username does not exist!");
            }else {
                User user = staticArrayLists.userStaticArrayList.get(findRoleIndex(userName,Role,staticArrayLists)) ;
                if (user.getPassword().equals(password)){
                    Classes.Role.loggedInRole = user ;
                    Classes.Role.loggedInRoleExistance = true ;
                    System.out.println("you are logged in as USER!");
                    Functions.showAllAvailableRestaurants(staticArrayLists);
                }else {
                    System.out.println("password is incorrect!");
                }
            }
        }else if (Role.equals("DELIVERY")){
            if (isUserNameUnique(userName,Role,staticArrayLists)){
                System.out.println("This username does not exist!");
            }else {
                Delivery delivery = staticArrayLists.deliveriesArraylist.get(findRoleIndex(userName,Role,staticArrayLists)) ;
                if (delivery.getPassword().equals(password)){
                    Classes.Role.loggedInRole = delivery ;
                    Classes.Role.loggedInRoleExistance = true ;
                    System.out.println("you are logged in as DELIVERY!");
                    showOrdersListForDelivery(staticArrayLists,map) ;
                }else {
                    System.out.println("password is incorrect!");
                }
            }
        }
    }
    public static boolean isUserNameUnique(String UserName,String Role,StaticArrayLists staticArrayLists){
        boolean isUserNameUnique = true ;
        if (Role.equals("ADMIN")){
            for (int i = 0 ; i < staticArrayLists.adminStaticArrayList.size() ; i++){
                if (staticArrayLists.adminStaticArrayList.get(i).getUserName().equals(UserName)){
                    isUserNameUnique = false ;
                }
            }
        }else if (Role.equals("USER")){
            for (int i = 0 ; i < staticArrayLists.userStaticArrayList.size() ; i++){
                if (staticArrayLists.userStaticArrayList.get(i).getUserName().equals(UserName)){
                    isUserNameUnique = false ;
                }
            }
        }else if (Role.equals("DELIVERY")){
            for (int i = 0 ; i < staticArrayLists.deliveriesArraylist.size() ; i++){
                if (staticArrayLists.deliveriesArraylist.get(i).getUserName().equals(UserName)){
                    isUserNameUnique = false ;
                }
            }
        }
        return isUserNameUnique ;
    }
    public static int findRoleIndex(String userName,String Role,StaticArrayLists staticArrayLists){
        int index = 0 ;
        if (Role.equals("ADMIN")){
            for (int i = 0 ; i < staticArrayLists.adminStaticArrayList.size() ; i++){
                if (staticArrayLists.adminStaticArrayList.get(i).getUserName().equals(userName)){
                    index = i ;
                }
            }
        }else if (Role.equals("USER")){
            for (int i = 0 ; i < staticArrayLists.userStaticArrayList.size() ; i++){
                if (staticArrayLists.userStaticArrayList.get(i).getUserName().equals(userName)){
                    index = i ;
                }
            }
        }else if (Role.equals("DELIVERY")){
            for (int i = 0 ; i < staticArrayLists.deliveriesArraylist.size() ; i++){
                if (staticArrayLists.deliveriesArraylist.get(i).getUserName().equals(userName)){
                    index = i ;
                }
            }
        }
        return index ;
    }
    public static void ForgetPassword(String Role,String userName,StaticArrayLists staticArrayLists){
        if (Role.equals("ADMIN")){
            Admin admin = staticArrayLists.adminStaticArrayList.get(findRoleIndex(userName,Role,staticArrayLists)) ;
            System.out.println("please answer following question to reset your password : ");
            System.out.println(admin.securityQuestion);
            String answer = scanner.nextLine() ;
            if (answer.equals(admin.getSecurityQuestionAnswer())){
                System.out.println("enter your new password : ");
                String password = scanner.nextLine() ;
                admin.setPassword(password);
            }else{
                System.out.println("your answer is incorrect!");
            }
        }else {
            User user = staticArrayLists.userStaticArrayList.get(findRoleIndex(userName,Role,staticArrayLists)) ;
            System.out.println("please answer following question to reset your password : ");
            System.out.println(user.securityQuestion);
            String answer = scanner.nextLine() ;
            if (answer.equals(user.getSecurityQuestionAnswer())){
                System.out.println("enter your new password : ");
                String password = scanner.nextLine() ;
                user.setPassword(password);
            }else{
                System.out.println("your answer is incorrect!");
            }
        }
    }
    public static void makeSecurityQuestion(Role role){
        System.out.println("now please answer this security question for when you forget your password : ");
        double randomNumber = (Math.random()) * 5 ;
        int randomInt = (int) randomNumber ;
        String Question = new String("");
        String Answer ;
        if (randomInt==0){
            Question = new String("What is your favourite color ? : ") ;
        }else if (randomInt==1){
            Question = new String("What is your favourite food ? : ") ;
        }else if (randomInt==2){
            Question = new String("What is your favourite soccer team ? : ") ;
        }else if (randomInt==3){
            Question = new String("What is your favourite actor/actress ? : ") ;
        }else if (randomInt==4){
            Question = new String("What is your favourite movie ? : ") ;
        }
        System.out.println(Question);
        role.securityQuestion = new String(Question) ;
        String answer = scanner.nextLine() ;
        role.setSecurityQuestionAnswer(answer);
    }
    public static void searchRestaurant(Admin admin,String ID){
        boolean restaurantExistance = false ;
        for (int i = 0 ; i < admin.adminRestaurants.size() ; i++){
            if (admin.adminRestaurants.get(i).restaurantID.equals(ID)){
                restaurantExistance = true ;
                Restaurant.loggedInRestaurantForAdmin = admin.adminRestaurants.get(i) ;
            }
        }
        if (restaurantExistance == false){
            System.out.println("There is no Restaurant with this ID!");
        }else {
            System.out.println("We are at panel of "+Restaurant.loggedInRestaurantForAdmin.restaurantName+" with ID of "+Restaurant.loggedInRestaurantForAdmin.restaurantID);
        }
    }
    public static void showFoodType(){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        if (restaurant.restaurantFoodType.size() == 0){
            System.out.println("no food type added yet !");
        }else {
            for (int i = 0 ; i < restaurant.restaurantFoodType.size() ; i++){
                System.out.println( (i+1) +". "+restaurant.restaurantFoodType.get(i));
            }
        }
    }
    public static String setID(String thingString,StaticArrayLists staticArrayLists){
        String firstchapter = new String("") ;
        String ID = new String("") ;
        int counter = 0 ;
        int randomNumber = (int) ( ((Math.random()) * 9999) + 1 ) ;
        if (thingString.equals("restaurant")){
            firstchapter = new String("R") ;
            staticArrayLists.counterIDRestaurant++ ;
            counter = staticArrayLists.counterIDRestaurant ;
        }else if (thingString.equals("food")){
            firstchapter = new String("F") ;
            staticArrayLists.counterIDFood++ ;
            counter = staticArrayLists.counterIDFood ;
        }else if (thingString.equals("comment")){
            firstchapter = new String("C") ;
            staticArrayLists.counterIDComment++ ;
            counter = staticArrayLists.counterIDComment ;
        }else if (thingString.equals("order")){
            firstchapter = new String("O") ;
            staticArrayLists.counterIDOrder++ ;
            counter = staticArrayLists.counterIDOrder ;
        }else if (thingString.equals("rating")){
            firstchapter = new String("RA") ;
            staticArrayLists.counterIDRating++ ;
            counter = staticArrayLists.counterIDRating ;
        }else if (thingString.equals("delivery")){
            firstchapter = new String("D") ;
            staticArrayLists.counterIDDelivery++ ;
            counter = staticArrayLists.counterIDDelivery ;
        }else if (thingString.equals("admin")){
            firstchapter = new String("A") ;
            staticArrayLists.counterIDAdmin++ ;
            counter = staticArrayLists.counterIDAdmin ;
        }else if (thingString.equals("user")){
            firstchapter = new String("U") ;
            staticArrayLists.counterIDUser++ ;
            counter = staticArrayLists.counterIDUser ;
        }
        ID = firstchapter + randomNumber + counter ;
        return ID ;
    }
    public static void showMenuForAdmin(){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        if (restaurant.restaurantMenu.size() == 0){
            System.out.println("No food added to the menu yet!");
        }else {
            for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
                Food food = restaurant.restaurantMenu.get(i) ;
                System.out.println("food name : "+food.foodName+" * food id : "+food.foodID+" * food cost : "+food.foodCost+" * active discount : "+food.discountActivation+" * food rate : "+food.getRating());
            }
        }
    }
    public static void editFood(String foodID,String changingParameters,String newValue){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        int index = 0 ;
        for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
            if (foodID.equals(restaurant.restaurantMenu.get(i).foodID)){
                index = i ;
                //food = restaurant.restaurantMenu.get(i) ;
            }
        }
        if (changingParameters.equals("NAME")){
            restaurant.restaurantMenu.get(index).foodName = newValue ;
            System.out.println("new name sat for food with id of : "+foodID);
        }else if (changingParameters.equals("PRICE")){
            restaurant.restaurantMenu.get(index).foodCost = Integer.parseInt(newValue) ;
            System.out.println("new price sat for food with id of : "+foodID);
        }else if (changingParameters.equals("DISCOUNT")){
            if (restaurant.restaurantMenu.get(index).discountActivation){
                restaurant.restaurantMenu.get(index).discountActivation = false ;
            }else {
                restaurant.restaurantMenu.get(index).discountActivation = true ;
                if (Integer.parseInt(newValue) <= 50 && Integer.parseInt(newValue) > 0){
                    restaurant.restaurantMenu.get(index).discountValue = Integer.parseInt(newValue) ;
                }else {
                    System.out.println("discount value must be less than 50 percent and positive !");
                }
            }
        }
    }
    public static void addFood(String foodName,int foodCost,StaticArrayLists staticArrayLists){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        boolean foodExistance = false ;
        for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
            if (foodName.equals(restaurant.restaurantMenu.get(i).foodName)){
                foodExistance = true ;
            }
        }
        if (foodExistance){
            System.out.println("sorry a food exist with this name in the menu ! ");
        }else {
            Food food = new Food(foodName,foodCost) ;
            food.foodID = setID("food",staticArrayLists) ;
            restaurant.restaurantMenu.add(food) ;
            staticArrayLists.allFoodsArrayList.add(food) ;
            System.out.println("food added to menu successfully!");
        }
    }
    public static void showRestaurantList(Admin admin){
        if (admin.adminRestaurants.size() == 0){
            System.out.println("there is no restaurant in the list!");
        }else {
            for (int i = 0 ; i < admin.adminRestaurants.size() ; i++){
                Restaurant restaurant = admin.adminRestaurants.get(i) ;
                System.out.println("Restaurant Name : "+restaurant.restaurantName+" , Restaurant ID : "+restaurant.restaurantID) ;
            }
        }
    }
    public static void deleteFood(String foodID,StaticArrayLists staticArrayLists){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        boolean foodIDExistance = false , foodExistanceRestaurant = false;
        int foodIndexFood = 0 , foodIndexRestaurant = 0 ;
        for (int i = 0 ; i < staticArrayLists.allFoodsArrayList.size() ; i++){
            if (staticArrayLists.allFoodsArrayList.get(i).foodID.equals(foodID)){
                foodIDExistance = true ;
                foodIndexFood = i ;
            }
        }
        for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
            if (restaurant.restaurantMenu.get(i).foodID.equals(foodID)){
                foodExistanceRestaurant = true ;
                foodIndexRestaurant = i ;
            }
        }
        if (foodIDExistance){
            if (foodExistanceRestaurant){
                staticArrayLists.allFoodsArrayList.remove(foodIndexFood) ;
                restaurant.restaurantMenu.remove(foodIndexRestaurant) ;
                System.out.println("Food deleted successfully!");
            }else {
                System.out.println("This food ID does't exist in this Restaurant!");
            }
        }else {
            System.out.println("This food ID does't exist!");
        }
    }
    public static void deactiveFood(String foodID){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        boolean foodIDExistance = foodIDExistanceChecker(restaurant,foodID) ;
        if (foodIDExistance){
            Food food = foodIDfounder(restaurant,foodID) ;
            if(food.possibilityOfOrdering){
                if (food.activeOrder){
                    System.out.println("You can't deactive a food when it has active order!");
                }else {
                    System.out.println("Are you sure you want to deactive this food ?");
                    String answer = scanner.nextLine() ;
                    if (answer.toLowerCase().equals("yes")){
                        food.possibilityOfOrdering = false ;
                        System.out.println("food deactived successfully!");
                    }
                }
            }else {
                System.out.println("food has been deactive!");
            }

        }else {
            System.out.println("this foodID doesn't exist in this restaurant!");
        }
    }
    public static void activeFood(String foodID){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        boolean foodIDExistance = foodIDExistanceChecker(restaurant,foodID) ;
        if (foodIDExistance){
            Food food = foodIDfounder(restaurant,foodID) ;
            if(food.possibilityOfOrdering){
                System.out.println("food has been active!");
            }else {
                System.out.println("Are you sure you want to active this food ? (yes or no) ");
                String answer = scanner.nextLine() ;
                if (answer.toLowerCase().equals("yes")){
                    food.possibilityOfOrdering = true ;
                    System.out.println("food actived successfully!");
                }
            }
        }else {
            System.out.println("this foodID doesn't exist in this restaurant!");
        }
    }
    public static void discountFood(String foodID,int discountPercent,int timestampHour){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        boolean foodIDExistance = foodIDExistanceChecker(restaurant,foodID) ;
        if (foodIDExistance){
            Food food = foodIDfounder(restaurant,foodID) ;
            if(food.discountActivation){
                System.out.println("You can't add another discount to the food when it has an active discount!");
            }else {
                if (discountPercent <= 50){
                    food.discountActivation = true ;
                    food.discountValue = discountPercent ;
                    food.discountTimeStampHour = timestampHour ;
                    System.out.println("discount for food with id of : "+foodID+" actived.");
                }else {
                    System.out.println("You can't a discount with more than half of food cost!");
                }
            }
        }else {
            System.out.println("this foodID doesn't exist in this restaurant!");
        }
    }
    public static boolean foodIDExistanceChecker(Restaurant restaurant,String foodID){
        boolean foodIDExistance = false ;
        for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
            if (foodID.equals(restaurant.restaurantMenu.get(i).foodID)){
                foodIDExistance = true ;
            }
        }
        return foodIDExistance ;
    }
    public static Food foodIDfounder(Restaurant restaurant,String foodID){
        Food food = restaurant.restaurantMenu.get(0) ;
        for (int i = 0 ; i < restaurant.restaurantMenu.size() ; i++){
            if (foodID.equals(restaurant.restaurantMenu.get(i).foodID)){
                food = restaurant.restaurantMenu.get(i) ;
            }
        }
        return food ;
    }
    public static boolean commentIDExistanceChecker(String commentID){
        Food food = Food.selectedFoodForAdmin ;
        boolean commentExistance = false ;
        for (int i = 0 ; i < food.foodCommentsArrayList.size() ; i++){
            if (commentID.equals(food.foodCommentsArrayList.get(i).commentID)){
                commentExistance = true ;
            }
        }
        return commentExistance ;
    }
    public static Comment commentFounder(String commentID){
        Food food = Food.selectedFoodForAdmin ;
        Comment comment = food.foodCommentsArrayList.get(0) ;
        for (int i = 0 ; i < food.foodCommentsArrayList.size() ; i++){
            if (commentID.equals(food.foodCommentsArrayList.get(i).commentID)){
                comment = food.foodCommentsArrayList.get(i) ;
            }
        }
        return comment ;
    }
    public static void commentResponse(String commentID,String response){
        boolean commentIDExistance = commentIDExistanceChecker(commentID) ;
        if (commentIDExistance){
            Comment comment = commentFounder(commentID) ;
            if (comment.commentResponse.equals("")){
                comment.commentResponse = response ;
                System.out.println("your response accepted!");
            }else {
                System.out.println("you have responsed to this comment before!");
            }
        }else {
            System.out.println("This commentID doesn't exist in this food!");
        }
    }
    public static void displayOpenOrders(){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        if (restaurant.restaurantOrders.size()==0){
            System.out.println("there is no order !");
        }else {
            for (int i = 0 ; i < restaurant.restaurantOrders.size() ; i++){
                Order order = restaurant.restaurantOrders.get(i) ;
                System.out.print((i+1)+". ") ;
                for (int j = 0 ; j < order.orderFoods.size() ; j++){
                    System.out.println("  "+order.orderFoods.get(j).foodName+ " * " + order.orderID);
                }
            }
        }
    }
    public static void editOrder(String orderID, String parameter, String value, StaticArrayLists staticArrayLists, Map map){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        Order order = restaurant.restaurantOrders.get(0) ;
        boolean orderIDExistance = false ;
        for (int i = 0 ; i < restaurant.restaurantOrders.size() ; i++){
            if (orderID.equals(restaurant.restaurantOrders.get(i).orderID)){
                order = restaurant.restaurantOrders.get(i) ;
                orderIDExistance = true ;
            }
        }
        if (orderIDExistance){
            if (parameter.equals("STATUS")){
                if (value.equals("COOKING")){
                    order.orderStatus = STATUS.COOKING ;
                }else if (value.equals("SENT")){
                    order.orderStatus = STATUS.SENT ;
                    int orderIndex = 0 ;
                    for (int i = 0 ; i < restaurant.restaurantOrders.size() ; i++){
                        if (orderID.equals(restaurant.restaurantOrders.get(i).orderID)){
                            orderIndex = i ;
                        }
                    }
                    order.deliveryOfOrder.deliveryLocation = restaurant.restaurantLocation ;
                    order.deliveryTimeRemains = map.getTime(restaurant.restaurantLocation,order.orderedUser.userLocation) ;
                    restaurant.restaurantOrdersHistory.add(order) ;
                    restaurant.restaurantOrders.remove(orderIndex) ;
                }else if(value.equals("READYFORSENDING")){
                    order.orderStatus = STATUS.READYFORSENDING ;
                    staticArrayLists.OrdersWithoutDeliveryArrayList.add(order) ;
                }else {
                    System.out.println("entered status is not valid!") ;
                }
            }else if (parameter.equals("TIME")){
                int time = Integer.parseInt(value) ;
                if (time > 0){
                    order.deliveryTimeRemains = Long.parseLong(Integer.toString(time)) ;
                }else {
                    System.out.println("entered time is not valid !");
                }
            }
        }else {
            System.out.println("This id doesn't exist!");
        }
    }
    public static void showOrderHistory(){
        Restaurant restaurant = Restaurant.loggedInRestaurantForAdmin ;
        if (restaurant.restaurantOrdersHistory.size() == 0){
            System.out.println("there is no order in the history !");
        }else {
            for (int i = 0 ; i < restaurant.restaurantOrdersHistory.size() ; i++){
                Order order = restaurant.restaurantOrdersHistory.get(i) ;
                System.out.print((i+1)+". ") ;
                for (int j = 0 ; j < order.orderFoods.size() ; j++){
                    System.out.println("  "+order.orderFoods.get(j).foodName+" * "+order.orderFoods.get(j).foodID);
                }
            }
        }
    }
    public static void showAllAvailableRestaurants(StaticArrayLists staticArrayLists){
        if (staticArrayLists.allRestaurantsArrayList.size() == 0)
            System.out.println("Sorry, There is no available restaurant at this time !!!");
        else {
            System.out.println("Here is the list of available restaurants for you:");
            User user = (User) Role.loggedInRole ;
            int userLocation = user.userLocation ;
            for (int i = 0 ; i < staticArrayLists.allRestaurantsArrayList.size() ; i++){
                if ( Math.abs(staticArrayLists.allRestaurantsArrayList.get(i).restaurantLocation - userLocation) < 300 ){
                    System.out.println("Restaurant \"" + staticArrayLists.allRestaurantsArrayList.get(i).restaurantName + "\" -> with the ID \"" + staticArrayLists.allRestaurantsArrayList.get(i).restaurantID + "\"");
                }
            }
            /*
            for (int i=0;i<((User)Role.loggedInRole).userOrders.size();i++)
                for (int j=0;j<((User)Role.loggedInRole).userOrders.get(i).orderedRestaurant.restaurantFoodType.size();j++)
                    for (int k=0;k<staticArrayLists.allRestaurantsArrayList.size();k++)
                        for (int l = 0;l<staticArrayLists.allRestaurantsArrayList.get(k).restaurantFoodType.size();l++)
                            if (((User)Role.loggedInRole).userOrders.get(i).orderedRestaurant.restaurantFoodType.get(j).equals(staticArrayLists.allRestaurantsArrayList.get(k).restaurantFoodType.get(l)))
                                System.out.println("Restaurant \"" + staticArrayLists.allRestaurantsArrayList.get(k).restaurantName + "\" -> with the ID \"" + staticArrayLists.allRestaurantsArrayList.get(k).restaurantID + "\"");
            for (int i=0;i<((User)Role.loggedInRole).userOrders.size();i++)
                for (int j=0;j<((User)Role.loggedInRole).userOrders.get(i).orderedRestaurant.restaurantFoodType.size();j++)
                    for (int k=0;k<staticArrayLists.allRestaurantsArrayList.size();k++)
                        for (int l = 0;l<staticArrayLists.allRestaurantsArrayList.get(k).restaurantFoodType.size();l++)
                            if (!((User)Role.loggedInRole).userOrders.get(i).orderedRestaurant.restaurantFoodType.get(j).equals(staticArrayLists.allRestaurantsArrayList.get(k).restaurantFoodType.get(l)))
                                System.out.println("Restaurant \"" + staticArrayLists.allRestaurantsArrayList.get(k).restaurantName + "\" -> with the ID \"" + staticArrayLists.allRestaurantsArrayList.get(k).restaurantID + "\"");
        */
        }
    }
    public static void ShowRelatedRestaurants (String name,StaticArrayLists staticArrayLists){
        boolean found = false;
        for (int i = 0; i < staticArrayLists.allRestaurantsArrayList.size(); i++)
            if (staticArrayLists.allRestaurantsArrayList.get(i).restaurantName.indexOf(name) != -1)
                found = true;
        if (found) {
            System.out.println("Related restaurants with the name \"" + name + "\":");
            for (int i = 0; i < staticArrayLists.allRestaurantsArrayList.size(); i++)
                if (staticArrayLists.allRestaurantsArrayList.get(i).restaurantName.indexOf(name) != -1)
                    System.out.println("Restaurant \"" + staticArrayLists.allRestaurantsArrayList.get(i).restaurantName + "\" -> with the ID \"" + staticArrayLists.allRestaurantsArrayList.get(i).restaurantID + "\" found!");
        } else
            System.out.println("There is no related restaurant with this name !!!");
    }
    public static void selectRestaurant (String restaurantID,StaticArrayLists staticArrayLists){
        int k = -1;
        for (int i = 0; i < staticArrayLists.allRestaurantsArrayList.size(); i++)
            if (staticArrayLists.allRestaurantsArrayList.get(i).restaurantID.equals(restaurantID))
                k = i;
        if (k != -1) {
            Restaurant.loggedInRestaurantForUser = staticArrayLists.allRestaurantsArrayList.get(k);
            System.out.println("Restaurant with the ID \"" + restaurantID + "\" selected!");
            showMenuForUser(Restaurant.loggedInRestaurantForUser);
        }
        else
            System.out.println("There is no related restaurant with this ID !!!");

    }
    public static void showMenuForUser(Restaurant restaurant){
        if (restaurant.restaurantMenu.size() == 0)
            System.out.println("Sorry, There is no available food for you at this time !!!");
        else {
            System.out.println("Here is the list of foods at this restaurant:");
            for (int i = 0; i < restaurant.restaurantMenu.size(); i++)
                System.out.println("Food \"" + restaurant.restaurantMenu.get(i).foodName + "\" with the ID \"" + restaurant.restaurantMenu.get(i).foodID + "\" and the price " + restaurant.restaurantMenu.get(i).foodCost + "$");
        }
    }
    public static void ShowRelatedFoods (String name){
        boolean found = false;
        for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantMenu.size(); i++)
            if (Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodName.indexOf(name) != -1)
                found = true;
        if (found) {
            System.out.println("Related foods with the name \"" + name + "\":");
            for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantMenu.size(); i++)
                if (Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodName.indexOf(name) != -1)
                    System.out.println("Food \"" + Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodName + "\" -> with the ID \"" + Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodID + "\" found!");
        } else
            System.out.println("There is no related food with this name !!!");
    }
    public static void selectFood (String foodID){
        int k = -1;
        for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantMenu.size(); i++)
            if (Restaurant.loggedInRestaurantForUser.restaurantMenu.get(i).foodID.equals(foodID))
                k = i;
        if (k != -1) {
            Food.selectedFoodForUser = Restaurant.loggedInRestaurantForUser.restaurantMenu.get(k);
            System.out.println("Food with the ID \"" + foodID + "\" selected!");
        }
        else
            System.out.println("There is no related food with this ID !!!");
    }
    public static void displayFoodRating(User user, Food food){
        if (food.foodRatingsArrayList.size() == 0)
            System.out.println("Nobody rates this food yet !!!");
        else {
            int k = -1;
            for (int i = 0;i<food.foodRatingsArrayList.size();i++)
                if (food.foodRatingsArrayList.get(i).ratedUser == user)
                    k = i;
            if (k != -1)
                System.out.println("You have already rated \"" + food.foodRatingsArrayList.get(k).rating + "\" with the ID:" + food.foodRatingsArrayList.get(k).ratingID + " to this food ");
            else
                System.out.println("You haven't rate this food yet");
            System.out.println("This food gets the rate of \"" + food.getRating() + "\" from the Users!");
        }
    }
    public static void displayRestaurantRating(User user, Restaurant restaurant){
        if (restaurant.restaurantRatingsArrayList.size() == 0)
            System.out.println("Nobody rates this restaurant yet !!!");
        else {
            int k = -1;
            for (int i = 0;i<restaurant.restaurantRatingsArrayList.size();i++)
                if (restaurant.restaurantRatingsArrayList.get(i).ratedUser == user)
                    k = i;
            if (k != -1)
                System.out.println("You have already rated \"" + restaurant.restaurantRatingsArrayList.get(k).rating + "\" with the ID:" + restaurant.restaurantRatingsArrayList.get(k).ratingID + " to this food ");
            else
                System.out.println("You haven't rate this food yet");
            System.out.println("This food gets the rate of \"" + restaurant.getRating() + "\" from the Users!");
        }
    }

    public static void showRestaurantComments (Restaurant restaurant){
        if (restaurant.restaurantCommentsArrayList.size() == 0)
            System.out.println("There is no comments about this restaurant!");
        else {
            System.out.println("Comments:");
            for (int i = 0; i < restaurant.restaurantCommentsArrayList.size(); i++)
                System.out.println("Comment ID: \""+ restaurant.restaurantCommentsArrayList.get(i).commentID + "\" -> \"" + restaurant.restaurantCommentsArrayList.get(i).commentedUser.userID + "\" says: " + restaurant.restaurantCommentsArrayList.get(i).comment);
        }
    }
    public static void getRestaurantComment(Restaurant restaurant,StaticArrayLists staticArrayLists){
        System.out.print("Please enter your comment about this restaurant \"" + restaurant.restaurantName + "\": ");
        String comment = scanner.nextLine();
        restaurant.restaurantCommentsArrayList.add(new Comment());
        restaurant.restaurantCommentsArrayList.get(restaurant.restaurantCommentsArrayList.size()-1).comment = comment;
        restaurant.restaurantCommentsArrayList.get(restaurant.restaurantCommentsArrayList.size()-1).commentID = setID("comment",staticArrayLists);
        restaurant.restaurantCommentsArrayList.get(restaurant.restaurantCommentsArrayList.size()-1).commentedUser = (User) Role.loggedInRole;
        System.out.println("Thanks for your oponion :)");
    }
    public static void editRestaurantComment(String commentID){
        int k = -1;
        for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantCommentsArrayList.size(); i++)
            if (Restaurant.loggedInRestaurantForUser.restaurantCommentsArrayList.get(i).commentID.equals(commentID))
                k = i;
        if (k == -1)
            System.out.println("Sorry, There is no comment with this ID !!!");
        else if (k != -1 && !Role.loggedInRole.equals(Restaurant.loggedInRestaurantForUser.restaurantCommentsArrayList.get(k).commentedUser))
            System.out.println("Sorry, You can't edit this comment because it's Not yours !!!");
        else {
            System.out.print("Please enter your new comment: ");
            String newComment = scanner.nextLine();
            Restaurant.loggedInRestaurantForUser.restaurantCommentsArrayList.get(k).comment = newComment;
            System.out.println("Thanks");
        }
    }
    public static void getRestaurantRating (StaticArrayLists staticArrayLists,User user,Restaurant restaurant){
        int k = -1;
        for (int i=0;i<restaurant.restaurantRatingsArrayList.size();i++)
            if (restaurant.restaurantRatingsArrayList.get(i).ratedUser == user)
                k = i;
        if (k != -1){
            System.out.println("You have already rated this food, now you can edit that using ratingID.");
        }
        else {
            System.out.print("Please enter your rating to this restaurant: (0 to 5)");
            double rating  = scanner.nextDouble();
            if (rating >= 0 && rating <= 5){
                System.out.println("Thanks for your rating :)");
                Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.add(new Rating());
                Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.size()-1).rating = rating;
                Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.size()-1).ratingID = setID("rating",staticArrayLists);
                Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.size()-1).ratedUser = (User) Role.loggedInRole;
            } else
                System.out.println("Sorry, You should rate from 0 to 5 !!!");
        }
    }
    public static void editRestaurantRating(String ratingID){
        int k = -1;
        for (int i = 0; i < Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.size(); i++)
            if (Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(i).ratingID == ratingID)
                k = i;
        if (k == -1)
            System.out.println("Sorry, There is no rating with this ID !!!");
        else if (k != -1 && !Role.loggedInRole.equals(Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(k).ratedUser))
            System.out.println("Sorry, You can't edit this rating because it's Not yours !!!");
        else {
            System.out.print("Please enter your new rating: ");
            double newRating = scanner.nextDouble();
            Restaurant.loggedInRestaurantForUser.restaurantRatingsArrayList.get(k).rating = newRating;
            System.out.println("Thanks");
        }
    }
    public static void getFoodComment(Food food,StaticArrayLists staticArrayLists){
        System.out.print("Please enter your comment about this food \"" + food.foodName + "\": ");
        String comment = scanner.nextLine();
        food.foodCommentsArrayList.add(new Comment());
        food.foodCommentsArrayList.get(food.foodCommentsArrayList.size()-1).comment = comment;
        food.foodCommentsArrayList.get(food.foodCommentsArrayList.size()-1).commentID = setID("comment",staticArrayLists);
        food.foodCommentsArrayList.get(food.foodCommentsArrayList.size()-1).commentedUser = (User) Role.loggedInRole;
        System.out.println("Thanks for your oponion :)");
    }
    public static void editFoodComment(String commentID){
        int k = -1;
        for (int i = 0; i < Food.selectedFoodForUser.foodCommentsArrayList.size(); i++)
            if (Food.selectedFoodForUser.foodCommentsArrayList.get(i).commentID.equals(commentID))
                k = i;
        if (k == -1)
            System.out.println("Sorry, There is no comment with this ID !!!");
        else if (k != -1 && !Role.loggedInRole.equals(Food.selectedFoodForUser.foodCommentsArrayList.get(k).commentedUser))
            System.out.println("Sorry, You can't edit this comment because it's Not yours !!!");
        else {
            System.out.print("Please enter your new comment: ");
            String newComment = scanner.nextLine();
            Food.selectedFoodForUser.foodCommentsArrayList.get(k).comment = newComment;
            System.out.println("Thanks");
        }
    }
    public static void getFoodRating (StaticArrayLists staticArrayLists,User user,Food food){
        int k = -1;
        for (int i=0;i<food.foodRatingsArrayList.size();i++)
            if (food.foodRatingsArrayList.get(i).ratedUser == user)
                k = i;
        if (k != -1){
            System.out.println("You have already rated this food, now you can edit that using ratingID.");
        }
        else{
            System.out.print("Please enter your rating to this food: (0 to 5)");
            double rating  = scanner.nextDouble();
            if (rating >= 0 && rating <= 5){
                System.out.println("Thanks for your rating :)");
                Food.selectedFoodForUser.foodRatingsArrayList.add(new Rating());
                Food.selectedFoodForUser.foodRatingsArrayList.get(Food.selectedFoodForUser.foodRatingsArrayList.size()-1).rating = rating;
                Food.selectedFoodForUser.foodRatingsArrayList.get(Food.selectedFoodForUser.foodRatingsArrayList.size()-1).ratingID = setID("rating",staticArrayLists);
                Food.selectedFoodForUser.foodRatingsArrayList.get(Food.selectedFoodForUser.foodRatingsArrayList.size()-1).ratedUser = (User) Role.loggedInRole;
            } else
                System.out.println("Sorry, You should rate from 0 to 5 !!!");
        }
    }
    public static void editFoodRating(String ratingID){
        int k = -1;
        for (int i = 0; i < Food.selectedFoodForUser.foodRatingsArrayList.size(); i++)
            if (Food.selectedFoodForUser.foodRatingsArrayList.get(i).ratingID == ratingID)
                k = i;
        if (k == -1)
            System.out.println("Sorry, There is no rating with this ID !!!");
        else if (k != -1 && !Role.loggedInRole.equals(Food.selectedFoodForUser.foodRatingsArrayList.get(k).ratedUser))
            System.out.println("Sorry, You can't edit this rating because it's Not yours !!!");
        else {
            System.out.print("Please enter your new rating: ");
            double newRating = scanner.nextDouble();
            Food.selectedFoodForUser.foodRatingsArrayList.get(k).rating = newRating;
            System.out.println("Thanks");
        }
    }
    public static void showFoodComments (Food food){
        if (Food.selectedFoodForUser.foodCommentsArrayList.size() == 0)
            System.out.println("There is no comments about this food!");
        else {
            System.out.println("Comments:");
            for (int i = 0; i < Food.selectedFoodForUser.foodCommentsArrayList.size(); i++)
                System.out.println("Comment ID: \""+ Food.selectedFoodForUser.foodCommentsArrayList.get(i).commentID + "\" -> \"" + Food.selectedFoodForUser.foodCommentsArrayList.get(i).commentedUser.userID + "\" says: " + Food.selectedFoodForUser.foodCommentsArrayList.get(i).comment);
        }
    }
    public static void showOrdersHistory(User user){
        System.out.println("Orders history for you:");
        for (int i = 0; i < user.userOrders.size(); i++){
            System.out.print(user.userOrders.get(i).orderID + "- You ordered ");
            for (int j=0;j<user.userOrders.get(i).orderFoods.size();j++)
                System.out.print("\"" + user.userOrders.get(i).orderFoods.get(j).foodName + "\" ");
            System.out.println();
        }
    }
    public static void selectOrder(String orderID){
        int k = -1;
        for (int i = 0; i < ((User)Role.loggedInRole).userOrders.size(); i++)
            if (((User)Role.loggedInRole).userOrders.get(i).orderID.equals(orderID))
                k = i;
        if (k != -1 ) {
            Order.LoggedInOrderForUser = ((User) Role.loggedInRole).userOrders.get(k);
            System.out.println("Order with the ID \"" + orderID + "\" selected!");
            int orderSize = ((User) Role.loggedInRole).userOrders.get(k).orderFoods.size();
            if (orderSize != 0) {
                double totalCost = 0;
                System.out.println("Your order contains this foods:");
                for (int i = 0; i < orderSize; i++) {
                    System.out.print("Food \"" + ((User) Role.loggedInRole).userOrders.get(k).orderFoods.get(i).foodName + "\" ");
                    System.out.print("with the ID \"" + ((User) Role.loggedInRole).userOrders.get(k).orderFoods.get(i).foodID + "\" ");
                    System.out.println("with the price " + ((User) Role.loggedInRole).userOrders.get(k).orderFoods.get(i).foodCost + "$");
                    totalCost += ((User) Role.loggedInRole).userOrders.get(k).orderFoods.get(i).foodCost;
                }
                System.out.println("The money that you should pay is " + totalCost + "$");
            } else
                System.out.println("You add no foods in this Order!");
        } else
            System.out.println("There is no Order with this ID !!!");
    }
    public static void showCartStatus(User user){
        if (user.userCart.cartorders.size() == 0)
            System.out.println("You have no orders in your cart");
        else
            for (int i=0;i<user.userCart.cartorders.size();i++){
                System.out.print((i+1) + "-> From the restaurant \"" + user.userCart.cartorders.get(i).orderedRestaurant.restaurantName + "\" ");
                System.out.print("with the total cost " + user.userCart.cartorders.get(i).getOrderCost() + "$ ");
                System.out.println("and the ID \"" + user.userCart.cartorders.get(i).orderID + "\".");
            }
    }
    public static void confirmOrder(User user, String orderID){
        int k = -1;
        for (int i = 0; i < user.userCart.cartorders.size(); i++)
            if (user.userCart.cartorders.get(i).orderID.equals(orderID))
                k = i;
        if (k != -1 && user.userCart.cartorders.get(k).getOrderCost() <= user.getAccountCharge()){
            user.userOrders.add(user.userCart.cartorders.get(k));
            user.setAccountCharge(user.getAccountCharge() - user.userCart.cartorders.get(k).getOrderCost()) ;
            user.userCart.cartorders.get(k).orderedRestaurant.restaurantOrders.add(user.userCart.cartorders.get(k)) ;
            user.userCart.cartorders.get(k).orderStatus = STATUS.COOKING ;
            user.userCart.cartorders.remove(k) ;
            System.out.println("Order successfully confirmed");
        } else if (k != -1 && user.userCart.cartorders.get(k).getOrderCost() > user.getAccountCharge()){
            System.out.println("Please charge your account first !!!");
        }
        else
            System.out.println("There is no order with this ID !!!");
    }
    public static void chargeAccount(User user) {
        System.out.print("How much would you want to charge? ");
        double newCharge = scanner.nextDouble();
        if (newCharge >= 0) {
            System.out.println("Account charged successfully :)");
            user.setAccountCharge(newCharge);
        }
        else
            System.out.println("Sorry, you should enter a positive value !!!");
    }
    public static void showAccountCharge(User user){
        System.out.println("You have " + user.getAccountCharge() + "$ in your account :)");
    }
    public static void addFoodToCart(User user,Restaurant restaurant,Food food,StaticArrayLists staticArrayLists){
        int l = -1;
        for (int i = 0; i < staticArrayLists.userStaticArrayList.size(); i++)
            if (user.userID.equals(staticArrayLists.userStaticArrayList.get(i).userID))
                l = i;
        if (user.userCart.cartorders.size() != 0){
            int k = -1;
            for (int i=0 ; i < user.userCart.cartorders.size() ; i++)
                if (restaurant.restaurantID.equals(user.userCart.cartorders.get(i).orderedRestaurant.restaurantID))
                    k = i ;
            if (k == -1){
                Order order = new Order(Functions.setID("order",staticArrayLists)) ;
                order.orderFoods.add(food)  ;
                order.orderedRestaurant = restaurant ;
                order.orderedUser = user ;
                staticArrayLists.userStaticArrayList.get(l).userCart.cartorders.add(order) ;
                System.out.println("Added successfully");
            }else {
                staticArrayLists.userStaticArrayList.get(l).userCart.cartorders.get(k).orderFoods.add(food);
                System.out.println("Added successfully");
            }
        }
        else{
            Order order = new Order(Functions.setID("order",staticArrayLists)) ;
            order.orderFoods.add(food)  ;
            order.orderedRestaurant = restaurant ;
            order.orderedUser = user ;
            staticArrayLists.userStaticArrayList.get(l).userCart.cartorders.add(order) ;
            System.out.println("Added successfully");
        }
    }
    public static int setRandomLocation(StaticArrayLists staticArrayLists){
        int randomNumber = (int) ( Math.random() * 1000 ) ;
        boolean locationExistance = false ;
        for (int i = 0 ; i < staticArrayLists.LocationArrayList.size() ; i++){
            if (staticArrayLists.LocationArrayList.get(i) == randomNumber){
                locationExistance = true ;
            }
        }
        if (locationExistance){
            return setRandomLocation(staticArrayLists);
        }else {
            return randomNumber ;
        }
    }
    public static void showOrdersListForDelivery(StaticArrayLists staticArrayLists,Map map){
        Delivery delivery = (Delivery) Role.loggedInRole ;
        Order order = delivery.activeOrder ;
        if (delivery.activeOrderBoolean){
            ArrayList<Integer> Path = map.getPath(delivery.deliveryLocation,order.orderedRestaurant.restaurantLocation) ;
            for (int i = 0 ; i < Path.size() ; i++){
                if (i != Path.size()-1){
                    System.out.println(Path.get(i) + " -> ") ;
                }else {
                    System.out.println(Path.get(i)) ;
                }
            }
        }else {
            System.out.println("orders without delivery : ") ;
            for (int i = 0 ; i < staticArrayLists.OrdersWithoutDeliveryArrayList.size() ; i++){
                System.out.println( (i+1) +". order Id : "+ staticArrayLists.OrdersWithoutDeliveryArrayList.get(i).orderID + " * Restaurant : " + staticArrayLists.OrdersWithoutDeliveryArrayList.get(i).orderedRestaurant.restaurantName + " * Restaurant Location : " + staticArrayLists.OrdersWithoutDeliveryArrayList.get(i).orderedRestaurant.restaurantLocation);
            }
        }
    }
    public static void selectOrderForDelivery(StaticArrayLists staticArrayLists,String orderID,Map map){
        Delivery delivery = (Delivery) Role.loggedInRole ;
        boolean orderExistance = false ;
        int orderIndex = 0 ;
        for (int i = 0 ; i < staticArrayLists.OrdersWithoutDeliveryArrayList.size() ; i++){
            if(staticArrayLists.OrdersWithoutDeliveryArrayList.get(i).orderID.equals(orderID)){
                orderExistance = true ;
                orderIndex = i ;
            }
        }
        if (orderExistance){
            Order order = staticArrayLists.OrdersWithoutDeliveryArrayList.get(orderIndex) ;
            delivery.activeOrder = order ;
            order.deliveryOfOrder = delivery ;
            staticArrayLists.StaticOrderDelivery = order ;
            delivery.activeOrderBoolean = true ;
            System.out.println("order seleceted : ") ;
            ArrayList<Integer> Path = map.getPath(delivery.deliveryLocation,order.orderedRestaurant.restaurantLocation) ;
            for (int i = 0 ; i < Path.size() ; i++){
                if (i != Path.size()-1){
                    System.out.println(Path.get(i) + " -> ") ;
                }else {
                    System.out.println(Path.get(i)) ;
                }
            }
        }else {
            System.out.println("there is no order with this order ID ... ") ;
        }
    }
    public static void orderDelivered(StaticArrayLists staticArrayLists){
        Order order = staticArrayLists.StaticOrderDelivery ;
        Delivery delivery = order.deliveryOfOrder ;
        order.orderStatus = STATUS.DELIVERED ;
        order.deliveryTimeRemains = Long.parseLong("0") ;
        order.deliveryOfOrder.deliveryLocation = order.orderedUser.userLocation ;
        delivery.activeOrderBoolean = false ;
    }
}