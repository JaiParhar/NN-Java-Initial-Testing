package main.genetic;

import java.util.Random;

import main.neural.Network;

public class Individual {

	public static final int INPUT_NEURONS = 26*20;
	public static final int HIDDEN_LAYERS = 2;
	public static final int HIDDEN_NEURONS = 50;
	public static final int OUTPUT_NEURONS = 3;
	
	public static final double WEIGHT_RANGE = 10;
	public static final double BIAS_RANGE = 10;
	
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
	
	public Network getNetwork() {
		return network;
	}
	
	public static Individual getMutatedIndividual(Individual indiv, int mutations, int seed) {
		while(mutations > 0) {
			int totalNeurons = INPUT_NEURONS + (HIDDEN_LAYERS * HIDDEN_NEURONS) + OUTPUT_NEURONS;
			int totalSynapses 
					= (indiv.getNetwork().getInputSynapses().length * indiv.getNetwork().getInputSynapses()[0].length)
					+ (indiv.getNetwork().getHiddenSynapses().length * indiv.getNetwork().getHiddenSynapses()[0].length * indiv.getNetwork().getHiddenSynapses()[0][0].length)
					+ (indiv.getNetwork().getOutputSynapses().length * indiv.getNetwork().getOutputSynapses()[0].length);
			
			double mutationChance = 1.0/((double)totalNeurons + (double)totalSynapses);
			
			Random r = new Random(seed);
			
			//Mutate hidden layers
			for(int i = 0; i < HIDDEN_LAYERS; i++) {
				for(int j = 0; j < HIDDEN_NEURONS; j++) {
					if(r.nextDouble() < mutationChance) {
						indiv.getNetwork().getHiddenLayer()[i][j].randomizeBias(r, BIAS_RANGE);
						mutations--;
					}
				}
			}
			
			//Mutate output layers
			for(int out = 0; out < OUTPUT_NEURONS; out++) {
				if(r.nextDouble() < mutationChance) {
					indiv.getNetwork().getOutputLayer()[out].randomizeBias(r, BIAS_RANGE);
					mutations--;
				}
			}
			
			//Mutate input synapses
			for(int i = 0; i < indiv.getNetwork().getInputSynapses().length; i++) {
				for(int j = 0; j < indiv.getNetwork().getInputSynapses()[0].length; j++) { 
					if(r.nextDouble() < mutationChance) {
						indiv.getNetwork().getInputSynapses()[i][j].randomizeWeight(r, WEIGHT_RANGE);
						mutations--;
					}
				}
			}
			
		}
		
		return indiv;
	}
	
}
