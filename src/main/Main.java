package main;

import main.neural.Network;

public class Main {

	public static void main(String[] args) {
		Network net = new Network(2, 1, 1, 2);
		
		
		System.out.println(net.getOutputLayer()[0].getLayer());
	}

}
