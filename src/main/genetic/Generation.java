package main.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

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
	
	public void testIndividuals(Map<String, Integer> tests) {
		tests.forEach((question, answer) -> {
			for(int i = 0; i < generation.size(); i++) {
				generation.get(i).recordTestRun(generation.get(i).inputString(question, answer));
			}
		});
		
		Collections.sort(generation, new Comparator<Individual>() {
			@Override
			public int compare(Individual c1, Individual c2) {
				if (c1.getFitness() > c2.getFitness()) return -1;
				if (c1.getFitness() < c2.getFitness()) return 1;
				return 0;
		}});
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
