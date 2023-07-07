package Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    public ArrayList<Order> cartorders = new ArrayList<Order>();
}