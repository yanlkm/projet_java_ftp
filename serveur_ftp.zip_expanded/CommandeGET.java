import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.net.Socket;

public class CommandeGET extends Commande {
	
	public CommandeGET(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		
		ps.println("La commande get n'est pas encoré implémentée");
		
		
		System.out.println("DBG 1 GET CLIENT");
	   
		try {
			Socket socket_get = new Socket("localhost", 4041);
			System.out.println("DBG 2 GET CLIENT");
			BufferedOutputStream dataOut = new BufferedOutputStream(socket_get.getOutputStream());
		
		    File file = new File(CommandExecutor.repertoireCourant +"/"+ commandeArgs[0]);
		    FileInputStream fis = new FileInputStream(file);
	    
		    byte[] buffer = new byte[1024];
		    int bytesRead = 0;
		    while ((bytesRead = fis.read(buffer)) != -1) {
		    	
		        dataOut.write(buffer, 0, bytesRead);
		        dataOut.flush();
		        System.out.println("DBG 3 GET CLIENT");
		    }
		    System.out.println("DBG  GET CLIENT");
		    
		    fis.close();
		    dataOut.close();
		    socket_get.close();
		    //sock.close();
			}catch(Exception e) {e.printStackTrace();}
		
	}

}
