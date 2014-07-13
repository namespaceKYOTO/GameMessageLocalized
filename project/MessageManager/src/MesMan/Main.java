package MesMan;

import java.io.File;

/**
 * メインクラス.
 * @author t_sato
 *
 */
public class Main
{

	public static void main(String[] args)
	{
		CmdArgEx cmd = new CmdArgEx();
		cmd.analyzeCommandArguments(args);
		if(cmd.isVersion())
		{	
		}
		if(cmd.isHelp())
		{
		}
		if(cmd.getMode() != CmdArgEx.eMode.Sample)
		{
			// Check Config file
//			MesMan.CONFIG_FILE
			File file = new File(MesMan.CONFIG_FILE);
			if(file.exists() == false)
			{
				// create config File
			}
		}
		
		MesMan mesman = new MesMan(args, true);
	}

}
