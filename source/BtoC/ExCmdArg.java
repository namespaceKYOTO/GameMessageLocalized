/*---------------------------------------------------------------------*/
/*!
 * @brief	Extension Command Arguments class
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package BtoC;

import java.lang.*;
import java.util.Stack;
import Common.*;


public class ExCmdArg extends CmdArg
{
	public boolean			isVersion;
	public boolean			isHelp;
	public boolean			isExtern;
	public String			outDir;
	public String			dataListDir;
	public Stack<String>	datas;

	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public ExCmdArg()
	{
		Init();
	}
	
	public ExCmdArg(String optionPrefix)
	{
		super(optionPrefix);
		Init();
	}
	
	public ExCmdArg(String optionPrefix, String[] args)
	{
		super(optionPrefix);
		Init();
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	initialize method
	/*---------------------------------------------------------------------*/
	private void Init()
	{
		this.isVersion = false;
		this.isHelp = false;
		this.isExtern = false;
		this.outDir = new String("");
		this.dataListDir = null;
		this.datas = new Stack<String>();
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get the number of arguments of an option
	//*!param	option	option string
	//*!retval	-1 : get up the following options
	/*---------------------------------------------------------------------*/
	protected int getArgumentsNum(String option)
	{
		if("-v".equals(option) || "-version".equals(option))
		{
			return 0;
		}
		else if("-h".equals(option) || "-help".equals(option))
		{
			return 0;
		}
		else if("-ex".equals(option) || "-extern".equals(option))
		{
			return 0;
		}
		else if("-o".equals(option))
		{
			return 1;
		}
		else if("-d".equals(option) || "-data".equals(option))
		{
			return -1;
		}
		return 0;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	analyze command arguments
	//*!param	option	option string
	//*!param	args	option argument
	/*---------------------------------------------------------------------*/
	protected void optionAnalyze(String option, String[] args)
	{
		if("-v".equals(option) || "-version".equals(option))
		{
			this.isVersion = true;
		}
		else if("-h".equals(option) || "-help".equals(option))
		{
			this.isHelp = true;
		}
		else if("-ex".equals(option) || "-extern".equals(option))
		{
			this.isExtern = true;
		}
		else if("-o".equals(option))
		{
			if(args != null)
			{
				this.outDir = new String(args[0]);
			}
		}
		else if("-d".equals(option) || "-data".equals(option))
		{
			for(String arg : args)
			{
				this.datas.push(arg);
			}
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	analyze unknown argument
	//*!param	args	argument
	/*---------------------------------------------------------------------*/
	protected void unknownArgumentAnalyze(Stack<String> args)
	{
		for(String arg : args)
		{
			// get data list
			if(arg.indexOf("@") == 0)
			{
				String[] splitArg = arg.split("@");
				this.dataListDir = new String(splitArg[splitArg.length - 1]);
			}
			// get data
			else
			{
				datas.push(arg);
			}
		}
	}
}
