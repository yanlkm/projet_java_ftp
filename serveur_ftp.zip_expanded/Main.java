
/*
 * TP JAVA RIP
 * Min Serveur FTP
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	static ServerSocket serveur = null;
	static BufferedReader recevoir=null;
	static PrintStream envoi = null;
	//static Socket sock_serveur=null;
	public static void main(String[] args) throws Exception {
		Thread serverThread = new Thread(() -> {
			try {
				int port = 8923;

				try {
					serveur = new ServerSocket(port);
					System.out.println("Le Serveur FTP");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Erreur de création du serveur FTP !");

					return;
				}

				while (true) {
					try {
									
						
								// Attente de connexion d'un client
								Socket sock_serveur = serveur.accept();
								System.out.println("Connexion établie avec le client "
										+ sock_serveur.getInetAddress().getHostAddress());
								Thread clientThread = new Thread(() -> {
								try {
										if(sock_serveur.isClosed())
										{
											CommandExecutor.userOk=false;
											CommandExecutor.pwOk=false;
										}
									
								recevoir = new BufferedReader(new InputStreamReader(sock_serveur.getInputStream()));
								envoi = new PrintStream(sock_serveur.getOutputStream());
								envoi.println("1 Bienvenue ! ");
								String commande = "";
								while (true) {
									if (recevoir.ready()) {
										commande = recevoir.readLine();
										if (commande.equals("bye")) {
											break;
										}
										CommandExecutor.executeCommande(envoi, commande);
									}
								}
								CommandExecutor.userOk=false;
								CommandExecutor.pwOk=false;
								envoi.close();
								recevoir.close();
								sock_serveur.close();
								System.out.println("Client déconnecté !");
								if (sock_serveur.isClosed())
								{
				                    recevoir.close();
				                    envoi.close();
								}
							} catch (IOException ex) {
								ex.printStackTrace();
			                    try {
									recevoir.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			                    envoi.close();
							}
								
						});
						clientThread.start();

					} catch (Exception e) {
	                    recevoir.close();
	                    envoi.close();
						e.printStackTrace();
					}

				}

			} catch (Exception ex) {
                try {
					recevoir.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                envoi.close();
				ex.printStackTrace();
			}
		});
		serverThread.start();

	}
}
