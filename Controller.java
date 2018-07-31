
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Controller extends Thread {
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private static int ediblePos = 0;
	private static int bookedPos = 0;
	private static int freePos = 0;
    private static final Object edibleLock = new Object();
    private static final Object freeLock = new Object();
    private static final Object bookedLock = new Object();

	
	public Controller(Socket s) {
		this.s = s;
	}
	
	
	public void init() {
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
		}catch(IOException e){}
	}
	
	public void receive(){
        try {
            while(!in.ready());
            String s = in.readLine();
            if(s.equals("P?")) {
                synchronized (freeLock) {
                    if (Generator.Buffer[freePos] == "") {
                        out.print("1\n");
                        out.flush();
                        freePos = (freePos + 1) % Data.Size;
                    } else {
                        out.print("0\n");
                        out.flush();
                    }
                }

            }else if(s.equals("C?")) {
                if(Generator.Buffer[ediblePos] == "") {
                    out.print("0\n");
                    out.flush();
                }else{
                    synchronized (edibleLock) {
                        out.print("1" + Data.message + "\n");
                        out.flush();
                        Generator.Buffer[ediblePos] = "";
                        ediblePos = (ediblePos + 1) % Data.Size;
                    }
                }
            }else if(s.equals(Data.message)) {
                if(Generator.Buffer[bookedPos] == "") {
                    synchronized (bookedLock) {
                        Generator.Buffer[bookedPos] = Data.message;
                        bookedPos = (bookedPos + 1) % Data.Size;
                    }
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
	}
	
	public void run(){
		init();
		while (true){
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			receive();
		}
	}
}
