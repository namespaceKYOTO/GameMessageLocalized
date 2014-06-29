package BtoC;

import java.util.Stack;
import Common.*;


/**
 * 引数解析.
 * @author t_sato
 *
 */
public class ExCmdArg extends CmdArg
{
	public boolean			isVersion;
	public boolean			isHelp;
	public boolean			isExtern;
	public String			outDir;
	public String			dataListDir;
	public Stack<String>	datas;

	/**
	 * コンストラクタ.
	 */
	public ExCmdArg()
	{
		Init();
	}
	
	/**
	 * コンストラクタ.
	 * @param optionPrefix 引数の接頭辞
	 */
	public ExCmdArg(String optionPrefix)
	{
		super(optionPrefix);
		Init();
	}
	
	/**
	 * コンストラクタ.
	 * @param optionPrefix 引数の接頭辞
	 * @param args 引数
	 */
	public ExCmdArg(String optionPrefix, String[] args)
	{
		super(optionPrefix);
		Init();
	}
	
	/**
	 * 初期化. 
	 */
	private void Init()
	{
		this.isVersion = false;
		this.isHelp = false;
		this.isExtern = false;
		this.outDir = new String("");
		this.dataListDir = null;
		this.datas = new Stack<String>();
	}
	
	/* (非 Javadoc)
	 * @see Common.CmdArg#getArgumentsNum(java.lang.String)
	 */
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
	
	/* (非 Javadoc)
	 * @see Common.CmdArg#optionAnalyze(java.lang.String, java.lang.String[])
	 */
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
	
	/* (非 Javadoc)
	 * @see Common.CmdArg#unknownArgumentAnalyze(java.util.Stack)
	 */
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
