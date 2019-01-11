package main;

import java.util.HashMap;
import java.util.Map;

import main.genetic.Generation;

public class Main {

	public static void main(String[] args) {
		
		Map<String, Integer> questions = new HashMap<String, Integer>();
		questions.put("a", 0);
		questions.put("b", 1);
		questions.put("c", 2);
		questions.put("d", 3);
		
		

		Generation g1 = new Generation(1000, 12012938);
		g1.testIndividuals(questions);
		for(int i = 0; i < 1000;i++) {
			System.out.println(g1.getIndividuals().get(i).getFitness());
		}
	}

}
