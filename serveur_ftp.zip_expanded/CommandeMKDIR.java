import java.io.File;
import java.io.PrintStream;

public class CommandeMKDIR extends Commande {
        
        public CommandeMKDIR(PrintStream ps, String commandeStr) {
                super(ps, commandeStr);
        }

        public void execute() {
                        
                try {
                        //appel de la fonction rmdir
        
            //on recupere le nom du repertoire
            File dir = new File(commandeArgs[0]);    
            
            File path_dir = new File(CommandExecutor.repertoireCourant+"/"+dir.getName());
        
            
            //on verifie si le repertoire exsit
            if(!path_dir.exists()) {
                    if((!path_dir.isDirectory())) {
                            path_dir.mkdir();
                            ps.println("0 Création de votre repertoire valide ! Le chemin est : "+ CommandExecutor.chemin_absolu +"/" + commandeArgs[0]);
                            
                    } else {
                            ps.println("2 Erreur dans la création du repertoire !");
                    }
            } else {
                    ps.println("2 Un repertoire du même nom existe deja ! ");
            }
                } catch ( Exception e ) {
            e.printStackTrace();
                }
        }

}