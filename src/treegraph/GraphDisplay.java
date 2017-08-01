package treegraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * Created by Galvin on 5/5/2015.
 */

public class GraphDisplay extends JComponent {

    public GraphDisplay() {
        minX = minY = Double.POSITIVE_INFINITY;
        maxX = maxY = Double.NEGATIVE_INFINITY;
    }

    public synchronized void addNode(Object identifier, double x, double y, Color col) {
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
        nodes.put(identifier, new Node(x, y, col));
        repaint();
    }

    public synchronized void addNode(Object identifier, double x, double y) {
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
        nodes.put(identifier, new Node(x, y, NODE_COLOR));
        repaint();
    }

    public synchronized void addEdge(Object start, Object end, Color c) {
        removeEdge(start, end);
        edges.add(new Edge(start, end, c));
        repaint();
    }

    public synchronized boolean removeEdge(Object start, Object end) {
        Iterator<Edge> it = edges.iterator();
        while (it.hasNext()) {
            Edge tmp = it.next();
            if (tmp.joins(start, end)) {
                it.remove();
                repaint();
                return true;
            }
        }
        return false;
    }

    public void addEdge(Object start, Object end) {
        addEdge(start, end, Color.black);
    }

    public JFrame showInWindow(int width, int height, String title) {
        JFrame f = new JFrame();
        f.add(this);
        f.setSize(width, height);
        f.setTitle(title);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        f.setVisible(true);
        return f;
    }

    public void paint(Graphics g) {
        if (nodes.isEmpty())
            return;

        double xscl = (getSize().width - 2 * MARGIN) / (maxX - minX);
        double yscl = (getSize().height - 2 * MARGIN) / (maxY - minY);

        g.translate(+MARGIN, +MARGIN);

        synchronized (this) {
            for (Edge e : edges)
                e.paint(g, xscl, yscl, minX, minY);
            for (int i = 0; i < nodes.size(); i++)
                nodes.get(i).paint(g, xscl, yscl, minX, minY, i);
        }

        g.translate(-MARGIN, -MARGIN);
    }

    protected double minX, maxX, minY, maxY;
    protected HashMap<Object, Node> nodes = new HashMap<Object, Node>();
    protected Vector<Edge> edges = new Vector<Edge>();

    protected int MARGIN = 20;
    protected int NODE_RADIUS = 5;
    protected Color NODE_COLOR = Color.blue.brighter();

    private class Node {
        public Node(double x, double y, Color col) {
            this.x = x;
            this.y = y;
            this.col = col;
        }

        public void paint(Graphics g, double xscl, double yscl, double tx,
                          double ty, int number) {
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(number), (int) ((x - tx) * xscl - NODE_RADIUS), (int) ((y - ty)
                    * yscl - NODE_RADIUS));
            g.setColor(col);
            g.fillOval((int) ((x - tx) * xscl - NODE_RADIUS), (int) ((y - ty)
                    * yscl - NODE_RADIUS), 2 * NODE_RADIUS, 2 * NODE_RADIUS);
        }

        protected double x, y;
        protected Color col;

    }

    private class Edge {
        public Edge(Object start, Object end, Color col) {
            this.start = start;
            this.end = end;
            this.col = col;
        }

        public boolean joins(Object a, Object b) {
            return (start.equals(a) && end.equals(b))
                    || (start.equals(b) && end.equals(a));
        }

        public void paint(Graphics g, double xscl, double yscl, double tx,
                          double ty) {
            Node a = nodes.get(start);
            Node b = nodes.get(end);
            g.setColor(col);
            g.drawLine((int) (xscl * (a.x - tx)), (int) (yscl * (a.y - ty)),
                    (int) (xscl * (b.x - tx)), (int) (yscl * (b.y - ty)));
        }

        protected Object start, end;
        protected Color col;
    }
}

class Graph {

    Map<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
    Map<Integer, Set<Integer>> neighbours = new HashMap<Integer, Set<Integer>>();
    int numEdges;

    // Vertex class with point x and y
    class Vertex {
        Point p;

        public Vertex(Point p) {
            this.p = p;
        }

        public Point getPoint() {
            return p;
        }
    }

    public void addVertex(Point p) {

        neighbours.put(vertices.size(), new HashSet<Integer>());
        //Map the id of the vertex to vertex, which is tracked by the number of vertices
        vertices.put(vertices.size(), new Vertex(p));
    }

    //Add edge between two vertices
    public boolean addEdge(int n1, int n2) {
        // If it already exists, dont add
        if (edgeExist(n1, n2))
            return false;

        neighbours.get(n1).add(n2);
        neighbours.get(n2).add(n1);
        numEdges++;
        return true;
    }

    // Check whether an edge exist or not
    public boolean edgeExist(int n1, int n2) {
        return neighbours.get(n1).contains(n2)
                && neighbours.get(n2).contains(n1);
    }

    //Check for weight between two vertices
    public double getWeight(int n1, int n2) {
        //Only if the edge exist, then return weight
        if (edgeExist(n1, n2)) {
            //Distance gives pythagoras theorem and calculates the difference between two points
            return vertices.get(n1).getPoint().distance(vertices.get(n2).getPoint());
        } else {
            //Returns positive infinity, only occur when you include in the code
            return Double.POSITIVE_INFINITY;
        }
    }

    //Return neighbours of the set identify n1
    public Set<Integer> getNeighbours(int n1) {
        return neighbours.get(n1);
    }

    public int getNumberOfVertices() {
        return vertices.size();
    }

    public int getNumEdges() {
        return numEdges;
    }

}

class GraphTest {

    public GraphTest() {
        int num = 20;

        Graph graph = generateRandomGraph(num, 0.2);

        GraphDisplay gd = new GraphDisplay();
        gd.showInWindow(400, 400, "A Random Graph");

        //Draw the nodes, according to generateRandomGraph has given, and take reference from that
        for (int i = 0; i < num; i++) {
            gd.addNode(i, graph.vertices.get(i).p.x, graph.vertices.get(i).p.y);
        }

        //Draw the edges
        for (int i = 0; i < num; i++)
            for (Integer neighbour : graph.getNeighbours(i))
                gd.addEdge(i, neighbour);

        //Draw edges using prim algorithm
        for (Edge edge : generatePrim(graph)) {
            gd.addEdge(edge.num1, edge.num2, Color.BLUE);
        }

        //Draw edges using kruskal algorithm
        for (Edge edge : generateKruskal(graph)) {
            gd.addEdge(edge.num1, edge.num2, Color.RED);
        }

    }

    //Generate a randomGraph arguments: number of vertices, probability of the edge
    public Graph generateRandomGraph(int num, double prob) {
        Random rand = new Random();

        Graph graph = new Graph();

        //Add num number of vertices
        for (int i = 0; i < num; i++) {
            graph.addVertex(new Point(rand.nextInt(400), rand.nextInt(400)));
        }

        //Add Edges, based on the probability on whether it should be added
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (Math.random() <= prob) {
                    graph.addEdge(i, j);
                }
            }
        }

        return graph;
    }

    //Prim algorithm
    public Set<Edge> generatePrim(Graph graph) {
        int numVertex = graph.getNumberOfVertices();

        //Double array
        double[] d = new double[numVertex];

        //Populate all the vertices distance with infinity, thats the minimum distance
        for (int i = 0; i < numVertex; i++) {
            d[i] = Double.POSITIVE_INFINITY;
        }

        Set<Edge> edges = new HashSet<Edge>();
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
        int node = 0;

        for (int j = 1; j < numVertex; j++) {
            //Start with zero distance
            d[node] = 0;

            //Iterate based on the number of neighbours the node has
            for (Integer k : graph.getNeighbours(node)) {
                //If its lesser than zero, then get the weight and add it to the priority queue
                if (graph.getWeight(node, k) < d[k]) {
                    d[k] = graph.getWeight(node, k);
                    pq.add(new Edge(d[k], node, k));
                }
            }

            while (true) {
                //Poll retrieves and removes the head of this queue, or returns null if this queue is empty.
                Edge e = pq.poll();
                System.out.println(e.num1);

                if (d[e.num2] > 0) {
                    node = e.num2;
                    edges.add(e);
                    break;
                }
            }
        }
        return edges;
    }

    public Set<Edge> generateKruskal(Graph graph) {
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
        int numVertex = graph.getNumberOfVertices();
        Set<Edge> edges = new HashSet<Edge>();

        for (int i = 0; i < numVertex; i++) {

            for (Integer j : graph.getNeighbours(i)) {
                Edge e = new Edge(graph.getWeight(i, j), i, j);
                if (!pq.contains(e)) {
                    pq.add(e);
                }
            }
        }

        DisjointSets dj = new DisjointSets(numVertex);

        int edgesAccepted = 0;

        while (edgesAccepted < numVertex - 1) {
            Edge e = pq.poll();

            int findA = dj.find(e.num1);
            int findB = dj.find(e.num2);

            if (findA != findB) {
                dj.union(findA, findB);
                edges.add(e);
                edgesAccepted++;
            }
        }

        return edges;
    }

    public static void main(String[] args) {
        new GraphTest();
    }


}

class DisjointSets {
    public DisjointSets(int numElements) {
        s = new int[numElements];
        for (int i = 0; i < s.length; i++)
            s[i] = -1;
    }

    public void union(int root1, int root2) {
        if (s[root2] < s[root1]) {
            s[root1] = root2;
        } else {
            if (s[root1] == s[root2])
                s[root1]--;
            s[root2] = root1;
        }
    }

    public int find(int x) {
        if (s[x] < 0)
            return x;
        else
            return s[x] = find(s[x]);
    }

    private int[] s;
}

class Edge implements Comparable<Edge> {

    public double weight;
    public int num1, num2;

    public Edge(double weight, int num1, int num2) {
        this.weight = weight;
        this.num1 = num1;
        this.num2 = num2;
    }

    //
    @Override
    public int compareTo(Edge o) {
        // TODO Auto-generated method stub
        return (int) (weight - o.weight);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }

        Edge e = (Edge)o;
        return (e.weight == weight && ((e.num1 == num2 && e.num2 == num1) || ((num1 == e.num1 && num2 == e.num2))));
    }

}
