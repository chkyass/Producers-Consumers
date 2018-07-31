import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;


public class Consumer extends Thread {
	private InetAddress host;
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private int port = 6000;
	private ArrayList<String> messages = new ArrayList<> ();
	
	public void init(){
		try {
			host = InetAddress.getLocalHost();
			s = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(),true);
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void consume(){
		try {
			while(!in.ready());
			String m;
			if((m = in.readLine()).charAt(0) == '1'){
				messages.add(m.substring(1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void askForConsume(String m){
		try {
			sleep(new Random().nextInt(1000));
			out.print(m);
			out.flush();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		init();
		while(true){
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			askForConsume("C?\n");
			consume();
		}
	}
}
