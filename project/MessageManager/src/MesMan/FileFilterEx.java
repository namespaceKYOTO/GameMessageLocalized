
package MesMan;

import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 * ファイルフィルター.
 * @author t_sato
 *
 */
public class FileFilterEx extends FileFilter
{
	String suffix;
	String description;
	
	/**
	 * コンストラクタ.
	 */
	public FileFilterEx()
	{
		suffix = new String("");
		description = new String("");
	}
	/**
	 *　コンストラクタ. 
	 * @param suffix 制限する拡張子
	 * @param description 制限に対する説明
	 */
	public FileFilterEx(String suffix, String description)
	{
		this.suffix = suffix;
		this.description = description;
	}
	
	/**
	 * 制限する拡張子の設定.
	 * @param str 制限する拡張子 
	 */
	void setSuffix(String str)
	{
		this.suffix = str;
	}
	/**
	 * 制限に対する説明設定.
	 * @param str 制限に対する説明
	 */
	void setDescription(String str)
	{
		this.description = str;
	}
	
	@Override
	public boolean accept(File f)
	{
		if(f.isDirectory())
		{
			return true;
		}
		
		if(f.getName().endsWith(this.suffix) == true)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public String getDescription()
	{
		return this.description;
	}
}
