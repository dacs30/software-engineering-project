package edu.wpi.MochaManticores;

import java.util.HashMap;

/**
 * This class is intended to generate a test map
 * to demonstrate algorithm functionality
 * Map found here:https://drive.google.com/file/d/17WtZL_ERqR0NXIQHHikw1jd-Ou7aTEYv/view?usp=sharing
 * @author aksil
 */
public class TestMapSetup {
    //Declare instance variables
    public NodeMap testMap;

    public void setUpMap() {
        /**
         * function: setUpMap()
         * usage: generates a small map for testing purposes
         * inputs: none
         * returns: void
         */

        //Create Nodes
        //Node 1
        HashMap<Integer, Integer> hm1 = new HashMap<Integer, Integer>(8);
        hm1.put(2, 10);
        hm1.put(4, 10);
        hm1.put(5, 14);
        ConnectedNodes neighbors1 = new ConnectedNodes(hm1);
        ParkingSpace node1 = new ParkingSpace(1, neighbors1, 1, "PS1", false);

        //Node 2
        HashMap<Integer, Integer> hm2 = new HashMap<Integer, Integer>(8);
        hm2.put(1, 10);
        hm2.put(3, 10);
        hm2.put(4, 14);
        hm2.put(5, 10);
        hm2.put(6, 14);
        ConnectedNodes neighbors2 = new ConnectedNodes(hm2);
        ParkingSpace node2 = new ParkingSpace(2, neighbors2, 1, "PS2", false);

        //Node 3
        HashMap<Integer, Integer> hm3 = new HashMap<Integer, Integer>(8);
        hm3.put(2, 10);
        hm3.put(6, 10);
        hm3.put(5, 14);
        ConnectedNodes neighbors3 = new ConnectedNodes(hm3);
        ParkingSpace node3 = new ParkingSpace(3, neighbors3, 1, "PS3", false);

        //Node 4
        HashMap<Integer, Integer> hm4 = new HashMap<Integer, Integer>(8);
        hm4.put(1, 10);
        hm4.put(5, 10);
        hm4.put(2, 14);
        hm4.put(7, 23);
        ConnectedNodes neighbors4 = new ConnectedNodes(hm4);
        ParkingSpace node4 = new ParkingSpace(4, neighbors4, 1, "PS4", false);

        //Node 5
        HashMap<Integer, Integer> hm5 = new HashMap<Integer, Integer>(8);
        hm5.put(1, 14);
        hm5.put(2, 10);
        hm5.put(3, 14);
        hm5.put(4, 10);
        hm5.put(6, 10);
        hm5.put(7, 14);
        ConnectedNodes neighbors5 = new ConnectedNodes(hm5);
        ParkingSpace node5 = new ParkingSpace(5, neighbors5, 1, "PS5", false);

        //Node 6
        HashMap<Integer, Integer> hm6 = new HashMap<Integer, Integer>(8);
        hm6.put(2, 14);
        hm6.put(3, 10);
        hm6.put(5, 10);
        hm6.put(7, 10);
        ConnectedNodes neighbors6 = new ConnectedNodes(hm6);
        ParkingSpace node6 = new ParkingSpace(6, neighbors6, 1, "PS6", false);

        //Node 7
        HashMap<Integer, Integer> hm7 = new HashMap<Integer, Integer>(8);
        hm7.put(4, 23);
        hm7.put(5, 14);
        hm7.put(6, 10);
        hm7.put(13, 10);
        hm7.put(14, 30);
        ConnectedNodes neighbors7 = new ConnectedNodes(hm7);
        POI node7 = new POI(7, neighbors7, 1, "CrossWalk1");

        //Node 13
        HashMap<Integer, Integer> hm13 = new HashMap<Integer, Integer>(8);
        hm13.put(7, 10);
        hm13.put(8, 10);
        ConnectedNodes neighbors13 = new ConnectedNodes(hm13);
        POI node13 = new POI(13, neighbors13, 1, "Stairs1");

        //Node 14
        HashMap<Integer, Integer> hm14 = new HashMap<Integer, Integer>(8);
        hm14.put(7, 30);
        hm14.put(8, 30);
        ConnectedNodes neighbors14 = new ConnectedNodes(hm14);
        POI node14 = new POI(14, neighbors14, 1, "Ramp1");

        //Node 8
        HashMap<Integer, Integer> hm8 = new HashMap<Integer, Integer>(8);
        hm8.put(13, 10);
        hm8.put(14, 30);
        hm8.put(9, 10);
        hm8.put(12, 14);
        ConnectedNodes neighbors8 = new ConnectedNodes(hm8);
        POI node8 = new POI(8, neighbors8, 1, "FrontDoor1");

        //Node 9
        HashMap<Integer, Integer> hm9 = new HashMap<Integer, Integer>(8);
        hm9.put(8, 10);
        hm9.put(10, 10);
        hm9.put(11, 10);
        hm9.put(12, 10);
        ConnectedNodes neighbors9 = new ConnectedNodes(hm9);
        MapNode node9 = new MapNode(9, neighbors9, 1);

        //Node 10
        HashMap<Integer, Integer> hm10 = new HashMap<Integer, Integer>(8);
        hm10.put(9, 10);
        hm10.put(11, 14);
        hm10.put(12, 14);
        ConnectedNodes neighbors10 = new ConnectedNodes(hm10);
        POI node10 = new POI(10, neighbors10, 1, "Reception1");

        //Node 11
        HashMap<Integer, Integer> hm11 = new HashMap<Integer, Integer>(8);
        hm11.put(9, 10);
        hm11.put(10, 14);
        ConnectedNodes neighbors11 = new ConnectedNodes(hm11);
        POI node11 = new POI(11, neighbors11, 1, "Florist1");

        //Node 12
        HashMap<Integer, Integer> hm12 = new HashMap<Integer, Integer>(8);
        hm12.put(8, 14);
        hm12.put(9, 10);
        hm12.put(10, 14);
        ConnectedNodes neighbors12 = new ConnectedNodes(hm12);
        POI node12 = new POI(12, neighbors12, 1, "Door2");

        //Create NodeMap object
        HashMap<Integer, MapNode> testMap1 = new HashMap<Integer, MapNode>(20);
        testMap1.put(1, node1);
        testMap1.put(2, node2);
        testMap1.put(3, node3);
        testMap1.put(4, node4);
        testMap1.put(5, node5);
        testMap1.put(6, node6);
        testMap1.put(7, node7);
        testMap1.put(8, node8);
        testMap1.put(9, node9);
        testMap1.put(10, node10);
        testMap1.put(11, node11);
        testMap1.put(12, node12);
        testMap1.put(13, node13);
        testMap1.put(14, node14);
        NodeMap testNodeMap1 = new NodeMap(testMap1, "test map 1");
        this.testMap = testNodeMap1;
    }
}
