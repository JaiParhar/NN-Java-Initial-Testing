package main.neural;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Neuron implements Serializable{

	private static final long serialVersionUID = -8187992277576278733L;
	
	double value;
	double bias;
	int layer;
	
	public Neuron(int l) {
		layer = l;
		value = 0;
		bias = 0;
	}
	
	public void randomizeBias(Random r, double range) {
		if(layer != 0) {
			if(r.nextBoolean()) { setBias(r.nextDouble() * range); }
			else { setBias(r.nextDouble() * range * -1.0); }
		}
	}
	
	public void setBias(double b) {
		if(layer != 0) {
			bias = b;
		}
	}
	
	public double getBias() {
		return bias;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double v) {
		value = v;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setValueFromSynapses(ArrayList<Synapse> s) {
		value = bias;
		for(int i = 0; i < s.size(); i++) {
			value += s.get(i).getCalculatedValue();
		}
		sigmoidValue();
	}
	
	public void setValueFromSynapses(Synapse s[]) {
		value = bias;
		for(int i = 0; i < s.length; i++) {
			value += s[i].getCalculatedValue();
		}
		sigmoidValue();
	}
	
	public void sigmoidValue() {
		value = sigmoid(value);
	}
	
	private double sigmoid(double x)
	{
	    return 1 / (1 + Math.exp(-x));
	}
	
}
