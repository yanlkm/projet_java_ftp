package client_ftp;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) throws Exception {
        System.out.println("Client FTP");
        Thread socketThread = new Thread( () -> {
        // Connexion au serveur FTP
        
        	try {
        Socket socket = new Socket("localhost", 8923);
        
        // Flux de communication avec le serveur FTP
        BufferedReader recevoir = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream envoi = new PrintStream(socket.getOutputStream());
        
        // Commande de connexion initiale
        
        String reponse = recevoir.readLine();
        if (reponse.startsWith("1 Bienvenue ! ")) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Erreur de connexion : " + reponse);
          
        }
 
        String commande = "";
        Scanner scanner = new Scanner(System.in);
		while (!commande.equals("bye"))
		{	
			System.out.println("saisir votre scanner");
			commande = scanner.nextLine();
			String ligne = commande;
			
			if (commande.startsWith("stor")) {
			Thread storThread= new Thread( () -> {      
			try {
			       
			envoi.println(ligne);	
			ServerSocket sock = new ServerSocket(4000);
			Socket socket_stor = sock.accept();
			
			System.out.println("DBG 1 STOR CLIENT");
		   
			String[] arguments = ligne.split(" ");
		  
		    BufferedOutputStream dataOut = new BufferedOutputStream(socket_stor.getOutputStream());
			
		    File file = new File(arguments[1]);
		    FileInputStream fis = new FileInputStream(file);
	    
		    byte[] buffer = new byte[1024];
		    int bytesRead = 0;
		    while ((bytesRead = fis.read(buffer)) != -1) {
		    	System.out.println("DBG 2 STOR CLIENT");
		        dataOut.write(buffer, 0, bytesRead);
		        dataOut.flush();
		        System.out.println("DBG 3 STOR CLIENT");
		    }
		    System.out.println("DBG  STOR CLIENT");
		    
		    fis.close();
		    dataOut.close();
		    socket_stor.close();
		    sock.close();
			}catch(IOException ex) 
			{ex.printStackTrace();} 

			        });
			        storThread.start();
			        reponse = recevoir.readLine();

		    }  
				 else if (commande.startsWith("get")) {
				envoi.println(ligne);
				Thread getThread= new Thread( () -> {
				try {
				       
					
				ServerSocket sock = new ServerSocket(4041);
				Socket socket_get = sock.accept();
				
				//System.out.println("DBG 1 GET CLIENT");
				
				String[] arguments = ligne.split(" ");
				System.out.println(arguments[1]);
				File file = new File(System.getProperty("user.dir")+"/"+arguments[1]);
				 BufferedReader reader = new BufferedReader(new InputStreamReader(socket_get.getInputStream()));
				 FileOutputStream fos = new FileOutputStream(file); 
		           
		            StringBuilder contentBuilder = new StringBuilder();
		            String line;
		            while ((line = reader.readLine()) != null) {
		                    contentBuilder.append(line).append("\n");
		                    System.out.println(line); // Ajouter cette ligne pour afficher chaque ligne reÃ§ue depuis le client
		            }
		            reader.close();
		            String fileContent = contentBuilder.toString();
		            System.out.println(fileContent);

		            // écrire le contenu du fichier dans le fichier local sur le serveur
		            fos.write(fileContent.getBytes());
		            
		            // Fermer le flux de sortie
		            fos.close();
		            //socket.close();
		            socket_get.close();
		            sock.close();
		            // Afficher un message de confirmation
		            System.out.println("File saved as: " + file.getAbsolutePath());
			    
			   
				}catch(IOException ex) 
				{ex.printStackTrace();}});
		        getThread.start();
		        reponse = recevoir.readLine();

				
			}
			else {envoi.println(ligne);
			reponse = recevoir.readLine();
}
			if(reponse!=null && !reponse.equals(""))
			{System.out.println(reponse);
			reponse = recevoir.readLine();}
		}
        	
        // Fermeture de la connexion avec le serveur FTP
		
		scanner.close();
        recevoir.close();
        envoi.close();
       socket.close();

            
            
}catch(IOException ex) 
{ex.printStackTrace();} 

        });
        socketThread.start();
        //socketThread.join();
        
        }

        }
/**/
//reponse = recevoir.readLine();



/*if (commande.startsWith("stor")) {
	System.out.println("DBG 1 STOR CLIENT");
   
    String[] arguments = commande.split(" ");
    String filePath = arguments[1];
    BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream());

    FileInputStream fis = new FileInputStream(filePath);
    byte[] buffer = new byte[10240];
    int bytesRead = 0;
    while ((bytesRead = fis.read(buffer)) != -1) {
    	System.out.println("DBG 2 STOR CLIENT");
        dataOut.write(buffer, 0, bytesRead);
        dataOut.flush();
        System.out.println("DBG 3 STOR CLIENT");
    }
    System.out.println("DBG  STOR CLIENT");
    fis.close();
   
    dataOut.close();
    
}*/

/*package client_ftp;
import java.io.*;
import java.net.*;
import java.util.*; 

public class Main {
	public static void main(String[] args) throws Exception {
        System.out.println("Client FTP");
        
        
        //try {
       // Connexion au serveur FTP
        Socket socket = new Socket("localhost", 8923);
        
        // Flux de communication avec le serveur FTP
        BufferedReader recevoir = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream envoi = new PrintStream(socket.getOutputStream());
        
        // Commande de connexion initiale
       
        
        String reponse = recevoir.readLine();
        if (reponse.startsWith("1 Bienvenue ! ")) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Erreur de connexion : " + reponse);
           
        }


        // Création d'un thread pour la réception des réponses du serveur
       // Thread receptionThread = new Thread(() -> {
          //  try {
            	
               /* while ( recevoir.toString()!=null && !(recevoir.ready())) {
                    System.out.println(recevoir.readLine());
            	
                    //}
      
          //  } catch (IOException e) {
            //    e.printStackTrace();
          //  }
       // });
        //receptionThread.start();
       
        
        Scanner scanner = new Scanner(System.in);
        String commande = "";
        //while (!commande.equals("bye")) {
        	
            commande = scanner.nextLine();
            
            /*if (commande.startsWith("stor")) {
            	System.out.println("DBG 1 STOR CLIENT");
               
                String[] arguments = commande.split(" ");
                String filePath = arguments[1];
                BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream());

                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[10240];
                int bytesRead = 0;
                while ((bytesRead = fis.read(buffer)) != -1) {
                	System.out.println("DBG 2 STOR CLIENT");
                    dataOut.write(buffer, 0, bytesRead);
                    dataOut.flush();
                    System.out.println("DBG 3 STOR CLIENT");
                }
                System.out.println("DBG  STOR CLIENT");
                fis.close();
               
                dataOut.close();
                
            } 
            else {
               // envoi.println(commande);
            //}
            
       // }
        
        reponse = recevoir.readLine();
    	while(reponse != null) {
            System.out.println(reponse);
            reponse = recevoir.readLine();
            
        }
        scanner.close();
        envoi.close();
        recevoir.close();
        socket.close();
        }
        
       // catch(Exception e) {
        	  //System.out.println("Connexion interrompue !");
        	   
        //}
        
        


        
        // Fermeture de la connexion avec le serveur FTP
     
    
   // }
}*/
