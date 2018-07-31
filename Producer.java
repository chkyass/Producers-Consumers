import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;


public class Producer extends Thread {
    private InetAddress host;
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private int port = 6000;
	
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
	
	public void produce(String m){
		try {
			while(!in.ready()){}
			if(in.readLine().equals("1")){
				out.print(m+"\n");
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void askForProduction(String m){
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
			askForProduction("P?\n");
			produce(Data.message);
		}
	}
	
	
}
