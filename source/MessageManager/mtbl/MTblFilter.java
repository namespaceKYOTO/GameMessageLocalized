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
	static private String MTBL = "mtbl";
	static private String DESCRIPTION = "Message Table(.mtbl)";
	
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
		
		if(f.getName().endsWith(MTBL) == true)
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
		return DESCRIPTION;
	}
}
