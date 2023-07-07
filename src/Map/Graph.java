package Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Node is for storing graph vertices data
 * T is Node name's class
 * Note: Node is equivalent to city in Map
 */

class Node<T> implements Serializable {
    private T name;
    // cost is used in dijketsra alg: it is the cost of path from root to this Node
    private Long cost;
    // isVisited is used in dijkestra alg: for avoiding infinite loop in alg
    private Boolean isVisited;
    // parent is used in dijkestra alg: for evaluating the path, parent is the previous node
    private Node<T> parent;
    // neighbors holds this Node's neighbor nodes with the weight, Equivalent to neighbors edges
    private Hashtable<Node<T>, Long> neighbors;

    protected Node(T name) {
        this.name = name;
        this.neighbors = new Hashtable<>();
        this.cost = Long.MAX_VALUE;          // infinity
        this.isVisited = false;
        this.parent = null;
    }

    /*
     * adding a neighbor (Equivalent to edge with weight)
     * neoghbor: the Node will be added as neighbor, its class has to be the same as this Node's class
     * wight:    the weight between this Node and neighbor Node
     * Note:     the direction of the edge is from this Node to neighbor Node ( thisNode -----> neighborNode )
     * output:   flase if neighbor exists otherwise true
     */

    public Boolean addNeighbor(Node<T> neighbor, Long weight) {
        if(this.containsNeighbor(neighbor)) return false;
        this.neighbors.put(neighbor, weight);
        return true;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public void setAsVisited() {
        this.isVisited = true;
    }

    @Override
    public boolean equals(Object obj) {
        return !(obj instanceof Node<?>) ? false : ((Node<?>) obj).getName() == this.name;
    }

    public T getName() {
        return name;
    }

    public Long getCost() {
        return cost;
    }

    public Boolean isVisited() {
        return isVisited;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Long getWeight(Node<T> neighbor) {
        if(neighbor == null) return null;
        Enumeration<Node<T>> neis = neighbors.keys();
        while(neis.hasMoreElements()) {
            Node<?> node = neis.nextElement();
            if(node.equals(neighbor)) return neighbors.get(node);
        } return null;
    }

    public ArrayList<Node<T>> getNeighbors() {
        Enumeration<Node<T>> neis = neighbors.keys();
        ArrayList<Node<T>> arrayNeighbors = new ArrayList<>();
        while(neis.hasMoreElements()) arrayNeighbors.add(neis.nextElement());
        return arrayNeighbors;
    }

    public Boolean containsNeighbor(Node<T> neighbor) {
        return getWeight(neighbor) == null ? false : true;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    @Override
    public Node<T> clone() {
        Node<T> node = new Node<>(this.name);
        return node;
    }
}

/*
 * Graph is for storing graph's data including vertices and edges
 * T is graph's nodes class
 * Graph is for modeling Map, Nodes is cities (or homes or ...) and Edges (or weight between nodes) is the cost of path between cities (or homes or ...)
 */

public class Graph<T> implements Serializable {
    private Integer nodeSize;
    private Integer edgeSize;
    private Hashtable<Node<T>, Hashtable<Node<T>, Long>> graph = null; // graph is in fact edges data but holds all data about graph
    private ArrayList<Node<T>> nodes;

    protected Graph(Integer nodeSize, Integer edgeSize) {
        this.nodeSize = nodeSize;
        this.edgeSize = edgeSize;
        this.nodes = new ArrayList<>(nodeSize);
    }

    protected Graph(Integer nodeSize) {
        this(nodeSize, Integer.MAX_VALUE);
    }

    protected Graph() {
        this(Integer.MAX_VALUE);
    }

    public void updateGraph(T node, T neighborNode, Long weight) {
        if(weight == null) weight = 0L;
        Node<T> newNode = new Node<>(node);
        Node<T> neighbor = new Node<>(neighborNode);
        int indexNode = nodes.indexOf(newNode);
        int indexNeighbor = nodes.indexOf(neighbor);
        if(indexNode >= 0) {
            if(indexNeighbor >= 0) {
                nodes.get(indexNode).addNeighbor(nodes.get(indexNeighbor), weight);
            } else {
                nodes.get(indexNode).addNeighbor(neighbor, weight);
                nodes.add(neighbor);
            }
        } else {
            if(indexNeighbor >= 0) {
                newNode.addNeighbor(nodes.get(indexNeighbor), weight);
                nodes.add(newNode);
            } else {
                newNode.addNeighbor(neighbor, weight);
                nodes.add(newNode);
                nodes.add(neighbor);

            }
        }
    }

    public void updateGraph(T node, T neighborNode) {
        this.updateGraph(node, neighborNode, 0L);
    }

    public void updateGraph(T node) {
        this.updateGraph(node, null, 0L);
    }

    public ArrayList<Node<T>> getNodes() {
        return nodes;
    }

    public Node<T> getNode(T node) {
        int index = nodes.indexOf(new Node<T>(node));
        if(index < 0) return null;
        return nodes.get(index);
    }

    public ArrayList<T> getNodesName() {
        ArrayList<T> names = new ArrayList<>();
        for(Node<T> n : nodes) names.add(n.getName());
        return names;
    }

    public Hashtable<T,Hashtable<T,Long>> getGraph() {
        Hashtable<T, Hashtable<T, Long>> graph = new Hashtable<>();
        for(Node<T> node : nodes) {
            Hashtable<T, Long> neighbors = new Hashtable<>();
            ArrayList<Node<T>> arrayNeighbors = node.getNeighbors();
            for(Node<T> neighbor : arrayNeighbors) neighbors.put(neighbor.getName(), node.getWeight(neighbor));
            graph.put(node.getName(), neighbors);
        } return graph;
    }

    public Long getWeight(T node1, T node2) {
        int index = nodes.indexOf(new Node<T>(node1));
        if(index < 0) return null;
        return nodes.get(index).getWeight(new Node<T>(node2));
    }

    public ArrayList<Node<T>> getNeighborNodes(T node) {
        int index = nodes.indexOf(new Node<T>(node));
        if(index < 0) return null;
        return nodes.get(index).getNeighbors();
    }

    public ArrayList<T> getNeighborNames(T node) {
        ArrayList<T> names = new ArrayList<>();
        ArrayList<Node<T>> nodes = this.getNeighborNodes(node);
        if(nodes == null) return names;
        for(Node<T> n : nodes) names.add(n.getName());
        return names;
    }

    public Integer getNodeSize() {
        return this.nodeSize;
    }

    public static Graph readGraph(String path) {
        File file = new File(path);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Integer nodeSize = scanner.hasNextInt() ? scanner.nextInt() : null;
        Integer edgeSize = scanner.hasNextInt() ? scanner.nextInt() : null;
        Graph<Long> graph = new Graph(nodeSize, edgeSize);

        while(scanner.hasNextLong())
            graph.updateGraph(scanner.nextLong(),
                    scanner.hasNextLong() ? scanner.nextLong() : null,
                    scanner.hasNextLong() ? scanner.nextLong() : null);

        return graph;
    }

    @Override
    public Graph clone() {
        Graph<T> graph = new Graph(this.nodeSize,this.edgeSize);
        for(Node<T> node : this.nodes) {
            for(Node<T> neighbor : node.getNeighbors()) {
                graph.updateGraph(node.getName(), neighbor.getName(), node.getWeight(neighbor));
            }
        } return graph;
    }
}