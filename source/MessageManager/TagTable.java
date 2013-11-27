/*---------------------------------------------------------------------*/
/*!
 * @brief	Tab Table
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.util.*;
import javax.swing.*;

public class TagTable extends JTable
{
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public TagTable(Vector rowData, Vector columnNames)
	{
		super(rowData, columnNames);
	}
}
