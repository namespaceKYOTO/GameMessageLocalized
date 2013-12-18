/*---------------------------------------------------------------------*/
/*!
 * @brief	file filter
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.io.File;
import javax.swing.filechooser.FileFilter;


public class FileFilterEx extends FileFilter
{
	String suffix;
	String description;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public FileFilterEx()
	{
		suffix = new String("");
		description = new String("");
	}
	public FileFilterEx(String suffix, String description)
	{
		this.suffix = suffix;
		this.description = description;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	setter
	/*---------------------------------------------------------------------*/
	void setSuffix(String str)
	{
		this.suffix = str;
	}
	void setDescription(String str)
	{
		this.description = str;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	Whether the given file is accepted by this filter.
	/*---------------------------------------------------------------------*/
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
	
	/*---------------------------------------------------------------------*/
	//*!brief	Whether the given file is accepted by this filter.
	/*---------------------------------------------------------------------*/
	@Override
	public String getDescription()
	{
		return this.description;
	}
}
