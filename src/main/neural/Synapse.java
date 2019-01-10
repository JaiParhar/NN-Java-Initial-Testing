package main.neural;

import java.io.Serializable;
import java.util.Random;

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
	
	public void randomizeWeight(Random r, double range) {
		if(r.nextBoolean()) { setWeight(r.nextDouble() * range); }
		else { setWeight(r.nextDouble() * range * -1.0); }
	}
	
	public void setWeight(double w) {
		weight = w;
	}
}
