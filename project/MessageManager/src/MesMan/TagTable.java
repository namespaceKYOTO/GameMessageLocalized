/*---------------------------------------------------------------------*/
/*!
 * @brief	Tab Table
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.util.*;

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
		ArrayList<Byte> codeList = new ArrayList<Byte>();
		
		// 
		Stack<String> rowData = this.getRow().get(index);
		String code = null;
		int columnIndex = this.getColumnIndex("Code");
		System.out.println(String.format("Code index : %d", columnIndex));
		if(columnIndex != -1)
		{
			code = rowData.get(columnIndex);
			System.out.println("code : " + code);
			//Long data = Long.valueOf(code);
			Long data = Long.decode(code);
			for(int i = 0; i < Long.MAX_VALUE / 0xFF; ++i)
			{
				if(data == 0)
				{
					if(i ==0)
					{
						codeList.add(Byte.valueOf((byte)0));
					}
					break;
				}
				
				codeList.add(Byte.valueOf((byte)(data & 0x00000000000000FFL)));
				data >>= 8;
			}
		}
		else
		{
			return null;
		}
		
		Byte[] ret = new Byte[codeList.size()];
		codeList.toArray(ret);
		return ret;
	}
}
