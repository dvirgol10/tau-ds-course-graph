/*
You must NOT change the signatures of classes/methods in this skeleton file.
You are required to implement the methods of this skeleton file according to the requirements.
You are allowed to add classes, methods, and members as required.
 */

/**
 * This class represents a graph that efficiently maintains the heaviest neighborhood over edge addition and
 * vertex deletion.
 *
 */
public class Graph {
    /**
     * Initializes the graph on a given set of nodes. The created graph is empty, i.e. it has no edges.
     * You may assume that the ids of distinct nodes are distinct.
     *
     * @param nodes - an array of node objects
     */
    public Graph(Node [] nodes){
        //TODO: implement this method.
    }

    /**
     * This method returns the node in the graph with the maximum neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){
        //TODO: implement this method.
        return null;
    }

    /**
     * given a node id of a node in the graph, this method returns the neighborhood weight of that node.
     *
     * @param node_id - an id of a node.
     * @return the neighborhood weight of the node of id 'node_id' if such a node exists in the graph.
     * Otherwise, the function returns -1.
     */
    public int getNeighborhoodWeight(int node_id){
        //TODO: implement this method.
        return 0;
    }

    /**
     * This function adds an edge between the two nodes whose ids are specified.
     * If one of these nodes is not in the graph, the function does nothing.
     * The two nodes must be distinct; otherwise, the function does nothing.
     * You may assume that if the two nodes are in the graph, there exists no edge between them prior to the call.
     *
     * @param node1_id - the id of the first node.
     * @param node2_id - the id of the second node.
     * @return returns 'true' if the function added an edge, otherwise returns 'false'.
     */
    public boolean addEdge(int node1_id, int node2_id){
        //TODO: implement this method.
        return false;
    }

    /**
     * Given the id of a node in the graph, deletes the node of that id from the graph, if it exists.
     *
     * @param node_id - the id of the node to delete.
     * @return returns 'true' if the function deleted a node, otherwise returns 'false'
     */
    public boolean deleteNode(int node_id){
        //TODO: implement this method.
        return false;
    }


    /**
     * This class represents a node in the graph.
     */
    public class Node{
        /**
         * Creates a new node object, given its id and its weight.
         * @param id - the id of the node.
         * @param weight - the weight of the node.
         */
        public Node(int id, int weight){
            //TODO: implement this method.
            return;
        }

        /**
         * Returns the id of the node.
         * @return the id of the node.
         */
        public int getId(){
            //TODO: implement this method.
            return 0;
        }

        /**
         * Returns the weight of the node.
         * @return the weight of the node.
         */
        public int getWeight(){
            //TODO: implement this method.
            return 0;
        }
    }

    public static class LinkedList<T> {
        ListNode sentinel;
        int length;

        public LinkedList() {
            this.length = 0;
            this.sentinel = new ListNode(null);
            this.sentinel.prev = this.sentinel;
            this.sentinel.next = this.sentinel;
        }

        public void insertFirst(T item) {
            ListNode node = new ListNode(item);
            node.prev = this.sentinel;
            node.next = this.sentinel.next;
            sentinel.next = node;
            node.next.prev = node;
            this.length += 1;
        }


        public ListNode Retrieve(T item) {
            ListNode node = this.sentinel.next;
            int i = 0;
            while (this.length != i) {
                if (item.equals(node.getItem())) { //TODO implement equals in T-s
                    return node;
                }
                node = node.next;
                i += 1;
            }
            return null;
        }

        public void DeleteNode(ListNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        public class ListNode {
            T item;
            ListNode prev;
            ListNode next;

            public ListNode(T item) {
                this.item = item;
            }

            public T getItem(){
                return this.item;
            }
        }
    }

    /*
        public static class LinkedList<T> {
        ListNode sentinel;
        int length;

        public LinkedList() {
            this.length = 0;
            this.sentinel = new ListNode(null, null, null);
            this.sentinel.setPrev(this.sentinel);
            this.sentinel.setNext(this.sentinel);
        }

        public ListNode getSentinel() {
            return this.sentinel;
        }

        public void insertFirst(T item) {
            ListNode node = new ListNode(item, this.getSentinel(), this.getSentinel().getNext());

        }

        public class ListNode {
            T item;
            ListNode prev;
            ListNode next;

            public ListNode(T item, ListNode prev, ListNode next) {
                this.item = item;
                this.prev = prev;
                this.next = next;
            }

            public ListNode getPrev() {
                return this.prev;
            }

            public void setPrev(ListNode prev) {
                this.prev = prev;
            }

            public ListNode getNext() {
                return this.next;
            }

            public void setNext(ListNode next) {
                this.next = next;
            }
        }
    }
     */

}




