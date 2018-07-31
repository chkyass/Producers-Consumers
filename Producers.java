import java.util.ArrayList;


public class Producers {
	public static ArrayList<Producer> producers = new ArrayList<Producer>();
	
	public static void main(String args[]){
		for (int i = 0; i< Data.P; i++){
			Producer p = new Producer();
			p.start();
			Producers.producers.add(p);
		}
	}
}
