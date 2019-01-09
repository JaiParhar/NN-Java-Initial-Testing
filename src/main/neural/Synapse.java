package main.neural;

public class Synapse {
	double weight;
	
	Neuron parent;
	Neuron child;
	
	public Synapse(Neuron p, Neuron c) {
		parent = p;
		child = c;
		weight = 1;
	}
	
	public double getCalculatedValue() {
		return (parent.getValue() * weight);
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double w) {
		weight = w;
	}
}
