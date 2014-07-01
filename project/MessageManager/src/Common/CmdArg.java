package Common;

import java.util.Stack;

/**
 * 引数解析.
 * @author t_sato
 *
 */
public class CmdArg
{
	protected String optionPrefix;
	
	/**
	 * コンストラクタ. 
	 */
	public CmdArg()
	{
		Init();
	}
	
	/**
	 * コンストラクタ.
	 * @param optionPrefix 引数の接頭辞
	 */
	public CmdArg(String optionPrefix)
	{
		Init();
		this.optionPrefix = new String(optionPrefix);
	}
	
	/**
	 * 初期化. 
	 */
	private void Init()
	{
		this.optionPrefix = "";
	}
	
	/**
	 * 指定オプションに続く有効な引数の数取得.
	 * @param option オプション
	 * @return 有効な引数の数
	 */
	protected int getArgumentsNum(String option)
	{
		// よくあるオプションについて記述しとく...
		String v = optionPrefix + "v";
		String h = optionPrefix + "h";
		String o = optionPrefix + "o";
		String version = optionPrefix + "version";
		String help = optionPrefix + "help";
		String out = optionPrefix + "out";
		
		// バージョン
		if(v.equals(option) || version.equals(option))
		{
			return 0;
		}
		// ヘルプ
		else if(h.equals(option) || help.equals(option))
		{
			return 0;
		}
		// 出力ディレクトリ
		else if(o.equals(option) || out.equals(option))
		{
			return 1;	// "o"の後にはディレクトリの指定があるので1
		}
		// ...etc
		return -1;
	}
	
	/**
	 * オプション解析.
	 * @param option オプション
	 * @param args 引数
	 */
	protected void optionAnalyze(String option, String[] args)
	{
	}
	
	/**
	 * オプションの指定がなかった引数の解析.
	 * @param args 引数
	 */
	protected void unknownArgumentAnalyze(Stack<String> args)
	{
	}
	
	/**
	 * 引数の解析.
	 * @param args 引数
	 */
	public void analyzeCommandArguments(String[] args)
	{
		Stack<String> unknownArgument = new Stack<String>();
		
		for( int i = 0; i < args.length;/* ++i */)
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
				++i;
			}
		}
		
		unknownArgumentAnalyze(unknownArgument);
	}
}
