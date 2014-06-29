package BtoC;


/**
 * バイナリファイルを.cファイルへ変換.
 * @author t_sato
 *
 */
public class BtoC
{
	static String version = "BtoC version \"1.0\"";
	static String author = "(c) 2013 t_sato";
	
	public static void main(String[] args)
	{
		// analyze for command argument
		ExCmdArg cmdArg = new ExCmdArg("-");
		cmdArg.analyzeCommandArguments(args);
		
		//
		if(cmdArg.isVersion)
		{
			System.out.println(version);
			System.out.println(author);
		}
		if(cmdArg.isHelp)
		{
			System.out.println("binary file to .c file");
		}
		
		// out put cfile
		ToCFile toCFile = new ToCFile();
		toCFile.toCFile(cmdArg);
	}
}
