package main.genetic;

import java.util.ArrayList;

public class Generation {
	
	ArrayList<Individual> generation;
	
	public Generation(int individualsPerGeneration, int startingSeed) {
		generation = new ArrayList<Individual>();
		for(int i = 0; i < individualsPerGeneration; i++) {
			generation.add(new Individual());
			generation.get(generation.size()-1).generateRandom(startingSeed);
			startingSeed++;
		}
	}
	
	public void testIndividuals() {
		
	}
	
	public static Generation mutateGeneration(Generation inGen, int mutations, int startingSeed) {
		for(int i = 0; i < inGen.getIndividuals().size(); i++) {
			inGen.getIndividuals().set(i, Individual.getMutatedIndividual(inGen.getIndividuals().get(i), mutations, startingSeed));
			startingSeed++;
		}
		return inGen;
	}
	
	public ArrayList<Individual> getIndividuals() {
		return generation;
	}
	
}
