package main.neural;

import java.util.ArrayList;

public class Neuron {

	double value;
	int layer;
	
	public Neuron(int l) {
		layer = l;
		value = 0;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double v) {
		value = v;
	}
	
	public void setValue(ArrayList<Double> v) {
		value = 0;
		for(int i = 0; i < v.size(); i++) {
			value += v.get(i);
		}
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setValueFromSynapses(Synapse s[]) {
		//TODO: USE SIGMOID HERE TO ENSURE VALUE LESS THAN 1
		value = 0;
		for(int i = 0; i < s.length; i++) {
			value += s[i].getCalculatedValue();
		}
	}
	
}
