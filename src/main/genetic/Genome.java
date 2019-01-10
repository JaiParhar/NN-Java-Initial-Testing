package main.genetic;

import main.neural.Network;

public class Genome {

	public static final int INPUT_NEURONS = 26*20;
	public static final int HIDDEN_LAYERS = 2;
	public static final int HIDDEN_NEURONS = 50;
	public static final int OUTPUT_NEURONS = 3;
	
	public static final int WEIGHT_RANGE = 10;
	public static final int BIAS_RANGE = 10;
	
	Network network;
	
	public Genome() {
		network = new Network(INPUT_NEURONS, HIDDEN_LAYERS, HIDDEN_NEURONS, OUTPUT_NEURONS);
	}
	
	public void generateGenomeRandom(int seed) {
		network.randomizeSynapseWeights(seed, WEIGHT_RANGE);
		network.randomizeNeuronBiases(seed+1, BIAS_RANGE);
	}
	
	public String saveGenome() {
		String neuronBiases = "-Biases-\n";
		
		for(int i = 0; i < network.getHiddenLayer().length; i++) {
			for(int j = 0; j < network.getHiddenLayer()[i].length; j++) { 
				neuronBiases += network.getHiddenLayer()[i][j].getBias() + " ";
			}
		}
		
		neuronBiases += "\n";
		
		for(int i = 0; i < network.getOutputLayer().length; i++) {
			neuronBiases += network.getOutputLayer()[i].getBias() + " ";
		}
		
		neuronBiases += "\n-Synapses-\n";
		
		
		
		
		
		System.out.println(neuronBiases);
		
		return null;
	}
	
}
