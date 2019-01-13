package main;

import main.neural.Network;

public class LanguageDetectionAI {

	public static final int MAX_WORD_LENGTH = 1;
	public static final int ALPHABET_LENGTH = 26;
	
	public static final int INPUT_NEURONS = ALPHABET_LENGTH * MAX_WORD_LENGTH;
	public static final int HIDDEN_LAYERS = 1;
	public static final int HIDDEN_NEURONS = 20;
	public static final int OUTPUT_NEURONS = 10;
	
	Network net;
	
	public LanguageDetectionAI() {
		net = new Network(INPUT_NEURONS, HIDDEN_LAYERS, HIDDEN_NEURONS, OUTPUT_NEURONS);
	}
	
	public int realRun(String input) {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		input = input.toUpperCase();
		if(input.length() > MAX_WORD_LENGTH) {
			input = input.substring(0, MAX_WORD_LENGTH);
		}
		
		for(int in = 0; in < input.length(); in++) {
			char currLet = input.charAt(in);
			for(int alphabet = 0; alphabet < letters.length(); alphabet++) {
				if(currLet == letters.charAt(alphabet)) {
					net.getInputLayer()[alphabet+letters.length()*in].setValue(1.0);
				} else {
					net.getInputLayer()[alphabet+letters.length()*in].setValue(0.0);
				}
			}
		}
		
		net.calculateNetwork();
		
		double highestValue = -1.0;
		int highestIndex = -1;
		for(int i = 0; i < net.getOutputLayer().length; i++) {
			if(net.getOutputLayer()[i].getValue() > highestValue) {
				highestValue = net.getOutputLayer()[i].getValue();
				highestIndex = i;
			}
		}
		
		return highestIndex;
	}
	
	public double trainingRun(String input, int correctAnswer) {
		
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		input = input.toUpperCase();
		if(input.length() > MAX_WORD_LENGTH) {
			input = input.substring(0, MAX_WORD_LENGTH);
		}
		
		double desiredOutput[] = new double[net.getOutputLayer().length];
		for(int i = 0; i < desiredOutput.length; i++) {
			desiredOutput[i] = 0.0;
		}
		desiredOutput[correctAnswer] = 1.0;
		
		for(int in = 0; in < input.length(); in++) {
			char currLet = input.charAt(in);
			for(int alphabet = 0; alphabet < letters.length(); alphabet++) {
				if(currLet == letters.charAt(alphabet)) {
					net.getInputLayer()[alphabet+letters.length()*in].setValue(1.0);
				} else {
					net.getInputLayer()[alphabet+letters.length()*in].setValue(0.0);
				}
			}
		}
		
		net.gradientDescent(desiredOutput, 0.01);
		
		return net.calculateCost(desiredOutput);
	}
	
}
