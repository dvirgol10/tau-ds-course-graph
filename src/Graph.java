/*
You must NOT change the signatures of classes/methods in this skeleton file.
You are required to implement the methods of this skeleton file according to the requirements.
You are allowed to add classes, methods, and members as required.
 */

import java.util.Random;

/**
 * This class represents a graph that efficiently maintains the heaviest neighborhood over edge addition and
 * vertex deletion.
 *
 */
public class Graph {

    public NeighborhoodsList neighborhoodsList;
    public MaxHeap neighborhoodWeightHeap;
    public HashTable tableIdToRepresentation;


    /**
     * Initializes the graph on a given set of nodes. The created graph is empty, i.e. it has no edges.
     * You may assume that the ids of distinct nodes are distinct.
     *
     * @param nodes - an array of node objects
     */
    public Graph(Node[] nodes){
        this.neighborhoodsList = new NeighborhoodsList(nodes.length);
        this.neighborhoodWeightHeap = new MaxHeap(nodes);
        this.tableIdToRepresentation = new HashTable(this.neighborhoodWeightHeap);
    }


    /**
     * This method returns the node in the graph with the maximum neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){
        if (this.neighborhoodWeightHeap.getSize() == 0) {
            return null;
        }
        return this.neighborhoodWeightHeap.max();
    }


    /**
     * given a node id of a node in the graph, this method returns the neighborhood weight of that node.
     *
     * @param node_id - an id of a node.
     * @return the neighborhood weight of the node of id 'node_id' if such a node exists in the graph.
     * Otherwise, the function returns -1.
     */
    public int getNeighborhoodWeight(int node_id){
        LinkedList<HashTable.HashTableNode>.ListNode listNode = this.tableIdToRepresentation.find(node_id);
        if (listNode == null) {
            return -1;
        }
        return this.tableIdToRepresentation.find(node_id).item.heapNode.key;
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
        LinkedList<HashTable.HashTableNode>.ListNode listNode1 = this.tableIdToRepresentation.find(node1_id);
        LinkedList<HashTable.HashTableNode>.ListNode listNode2 = this.tableIdToRepresentation.find(node2_id);
        if (listNode1 == null || listNode2 == null) {
            return false;
        }
        this.neighborhoodsList.createEdgeInNeighborList(listNode1.item.nodeID, listNode2.item.nodeID);
        return true;
    }


    /**
     * Given the id of a node in the graph, deletes the node of that id from the graph, if it exists.
     *
     * @param node_id - the id of the node to delete.
     * @return returns 'true' if the function deleted a node, otherwise returns 'false'
     */
    public boolean deleteNode(int node_id){
        HashTable.HashTableNode hashTableNode = tableIdToRepresentation.delete(node_id);
        if (null == hashTableNode) {
            return false;
        }
        neighborhoodsList.deleteNodeFromNeighborList(hashTableNode);
        return true;
    }



    /**
     * This class represents a node in the graph.
     */
    public class Node{
        /**
         * The id of the node
         */
        public int id;

        /**
         * The weight of the node
         */
        public int weight;


        /**
         * Creates a new node object, given its id and its weight.
         * @param id - the id of the node.
         * @param weight - the weight of the node.
         */
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
        }


        /**
         * Returns the id of the node.
         * @return the id of the node.
         */
        public int getId(){
            return this.id;
        }


        /**
         * Returns the weight of the node.
         * @return the weight of the node.
         */
        public int getWeight(){
            return this.weight;
        }
    }

    /**
     * This generic class implements specific necessary operations of the ADT list by doubly linked list.
     */
    public static class LinkedList<T> {
        /**
         * The sentinel of the list, for more convenient implementation.
         */
        public ListNode sentinel;

        /**
         * The length of the linked list.
         */
        public int length;

        /**
         * Creates an empty linked list, i.e. it has no elements.
         *
         */
        public LinkedList() {
            this.length = 0;
            this.sentinel = new ListNode(null);
            this.sentinel.prev = this.sentinel;
            this.sentinel.next = this.sentinel;
        }


        /**
         * Given an item, inserts it to the start of the linked list.
         *
         * @param item - the item to insert.
         */
        public void insertFirst(T item) {
            ListNode node = new ListNode(item);
            node.prev = this.sentinel;
            node.next = this.sentinel.next;
            sentinel.next = node;
            node.next.prev = node;
            this.length += 1;
        }

        /**
         * Returns the first (list) node of the linked list.
         * @return the first (list) node of the linked list.
         */
        public ListNode retrieveFirstNode() {
            return this.sentinel.next;
        }


        /**
         * Given an item, returns the first (list) node in the linked list that keeps the item, if it exists.
         * Otherwise, returns null.
         *
         * @param item - the item of a (list) node to return.
         * @return returns the first (list) node in the linked list that keeps the item if it exists,
         * otherwise returns 'null'.
         */
        public ListNode retrieveNode(T item) {
            ListNode node = this.sentinel.next;
            int i = 0;
            while (this.length != i) {
                if (item.equals(node.getItem())) {
                    return node;
                }
                node = node.next;
                i += 1;
            }
            return null;
        }


        /**
         * Given an (list) node, deletes it from its linked list.
         *
         * @param node - the (list) node to delete.
         */
        public void deleteNode(ListNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.item = null;
            node.prev = null;
            node.next = null;
            this.length -= 1;
        }


        /**
         * This class represents a node in a doubly linked list.
         */
        public class ListNode {
            /**
             * The data which the (list) node keeps.
             */
            public T item;

            /**
             * The previous (list) node in the linked list.
             */
            public ListNode prev;

            /**
             * The next (list) node in the linked list.
             */
            public ListNode next;


            /**
             * Creates a new (list) node, given its item.
             * @param item - the item of the (list) node.
             */
            public ListNode(T item) {
                this.item = item;
            }


            /**
             * Returns the item of the node.
             * @return the item of the node.
             */
            public T getItem(){
                return this.item;
            }
        }
    }


    public class NeighborhoodsList {
        public LinkedList<NeighborNode>[] arrNeighborsLists;
        public int numEdges;

        public NeighborhoodsList(int numNodes) {
            this.arrNeighborsLists = new LinkedList[numNodes];
            for (int i = 0; i < numNodes; i++) {
                this.arrNeighborsLists[i] = new LinkedList<NeighborNode>();
            }
            this.numEdges = 0;
        }


        public void createEdgeInNeighborList(int node1ID, int node2ID) {
            NeighborNode neighborNode1 = new NeighborNode(node1ID);
            NeighborNode neighborNode2 = new NeighborNode(node2ID);

            HashTable.HashTableNode hashTableNode1 = tableIdToRepresentation.find(node1ID).item;
            HashTable.HashTableNode hashTableNode2 = tableIdToRepresentation.find(node2ID).item;

            int node1NListIndex = hashTableNode1.nodeNListIndex;
            int node2NListIndex = hashTableNode2.nodeNListIndex;

            this.arrNeighborsLists[node1NListIndex].insertFirst(neighborNode2);
            this.arrNeighborsLists[node2NListIndex].insertFirst(neighborNode1);

            neighborNode1.ListNodeOfNeighborInEdge = this.arrNeighborsLists[node1NListIndex].retrieveFirstNode();
            neighborNode2.ListNodeOfNeighborInEdge = this.arrNeighborsLists[node2NListIndex].retrieveFirstNode();

            neighborhoodWeightHeap.increaseNeighborhoodWeight(hashTableNode1.heapNode, hashTableNode2.heapNode.value.getWeight());
            neighborhoodWeightHeap.increaseNeighborhoodWeight(hashTableNode2.heapNode, hashTableNode1.heapNode.value.getWeight());

            this.numEdges += 1;
        }


        public void deleteNodeFromNeighborList(HashTable.HashTableNode hashTableNode) {
            int nodeNListIndex = hashTableNode.nodeNListIndex;

            LinkedList<NeighborNode> NeighborsLinkedList = this.arrNeighborsLists[nodeNListIndex];

            int i = 0;
            LinkedList<NeighborNode>.ListNode listNode = NeighborsLinkedList.sentinel.next;
            while (NeighborsLinkedList.length != i) {
                NeighborNode neighborNode = listNode.item;
                HashTable.HashTableNode hashTableNeighborNode = tableIdToRepresentation.find(neighborNode.nodeID).item;
                this.arrNeighborsLists[hashTableNeighborNode.nodeNListIndex].deleteNode(neighborNode.ListNodeOfNeighborInEdge);
                this.numEdges -= 1;

                neighborhoodWeightHeap.decreaseNeighborhoodWeight(hashTableNeighborNode.heapNode, hashTableNode.heapNode.value.getWeight());

                listNode = listNode.next;
                i += 1;
            }

            neighborhoodWeightHeap.deleteHeapNode(hashTableNode.heapNode);
            this.arrNeighborsLists[nodeNListIndex] = null;
        }


        public class NeighborNode {
            public int nodeID;
            public LinkedList<NeighborNode>.ListNode ListNodeOfNeighborInEdge;

            public NeighborNode(int nodeID) {
                this.nodeID = nodeID;
            }


        }
    }


    public static class MaxHeap {
        public HeapNode[] heapArr;
        public int size;


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
            heapNode.heapIndex = i;
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
            this.heapifyDown(heapNode.heapIndex);
        }


        public void increaseNeighborhoodWeight(HeapNode heapNode , int delta) {
            heapNode.key += delta;
            this.heapifyUp(heapNode.heapIndex);
        }


        public void deleteHeapNode(HeapNode heapNode) {
            this.heapifyUp(heapNode.heapIndex);
            this.heapifyDown(heapNode.heapIndex);
        }


        public class HeapNode {
            public int key;
            public Node value;
            public int heapIndex;

            public HeapNode(int key, Node value, int heapIndex) {
                this.key = key;
                this.value = value;
                this.heapIndex = heapIndex;
            }
        }

    }


    public static class HashTable {
        public int p;
        public int a;
        public int b;
        LinkedList<HashTableNode>[] table;


        public HashTable(MaxHeap maxHeap) {
            this.table = new LinkedList[maxHeap.getSize()];
            for (int i = 0; i < maxHeap.getSize(); i++) {
                this.table[i] = new LinkedList<HashTableNode>();
            }
            this.p = 1000000009;

            Random random = new Random();
            this.a = random.nextInt(this.p - 1) + 1;
            this.b = random.nextInt(this.p);
            //TODO: assign values to this.a and this.b

            for (int i = 0; i < maxHeap.getSize(); i++) {
                this.insert(maxHeap.heapArr[i].value.getId(), i, maxHeap.heapArr[i]);
            }
        }


        public LinkedList<HashTableNode> findChain(int x) {
            return this.table[((this.a * x + this.b) % this.p) % this.table.length];
        }


        public void insert(int nodeID, int nodeIndex, MaxHeap.HeapNode heapNode) {
            if (this.find(nodeID) == null){
                findChain(nodeID).insertFirst(new HashTableNode(nodeID, nodeIndex, heapNode));
            }
        }


        public LinkedList<HashTableNode>.ListNode find(int nodeID) {
            return this.findChain(nodeID).retrieveNode(new HashTableNode(nodeID, -1, null));
        }


        public HashTableNode delete(int nodeID) {
            LinkedList<HashTableNode>.ListNode listNode = this.find(nodeID);

            if (listNode == null) {
                return null;
            }

            HashTableNode hashTableNode = listNode.item;
            this.findChain(nodeID).deleteNode(listNode);
            return hashTableNode;

        }

        public static class HashTableNode {
            public int nodeID;
            public int nodeNListIndex;
            public MaxHeap.HeapNode heapNode;

            public HashTableNode(int nodeID, int nodeNListIndex, MaxHeap.HeapNode heapNode) {
                this.nodeID = nodeID;
                this.nodeNListIndex = nodeNListIndex;
                this.heapNode = heapNode;
            }


            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                HashTableNode that = (HashTableNode) o;
                return nodeID == that.nodeID;
            }
        }
    }



}




