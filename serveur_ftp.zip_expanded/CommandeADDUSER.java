import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeADDUSER extends Commande {
	
	 public CommandeADDUSER(PrintStream ps, String commandeStr) {
	        super(ps, commandeStr);
	    }

	@Override
	public void execute() {
		System.out.println("Entré dans adduser ");
		if (CommandExecutor.var_usr.equals("admin"))
		{
			File dir = new File(CommandExecutor.repertoire_absolu+"/"+ commandeArgs[0]);
			if (!dir.exists()) {
			    boolean created = dir.mkdir();
			    if (created) {
			    	String filename = "pw.txt";
			        String content = commandeArgs[1];

			        try {
			          FileWriter fileWriter = new FileWriter(CommandExecutor.repertoire_absolu+"/"+commandeArgs[0]+"/"+filename);
			          fileWriter.write(content);
			          fileWriter.close();
			          CommandExecutor.list[CommandExecutor.list.length]=commandeArgs[0];
			          
			          System.out.println("nouvelle liste de users : " + CommandExecutor.list); 
			          
			          ps.println("0 Le fichier a été créé avec succès.");
			          
			        } catch (IOException e) {
			          ps.println("2 Une erreur est survenue lors de la création du fichier : " + e.getMessage());
			        }
			      }
			    } else {
			        ps.println("2 Erreur lors de la création du répertoire.");
			    }
		} else {
			    ps.println("2 Vous n'êtes pas administrateur");
			}
		}
		
	}
	 

