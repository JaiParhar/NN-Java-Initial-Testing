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
		ind.generateRandom(420);
		
		int i = 0;
		while(true) {
			double a = ind.getNetwork().getOutputLayer()[0].getBias();
			ind = Individual.getMutatedIndividual(ind, 100, i); i++;
			double b = ind.getNetwork().getOutputLayer()[0].getBias();
			System.out.println(a + " " + b);
			if(a != b) { System.out.println(a + " " + b); break; }
			
		}
		
	}

}
