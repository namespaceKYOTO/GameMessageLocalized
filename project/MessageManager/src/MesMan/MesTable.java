/*---------------------------------------------------------------------*/
/*!
 * @brief	Message Table
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.util.Stack;

public class MesTable extends TableEx
{
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public MesTable(MessageManager messageMan, String[] columns, int width, int height)
	{
		super(messageMan, columns, width, height);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get Language Num
	/*---------------------------------------------------------------------*/
	public int getLanguageNum()
	{
		Stack<String> columnName = this.getColumnName();
		int size = columnName.size();
		
		if(columnName.search("Label") > 0)
		{
			--size;
		}
		if(columnName.search("Description") > 0)
		{
			--size;
		}
		
		return size;
	}
}
