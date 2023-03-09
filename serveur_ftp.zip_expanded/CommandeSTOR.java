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









		/*if (commandeArgs.length == 1) {
	        String filePath = commandeArgs[0];
	        File file = new File(filePath);

	        try {
	        	
	        	
	            FileOutputStream fos = new FileOutputStream(file);
	            
	            InputStream is = Main.client.getInputStream(); 
	            BufferedInputStream bis = new BufferedInputStream(is);

	            byte[] buffer = new byte[1024];
	            int bytesRead = 0;
	            while ((bytesRead = bis.read(buffer)) != -1) {
	                fos.write(buffer, 0, bytesRead);
	            }
	            
	            System.out.println("Données reçues : " + buffer.toString());
	            
	            // Fermeture de la socket et des flux
	            bis.close();
	            fos.close();
	         
	            
	        
	        
	            ps.println("2 Transfert du fichier " + filePath + " réussi.");
	        } catch (IOException e) {
	            ps.println("2 Erreur lors du transfert du fichier " + filePath + " : " + e.getMessage());
	        }
	    } 
		else { ps.println("argument manquant");
            
        }*/


