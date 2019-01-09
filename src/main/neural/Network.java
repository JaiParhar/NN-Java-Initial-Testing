package main.neural;

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
	
	public Neuron[] getInputLayer() { return inputLayer; }
	public Neuron[] getHiddenLayer(int layer) { return hiddenLayers[layer]; }
	public Neuron[] getOutputLayer() { return outputLayer; }
	
}
