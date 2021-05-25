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

        public ListNode retrieveFirstNode() {
            return this.sentinel.next;
        }


        public ListNode retrieve(T item) {
            ListNode node = this.sentinel.next;
            int i = 0;
            while (this.length != i) {
                if (item.equals(node.getItem())) { //TODO: implement equals in T-s
                    return node;
                }
                node = node.next;
                i += 1;
            }
            return null;
        }


        public void deleteNode(ListNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.item = null;
            node.prev = null;
            node.next = null;
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


    public class NeighborhoodsList {
        LinkedList<NeighborNode>[] arrNeighborsLists;
        int numEdges;

        public NeighborhoodsList(int numNodes) {
            this.arrNeighborsLists = new LinkedList[numNodes];
            for (int i = 0; i < numNodes; i++) {
                this.arrNeighborsLists[i] = new LinkedList<NeighborNode>();
            }
            this.numEdges = 0;
        }


        public void createEdgeInNeighborList(int node1Index, int node2Index) {
            NeighborNode neighborNode1 = new NeighborNode(node1Index);
            NeighborNode neighborNode2 = new NeighborNode(node2Index);

            this.arrNeighborsLists[node1Index].insertFirst(neighborNode2);
            this.arrNeighborsLists[node2Index].insertFirst(neighborNode1);


            neighborNode1.ListNodeOfNeighborInEdge = this.arrNeighborsLists[node1Index].retrieveFirstNode();
            neighborNode2.ListNodeOfNeighborInEdge = this.arrNeighborsLists[node2Index].retrieveFirstNode();

            this.numEdges += 1;
        }


        public void deleteNodeFromNeighborList(int nodeIndex) {
            LinkedList<NeighborNode> NeighborsLinkedList = this.arrNeighborsLists[nodeIndex];
            LinkedList<NeighborNode>.ListNode listNode = NeighborsLinkedList.sentinel.next;
            int i = 0;
            while (NeighborsLinkedList.length != i) {
                NeighborNode neighborNode = listNode.item;
                this.arrNeighborsLists[neighborNode.nodeIndex].deleteNode(neighborNode.ListNodeOfNeighborInEdge);
                this.numEdges -= 1;

                listNode = listNode.next;
                i += 1;
            }
            this.arrNeighborsLists[nodeIndex] = null;
        }


        public class NeighborNode {
            int nodeIndex;
            LinkedList<NeighborNode>.ListNode ListNodeOfNeighborInEdge;

            public NeighborNode(int nodeIndex) {
                this.nodeIndex = nodeIndex;
            }


        }
    }


    public static class MaxHeap {
        HeapNode[] heapArr;
        int size;


        public MaxHeap(Node[] nodes) {
            this.heapArr = new HeapNode[nodes.length];
            this.size = nodes.length;
            for (int i = 0; i < nodes.length; i++) {
                this.heapArr[i] = new HeapNode(nodes[i].getWeight(), nodes[i], i);
            }

            int j = parent(this.size - 1);
            while (j >= 0) {
                this.heapifyDown(j);
                j -= 1;
            }


        }


        public int left(int i) {
            return 2 * i + 1;
        }


        public int right(int i) {
            return 2 * i + 2;
        }


        public int parent(int i) {
            return (i + 1) / 2 - 1;
        }


        public int getSize() {
            return this.size;
        }

        public void setHeapNodeAtIndex(HeapNode heapNode, int i) {
            this.heapArr[i] = heapNode;
            heapNode.index = i;
        }

        public void swapHeapNodes(int i, int j) {
            HeapNode tmp = this.heapArr[i];
            this.setHeapNodeAtIndex(this.heapArr[j], i);
            this.setHeapNodeAtIndex(tmp, j);
        }


        public void heapifyUp(int i) {
            while (i > 0 && this.heapArr[i].key > this.heapArr[parent(i)].key) {
                this.swapHeapNodes(i, parent(i));
                i = parent(i);
            }
        }


        public void heapifyDown(int i) {
            int l;
            int r;
            int largest;
            boolean isCompleted = false;
            while (!isCompleted) {
                l = left(i);
                r = right(i);
                largest = i;
                if (l < this.getSize() && this.heapArr[l].key > this.heapArr[largest].key) {
                    largest = l;
                }
                if (r < this.getSize() && this.heapArr[r].key > this.heapArr[largest].key) {
                    largest = r;
                }
                if (largest > i) {
                    swapHeapNodes(i, largest);
                    i = largest;
                } else {
                    isCompleted = true;
                }
            }

        }


        public Node max() {
            return heapArr[0].value;
        }


        public void decreaseNeighborhoodWeight(HeapNode heapNode , int delta) {
            heapNode.key -= delta;
            this.heapifyDown(heapNode.index);
        }


        public void deleteHeapNode(HeapNode heapNode) {
            this.heapifyUp(heapNode.index);
            this.heapifyDown(heapNode.index);
        }


        public class HeapNode {
            int key;
            Node value;
            int index;

            public HeapNode(int key, Node value, int index) {
                this.key = key;
                this.value = value;
                this.index = index;
            }
        }

    }



}




