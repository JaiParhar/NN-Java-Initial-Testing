package main;

import java.io.IOException;

import main.neural.Network;

public class Main {

	public static void main(String[] args) {
		
		LanguageDetectionAI ai = new LanguageDetectionAI();
		
		/*int i = 0;
		while(true) {
			i++;
			System.out.println(ai.trainingRun("a", 0) + " a");
			System.out.println(ai.trainingRun("b", 1) + " b");
			System.out.println(ai.trainingRun("c", 2) + " c");
			if(i > 1000) {
				try {
					System.out.println("Done");
					System.out.println(ai.realRun("b"));
					Network.saveToFile(ai.getNetwork(), "C:/Users/Kush/Desktop/Saved Neural Nets/lang.nn");
					break;
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		}*/
		
		try {
			ai.setNetwork(Network.loadFromFile("C:/Users/Kush/Desktop/Saved Neural Nets/lang.nn"));
			System.out.println(ai.realRun("a"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
