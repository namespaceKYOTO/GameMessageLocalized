/*---------------------------------------------------------------------*/
/*!
 * @brief	.mtbl filter
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.io.File;
import javax.swing.filechooser.FileFilter;


public class MTblFilter extends FileFilter
{
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public MTblFilter()
	{
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
		
		if("mtbl".equals(getSuffix(f)))
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
		return "Message Table";
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get file suffix
	/*---------------------------------------------------------------------*/
	private String getSuffix(File f)
	{
		String name = f.getName();
		String[] split = name.split(".");
		if(split.length >= 2)
		{
			return split[split.length - 1];
		}
		else
		{
			return null;
		}
	}
}
