package finalproject.datastructures;

import java.util.LinkedList;

/**
 * This is a class to hold a graph.
 */

public class DSGraph {

    // Our graph is a DSHashMap, mapping strings to a DSArrayList of neighbors
    DSHashMap<DSArrayList<String>> graph;

    public DSGraph() {
        this.graph = new DSHashMap<>();
    }

    /**
     * Adds a vertex to the graph, if it is not already there.
     *
     * @param v The String that represents the new vertex
     */
    public void addVertex(String v) {
        // Make sure v1 exists as a vertex
        if (!graph.containsKey(v)) {
            graph.put(v, new DSArrayList<>());
        }
    }

    /**
     * Add an edge between vertices v1 and v2.
     * If v1 and/or v2 do not already exist, create them.
     *
     * @param v1 The first vertex
     * @param v2 The second vertex
     */
    public void addEdge(String v1, String v2) {
        // Make sure v1 exists as a vertex
        addVertex(v1);
        // Make sure v2 exists as a vertex
        addVertex(v2);

        // Add v1 and v2 to each others' neighbor lists
        if(graph.get(v1).contains(v2)) return; // Don't add a duplicate edge
        graph.get(v1).add(v2);
        graph.get(v2).add(v1);
    }

    /**
     * Determine whether or not the graph has a triangle.
     *
     * A triangle is a set of 3 vertices {A, B, C} such that the three
     * edges (A, B), (A, C) and (B, C) are all present in the graph
     *
     * @return true if the graph contains no triangle, otherwise false
     */
    public Boolean triangleFree() {
        for (String v1 : graph) {
            DSArrayList<String> nbs = graph.get(v1);
            for (int i = 0; i < nbs.length - 1; i++) {
                for (int j = i + 1; j < nbs.length; j++) {
                    if (graph.get(nbs.get(i)).contains(nbs.get(j))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Produce the number of connected components in this DSGraph
     *
     * @return The number of components
     */
    public int numberOfComponents() {
        int rv = 0;
        DSHashMap<String> visited = new DSHashMap<>();

        for (String v : graph) {
            if (visited.containsKey(v))
                continue;
            rv++; // Found a new component
            LinkedList<String> q = new LinkedList<>();
            q.add(v);
            visited.put(v, "");

            while (!q.isEmpty()) {
                String n = q.pop();
                for (String w : graph.get(n)) {
                    if (visited.containsKey(w))
                        continue;
                    visited.put(w, "");
                    q.add(w);
                }
            }

        }
        return rv;
    }

    // Finds a shortest path from start to end in this graph
    public void shortestPath(String start, String end) {
        LinkedList<String> q = new LinkedList<>();
        DSHashMap<String> parent = new DSHashMap<>();
        // visited keeps track of vertices we've seen before
        DSHashMap<String> visited = new DSHashMap<>();

        q.push(start);
        visited.put(start, "");
        String farthest = "";

        while (!q.isEmpty()) {
            String v = q.removeFirst();
            for (String nbr : graph.get(v)) {
                if (visited.containsKey(nbr))
                    continue; // move on to the next neighbor
                q.add(nbr);
                farthest = nbr;
                visited.put(nbr, "");
                parent.put(nbr, v);
            }
        }
        //System.out.printf("The farthest word from %s is %s\n", start, farthest);

        // Discover undiscovered vertices
        int numUnreachable = 0;
        for (String v : graph) {
            if (!visited.containsKey(v)) {
                numUnreachable++;
                // System.out.printf("This vertex is unreachable from %s: %s\n", start, v);
            }
        }
        // System.out.println(
        //      "From " + start + ", there are " + numUnreachable + " unreachable words.");

        // If there is no path ...
        if (!visited.containsKey(end)) {
            //  System.out.println(end + " is unreachable from " + start);
            return;
        }
        printPath(start, end, parent);
    }

    // Overseer DFS from a single vertex
    public void DFS(String start, String end) {
        // The start
        DSHashMap<String> visited = new DSHashMap<>();
        DSHashMap<String> parent = new DSHashMap<>();
        visited.put(start, "");
        boolean pathExists = DFSPath(start, end, visited, parent);
        if (pathExists) {
            printPath(start, end, parent);
        } else {
            System.out.println("No path");
        }
    }
    // Find any path from start to end
    public boolean DFSPath(String start, String end,DSHashMap<String> visited, DSHashMap<String> parent) {
        if (start.equals(end)) {
            return true;
        }

        for (String nbr : graph.get(start)) {
            if (visited.containsKey(nbr))
                continue;
            parent.put(nbr, start);
            visited.put(nbr, "");
            if (DFSPath(nbr, end, visited, parent))
                return true;
        }
        return false;
    }
    public void printPath(String start, String end, DSHashMap<String> parent) {
        // There is a path. Let's print it.
        int length = 1;
        String path = "";
        String vertex = end; // Where we are as we walk backwards on the path
        while (vertex != start) {
            // Stick the current vertex at the start of the path
            path = vertex + "-" + path;
            // Update the vertex we are at
            vertex = parent.get(vertex); // move to the parent
            length++;
        }
        path = start + "-" + path;
        System.out.println(path);
        System.out.println("That path had length " + length);
    }

    /**
     * Find the size of the component in the graph containing vertex v
     *
     * @param v A vertex in the component
     * @return The total number of vertices in v's component
     */
    public int componentSize(String v) {
        DSHashMap<String> visited = new DSHashMap<>();
        visited.put(v, "");
        componentSizeVisit(v, visited);
        System.out.println(visited);
        return visited.size();
    }

    /**
     * Recursion for the componentSize method
     *
     * @param v       A vertex to DFS search from
     * @param visited A hash map of all vertices that have been visited so far
     */
    public void componentSizeVisit(String v, DSHashMap<String> visited) { //this is a depth first search algo
        for (String nbr : graph.get(v)) { // Loop over neighbors of v
            if (visited.containsKey(nbr))
                continue;
            visited.put(nbr, "");
            componentSizeVisit(nbr, visited);
        }
    }

    // Prints one vertex from every component in the graph, with that component's
    // size
    public void discoverAllComponents() {
        LinkedList<String> q = new LinkedList<>();
        // visited keeps track of vertices we've seen before
        DSHashMap<String> visited = new DSHashMap<>();

        // Loop over all vertices of the graph
        for (String start : graph) {
            if (visited.containsKey(start))
                continue;

            // New, unseen vertex. Do a BFS from v
            q.clear();
            q.add(start); // v is this BFS's start vertex
            visited.put(start, "");
            int componentSize = 1;

            while (!q.isEmpty()) {
                String v = q.removeFirst();
                for (String nbr : graph.get(v)) {
                    if (visited.containsKey(nbr))
                        continue; // move on to the next neighbor
                    q.add(nbr);
                    componentSize++;
                    visited.put(nbr, "");
                }
            }

            System.out.printf("The component containing %s has size %d\n", start, componentSize);
        }
    }

    /*
     * Homework Functions!
     */

    /**
     * Compute the number of vertices of this graph
     *
     * @return the number of vertices
     */
    public int numVertices() {
        return graph.numItems;
    } //the number of vertices is the number of keys in a graph

    /**
     * Compute the number of edges of this graph
     *
     * @return the number of edges
     */
    public int numEdges() {
        LinkedList<String> q = new LinkedList<>();
        // visited keeps track of vertices we've seen before
        DSHashMap<String> visited = new DSHashMap<>();
        int degrees = 0;
        // Loop over all vertices of the graph
        for (String start : graph) {
            if (!visited.containsKey(start)){
                // New, unseen vertex. Do a BFS from v
                q.clear();
                q.add(start); // v is this BFS's start vertex

                while (!q.isEmpty()) {
                    String v = q.removeFirst();
                    for (String nbr : graph.get(v)) {
                        degrees++; //count number of degrees of neighbours
                    }
                }
            }
        }
        return degrees/2;
    }

    /**
     * Determine if the graph is connected
     *
     * @return true if the graph consists of just a single component
     */
    public boolean isConnected() {
        return numberOfComponents() == 1;
    }

    /**
     * Determine if the graph is a tree.
     * A tree is a graph that is connected and has no cycles.
     *
     * return true if this graph is a tree
     */
    public boolean isTree() {
        //using Tree theorem if the graph has only one component and numVertices()-numEdges()==1; the graph is a tree
        if (isConnected() && numVertices()-numEdges()==1) return true;
        return false;
    }

    /**
     * Determine if the graph contains any cycles
     *
     * @return true if the graph contains any cycles.
     */
    public boolean hasCycle() {
        LinkedList<String> q = new LinkedList<>();
        // visited keeps track of vertices we've seen before
        DSHashMap<String> visited = new DSHashMap<>();
        DSHashMap<String> parent = new DSHashMap<>();
        // Loop over all vertices of the graph
        for (String start : graph) {
            if (!visited.containsKey(start)){
                // New, unseen vertex. Do a BFS from v
                q.clear();
                q.add(start); // v is this BFS's start vertex

                while (!q.isEmpty()) {
                    String v = q.removeFirst();
                    parent.put(v," ");
                    for (String nbr : graph.get(v)) {
                        if (!visited.containsKey(nbr)){
                            q.add(nbr);
                            visited.put(nbr, "");
                        }
                        else if (visited.containsKey(nbr) && !parent.containsKey(nbr))return true; //if a nbr is visited but not a parent there is a cycle
                    }
                }
            }
        }
        return false;
    }

    /**
     * Find two vertices that are as far apart as possible.
     *
     * That is, find two vertices with the property that
     * the shortest distance between them in the graph is
     * at least as great as the shortest distance between
     * any other two vertices in the graph.
     *
     * @return an array containing two such vertices
     */
    public String[] furthestPair() {
        int maxDistance = 0;
        String fPair = "";
        String sPair = "";
        for (String s: graph ) {
            for (String k: graph) {
                if (distance(s,k)>maxDistance) { //check distance between two vertices
                    maxDistance = distance(s,k); //update maxDistance each time distance if greater than maxDistance
                    fPair = s; // fPair updated
                    sPair = k; // sPair updated
                }
            }
        }
        return new String[] {fPair,sPair};
    }

    /**
     * Find the distance between two vertices in this graph
     *
     * The distance between two vertices is the number of edges
     * in the shortest path from one vertex to the other vertex.
     *
     * @param v The first vertex
     * @param w The second vertex
     * @return the distance between v and w
     */
    public int distance(String v, String w) {
        LinkedList<String> q = new LinkedList<>();
        DSHashMap<String> childToParent = new DSHashMap<>();
        // visited keeps track of vertices we've seen before
        DSHashMap<String> visited = new DSHashMap<>();

        q.push(v);
        visited.put(v, "");
        while (!q.isEmpty()) {
            String s = q.removeFirst();
            if (graph.containsKey(v))
                for (String nbr : graph.get(s)) {
                    if (!visited.containsKey(nbr))
                        q.add(nbr);
                    visited.put(nbr, "");
                    childToParent.put(nbr, s);
                }
        }
        // If there is no path
        if (!visited.containsKey(w)||!visited.containsKey(v)) {
            return -1;
        }
        if (!childToParent.containsKey(w)) return 0;
        return path(v, w, childToParent)-1;
    }
    /**
     * Finds the size of a path between two string in the graph
     *  @param start
     * @param end
     * @param parent
     * @return the length of a path
     * */
    public int path(String start, String end, DSHashMap<String> parent) {
        // There is a path. Let's print it.
        int length = 1;
        String path = "";
        String vertex = end; // Where we are as we walk backwards on the path
        while (vertex != start) {
            // Stick the current vertex at the start of the path
            path = vertex + "-" + path;
            // Update the vertex we are at
            vertex = parent.get(vertex); // move to the parent
            length++;
        }
        path = start + "-" + path;
        return length;
    }

}