import java.io.*;

public class CommandePASS extends Commande {
	
	public CommandePASS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		// Le mot de passe est : abcd File file = new File(".");
		
		File file = new File(CommandExecutor.var_usr + File.separator + "pw.txt");
		String path = file.getAbsoluteFile().toString();
		
		String firstLine="";
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		firstLine = br.readLine();
		br.close();
		}
		catch (IOException e) {
            e.printStackTrace();
        };
	
		if(commandeArgs[0].toLowerCase().equals(firstLine)) {
			CommandExecutor.pwOk = true;
			ps.println("0 Vous êtes bien connecté sur notre serveur (mdp ok) : "+ firstLine);
		}
		
		else {
			ps.println("2 Le mode de passe est faux");
		}
		
	}

}
