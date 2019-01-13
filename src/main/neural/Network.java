package main.neural;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Network implements Serializable {

	private static final long serialVersionUID = -9209288686210961120L;
	
	Neuron inputNeurons[];
	Neuron hiddenNeurons[][];
	Neuron outputNeurons[];
	
	Synapse inputSynapses[][];
	Synapse hiddenSynapses[][][];
	Synapse outputSynapses[][];
	
	public Network(int inNeurons, int hLayers, int hNeurons, int outNeurons) {
		if(hLayers < 1) {
			System.out.println("Error: At least 1 hidden layer is required");
			System.exit(-1);
		}
		initNeurons(inNeurons, hLayers, hNeurons, outNeurons);
		initSynapses();
	}
	
	private void initSynapses() {
		//Input to first layer of hidden
		inputSynapses = new Synapse[inputNeurons.length][hiddenNeurons[0].length];
		for(int in = 0; in < inputNeurons.length; in++) {
			for(int hn = 0; hn < hiddenNeurons[0].length; hn++) {
				inputSynapses[in][hn] = new Synapse(inputNeurons[in], hiddenNeurons[0][hn]);
			}
		}
		
		//Hidden layers to each other
		hiddenSynapses = new Synapse[hiddenNeurons.length-1][hiddenNeurons[0].length][hiddenNeurons[0].length];
		for(int layer = 0; layer < hiddenNeurons.length-1; layer++) {
			for(int start = 0; start < hiddenNeurons[layer].length; start++) {
				for(int end = 0; end < hiddenNeurons[layer+1].length; end++) {
					hiddenSynapses[layer][start][end] = new Synapse(hiddenNeurons[layer][start], hiddenNeurons[layer+1][end]);
				}
			}
		}
		
		//Last layer of hidden to output
		outputSynapses = new Synapse[hiddenNeurons[0].length][outputNeurons.length];
		for(int on = 0; on < outputNeurons.length; on++) {	
			for(int hn = 0; hn < hiddenNeurons[0].length; hn++) {
				outputSynapses[hn][on] = new Synapse(hiddenNeurons[hiddenNeurons.length-1][hn], outputNeurons[on]);
			}
		}
	}
	
	private void initNeurons(int inNeurons, int hLayers, int hNeurons, int outNeurons) {
		inputNeurons = new Neuron[inNeurons];
		for(int in = 0; in < inNeurons; in++) {
			inputNeurons[in] = new Neuron(0);
		}
		
		hiddenNeurons = new Neuron[hLayers][hNeurons];
		for(int hL = 0; hL < hLayers; hL++) {
			for(int hN = 0; hN < hNeurons; hN++) {
				hiddenNeurons[hL][hN] = new Neuron(hL+1);
			}
		}
		
		outputNeurons = new Neuron[outNeurons];
		for(int out = 0; out < outNeurons; out++) {
			outputNeurons[out] = new Neuron(1+hLayers);
		}
	}
	
	public void calculateNetwork() {
		ArrayList<Synapse> synapses = new ArrayList<Synapse>();
		
		//Calculating the first layer of hidden neurons from input layer
		for(int inEndS = 0; inEndS < inputSynapses[0].length; inEndS++) {
			for(int inStartS = 0; inStartS < inputSynapses.length; inStartS++) {
				//Adds all the synapses that end in neuron inEndS to the arraylist, except for bias neuron	
				synapses.add(inputSynapses[inStartS][inEndS]);
			}
			
			//Calculates value of neuron
			inputSynapses[0][inEndS].child.setValueFromSynapses(synapses);
			
			//Clears arraylist to be used in next iteration of loop
			synapses.clear();
		}
		
		//Calculates hidden layers of neurons
		for(int layer = 0; layer < hiddenSynapses.length; layer++) {
			for(int end = 0; end < hiddenSynapses[layer][0].length; end++) {
				for(int start = 0; start < hiddenSynapses[layer].length; start++) {
					//Adds all synapses in Layer layer that end in end to the arraylist
					synapses.add(hiddenSynapses[layer][start][end]);
				}

				//Calculates the value of the child neuron
				hiddenSynapses[layer][0][end].child.setValueFromSynapses(synapses);
				
				//Clears the arraylist to be used in next iteration of loop
				synapses.clear();
			}
		}
		
		//Calculates output layer of neurons
		for(int outEndS = 0; outEndS < outputSynapses[0].length; outEndS++) {
			for(int outStartS = 0; outStartS < outputSynapses.length; outStartS++) {
				//Adds all synapses in that end in outEndS to the arraylist
				synapses.add(outputSynapses[outStartS][outEndS]);
			}
			
			//Calculates the value of the child neuron
			outputSynapses[0][outEndS].child.setValueFromSynapses(synapses);
			
			//Clears the arraylist to be used in next iteration of loop
			synapses.clear();
		}
	}
	
	public void randomizeNeuronBiases(int seed, double range) {
		Random r = new Random(seed);
		
		//Input neurons will not have a bias, as they do not get calculated
		
		//Hidden neurons
		for(int i = 0; i < hiddenNeurons.length; i++) {
			for(int j = 0; j < hiddenNeurons[i].length; j++) {
				hiddenNeurons[i][j].randomizeBias(r, range);
			}
		}
		
		//Hidden neurons
		for(int i = 0; i < outputNeurons.length; i++) {
			outputNeurons[i].randomizeBias(r, range);
		}
	}
	
	public void randomizeSynapseWeights(int seed, double range) {
		Random r = new Random(seed);
		
		//Input synapses
		for(int i = 0; i < inputSynapses.length; i++) {
			for(int j = 0; j < inputSynapses[i].length; j++) {
				inputSynapses[i][j].randomizeWeight(r, range);
			}
		}
		
		//Hidden synapses
		for(int i = 0; i < hiddenSynapses.length; i++) {
			for(int j = 0; j < hiddenSynapses[i].length; j++) {
				for(int k = 0; k < hiddenSynapses[i][j].length; k++) {
					hiddenSynapses[i][j][k].randomizeWeight(r, range);
				}
			}
		}
		
		//Output synapses
		for(int i = 0; i < outputSynapses.length; i++) {
			for(int j = 0; j < outputSynapses[i].length; j++) {
				outputSynapses[i][j].randomizeWeight(r, range);
			}
		}	
		
	}
	
	//TODO: ADD THRESHOLD FOR THE EQUALS TO STATEMENT IN THE LOOPS
	private void gradientDescentNeurons(double desiredOutputs[], double descentStep) {
		//CONCEPT:
		//If the new cost is less than the old cost, keep the change you made
		//If the new cost is greater than the old cost, make a change in the other direction
		//If the cost is the same, revert the change
		
		for(int i = 0; i < hiddenNeurons.length; i++) {
			for(int j = 0; j < hiddenNeurons[i].length; j++) {
				double ogCost = calculateCost(desiredOutputs);
				
				hiddenNeurons[i][j].setBias(hiddenNeurons[i][j].getBias() + descentStep);
				calculateNetwork();
				
				double newCost = calculateCost(desiredOutputs);
				
				if(newCost == ogCost) {
					hiddenNeurons[i][j].setBias(hiddenNeurons[i][j].getBias() - descentStep);
				}else if(newCost > ogCost) {
					hiddenNeurons[i][j].setBias(hiddenNeurons[i][j].getBias() - 2*descentStep);
				}
				//calculateNetwork();
			}
		}
		
		for(int i = 0; i < outputNeurons.length; i++) {
			double ogCost = calculateCost(desiredOutputs);
			
			outputNeurons[i].setBias(outputNeurons[i].getBias() + descentStep);
			calculateNetwork();
			
			double newCost = calculateCost(desiredOutputs);
			
			if(newCost == ogCost) {
				outputNeurons[i].setBias(outputNeurons[i].getBias() - descentStep);
			} else if(newCost > ogCost) {
				outputNeurons[i].setBias(outputNeurons[i].getBias() - 2*descentStep);
			}
			//calculateNetwork();
		}
	}
	
	//TODO: ADD THRESHOLD FOR THE EQUALS TO STATEMENT IN THE LOOPS
	private void gradientDescentSynapses(double desiredOutputs[], double descentStep) {
		//CONCEPT:
		//If the new cost is less than the old cost, keep the change you made
		//If the new cost is greater than the old cost, make a change in the other direction
		//If the cost is the same, revert the change
		
		for(int i = 0; i < inputSynapses.length; i++) {
			for(int j = 0; j < inputSynapses[i].length; j++) {
				double ogCost = calculateCost(desiredOutputs);
				
				inputSynapses[i][j].setWeight(inputSynapses[i][j].getWeight() + descentStep);
				calculateNetwork();
				
				double newCost = calculateCost(desiredOutputs);
				
				if(newCost == ogCost) {
					inputSynapses[i][j].setWeight(inputSynapses[i][j].getWeight() - descentStep);
				}else if(newCost > ogCost) {
					inputSynapses[i][j].setWeight(inputSynapses[i][j].getWeight() - 2*descentStep);
				}
				//calculateNetwork();
			}
		}
		
		for(int i = 0; i < hiddenSynapses.length; i++) {
			for(int j = 0; j < hiddenSynapses[i].length; j++) {
				for(int k = 0; k < hiddenSynapses[i][j].length; k++) {
					double ogCost = calculateCost(desiredOutputs);
					
					hiddenSynapses[i][j][k].setWeight(hiddenSynapses[i][j][k].getWeight() + descentStep);
					calculateNetwork();
					
					double newCost = calculateCost(desiredOutputs);
					
					if(newCost == ogCost) {
						hiddenSynapses[i][j][k].setWeight(hiddenSynapses[i][j][k].getWeight() - descentStep);
					}else if(newCost > ogCost) {
						hiddenSynapses[i][j][k].setWeight(hiddenSynapses[i][j][k].getWeight() - 2*descentStep);
					}
					//calculateNetwork();
				}
			}
		}
		
		for(int i = 0; i < outputSynapses.length; i++) {
			for(int j = 0; j < outputSynapses[i].length; j++) {
				double ogCost = calculateCost(desiredOutputs);
				
				outputSynapses[i][j].setWeight(outputSynapses[i][j].getWeight() + descentStep);
				calculateNetwork();
				
				double newCost = calculateCost(desiredOutputs);
				
				if(newCost == ogCost) {
					outputSynapses[i][j].setWeight(outputSynapses[i][j].getWeight() - descentStep);
				}else if(newCost > ogCost) {
					outputSynapses[i][j].setWeight(outputSynapses[i][j].getWeight() - 2*descentStep);
				}
				//calculateNetwork();
			}
		}
	}
	
	//TODO: FIX THE THRESHOLD FOR THE GRADIENT DESCENTS
	public void gradientDescent(double desiredOutputs[], double descentStep) {
		calculateNetwork();
		gradientDescentSynapses(desiredOutputs, descentStep);
		gradientDescentNeurons(desiredOutputs, descentStep);
	}
	
	public double calculateCost(double desiredOutput[]) {
		this.calculateNetwork();
		
		if(desiredOutput.length != outputNeurons.length) {
			System.out.println("Desired outputs length does not match output neurons length. Check the desired outputs.");
			return -1;
		}
		
		double cost = 0.0;
		for(int i = 0; i < outputNeurons.length; i++) {
			cost += Math.pow((desiredOutput[i] - outputNeurons[i].getValue()), 2.0);
		}
		
		return cost;
	}
	
	public static void saveToFile(Network neuralNet, String path) throws IOException {
		FileOutputStream f = new FileOutputStream("C:/Users/Kush/Desktop/network.nn");
		ObjectOutput s = new ObjectOutputStream(f);
		s.writeObject(neuralNet);
		s.close();
	}
	
	public static Network loadFromFile(String path) throws Exception {
		FileInputStream f = new FileInputStream("C:/Users/Kush/Desktop/network.nn");
		ObjectInput s = new ObjectInputStream(f);
		Network neuralNet = (Network) s.readObject();
		s.close();
		return neuralNet;
	}
	
	public Synapse[][] getInputSynapses() { return inputSynapses; }
	public Synapse[][][] getHiddenSynapses() { return hiddenSynapses; }
	public Synapse[][] getHiddenSynapses(int layer) { return hiddenSynapses[layer]; }
	public Synapse[][] getOutputSynapses() { return outputSynapses; }
	
	public Neuron[] getInputLayer() { return inputNeurons; }
	public Neuron[][] getHiddenLayer() { return hiddenNeurons; }
	public Neuron[] getHiddenLayer(int layer) { return hiddenNeurons[layer]; }
	public Neuron[] getOutputLayer() { return outputNeurons; }
	
}
