package main.neural;

import java.util.ArrayList;

public class Network {

	Neuron inputLayer[];
	Neuron bias;
	
	Neuron hiddenLayers[][];
	
	Neuron outputLayer[];
	
	Synapse inputSynapses[][];
	Synapse biasSynapses[];
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
		inputSynapses = new Synapse[inputLayer.length][hiddenLayers[0].length];
		for(int in = 0; in < inputLayer.length; in++) {
			for(int hn = 0; hn < hiddenLayers[0].length; hn++) {
				inputSynapses[in][hn] = new Synapse(inputLayer[in], hiddenLayers[0][hn]);
			}
		}
		
		//Bias to first layer of hidden
		biasSynapses = new Synapse[hiddenLayers[0].length];
		for(int hn = 0; hn < hiddenLayers[0].length; hn++) {
			biasSynapses[hn] = new Synapse(bias, hiddenLayers[0][hn]);
		}
		
		//Hidden layers to each other
		hiddenSynapses = new Synapse[hiddenLayers.length-1][hiddenLayers[0].length][hiddenLayers[0].length];
		for(int layer = 0; layer < hiddenLayers.length-1; layer++) {
			for(int start = 0; start < hiddenLayers[layer].length; start++) {
				for(int end = 0; end < hiddenLayers[layer+1].length; end++) {
					hiddenSynapses[layer][start][end] = new Synapse(hiddenLayers[layer][start], hiddenLayers[layer+1][end]);
				}
			}
		}
		
		//Last layer of hidden to output
		outputSynapses = new Synapse[outputLayer.length][hiddenLayers[0].length];
		for(int on = 0; on < outputLayer.length; on++) {	
			for(int hn = 0; hn < hiddenLayers[0].length; hn++) {
				outputSynapses[on][hn] = new Synapse(hiddenLayers[hiddenLayers.length-1][hn], outputLayer[on]);
			}
		}
	}
	
	private void initNeurons(int inNeurons, int hLayers, int hNeurons, int outNeurons) {
		inputLayer = new Neuron[inNeurons];
		for(int in = 0; in < inNeurons; in++) {
			inputLayer[in] = new Neuron(0);
		}
		
		bias = new Neuron(0);
		bias.setValue(1.0);
		
		hiddenLayers = new Neuron[hLayers][hNeurons];
		for(int hL = 0; hL < hLayers; hL++) {
			for(int hN = 0; hN < hNeurons; hN++) {
				hiddenLayers[hL][hN] = new Neuron(hL+1);
			}
		}
		
		outputLayer = new Neuron[outNeurons];
		for(int out = 0; out < outNeurons; out++) {
			outputLayer[out] = new Neuron(1+hLayers);
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
			
			//Adds bias neuron to arraylist
			synapses.add(biasSynapses[inEndS]);
			
			//Calculates value of neuron
			biasSynapses[inEndS].child.setValueFromSynapses(synapses);
			
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
			
			//Calculates the value of the child neuron, then sigmoids it because it is at the end
			outputSynapses[0][outEndS].child.setValueFromSynapses(synapses);
			outputSynapses[0][outEndS].child.sigmoidValue();
			
			//Clears the arraylist to be used in next iteration of loop
			synapses.clear();
		}
	}
	
	public Neuron[] getInputLayer() { return inputLayer; }
	public Neuron[] getHiddenLayer(int layer) { return hiddenLayers[layer]; }
	public Neuron[] getOutputLayer() { return outputLayer; }
	
}
