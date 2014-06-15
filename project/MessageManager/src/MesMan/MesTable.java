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
	private static String LABEL = "Label";
	private static String DESCRIPTION = "Description";
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public MesTable(MessageManager messageMan, String[] columns, int width, int height)
	{
		super(messageMan, columns, width, height);
	}
	
	public int getMessageNum()
	{
		int mesNum = 0;
		int labelIdx = this.getColumnIndex("Label");
		
		for (Stack<String> column : this.getRow()) {
			int columnCount = 0;
			for (String string : column) {
				if( columnCount == labelIdx && string != null && string.length() > 0) {
					++mesNum;
				}
				++columnCount;
			}
		}
		
		return mesNum;
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
	
	public int getColumnLabelIndex()
	{
		return getColumnIndex(LABEL);
	}
	
	public int getColumnDescriptionIndex()
	{
		return getColumnIndex(DESCRIPTION);	
	}
}
