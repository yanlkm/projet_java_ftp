import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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

public class IHM extends Application {

	private Socket socket;
	private BufferedReader recevoir;
	private PrintStream envoi;

	@Override
	public void start(Stage primaryStage) {
		// Créer une zone de texte pour afficher les réponses du serveur
		TextArea responseArea = new TextArea();
		responseArea.setEditable(false);
		primaryStage.setTitle("Client FTP");

		BorderPane layout = new BorderPane();

		// Créer un bouton pour se connecter au serveur
		Button connectButton = new Button("Se connecter");
		connectButton.setOnAction(e -> {

			try {
				socket = new Socket("localhost", 8923);
				recevoir = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				envoi = new PrintStream(socket.getOutputStream());
				String reponse = recevoir.readLine();
				if (reponse.startsWith("1 Bienvenue ! ")) {
					responseArea.appendText("\nConnexion réussie !\n");
				} else {
					responseArea.appendText("\nErreur de connexion : " + reponse + "\n");
				}
			} catch (IOException ex) {
				responseArea.appendText("\nErreur de connexion : " + ex.getMessage() + "\n");
			}
		});

		// Créer une boîte horizontale pour les champs de texte et le bouton de
		// connexion
		HBox inputBox = new HBox(connectButton);
		inputBox.setAlignment(Pos.CENTER);
		inputBox.setSpacing(10);
		inputBox.setPadding(new Insets(10));

		// Créer une étiquette pour la zone de texte
		Label responseLabel = new Label("Réponse du serveur :");

		// Ajouter la zone de texte et l'étiquette à un conteneur
		BorderPane responsePane = new BorderPane();
		responsePane.setTop(responseLabel);
		responsePane.setCenter(responseArea);

		// Ajouter les éléments à la mise en page
		layout.setTop(inputBox);
		layout.setCenter(responsePane);

		// Créer un champ de texte pour les commandes FTP
		TextField commandField = new TextField();
		commandField.setPromptText("Commande FTP");

		// Créer un bouton pour envoyer la commande FTP
		Button sendButton = new Button("Envoyer");
		sendButton.setOnAction(e -> {
			String commande = commandField.getText();
			String ligne = commande;
			if (socket != null && !socket.isClosed()) {
				try {
					if (commande.startsWith("stor")) {
						if ((commande.split(" ").length < 2) || ((commande.split(" ").length > 2))) {
							responseArea.appendText("\nVeuillez entrer le bon nombre d'argument pour la commande ");
							return;
							

						}
						Thread storThread = new Thread(() -> {
							try {
								envoi.println(ligne);
								ServerSocket sock = new ServerSocket(4000);
								Socket socket_stor = sock.accept();

								responseArea.appendText("\nDBG 1 STOR CLIENT");

								String[] arguments = ligne.split(" ");

								BufferedOutputStream dataOut = new BufferedOutputStream(socket_stor.getOutputStream());

								File file = new File(arguments[1]);
								responseArea.appendText(file.getAbsolutePath());
								FileInputStream fis = new FileInputStream(file);

								byte[] buffer = new byte[1024];
								int bytesRead = 0;
								while ((bytesRead = fis.read(buffer)) != -1) {
									responseArea.appendText("\nDBG 2 STOR CLIENT");
									dataOut.write(buffer, 0, bytesRead);
									dataOut.flush();
									responseArea.appendText("\nDBG 3 STOR CLIENT");
								}
								responseArea.appendText("\nDBG  STOR CLIENT");

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
						if ((commande.split(" ").length < 2) || ((commande.split(" ").length > 2))) {
							responseArea.appendText("\nVeuillez entrer le bon nombre d'argument pour la commande ");

							return;

						}

						Thread getThread = new Thread(() -> {

							try {
								envoi.println(ligne);
								ServerSocket sock = new ServerSocket(4041);
								Socket socket_get = sock.accept();

								String[] arguments = ligne.split(" ");
								responseArea.appendText(arguments[1]);
								File file = new File(System.getProperty("user.dir") + "/" + arguments[1]);
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(socket_get.getInputStream()));
								FileOutputStream fos = new FileOutputStream(file);

								StringBuilder contentBuilder = new StringBuilder();
								String line;
								while ((line = reader.readLine()) != null) {
									contentBuilder.append(line).append("\n");
									responseArea.appendText(line); // Ajouter cette ligne pour afficher chaque ligne
																	// reÃ§ue
																	// depuis le client
								}
								reader.close();
								String fileContent = contentBuilder.toString();

								fos.write(fileContent.getBytes());

								fos.close();
								socket_get.close();
								sock.close();
								responseArea.appendText("\nLe fichier a été sauvegardé : " + file.getAbsolutePath());
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						});
						getThread.start();
					} else if (commande.equals("")) {
						responseArea.appendText("\nVeuillez entrer UNE commande spécifique");
						return;
						 
					} else if (!(commande.startsWith("rmdir") || commande.startsWith("user")
							|| commande.startsWith("pass") || commande.startsWith("ls") || commande.startsWith("mkdir")
							|| commande.startsWith("rmdir") || commande.startsWith("adduser")
							|| commande.startsWith("pwd") || commande.startsWith("cd"))) {
						responseArea.appendText("\nVeuillez entrer UNE commande spécifique");
						return;
						 
					} else if (((commande.startsWith("user") || commande.startsWith("pass")
							|| commande.startsWith("mkdir") || commande.startsWith("rmdir"))
							&& (commande.split(" ").length < 2))
							|| ((commande.startsWith("user") || commande.startsWith("pass")
									|| commande.startsWith("mkdir") || commande.startsWith("rmdir"))
									&& (commande.split(" ").length > 2))) {

						responseArea.appendText("\nVeuillez entrer le bon nombre d'argument pour la commande ");
						return;
						
					} else if (((commande.startsWith("adduser")) && (commande.split(" ").length < 3))
							|| ((commande.startsWith("adduser")) && (commande.split(" ").length > 3))) {
						responseArea.appendText("\nVeuillez entrer le bon nombre d'argument pour la commande ");
						return;
					} else {
						envoi.println(ligne);
						String reponse = recevoir.readLine();
						responseArea.appendText(reponse);
						// Faire quelque chose avec chaque ligne lue

					}
					//envoi.println(commande); // Envoi de la commande FTP au serveur
					// String reponse = recevoir.readLine(); // Récupération de la réponse du
					// serveur
					// responseArea.appendText(reponse + "\n");
				} catch (IOException ex) {
					responseArea.appendText("\nErreur lors de l'envoi de la commande: " + ex.getMessage() + "\n");
				}
			} else {
				responseArea.appendText("\nLa connexion n'est pas établie\n");
			}

			// Si la commande est "bye", on ferme la connexion et quitte l'application
			if (commande.equals("bye")) {
				try {
					envoi.println(commande);
					String reponse = recevoir.readLine();
					responseArea.appendText(reponse + "\n");
					socket.close();
				} catch (IOException ex) {
					responseArea.appendText("\nErreur lors de la fermeture de la connexion : " + ex.getMessage() + "\n");
				}
				primaryStage.close();
			}
		});
		// Créer une boîte horizontale pour le champ de texte et le bouton d'envoi
		HBox commandBox = new HBox(commandField, sendButton);
		commandBox.setAlignment(Pos.CENTER);
		commandBox.setSpacing(10);
		commandBox.setPadding(new Insets(10));

		// Ajouter la boîte de commande à la mise en page
		layout.setBottom(commandBox);

		// Créer la scène et l'afficher
		Scene scene = new Scene(layout, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
