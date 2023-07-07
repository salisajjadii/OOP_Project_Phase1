//package Map;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class Main {
//    public static void main(String[] args) throws IOException {
//        Graph<Long> graph = Graph.readGraph("F:\\CP Projects\\OOP\\OOP project\\src\\Map\\graph.txt");
//        Dijkstra<Long> dijkstra = new Dijkstra<>(graph);
//
//        File file = new File("F:\\CP Projects\\OOP\\OOP project\\src\\Map\\out.txt");
//        FileWriter fileWriter = new FileWriter(file);
//        fileWriter.write("root,target,path,cost\n");
//
//        for(Long root = 1L ; root <= 100 ; root++) {
//            for(Long target = root + 1; target <= 100 ; target++) {
//                System.out.println("searching for root = "+root+" and target = "+target);
//                fileWriter.write(String.valueOf(root)+","+String.valueOf(target)+",");
//                if(dijkstra.search(root, target)) {
//                    System.out.println("path found: "+dijkstra.getPath());
//                    System.out.println("cost: "+dijkstra.getCost());
//                    fileWriter.write(dijkstra.getPath().toString().replaceAll(","," <-").replaceAll("\\]","").replaceAll("\\[", "")+
//                    ","+dijkstra.getCost().toString());
//                } else {
//                    System.out.println("there is no path");
//                    fileWriter.write("null,null");
//                }
//                System.out.println("-----------------------------------------------------");
//                fileWriter.write("\n");
//            }
//        }
//        fileWriter.close();
//    }
//}

package Map;

import java.io.IOException;


public class Main {
    public static void main(Long root,Long target)  throws IOException {
        String path = "F:\\CP Projects\\OOP\\OOP project\\src\\Map\\graph.txt";

        Dijkstra<Long> dijkstra = new Dijkstra<Long>(path);

        if(dijkstra.search(root, target)) {
            dijkstra.getCost();
            dijkstra.getPath();
        }

    }
}