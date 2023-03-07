import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {
    
    public CommandeLS(PrintStream ps, String commandeStr) {
            super(ps, commandeStr);
    }

    public void execute() {
            // Obtenez un objet File représentant le répertoire courant
            //File fic = new File(".");
            String path = CommandExecutor.repertoire_absolu +"/"+ CommandExecutor.chemin_absolu;
            ps.println(path);
    
            File directory = new File(path); // specify the directory path
            File[] files = directory.listFiles();
                  
            // Affichez le contenu du répertoire courant
            for (File fichier_Repertoire : files) {
            	if(fichier_Repertoire.isDirectory())
            	{
                    String nomFichier_Repertoire = fichier_Repertoire.getName();
                    ps.println("----Dir : "+nomFichier_Repertoire);
            	}
            	else 
            	{
            		String nomFichier_Repertoire = fichier_Repertoire.getName();
                    ps.println("----file : "+nomFichier_Repertoire);
            	}
                   
    }

    }
}
