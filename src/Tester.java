import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


class GraphTest {

    Graph.Node[] nodes = new Graph.Node[]{new Graph.Node(7, 1), new Graph.Node(5, 2), new Graph.Node(9, 4)};
    Graph graph;

    public boolean checkGraphAttributes(Graph graph, int[] nodes_id, int[] nodes_neighborhoodWeight,int max_id, int max_neighborhoodWeight, int numNodes, int numEdges) {
        boolean nodesToNeighborhoodWeight = true;
        for (int i = 0; i < nodes_id.length; i++) {
            if (graph.getNeighborhoodWeight(nodes_id[i]) != nodes_neighborhoodWeight[i]) {
                nodesToNeighborhoodWeight = false;
                break;
            }
        }

        return
                nodesToNeighborhoodWeight &&
                graph.maxNeighborhoodWeight().id == max_id &&
                graph.getNeighborhoodWeight(graph.maxNeighborhoodWeight().id) == max_neighborhoodWeight &&
                graph.getNumNodes() == numNodes &&
                graph.getNumEdges() == numEdges &&
                checkGraphMaxHeap();
    }

    @BeforeEach
    public void beforeTest() {
        graph = new Graph(nodes);
    }


    @Test
    public void generalTest() {
        assertTrue(checkGraphAttributes(graph, new int[]{7, 5, 9}, new int[]{1, 2, 4}, 9, 4, 3, 0));

        assertTrue(graph.addEdge(7,5));
        assertTrue(checkGraphAttributes(graph, new int[]{7, 5, 9}, new int[]{3, 3, 4}, 9, 4, 3, 1));

        assertTrue(graph.addEdge(7,9));
        assertTrue(checkGraphAttributes(graph, new int[]{7, 5, 9}, new int[]{7, 3, 5}, 7, 7, 3, 2));

        assertTrue(graph.deleteNode(7));
        assertTrue(checkGraphAttributes(graph, new int[]{5, 9}, new int[]{2, 4}, 9, 4, 2, 0));

        assertFalse(graph.deleteNode(10));
        assertFalse(graph.addEdge(10, 11));
        assertFalse(graph.addEdge(5, 10));
        assertFalse(graph.addEdge(10, 9));

        assertTrue(graph.addEdge(5, 9));
        assertTrue(checkGraphAttributes(graph, new int[]{5, 9}, new int[]{6, 6}, 5, 6, 2, 1));

        assertEquals(-1, graph.getNeighborhoodWeight(10));

        assertTrue(graph.deleteNode(5));
        assertTrue(checkGraphAttributes(graph, new int[]{9}, new int[]{4}, 9, 4, 1, 0));

        assertFalse(graph.addEdge(5, 9));
        assertFalse(graph.addEdge(9, 5));

        assertTrue(graph.deleteNode(9));
        assertFalse(graph.deleteNode(9));
        assertEquals(-1, graph.getNeighborhoodWeight(9));
        assertNull(graph.maxNeighborhoodWeight());
    }

    @Test
    public void testNeighborhoodsList() {
        Graph.NeighborhoodsList neighborhoodsList = graph.neighborhoodsList;
        assertEquals(0, neighborhoodsList.numEdges);
        Arrays.stream(neighborhoodsList.arrNeighborsLists).forEach(list -> assertEquals(0,list.length));
        checkGraphMaxHeap();
        assertTrue(graph.addEdge(5,7));
        checkGraphMaxHeap();
        Graph.LinkedList<Graph.NeighborhoodsList.NeighborNode> arrNeighborsListOf5 = neighborhoodsList.arrNeighborsLists[getNListIndex(5)];
        Graph.LinkedList<Graph.NeighborhoodsList.NeighborNode> arrNeighborsListOf7 = neighborhoodsList.arrNeighborsLists[getNListIndex(7)];
        assertEquals(1, arrNeighborsListOf5.length);
        assertEquals(1, arrNeighborsListOf7.length);
        assertEquals(7, arrNeighborsListOf5.retrieveFirstNode().item.node_id);
        assertEquals(5, arrNeighborsListOf7.retrieveFirstNode().item.node_id);

        graph.deleteNode(5);
        checkGraphMaxHeap();
        assertEquals(4, graph.maxNeighborhoodWeight().weight);
        arrNeighborsListOf7 = neighborhoodsList.arrNeighborsLists[getNListIndex(7)];
        assertEquals(0, arrNeighborsListOf7.length);
    }

    private boolean checkGraphMaxHeap() {
        return checkHeap(graph.neighborhoodWeightHeap);
    }

    private int getNListIndex(int node_id) {
        return graph.tableIdToRepresentation.find(node_id).item.nodeNListIndex;
    }

    private boolean checkHeap(Graph.MaxHeap heap) {
        boolean flag = true;
        for (int i = 0; i < heap.getSize(); i++) {
            if (heap.left(i) < heap.getSize()) {
                flag &= heap.heapArr[i].key >= heap.heapArr[heap.left(i)].key;
            }
            if (heap.right(i) < heap.getSize()) {
                flag &= heap.heapArr[i].key >= heap.heapArr[heap.right(i)].key;
            }
        }
        return flag;
    }
}