package main;

import main.neural.Network;

public class Main {

	public static void main(String[] args) {
		Network net = new Network(1, 1, 1, 1);
		
		net.getInputLayer()[0].setValue(0.5);
		net.calculateNetwork();
		
		System.out.println(net.getOutputLayer()[0].getValue());
	}

}
