package Map;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable {
    private final Dijkstra<Integer> dijkstra;

    public Map(String MapPath) {
        dijkstra = new Dijkstra<Integer>(MapPath);
    }

    public Boolean search(Integer start, Integer end) {
        return dijkstra.search(start, end);
    }

    public Long getCost(Integer start, Integer end) {
        if(dijkstra.isSame(start, end)) return dijkstra.getCost();
        if(!dijkstra.search(start,end)) return null;  // there is no path
        return dijkstra.getCost();
    }

    /*
    public static Long getCost(String path, Integer start, Integer end) {
        Dijkstra<Integer> dijkstra1 = new Dijkstra<Integer>(path);
        if(!dijkstra1.search(start, end)) return null;
        return dijkstra1.getCost();
    }
     */
    public Long getCost() {
        return dijkstra.getCost();
    }

    public ArrayList<Integer> getPath(Integer start, Integer end) {
        //dijkstra.search(start,end) ;
        if(dijkstra.isSame(start, end)) return dijkstra.getPath();
        if(!dijkstra.search(start, end)) return null;  // there is no path
        return dijkstra.getPath();
    }

    /*
    public static ArrayList<Integer> getPath(String path, Integer start, Integer end) {
        Dijkstra<Integer> dijkstra1 = new Dijkstra<Integer>(path);
        if(!dijkstra1.search(start, end)) return null;
        return dijkstra1.getPath();
    }
    */

    public ArrayList<Integer> getPath() {
        return dijkstra.getPath();
    }

    public Long getTime(Integer start, Integer end) {
        if(dijkstra.isSame(start, end)) return dijkstra.getTime();
        if(!dijkstra.search(start, end)) return null; // there is no path
        return dijkstra.getTime();
    }

    /*
    public static Long getTime(String path, Integer start, Integer end) {
        Dijkstra<Integer> dijkstra1 = new Dijkstra<Integer>(path);
        if(!dijkstra1.search(start, end)) return null;
        return dijkstra1.getTime();
    }
    */

    public Long getTime() {
        return dijkstra.getTime();
    }
}