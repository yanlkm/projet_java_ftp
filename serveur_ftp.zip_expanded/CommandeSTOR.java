import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class CommandeSTOR extends Commande {

	public CommandeSTOR( PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute()  {
		
		try {
            // Ouvrir un flux de sortie sur le fichier
            System.out.println("Exécution de la commande STOR");
            
            File file = new File(commandeArgs[0]);
            
            System.out.println("Receiving file: " + file.getName());
            
            FileOutputStream fos = new FileOutputStream(file);

            // Lire le contenu du fichier envoyÃ© par le client

            Socket socket = new Socket("localhost", 4000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            //BufferedReader reader = new BufferedReader(new InputStreamReader(Main.sock_serveur.getInputStream()));
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                    System.out.println(line); // Ajouter cette ligne pour afficher chaque ligne reÃ§ue depuis le client
            }
            reader.close();
            String fileContent = contentBuilder.toString();
            System.out.println(fileContent);

            // Ã‰crire le contenu du fichier dans le fichier local sur le serveur
            fos.write(fileContent.getBytes());
            
            // Fermer le flux de sortie
            fos.close();
            socket.close();
            // Afficher un message de confirmation
            System.out.println("File saved as: " + file.getAbsolutePath());

    } catch (IOException e) {
            e.printStackTrace();
    }
	}
}




