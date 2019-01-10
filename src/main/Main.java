package main;

import main.genetic.Individual;
import main.neural.Network;

public class Main {

	public static void main(String[] args) {
		
		Network n = new Network(1, 1, 1, 1);
		//n.randomizeNeuronBiases(102, 10);
		//n.randomizeSynapseWeights(2, 10);
		//System.out.println(n.getOutputSynapses()[0][0].getWeight());
		
		Individual ind = new Individual();
		ind.generateRandom(423);
		System.out.println(ind.inputString("Hello", 0));
		
	}

}
