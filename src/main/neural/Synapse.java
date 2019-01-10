package main.neural;

import java.io.Serializable;

public class Synapse implements Serializable{
	
	private static final long serialVersionUID = 3177958368398327122L;

	double weight;
	
	Neuron parent;
	Neuron child;
	
	public Synapse(Neuron p, Neuron c) {
		parent = p;
		child = c;
		weight = 1.0;
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
