import java.util.ArrayList;


public class Consumers {
	public static ArrayList<Consumer> consumers = new ArrayList<Consumer>();
	
	public static void main(String args[]){
		for (int i = 0; i< Data.K; i++){
			Consumer c = new Consumer();
			c.start();
			Consumers.consumers.add(c);
		}
	}
}
