package main;

import java.io.IOException;

import main.neural.Network;

public class Main {

	public static void main(String[] args) {
		
		LanguageDetectionAI ai = new LanguageDetectionAI();
		
		String englishDataset = "";
		String mandarinDataset = "";
		
		try {
			englishDataset = Utils.readFile("./res/English.dataset");
			mandarinDataset = Utils.readFile("./res/Mandarin.dataset");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int start = 0;
		int end = 1000;
		for(int i = start; i < start+end; i++) {
			System.out.println(Utils.getLine(englishDataset, i) + " has cost of " + ai.trainingRun(Utils.getLine(englishDataset, i), 0));
			System.out.println(Utils.getLine(mandarinDataset, i) + " has cost of " + ai.trainingRun(Utils.getLine(mandarinDataset, i), 1));
		}
		
		try {
			Network.saveToFile(ai.getNetwork(), "C:/Users/Kush/Desktop/Saved Neural Nets/EnglishMandarinNetwork.nn");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
