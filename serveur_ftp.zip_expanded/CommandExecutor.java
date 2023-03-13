import java.io.PrintStream;
import java.net.Socket;

public class CommandExecutor {
	static Socket sock_envoi;
	static final String repertoire_absolu = System.getProperty("user.dir");
	static String repertoireCourant = System.getProperty("user.dir");
	public static String[] list = {"admin","adrien","usr1","usr2","usr3"};
    static String var_usr ="";
	static String chemin_absolu=var_usr;
	
	public static boolean userOk = false ;
	public static boolean pwOk = false ;


	
	
	public static void executeCommande(PrintStream ps, String commande) {
		if(userOk && pwOk) {
			// Changer de repertoire. Un (..) permet de revenir au repertoire superieur
			if(commande.split(" ")[0].equals("cd")) (new CommandeCD(ps, commande)).execute();
	
			// Telecharger un fichier
			if(commande.split(" ")[0].equals("get")) (new CommandeGET(ps, commande)).execute();
			
			// Afficher la liste des fichiers et des dossiers du repertoire courant
			if(commande.split(" ")[0].equals("ls")) (new CommandeLS(ps, commande)).execute();
		
			// Afficher le repertoire courant
			if(commande.split(" ")[0].equals("pwd")) (new CommandePWD(ps, commande)).execute();
			
			// Envoyer (uploader) un fichier
			if(commande.split(" ")[0].equals("stor")) (new CommandeSTOR(ps, commande)).execute();
			//ajouter un user
			if(commande.split(" ")[0].equals("adduser")) (new CommandeADDUSER(ps, commande)).execute();
			// supprimer un repertoire vide 
			if(commande.split(" ")[0].equals("rmdir")) (new CommandeRMDIR(ps, commande)).execute();
			// creer un repertoire 
			if(commande.split(" ")[0].equals("mkdir")) (new CommandeMKDIR(ps, commande)).execute();
			else 
			{
				return;
			}
			

							}
		else {
			
			if(commande.split(" ")[0].equals("user") || commande.split(" ")[0].equals("pass"))
			{
				// Le login pour l'authentification
				if(commande.split(" ")[0].equals("user")) {(new CommandeUSER(ps, commande)).execute();}
					// Le mot de passe pour l'authentification
				if(commande.split(" ")[0].equals("pass")) {(new CommandePASS(ps, commande)).execute();}
	
			}
			else {
				ps.println("2 Vous n'êtes pas connecté !");
				}
			
			
			}
			
		}
	}


