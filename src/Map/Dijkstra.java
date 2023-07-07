package Map;

import java.io.Serializable;
import java.util.ArrayList;

public class Dijkstra<T> implements Serializable {
    private Graph<T> graph;
    private Node<T> target, root;
    private ArrayList<Node<T>> path = null;
    private Long cost = null;
    private static final int TIME = 60;

    public Dijkstra(Graph<T> graph) {
        this.graph = graph;
    }

    protected Dijkstra(String path) {
        this(Graph.readGraph(path));
    }

    public Boolean search(T root, T target) {
        Graph<T> graph = this.graph.clone();

        this.root = graph.getNode(root);
        this.target = graph.getNode(target);
        ArrayList<Node<T>> neighbors = this.root.getNeighbors();
        for(Node<T> neighbor : neighbors) {
            neighbor.setCost(this.root.getWeight(neighbor));
            neighbor.setParent(this.root);
        } this.root.setCost(null);

        Node<T> node = findLowestCostNode(graph);
        while(node != null) {
            Long cost = node.getCost();
            neighbors = node.getNeighbors();
            for(Node<T> neighbor : neighbors) {
                Long newCost = cost + node.getWeight(neighbor);
                if(newCost < neighbor.getCost()) {
                    neighbor.setCost(newCost);
                    neighbor.setParent(node);
                }
            } node.setAsVisited();
            node = findLowestCostNode(graph);
        } if(this.target.getCost() != Long.MAX_VALUE) calcPathAndCost();

        return (this.target.getCost() != Long.MAX_VALUE);
    }

    private Node<T> findLowestCostNode(Graph graph) {
        Long lowestCost = Long.MAX_VALUE;
        Node<T> lowestCostNode = null;
        ArrayList<Node<T>> nodes = graph.getNodes();
        for(Node<T> node : nodes) {
            if(!node.isVisited() && !node.equals(root)) {
                if(node.getCost() < lowestCost) {
                    lowestCost = node.getCost();
                    lowestCostNode = node;
                }
            }
        } return lowestCost == Long.MAX_VALUE ? null : lowestCostNode;
    }

    private void calcPathAndCost() {
        ArrayList<Node<T>> path = new ArrayList<>();
        Long cost = 0L;
        Node<T> node = target;
        while(node != null) {
            Node<T> parent = node.getParent();
            path.add(node);
            if(parent != null) cost += parent.getWeight(node);
            node = parent;
        }
        this.path = path;
        this.cost = cost;
    }

    public ArrayList<T> getPath() {
        ArrayList<T> p = new ArrayList<>();
        for(int i = path.size()-1; i >= 0; i--) p.add(path.get(i).getName());
        return p;
    }

    public Long getCost() {
        return cost;
    }

    public Long getTime() {
        return (cost * Dijkstra.TIME);
    }

    public Boolean isSame(T start, T end) {
        return (this.root.getName() == start && this.target.getName() == end);
    }
}