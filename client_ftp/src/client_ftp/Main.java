//package client_ftp;

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
	static Socket socket = null;
	static BufferedReader recevoir = null;
	static PrintStream envoi = null;
	/*
	 * static BufferedReader reader = null; static FileInputStream fis = null;
	 * static BufferedOutputStream dataOut = null; static FileOutputStream fos =
	 * null;
	 */

	public static void main(String[] args) throws Exception {

		System.out.println("Client FTP");
		Thread socketThread = new Thread(() -> {
			// Connexion au serveur FTP

			try {
				socket = new Socket("localhost", 8923);

				// Flux de communication avec le serveur FTP
				recevoir = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				envoi = new PrintStream(socket.getOutputStream());

				// Commande de connexion initiale

				String reponse = recevoir.readLine();
				if (reponse.startsWith("1 Bienvenue ! ")) {
					System.out.println("Connexion réussie !");
				} else {
					System.out.println("Erreur de connexion : " + reponse);

				}

				String commande = "";
				Scanner scanner = new Scanner(System.in);

				while (!(commande = scanner.nextLine()).equals("bye")) {

					String ligne = commande;
					
					

					if (commande.startsWith("stor")) {
						if ((commande.split(" ").length < 2) || ((commande.split(" ").length > 2)))
						{
							System.out.println("Veuillez entrer le bon nombre d'argument pour la commande ");
							
							continue;
							
						}
						Thread storThread = new Thread(() -> {
							try {
								envoi.println(ligne);
								ServerSocket sock = new ServerSocket(4000);
								Socket socket_stor = sock.accept();

								System.out.println("DBG 1 STOR CLIENT");

								String[] arguments = ligne.split(" ");

								BufferedOutputStream dataOut = new BufferedOutputStream(socket_stor.getOutputStream());
								
								File file = new File(arguments[1]);
								System.out.println(file.getAbsolutePath());
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
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						});
						storThread.start();
					} else if (commande.startsWith("get")) {
						if ((commande.split(" ").length < 2) || ((commande.split(" ").length > 2)))
						{
							System.out.println("Veuillez entrer le bon nombre d'argument pour la commande ");
							
							continue;
							
						}
						
						Thread getThread = new Thread(() -> {
							
							try {
								envoi.println(ligne);
								ServerSocket sock = new ServerSocket(4041);
								Socket socket_get = sock.accept();

								String[] arguments = ligne.split(" ");
								System.out.println(arguments[1]);
								File file = new File(System.getProperty("user.dir") + "/" + arguments[1]);
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(socket_get.getInputStream()));
								FileOutputStream fos = new FileOutputStream(file);

								StringBuilder contentBuilder = new StringBuilder();
								String line;
								while ((line = reader.readLine()) != null) {
									contentBuilder.append(line).append("\n");
									System.out.println(line); // Ajouter cette ligne pour afficher chaque ligne reÃ§ue
																// depuis le client
								}
								reader.close();
								String fileContent = contentBuilder.toString();
								

								fos.write(fileContent.getBytes());

								fos.close();
								socket_get.close();
								sock.close();
								System.out.println("Le fichier a été sauvegardé : " + file.getAbsolutePath());
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						});
						getThread.start();
					}  
					else if (commande.equals("")) {
						System.out.println("Veuillez entrer UNE commande spécifique");
						
						continue;
					}
					else if (!(commande.startsWith("rmdir")||commande.startsWith("user") ||commande.startsWith("pass")||
							commande.startsWith("ls")||commande.startsWith("mkdir")||commande.startsWith("rmdir")||commande.startsWith("adduser")||
							commande.startsWith("pwd")|| commande.startsWith("cd")))
					{
						System.out.println("Veuillez entrer UNE commande spécifique");	
						
						continue;
					}
					else if (((commande.startsWith("user") ||commande.startsWith("pass")||commande.startsWith("mkdir")||
							commande.startsWith("rmdir")) && (commande.split(" ").length < 2) ) || ((commande.startsWith("user") ||commande.startsWith("pass")||commande.startsWith("mkdir")||
									commande.startsWith("rmdir")) && (commande.split(" ").length > 2) ) ) {
						
						System.out.println("Veuillez entrer le bon nombre d'argument pour la commande ");
						
						continue;
					}
					else if(((commande.startsWith("adduser")) && (commande.split(" ").length < 3))||((commande.startsWith("adduser")) && (commande.split(" ").length > 3)) )
					{
						System.out.println("Veuillez entrer le bon nombre d'argument pour la commande ");
						continue;
					}
					else {
						envoi.println(ligne);
						reponse = recevoir.readLine();
						System.out.println(reponse);
						// Faire quelque chose avec chaque ligne lue

					}
				}
				envoi.println(commande);

				// Fermeture de la connexion avec le serveur FTP

				scanner.close();
				recevoir.close();
				envoi.close();
				socket.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (socket != null && !socket.isClosed()) {
					try {

						envoi.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		socketThread.start();
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Arrêt du client FTP.");
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
					recevoir.close();
					envoi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}));

	}
}
