package MesMan;

import java.util.Stack;

import Common.CmdArg;

/**
 * 引数解析クラス
 * @author t_sato
 *
 */
public class CmdArgEx extends CmdArg
{
	public enum eMode
	{
		Unknown,
		Normal,
		Sample,
	}
	
	private static String PREFIX = "-";
	private static String OPTION_MODE = "m";
	private static String OPTION_MESSAGE_TABLE = "mt";
	private static String OPTION_TAG_TABLE = "tt";
	private static String OPTION_CHARACTORSIZE_TABLE = "ct";
	
	private eMode mode;
	private String mtblFile;
	private String ttblFile;
	private String ctblFile;
	
	/**
	 * コンストラクタ.
	 */
	public CmdArgEx()
	{
		super(PREFIX);
		
		mode = eMode.Unknown;
		mtblFile = null;
		ttblFile = null;
		ctblFile = null;
	}
	
	/**
	 * モード取得.
	 * @return モード
	 */
	public eMode getMode()
	{
		return this.mode;
	}
	
	/**
	 * メッセージテーブルファイル名取得.
	 * @return ファイル名
	 */
	public String getMtblFile() {
		return mtblFile;
	}

	/**
	 * タグテーブルファイル名取得.
	 * @return ファイル名
	 */
	public String getTtblFile() {
		return ttblFile;
	}

	/**
	 * 文字サイズテーブルファイル名取得.
	 * @return ファイル名
	 */
	public String getCtblFile() {
		return ctblFile;
	}

	/* (非 Javadoc)
	 * @see Common.CmdArg#getArgumentsNum(java.lang.String)
	 */
	protected int getArgumentsNum(String option)
	{
		int num = super.getArgumentsNum(option);
		
		if(num == -1)
		{
			num = 0;
			
			String mode = PREFIX + OPTION_MODE;
			String m = PREFIX + OPTION_MESSAGE_TABLE;
			String t = PREFIX + OPTION_TAG_TABLE;
			String c = PREFIX + OPTION_CHARACTORSIZE_TABLE;
			
			// テーブルの指定
			if(m.equals(option) || t.equals(option) || c.equals(option))
			{
				num = 1;
			}
			// モード指定
			else if(mode.equals(option))
			{
				num = 1;
			}
		}
		
		return num;
	}
	
	/* (非 Javadoc)
	 * @see Common.CmdArg#optionAnalyze(java.lang.String, java.lang.String[])
	 */
	protected void optionAnalyze(String option, String[] args)
	{
		String mode = PREFIX + OPTION_MODE;
		String m = PREFIX + OPTION_MESSAGE_TABLE;
		String t = PREFIX + OPTION_TAG_TABLE;
		String c = PREFIX + OPTION_CHARACTORSIZE_TABLE;
		
		if(mode.equals(option))
		{
			this.mode = getMode(args[0]);
		}
		else if(m.equals(option))
		{
			mtblFile = new String(args[0]);
		}
		else if(t.equals(option))
		{
			ttblFile = new String(args[0]);
		}
		else if(c.equals(option))
		{
			ctblFile = new String(args[0]);
		}
	}
	
	/* (非 Javadoc)
	 * @see Common.CmdArg#unknownArgumentAnalyze(java.util.Stack)
	 */
	protected void unknownArgumentAnalyze(Stack<String> args)
	{
	}
	
	/**
	 * モード取得
	 * @param str 文字
	 * @return モード
	 */
	private eMode getMode(String str)
	{
		if("normal".equals(str))
		{
			return eMode.Normal;
		}
		else if("sample".equals(str))
		{
			return eMode.Sample;
		}
		return eMode.Unknown;
	}

}
