package main;

import main.genetic.Genome;
import main.neural.Network;

public class Main {

	public static void main(String[] args) {
		//Network net = new Network(1, 1, 1, 1);
		
		//net.getInputLayer()[0].setValue(0.5);
		//net.randomizeSynapseWeights(421, 1);
		//net.randomizeNeuronBiases(421, 1);
		//net.calculateNetwork();
		
		Genome g = new Genome();
		g.generateGenomeRandom(420);
		g.saveGenome();
		
		//System.out.println(net.getOutputLayer()[0].getBias());
	}

}
