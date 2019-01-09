package main.neural;

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
	
	public int getLayer() {
		return layer;
	}
	
	public void setValueFromSynapses(Synapse s[]) {
		value = 0;
		for(int i = 0; i < s.length; i++) {
			value += s[i].getCalculatedValue();
		}
		
		sigmoidValue();
	}
	
	public void addToValue(double i) {
		value += i;
	}
	
	public void sigmoidValue() {
		value = sigmoid(value);
	}
	
	private double sigmoid(double x)
	{
	    return 1 / (1 + Math.exp(-x));
	}
	
}
