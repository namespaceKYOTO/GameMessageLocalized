/*---------------------------------------------------------------------*/
/*!
 * @brief	Command Argument class
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package Common;

import java.lang.*;
import java.util.Stack;

public class CmdArg
{
	protected String optionPrefix;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public CmdArg()
	{
		Init();
	}
	
	public CmdArg(String optionPrefix)
	{
		Init();
		this.optionPrefix = new String(optionPrefix);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	initialize method
	/*---------------------------------------------------------------------*/
	private void Init()
	{
		this.optionPrefix = null;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get the number of arguments of an option
	//*!param	option	option string
	//*!retval	-1 : get up the following options
	/*---------------------------------------------------------------------*/
	protected int getArgumentsNum(String option)
	{
		return -1;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	analyze command arguments
	//*!param	option	option string
	//*!param	args	option argument
	/*---------------------------------------------------------------------*/
	protected void optionAnalyze(String option, String[] args)
	{
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	analyze unknown argument
	//*!param	args	argument
	/*---------------------------------------------------------------------*/
	protected void unknownArgumentAnalyze(Stack<String> args)
	{
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	analyze command arguments
	/*---------------------------------------------------------------------*/
	public void analyzeCommandArguments(String[] args)
	{
		Stack<String> unknownArgument = new Stack<String>();
		
		for( int i = 0; i < args.length; ++i )
		{
			// option
			if( args[i].indexOf(this.optionPrefix) == 0 )
			{
				// 
				int argNum = getArgumentsNum(args[i]);
				if( argNum < 0 )
				{
					argNum = Integer.MAX_VALUE;
				}
				
				// next arg
				++i;
				
				// get argument for option
				int argCount = 0;
				for( ; i + argCount < args.length && argCount < argNum; ++argCount )
				{
					// next option?
					if( args[i + argCount].indexOf(this.optionPrefix) == 0 )
					{
						break;
					}
				}
				
				// call option analyzer
				if( argCount > 0 )
				{
					String[] options = new String[argCount];
					System.arraycopy(args, i, options, 0, argCount);
					optionAnalyze(args[i - 1], options);
				}
				else 
				{
					optionAnalyze(args[i - 1], null);
				}
				
				i += argCount;
			}
			// unknown argument
			else
			{
				unknownArgument.push(args[i]);
			}
		}
		
		unknownArgumentAnalyze(unknownArgument);
	}
}
