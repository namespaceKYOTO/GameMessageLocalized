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
	protected boolean version;
	protected boolean help;
	
	protected String V = "v";
	protected String VERSION = "version";
	protected String H = "h";
	protected String HELP = "help";
	
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
		String v = optionPrefix + V;
		String h = optionPrefix + H;
		String version = optionPrefix + VERSION;
		String help = optionPrefix + HELP;
		
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
		// ...etc
		return -1;
	}
	
	/**
	 * バージョン指定があったか
	 * @return　有無
	 */
	public boolean isVersion() {
		return version;
	}

	/**
	 * ヘルプ指定があったか
	 * @return 有無
	 */
	public boolean isHelp() {
		return help;
	}

	/**
	 * オプション解析.
	 * @param option オプション
	 * @param args 引数
	 */
	protected void optionAnalyze(String option, String[] args)
	{
		String v = optionPrefix + V;
		String h = optionPrefix + H;
		String version = optionPrefix + VERSION;
		String help = optionPrefix + HELP;
		
		if(v.equals(option) || version.equals(option))
		{
			this.version = true;
		}
		else if(h.equals(option) || help.equals(option))
		{
			this.help = true;
		}
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
