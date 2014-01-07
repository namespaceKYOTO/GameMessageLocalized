/*---------------------------------------------------------------------*/
/*!
 * @brief	Tab Table
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TagTable extends TableEx
{
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public TagTable(String[] columns, int width, int height)
	{
		super(columns, width, height);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get tag code
	/*---------------------------------------------------------------------*/
	public Byte[] getTagCode(int index, String charset)
	{
		ArrayList<Byte> stack = new ArrayList<Byte>();
		
		Byte[] ret = new Byte[stack.size()];
		stack.toArray(ret);
		return ret;
	}
}
