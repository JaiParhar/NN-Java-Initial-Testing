package main;

import main.neural.Network;

public class Main {

	public static void main(String[] args) {
		
		//Value is 0.3463745322878998
		Network net = new Network(1, 2, 2, 1);
		
		net.getInputLayer()[0].setValue(0.5);
		net.randomizeSynapseWeights(0, 1);
		net.randomizeNeuronBiases(0, 1);
		net.calculateNetwork();
		
		try {
			
			//Network.saveToFile(net, "C:/Users/Kush/Desktop/network.nn");
			Network neuralNet = Network.loadFromFile("C:/Users/Kush/Desktop/network.nn");
			System.out.println(neuralNet.getOutputLayer()[0].getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
