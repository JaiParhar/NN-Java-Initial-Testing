package main;

import java.util.HashMap;
import java.util.Map;

import main.genetic.Generation;

public class Main {

	public static void main(String[] args) {
		
		Map<String, Integer> questions = new HashMap<String, Integer>();
		questions.put("a", 0);
		questions.put("b", 1);
		questions.put("c", 0);
		questions.put("d", 1);
		questions.put("e", 0);
		questions.put("f", 1);
		questions.put("g", 0);
		questions.put("h", 1);
		questions.put("i", 0);
		questions.put("j", 1);
		questions.put("k", 0);
		questions.put("l", 1);
		
		for(int i = 0; i < 1000;i++) {
			Generation g1 = new Generation(100, 1231231231);
			g1.testIndividuals(questions);
			System.out.println(g1.getIndividuals().get(99).correctAnswer);
		}
	}

}
