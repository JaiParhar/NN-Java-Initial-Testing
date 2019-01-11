package main.genetic;

import java.util.Random;

import main.neural.Network;

public class Individual {

	public static final int MAX_LETTERS = 20;
	
	public static final int INPUT_NEURONS = 26 * MAX_LETTERS;
	public static final int HIDDEN_LAYERS = 2;
	public static final int HIDDEN_NEURONS = 50;
	public static final int OUTPUT_NEURONS = 4;
	
	public static final double WEIGHT_RANGE = 10;
	public static final double BIAS_RANGE = 10;
	
	Network network;
	
	public int correctAnswer;
	int testRuns;
	
	double fitness;
	
	public Individual() {
		correctAnswer = 0;
		testRuns = 0;
		fitness = 0.0;
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
	
	public boolean inputString(String input, int correctOutputIndex) {
		
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		input = input.toUpperCase();
		if(input.length() > MAX_LETTERS) {
			input = input.substring(0, MAX_LETTERS);
		}
		
		//Iterates through each letter in the input
		for(int in = 0; in < input.length(); in++) {
			char currLet = input.charAt(in);
			for(int alphabet = 0; alphabet < letters.length(); alphabet++) {
				if(currLet == letters.charAt(alphabet)) {
					network.getInputLayer()[alphabet+letters.length()*in].setValue(1.0);
				} else {
					network.getInputLayer()[alphabet+letters.length()*in].setValue(0.0);
				}
			}
			
		}
		
		network.calculateNetwork();
		
		double highestValue = -1.0;
		int highestIndex = -1;
		for(int i = 0; i < network.getOutputLayer().length; i++) {
			if(network.getOutputLayer()[i].getValue() > highestValue) {
				highestValue = network.getOutputLayer()[i].getValue();
				highestIndex = i;
			}
		}
		
		return (highestIndex == correctOutputIndex);
	}
	
	public static Individual getMutatedIndividual(Individual indiv, int mutations, int seed) {
		Random r = new Random(seed);
		while(mutations > 0) {
			int totalNeurons = INPUT_NEURONS + (HIDDEN_LAYERS * HIDDEN_NEURONS) + OUTPUT_NEURONS;
			int totalSynapses 
					= (indiv.getNetwork().getInputSynapses().length * indiv.getNetwork().getInputSynapses()[0].length)
					+ (indiv.getNetwork().getHiddenSynapses().length * indiv.getNetwork().getHiddenSynapses()[0].length * indiv.getNetwork().getHiddenSynapses()[0][0].length)
					+ (indiv.getNetwork().getOutputSynapses().length * indiv.getNetwork().getOutputSynapses()[0].length);
			
			double mutationChance = 1.0/((double)totalNeurons + (double)totalSynapses);
			
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
				for(int j = 0; j < indiv.getNetwork().getInputSynapses()[i].length; j++) { 
					if(r.nextDouble() < mutationChance) {
						indiv.getNetwork().getInputSynapses()[i][j].randomizeWeight(r, WEIGHT_RANGE);
						mutations--;
					}
				}
			}
			
			//Mutate hidden synapses
			for(int i = 0; i < indiv.getNetwork().getHiddenSynapses().length; i++) {
				for(int j = 0; j < indiv.getNetwork().getHiddenSynapses()[i].length; j++) {
					for(int k = 0; k < indiv.getNetwork().getHiddenSynapses()[i][j].length; k++) {
						if(r.nextDouble() < mutationChance) {
							indiv.getNetwork().getHiddenSynapses()[i][j][k].randomizeWeight(r, WEIGHT_RANGE);
							mutations--;
						}
					}
				}
			}
			
			//Mutate output synapses
			for(int i = 0; i < indiv.getNetwork().getOutputSynapses().length; i++) {
				for(int j = 0; j < indiv.getNetwork().getOutputSynapses()[i].length; j++) { 
					if(r.nextDouble() < mutationChance) {
						indiv.getNetwork().getOutputSynapses()[i][j].randomizeWeight(r, WEIGHT_RANGE);
						mutations--;
					}
				}
			}
			
		}
		
		return indiv;
	}
	
	public void printWandB() {
		System.out.println("Biases start");
		for(int i = 0; i < HIDDEN_LAYERS; i++) {
			for(int j = 0; j < HIDDEN_NEURONS; j++) {
				System.out.println("Hidden neuron at [" + i + "][" + j + "] is " + getNetwork().getHiddenLayer()[i][j].getBias());
			}
		}
		System.out.println();
		
		for(int out = 0; out < OUTPUT_NEURONS; out++) {
			System.out.println("Output neuron at [" + out + "] is " + getNetwork().getOutputLayer()[out].getBias());
		}
		
		System.out.println("Biases end");
		System.out.println("Weight start");
		
		for(int i = 0; i < getNetwork().getInputSynapses().length; i++) {
			for(int j = 0; j < getNetwork().getInputSynapses()[i].length; j++) { 
				System.out.println("Input synapse weight at [" + i + "][" + j + "] is " + getNetwork().getInputSynapses()[i][j].getWeight());
			}
		}
		
		System.out.println();
		
		for(int i = 0; i < getNetwork().getHiddenSynapses().length; i++) {
			for(int j = 0; j < getNetwork().getHiddenSynapses()[i].length; j++) {
				for(int k = 0; k < getNetwork().getHiddenSynapses()[i][j].length; k++) {
					System.out.println("Hidden synapse weight at [" + i + "][" + j + "][" + k + "] is " + getNetwork().getHiddenSynapses()[i][j][k].getWeight());
				}
			}
		}
		
		System.out.println();
		
		for(int i = 0; i < getNetwork().getOutputSynapses().length; i++) {
			for(int j = 0; j < getNetwork().getOutputSynapses()[i].length; j++) { 
				System.out.println("Output synapse weight at [" + i + "][" + j + "] is " + getNetwork().getOutputSynapses()[i][j].getWeight());
			}
		}
		
		System.out.println("Weight end");
	}
	
	public void recordTestRun(boolean correct) {
		if(correct) { correctAnswer++; }
		testRuns++;
		setFitness(((double)correctAnswer) / ((double)testRuns));
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double f) {
		fitness = f;
	}
	
}
