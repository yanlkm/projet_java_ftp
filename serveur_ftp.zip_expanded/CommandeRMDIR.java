import java.io.File;
import java.io.PrintStream;

public class CommandeRMDIR extends Commande {
        
        public CommandeRMDIR(PrintStream ps, String commandeStr) {
                super(ps, commandeStr);
        }

        public void execute() {
                        
                try {
                        //appel de la fonction rmdir
            System.out.println("Exécution de la commande RMDIR");  
            //on recupere le nom du repertoire
            File dir = new File(commandeArgs[0]);    
            
            File path_dir = new File(CommandExecutor.repertoireCourant+"/"+dir.getName());
            
            //on verifie si le repertoire exsit
            if(path_dir.exists()) {
                    if((path_dir.isDirectory())) {
                            
                            if(path_dir.list().length == 0) {  
                                    //si le rep existe, que c'est un repertoire, et que il n'a pas de contenu on supprime
                                    path_dir.delete();
                                    ps.println("0 chemin repertoire supprimé : " + CommandExecutor.chemin_absolu + "/" + commandeArgs[0]);
                            } else {
                                    ps.println("Votre repertoire à du contenu impossible de le supprimer");
                            }
                    } else {
                            ps.println("Erreur, votre repertoire n'est pas un repertoire");
                    }
            } else {
                    ps.println("le repertoire n'existe pas");
            }
                } catch ( Exception e ) {
            e.printStackTrace();
                }
        }

}

