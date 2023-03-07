import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {

    public CommandeCD(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute() {

        // Vérifier si le premier argument est présent
        if(commandeArgs.length < 1) {


            //ps.println("2 La commande CD doit avoir un argument");

            String chemin_absolu2 = CommandExecutor.var_usr;

            File dir = new File(CommandExecutor.repertoire_absolu  + "/" + chemin_absolu2);

            if (!dir.isDirectory() || !dir.canRead()) {
                ps.println("2 Le répertoire " + chemin_absolu2 + " n'existe pas ou ne peut pas être lu");
                return;
            }


            // Mettre à jour le répertoire courant

            CommandExecutor.repertoireCourant = dir.getAbsolutePath();

            ps.println("0 Répertoire courant changé en " + chemin_absolu2);
            //ps.println(CommandExecutor.repertoireCourant);

        }
        else 

        {
            String path = commandeArgs[0].toString();

            if(path.equals(".."))
            {
            	ps.println(path);
            	ps.println("exécution de la commande 'cd ..' chemin absolu : " + CommandExecutor.chemin_absolu);
                String currentDir = CommandExecutor.chemin_absolu;   
                int debut = currentDir.indexOf("/");
                int fin = currentDir.lastIndexOf("/");
                ps.println("tete de beuteu");
                
                if(debut ==-1 && fin ==-1) 
                {
                    ps.println("Erreur: vous êtes déjà à la racine.");
                }

                else 
                {
                	ps.println("affichage var debut : "+debut);
                	ps.println("affichage var fin  : "+fin);
                    String test_currentDir = currentDir.substring(0,fin);
                 	ps.println("affichage nouveau chemin absolu : "+test_currentDir);
                    File chemin = new File( CommandExecutor.repertoire_absolu +"/"+ test_currentDir);
                    ps.println("affichage du chemin complet : "+CommandExecutor.repertoire_absolu +"/"+ test_currentDir);
                    
                    if(chemin.isDirectory())  
                    {
                        CommandExecutor.repertoireCourant = chemin.toString();
                        CommandExecutor.chemin_absolu = test_currentDir;
                        ps.println("current dir new :"+ test_currentDir);
                        ps.println("chemin absolu local new :"+  chemin.toString());    
                    }
                    else  {
                    	ps.println("Erreur pas ouf");
                    }

                }
            }

            else 
            {
            	
            	
            	

                if (path.startsWith(".."))

                {
                	ps.println("exécution de la commande 'cd ../vers/rep' chemin absolu : " + CommandExecutor.chemin_absolu);
                    String currentDir = CommandExecutor.chemin_absolu;   
                    int debut = currentDir.indexOf("/");
                    int fin = currentDir.lastIndexOf("/");
                    if(debut == -1 && fin ==-1) 
                    {
                        ps.println("Erreur: vous êtes déjà à la racine.");
                    }
                    else {

                        currentDir = currentDir.substring(0,fin);
                        String path_changed = path.substring(3,path.length());
                        ps.println(path_changed);
                        String test_currentDir = currentDir +"/"+ path_changed; 
                        File chemin = new File( CommandExecutor.repertoire_absolu+"/"+ test_currentDir);

                        if(chemin.isDirectory())  
                        {
                            CommandExecutor.repertoireCourant = chemin.toString();

                            CommandExecutor.chemin_absolu = test_currentDir;

                            ps.println("current dir new :"+ test_currentDir);
                            ps.println("chemin absolu local new :"+  chemin.toString());

                        }
                        else {
                            ps.println("Chemin incorrect bitches? :" + test_currentDir);
                        }

                    }


                }
                else {

                    String currentDir = CommandExecutor.chemin_absolu;
                    File file = new File(CommandExecutor.repertoire_absolu +"/"+ currentDir+"/" + path);
                    if(file.isDirectory())
                    {
                    	CommandExecutor.chemin_absolu = currentDir+"/" + path;
                        CommandExecutor.repertoireCourant = CommandExecutor.repertoire_absolu +"/"+CommandExecutor.chemin_absolu;

                        

                        ps.println("current dir new :"+ CommandExecutor.chemin_absolu);
                        ps.println("chemin absolu local new :"+  CommandExecutor.repertoireCourant);
                    }
                    else {

                        ps.println("Chemin incorrect bitches? : "+CommandExecutor.repertoire_absolu +"/"+ currentDir+"/" + path);
                    }



                }
                
                
                

            }

        }

    }

   
}
