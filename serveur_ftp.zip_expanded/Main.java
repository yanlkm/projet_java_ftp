/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	static Socket sock_serveur = null;
	public static void main(String[] args) throws Exception {
		System.out.println("Le Serveur FTP");
		int port = 8923;
        ServerSocket serveur = null;

       
            serveur = new ServerSocket(port);
           
                sock_serveur= serveur.accept();
              
		    	BufferedReader recevoir = new BufferedReader(new InputStreamReader(sock_serveur.getInputStream()));
				PrintStream envoi  = new PrintStream(sock_serveur.getOutputStream());
		       
				envoi.println("1 Bienvenue ! ");
				
				String commande = "";
				
				while (true) {
                    if (recevoir.ready()) {
                            if ((commande=recevoir.readLine()).equals("bye")) {
                                    break;}
                            
                            CommandExecutor.executeCommande(envoi, commande);
					}
				}
				   
				    envoi.close();
				    recevoir.close();
				    sock_serveur.close();
				    serveur.close();
			        System.out.println("Client déconnecté !");
	
	}
}
//try {
// Création du serveur FTP
//System.out.println("Serveur FTP en attente de connexion sur le port " + port);
// } catch (Exception e) {
     //System.out.println("Erreur de création du serveur FTP !");
    // return; // Quitte le programme
// }
// System.out.println("Connexion établie avec le client " + client.getInetAddress().getHostAddress());


// Code de traitement des commandes du client connecté
// Code d'attente de connexion d'un client
 
 //while (true) {
 
     //try {
         // Attente de connexion d'un client
				   
		 //}
            //catch (Exception e) {
             //System.out.println("Erreur lors de la connexion avec le client !");
             
             
             // Fermeture de la connexion avec le client
             
             
             //if (client != null) {
                 //try {
                    //client.close();
                 //} catch (Exception ex) {
                   //  ex.printStackTrace();
                // }
             //}
		 //}
        //}

	
		
		
		

	
	   

		
		
	
	


