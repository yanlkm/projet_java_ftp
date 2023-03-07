import java.io.PrintStream;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		// Ce serveur accepte uniquement le user personne
		if(commandeArgs[0].toLowerCase().equals("usr1") || commandeArgs[0].toLowerCase().equals("adrien") ||
				commandeArgs[0].toLowerCase().equals("usr2")
				|| commandeArgs[0].toLowerCase().equals("usr3")) {
			CommandExecutor.userOk = true;
			CommandExecutor.var_usr =commandeArgs[0];
			CommandExecutor.chemin_absolu=CommandExecutor.var_usr;
			CommandExecutor.repertoireCourant=CommandExecutor.repertoire_absolu +"/"+ CommandExecutor.chemin_absolu;
			ps.println("0 Commande user OK");
		}
		else {
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
		}
		
	}

}
