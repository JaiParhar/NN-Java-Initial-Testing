package main;

import java.io.IOException;

import main.neural.Network;

public class Main {

	public static void main(String[] args) {
		
		LanguageDetectionAI ai = new LanguageDetectionAI();
		
		while(true) { 
			double sum = 0.0;
			sum += ai.trainingRun("a", 0);
			//System.out.println("a is done");
			sum += ai.trainingRun("b", 1);
			//System.out.println("b is done");
			sum += ai.trainingRun("c", 2);
			//System.out.println("c is done");
			sum += ai.trainingRun("d", 3);
			//System.out.println("d is done");
			sum += ai.trainingRun("e", 4);
			//System.out.println("e is done");
			sum += ai.trainingRun("f", 5);
			//System.out.println("f is done");
			sum += ai.trainingRun("g", 6);
			//System.out.println("g is done");
			sum += ai.trainingRun("h", 7);
			//System.out.println("h is done");
			sum += ai.trainingRun("i", 8);
			//System.out.println("i is done");
			sum += ai.trainingRun("j", 9);
			//System.out.println("j is done");
			sum /= 10.0;
			System.out.println(sum);
			if(sum < 0.5) {
				try {
					Network.saveToFile(ai.net, "C:/Users/Kush/Desktop/Saved Neural Nets/lang.nn");
					break;
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		}
		
	}

}
