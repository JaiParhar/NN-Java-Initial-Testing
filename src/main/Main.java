package main;

import main.neural.Network;

public class Main {

	public static void main(String[] args) {
		Network net = new Network(1, 2, 5, 3);
		
		net.getInputLayer()[0].setValue(1);
		net.randomizeSynapseWeights(420, 1);
		net.calculateNetwork();
		
		//ERROR AT THE OUTPUT LAYER
		//System.out.println(net.getInputLayer()[0].getValue());
		//System.out.println(net.getHiddenLayer(0)[0].getValue());
		System.out.println(net.getOutputLayer()[0].getValue());
		System.out.println(net.getOutputLayer()[1].getValue());
		System.out.println(net.getOutputLayer()[2].getValue());
	}

}
