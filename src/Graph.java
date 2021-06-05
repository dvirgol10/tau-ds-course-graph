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
    /**
     * The adjacency list represents the edges in the graph.
     */
    public NeighborhoodsList neighborhoodsList;

    /**
     * The (binary) Max-Heap that maintains the heaviest neighborhood.
     */
    public MaxHeap neighborhoodWeightHeap;

    /**
     * The hash table that maps node id to hash node (which contains relevant info about the vertex).
     */
    public HashTable tableIdToRepresentation;


    /**
     * Initializes the graph on a given set of nodes. The created graph is empty, i.e. it has no edges.
     * You may assume that the ids of distinct nodes are distinct.
     *
     * time complexity: O(N) expected.
     *
     * @param nodes - an array of node objects
     */
    public Graph(Node[] nodes){
        this.neighborhoodsList = new NeighborhoodsList(nodes.length);
        this.neighborhoodWeightHeap = new MaxHeap(nodes);
        this.tableIdToRepresentation = new HashTable(this.neighborhoodWeightHeap);
    }


    /**
     * This method returns the node in the graph with the maximal neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     *
     * time complexity: O(1).
     *
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){
        if (this.getNumNodes() == 0) {
            return null;
        }
        return this.neighborhoodWeightHeap.max();
    }


    /**
     * given a node id of a node in the graph, this method returns the neighborhood weight of that node.
     *
     * time complexity: O(1) expected.
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
        return listNode.item.heapNode.key;
    }


    /**
     * This function adds an edge between the two nodes whose ids are specified.
     * If one of these nodes is not in the graph, the function does nothing.
     * The two nodes must be distinct; otherwise, the function does nothing.
     * You may assume that if the two nodes are in the graph, there exists no edge between them prior to the call.
     *
     * time complexity: O(log(n)) expected.
     *
     * @param node1_id - the id of the first node.
     * @param node2_id - the id of the second node.
     * @return returns 'true' if the function added an edge, otherwise returns 'false'.
     */
    public boolean addEdge(int node1_id, int node2_id){
        LinkedList<HashTable.HashTableNode>.ListNode listNode1 = this.tableIdToRepresentation.find(node1_id);
        LinkedList<HashTable.HashTableNode>.ListNode listNode2 = this.tableIdToRepresentation.find(node2_id);
        if (node1_id == node2_id || listNode1 == null || listNode2 == null) {
            return false;
        }
        this.neighborhoodsList.createEdgeInNeighborList(node1_id, node2_id);
        return true;
    }


    /**
     * Given the id of a node in the graph, deletes the node of that id from the graph, if it exists.
     *
     * time complexity: O((d_v+1) * log(n)) expected.
     *
     * @param node_id - the id of the node to delete.
     * @return returns 'true' if the function deleted a node, otherwise returns 'false'
     */
    public boolean deleteNode(int node_id){
        HashTable.HashTableNode hashTableNode = tableIdToRepresentation.delete(node_id);
        if (hashTableNode == null) {
            return false;
        }
        neighborhoodsList.deleteNodeFromNeighborList(hashTableNode);
        return true;
    }


    /**
     * Returns the number of vertices that present in the graph.
     *
     * time complexity: O(1).
     *
     * @return the number of vertices that present in the graph.
     */
    public int getNumNodes() {
        return this.neighborhoodWeightHeap.getSize();
    }


    /**
     * Returns the number of edges that present in the graph.
     *
     * time complexity: O(1).
     *
     * @return the number of edges that present in the graph.
     */
    public int getNumEdges() {
        return this.neighborhoodsList.numEdges;
    }



    /**
     * This class represents a node in the graph.
     */
    public static class Node{ //TODO check staticity
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
         *
         * time complexity: O(1).
         *
         * @param id - the id of the node.
         * @param weight - the weight of the node.
         */
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
        }


        /**
         * Returns the id of the node.
         *
         * time complexity: O(1).
         *
         * @return the id of the node.
         */
        public int getId(){
            return this.id;
        }


        /**
         * Returns the weight of the node.
         *
         * time complexity: O(1).
         *
         * @return the weight of the node.
         */
        public int getWeight(){
            return this.weight;
        }
    }

    /**
     * This generic class implements specific necessary operations of the ADT list (and more) by a doubly linked list.
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
         * time complexity: O(1).
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
         * time complexity: O(1).
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
         *
         * time complexity: O(1).
         *
         * @return the first (list) node of the linked list.
         */
        public ListNode retrieveFirstNode() {
            return this.sentinel.next;
        }


        /**
         * Given an item, returns the first (list) node in the linked list that keeps the item, if it exists.
         * Otherwise, returns null.
         *
         * time complexity: O(i) while i is the "index" of the first (list) node
         *                  in the linked list that keeps the item, if it exists.
         *                  Otherwise, i is the length of the linked list.
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
         * time complexity: O(1).
         *
         * @param node - the (list) node to delete.
         */
        public void deleteNodeFromList(ListNode node) {
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
             *
             * time complexity: O(1).
             *
             * @param item - the item of the (list) node.
             */
            public ListNode(T item) {
                this.item = item;
            }


            /**
             * Returns the item of the node.
             *
             * time complexity: O(1).
             *
             * @return the item of the node.
             */
            public T getItem(){
                return this.item;
            }
        }
    }


    /**
     * This class represents the edges in the graph by an adjacency list.
     */
    public class NeighborhoodsList {
        /**
         * Each cell in this array corresponds to a vertex (the cell of deleted vertex is 'null'), which the
         * linked list in it keeps the neighbors of the corresponding vertex and bidirectional pointers between
         * the two representations of each edge.
         */
        public LinkedList<NeighborNode>[] arrNeighborsLists;


        /**
         * The number of edges that present in the graph.
         */
        public int numEdges;


        /**
         * Creates an empty adjacency list, given its length (the number of vertices in the graph).
         *
         * time complexity: O(numNodes).
         *
         * @param numNodes - the number of vertices in the graph.
         */
        public NeighborhoodsList(int numNodes) {
            this.arrNeighborsLists = new LinkedList[numNodes];
            for (int i = 0; i < numNodes; i++) {
                this.arrNeighborsLists[i] = new LinkedList<NeighborNode>();
            }
            this.numEdges = 0;
        }


        /**
         * This function adds an edge between the two vertices whose ids are specified.
         * We assume that the two vertices are in the graph, and they are distinct.
         *
         * time complexity: O(log(n)) expected.
         *
         * @param node1_id - the id of the first vertex.
         * @param node2_id - the id of the second vertex.
         */
        public void createEdgeInNeighborList(int node1_id, int node2_id) {
            NeighborNode neighborNode1 = new NeighborNode(node1_id);
            NeighborNode neighborNode2 = new NeighborNode(node2_id);

            HashTable.HashTableNode hashTableNode1 = tableIdToRepresentation.find(node1_id).item;
            HashTable.HashTableNode hashTableNode2 = tableIdToRepresentation.find(node2_id).item;

            int node1NListIndex = hashTableNode1.nodeNListIndex;
            int node2NListIndex = hashTableNode2.nodeNListIndex;

            //Inserts the neighbor nodes to the start of the neighbors lists.
            this.arrNeighborsLists[node1NListIndex].insertFirst(neighborNode2);
            this.arrNeighborsLists[node2NListIndex].insertFirst(neighborNode1);

            //Updates the bidirectional pointers between the two representations of the new edge.
            neighborNode1.listNodeOfNeighborInEdge = this.arrNeighborsLists[node1NListIndex].retrieveFirstNode();
            neighborNode2.listNodeOfNeighborInEdge = this.arrNeighborsLists[node2NListIndex].retrieveFirstNode();

            //Updates the neighborhood weight of the two nodes.
            neighborhoodWeightHeap.increaseNeighborhoodWeight(hashTableNode1.heapNode, hashTableNode2.heapNode.value.getWeight());
            neighborhoodWeightHeap.increaseNeighborhoodWeight(hashTableNode2.heapNode, hashTableNode1.heapNode.value.getWeight());

            this.numEdges += 1;
        }


        /**
         * Given the hash-table node of a vertex in the graph,
         * deletes the vertex of that hash-table node from the graph.
         * We assume that the vertex is in the graph.
         *
         * time complexity: O((d_v+1) * log(n)) expected.
         *
         * @param hashTableNode - the hash-table node of the vertex to delete.
         */
        public void deleteNodeFromNeighborList(HashTable.HashTableNode hashTableNode) {
            //Retrieves the neighbors list of the vertex to delete.
            int nodeNListIndex = hashTableNode.nodeNListIndex;
            LinkedList<NeighborNode> NeighborsLinkedList = this.arrNeighborsLists[nodeNListIndex];

            int i = 0;
            LinkedList<NeighborNode>.ListNode listNode = NeighborsLinkedList.sentinel.next;
            while (NeighborsLinkedList.length != i) { //For each neighbor:
                NeighborNode neighborNode = listNode.item;
                HashTable.HashTableNode hashTableNeighborNode = tableIdToRepresentation.find(neighborNode.node_id).item;

                //Deletes the neighbor node of the vertex to delete from the neighbors list of his neighbor.
                this.arrNeighborsLists[hashTableNeighborNode.nodeNListIndex].deleteNodeFromList(neighborNode.listNodeOfNeighborInEdge);

                //Remove the weight of the vertex to delete from the neighborhood weight of his neighbor.
                neighborhoodWeightHeap.decreaseNeighborhoodWeight(hashTableNeighborNode.heapNode, hashTableNode.heapNode.value.getWeight());

                this.numEdges -= 1;
                listNode = listNode.next;
                i += 1;
            }

            //Deletes the neighborhood weight of the vertex to delete from the max-heap.
            neighborhoodWeightHeap.deleteHeapNode(hashTableNode.heapNode);
            //Replaces the pointer to the neighbors list of the vertex to delete with 'null'.
            this.arrNeighborsLists[nodeNListIndex] = null;
        }


        /**
         * This class represents a neighbor node in a neighbors list of the other vertex in the edge.
         */
        public class NeighborNode {
            /**
             * The id of the vertex which this neighbor node represents.
             */
            public int node_id;

            /**
             * A pointer to the (list) node of the other vertex in the edge,
             * which is located in the neighbors list of the current node.
             */
            public LinkedList<NeighborNode>.ListNode listNodeOfNeighborInEdge;

            /**
             * Creates a new neighbor node object, given the id of the vertex.
             *
             * time complexity: O(1).
             *
             * @param node_id - the id of the vertex which this neighbor node represents.
             */
            public NeighborNode(int node_id) {
                this.node_id = node_id;
            }
        }
    }



    /**
     * This class implements a (binary) Max-Heap that maintains the heaviest neighborhood.
     *
     */
    public static class MaxHeap {
        /**
         * The array that represents the heap.
         */
        public HeapNode[] heapArr;

        /**
         * The size of the heap, that is, the number neighborhoods present
         * in the graph (the current number of vertices in the graph).
         */
        public int size;


        /**
         * Initializes the max-heap of the neighborhood weights with a given set of nodes.
         * The graph has no edges, so in practice the neighborhood weights are node weights.
         * We assume that the ids of distinct nodes are distinct.
         *
         * time complexity: O(N).
         *
         * @param nodes - an array of node objects
         */
        public MaxHeap(Node[] nodes) {
            //Initialize the array of the heap.
            this.heapArr = new HeapNode[nodes.length];
            this.size = nodes.length;
            for (int i = 0; i < nodes.length; i++) {
                this.heapArr[i] = new HeapNode(nodes[i].getWeight(), nodes[i], i);
            }

            //Construct a legal binary max-heap.
            int j = parent(this.size - 1);
            while (j >= 0) {
                this.heapifyDown(j);
                j -= 1;
            }
        }


        /**
         * Returns the index of the left child of the (heap) node at index i.
         *
         * time complexity: O(1).
         *
         * @param i - an index of a (heap) node in the heap.
         * @return the index of the left child of the (heap) node at index i.
         */
        public int left(int i) {
            return 2 * i + 1;
        }


        /**
         * Returns the index of the right child of the (heap) node at index i.
         *
         * time complexity: O(1).
         *
         * @param i - an index of a (heap) node in the heap.
         * @return the index of the right child of the (heap) node at index i.
         */
        public int right(int i) {
            return 2 * i + 2;
        }


        /**
         * Returns the index of the parent of the (heap) node at index i.
         *
         * time complexity: O(1).
         *
         * @param i - an index of a (heap) node in the heap.
         * @return the index of the parent of the (heap) node at index i.
         */
        public int parent(int i) {
            return (i + 1) / 2 - 1;
        }


        /**
         * Returns the size of the heap.
         *
         * time complexity: O(1).
         *
         * @return the size of the heap.
         */
        public int getSize() {
            return this.size;
        }


        /**
         * Sets heapNode at index i in the heap, and updates his index field.
         *
         * time complexity: O(1).
         *
         * @param heapNode - a (heap) node to set at index i in the heap.
         * @param i - an index to set heapNode at.
         */
        public void setHeapNodeAtIndex(HeapNode heapNode, int i) {
            this.heapArr[i] = heapNode;
            heapNode.heapIndex = i;
        }


        /**
         * Swap the (heap) nodes at indices i and j in the heap.
         *
         * time complexity: O(1).
         *
         * @param i - an index in the heap.
         * @param j - an index in the heap.
         */
        public void swapHeapNodes(int i, int j) {
            HeapNode tmp = this.heapArr[i];
            this.setHeapNodeAtIndex(this.heapArr[j], i);
            this.setHeapNodeAtIndex(tmp, j);
        }


        /**
         * Corrects upwards the (heap) node at index i, if it is needed.
         *
         * time complexity: O(log(n)).
         *
         * @param i - an index to correct in the heap.
         */
        public void heapifyUp(int i) {
            while (i > 0 && this.heapArr[i].key > this.heapArr[parent(i)].key) {
                this.swapHeapNodes(i, parent(i));
                i = parent(i);
            }
        }


        /**
         * Corrects downwards the (heap) node at index i, if it is needed.
         *
         * time complexity: O(log(n)).
         *
         * @param i - an index to correct in the heap.
         */
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
                    this.swapHeapNodes(i, largest);
                    i = largest;
                } else {
                    isCompleted = true;
                }
            }

        }


        /**
         * Returns the node (vertex) with the heaviest neighborhood (maximal neighborhood weight).
         *
         * time complexity: O(1).
         *
         * @return the node (vertex) with the heaviest neighborhood (maximal neighborhood weight).
         */
        public Node max() {
            return this.heapArr[0].value;
        }


        /**
         * Decreases the neighborhood weight of heapNode by delta.
         *
         * time complexity: O(log(n)).
         *
         * @param heapNode - the (heap) node with neighborhood weight to decrease.
         * @param delta - a number to decrease by.
         */
        public void decreaseNeighborhoodWeight(HeapNode heapNode, int delta) {
            heapNode.key -= delta;
            this.heapifyDown(heapNode.heapIndex);
        }


        /**
         * Increases the neighborhood weight of heapNode by delta.
         *
         * time complexity: O(log(n)).
         *
         * @param heapNode - the (heap) node with neighborhood weight to increase.
         * @param delta - a number to increase by.
         */
        public void increaseNeighborhoodWeight(HeapNode heapNode, int delta) {
            heapNode.key += delta;
            this.heapifyUp(heapNode.heapIndex);
        }


        /**
         * Deletes heapNode from the heap.
         *
         * time complexity: O(log(n)).
         *
         * @param heapNode - the (heap) node to delete.
         */
        public void deleteHeapNode(HeapNode heapNode) {
            this.setHeapNodeAtIndex(this.heapArr[this.getSize() - 1], heapNode.heapIndex);
            this.heapArr[this.getSize() - 1] = null;
            this.size -= 1;

            this.heapifyUp(heapNode.heapIndex);
            this.heapifyDown(heapNode.heapIndex);
        }



        /**
         * This class represents a (heap) node in the heap, which its key is neighborhood weight.
         */
        public class HeapNode {
            /**
             * The neighborhood weight.
             */
            public int key;

            /**
             * The node (vertex) that this (heap) node represents its neighborhood weight.
             */
            public Node value;

            /**
             * The index of this (heap) node in the array of the heap.
             */

            public int heapIndex;

            /**
             * Creates a new (heap) node object, given its key (neighborhood weight),
             * its value (corresponding node (vertex)) and its index in the array of the heap.
             *
             * time complexity: O(1).
             *
             * @param key - The neighborhood weight.
             * @param value - The node (vertex) that this (heap) node represents its neighborhood weight.
             * @param heapIndex - The index of this (heap) node in the array of the heap.
             */
            public HeapNode(int key, Node value, int heapIndex) {
                this.key = key;
                this.value = value;
                this.heapIndex = heapIndex;
            }
        }
    }



    /**
     * This class implements specific necessary operations of the ADT dictionary (and more) by a hash table.
     */
    public static class HashTable {
        /**
         * A prime number that is used for the modular hash function.
         */
        public int p;

        /**
         * A number between 1 and p-1 that is used for the modular hash function.
         */
        public long a;

        /**
         * A number between 0 and p-1 that is used for the modular hash function.
         */
        public long b;

        /**
         * The array that represents the hash table.
         */
        public LinkedList<HashTableNode>[] table;


        /**
         * Initializes the hash table that maps node id to hash node.
         *
         * time complexity: O(n) expected.
         *
         * @param maxHeap - the max-heap that maintains the heaviest neighborhood in the graph.
         */
        public HashTable(MaxHeap maxHeap) {
            //Initializes the hash table.
            this.table = new LinkedList[maxHeap.getSize()];
            for (int i = 0; i < maxHeap.getSize(); i++) {
                this.table[i] = new LinkedList<HashTableNode>();
            }

            //Initializes the modular hash function.
            this.p = 1000000009;
            Random random = new Random();
            this.a = random.nextInt(this.p - 1) + 1;
            this.b = random.nextInt(this.p);

            //Inserts the elements to the hash table.
            for (int i = 0; i < maxHeap.getSize(); i++) {
                this.insert(maxHeap.heapArr[i].value.getId(), i, maxHeap.heapArr[i]);
            }
        }


        /**
         * Returns the chain in the hash table where the key node_id should be.
         *
         * time complexity: O(1).
         *
         * @param node_id - an id of a vertex.
         * @return the chain in the hash table where the key node_id should be.
         */
        public LinkedList<HashTableNode> findChain(int node_id) {
            return this.table[(int) (((this.a * node_id + this.b) % this.p) % this.table.length)];
        }


        /**
         * Returns the list node in a hash table chain that contains the hash table node with the key node_id,
         * or null if such a list node does not exist.
         *
         * time complexity: O(1) expected.
         *
         * @param node_id - an id of a vertex.
         * @return the list node that contains the hash table node with the key node_id,
         * or null if such a list node does not exist.
         */
        public LinkedList<HashTableNode>.ListNode find(int node_id) {
            return this.findChain(node_id).retrieveNode(new HashTableNode(node_id, -1, null));
        }


        /**
         * If an hash table node with the key node_id does not exists in the table,
         * inserts the trio node_id, nodeIndex and heapNode to the hash table (wrapped with HashTableNode object),
         * with the key node_id.
         * Otherwise, the function does nothing.
         *
         * time complexity: O(1) expected.
         *
         * @param node_id - an id of a vertex
         * @param nodeIndex - the index of a vertex with the id node_id (this is the
         *                  fixed index of the vertex in the neighborhoods list)
         * @param heapNode - the heap node in max-heap that corresponds to the vertex with the id node_id
         */
        public void insert(int node_id, int nodeIndex, MaxHeap.HeapNode heapNode) {
            if (this.find(node_id) == null){
                this.findChain(node_id).insertFirst(new HashTableNode(node_id, nodeIndex, heapNode));
            }
        }


        /**
         * Deletes the list node in a hash table chain that contains the hash table node with the key node_id,
         * if such a list node exist.
         *
         * time complexity: O(1) expected.
         *
         * @param node_id - an id of a vertex.
         * @return the hash table node that contains the key node_id,
         * or null if such a hash table node does not exist.
         */
        public HashTableNode delete(int node_id) {
            LinkedList<HashTableNode>.ListNode listNode = this.find(node_id);

            if (listNode == null) {
                return null;
            }

            HashTableNode hashTableNode = listNode.item;
            this.findChain(node_id).deleteNodeFromList(listNode);
            return hashTableNode;
        }



        /**
         * This class represents a hash table node in the hash table of the graph, which its key is node_id.
         */
        public static class HashTableNode {
            /**
             * The id of the vertex which this hash table node represents.
             */
            public int node_id;

            /**
             * The index of a vertex with the id node_id (this is the fixed index of the vertex in the neighborhoods list)
             */
            public int nodeNListIndex;

            /**
             * The heap node in max-heap that corresponds to the vertex with the id node_id.
             */
            public MaxHeap.HeapNode heapNode;


            /**
             * Creates a new hash table node object, given its key (node_id),
             * and the values nodeNListIndex and heapNode.
             *
             * time complexity: O(1).
             *
             * @param node_id - The id of the vertex which this hash table node represents.
             * @param nodeNListIndex - The index of a vertex with the id node_id (this is the fixed
             *                       index of the vertex in the neighborhoods list)
             * @param heapNode - The heap node in max-heap that corresponds to the vertex with the id node_id.
             */
            public HashTableNode(int node_id, int nodeNListIndex, MaxHeap.HeapNode heapNode) {
                this.node_id = node_id;
                this.nodeNListIndex = nodeNListIndex;
                this.heapNode = heapNode;
            }

            /**
             * Returns 'true' if
             * o is a HashTableNode object and
             * its node_id field is equal to the node_id field of the current hash table node.
             * Otherwise, the function returns 'false'.
             *
             * time complexity: O(1).
             *
             * @param o - Object o to check
             * @return 'true' if
             * o is a HashTableNode object and
             * its node_id field is equal to the node_id field of the current hash table node.
             * Otherwise, the function returns 'false'.
             */
            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || this.getClass() != o.getClass()) {
                    return false;
                }
                HashTableNode that = (HashTableNode) o;
                return this.node_id == that.node_id;
            }
        }
    }
}