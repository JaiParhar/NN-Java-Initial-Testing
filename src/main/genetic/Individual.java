package main.genetic;

import main.neural.Network;

public class Individual {

	public static final int INPUT_NEURONS = 26*20;
	public static final int HIDDEN_LAYERS = 2;
	public static final int HIDDEN_NEURONS = 50;
	public static final int OUTPUT_NEURONS = 3;
	
	public static final int WEIGHT_RANGE = 10;
	public static final int BIAS_RANGE = 10;
	
	Network network;
	
	public Individual() {
		network = new Network(INPUT_NEURONS, HIDDEN_LAYERS, HIDDEN_NEURONS, OUTPUT_NEURONS);
	}
	
	public void generateRandom(int seed) {
		network.randomizeSynapseWeights(seed, WEIGHT_RANGE);
		network.randomizeNeuronBiases(seed+1, BIAS_RANGE);
	}
	
	public void loadIndividual(String path) {
		try {
			network = Network.loadFromFile(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Individual getMutatedIndividual() {
		
		return null;
	}
	
}
