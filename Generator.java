import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class Generator extends Thread {
		
	public static String [] Buffer = new String [Data.Size];
	private int regiteredAgents = Data.P + Data.K;
	private ServerSocket service;
	private Socket sock;
	private int port = 6000;
	public ArrayList<Controller> controllers = new ArrayList<Controller>();

	public void init(){
		try {
			service = new ServerSocket(port);
            for(int i = 0; i< Data.Size; i++){
                Buffer[i]="";
            }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void display() {
        System.out.print("\033[H\033[2J");
        String s = "";
        for (int i = 0; i<Data.Size; i++) {
            if (Buffer[i] == "")
                s += "F|";
            else
                s += "M|";
        }
        System.out.println(s);
    }



	public void run(){
		init();

		while(regiteredAgents > 0) {
			try {
				regiteredAgents--;
				sock = service.accept();
				Controller c = new Controller(sock);
				controllers.add(c);
				c.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		while(true) {
			while(true) {
				try {
					Thread.sleep(1000);
					display();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main (String args[]) {
		Generator c = new Generator();
		c.start();
	}
		
}
