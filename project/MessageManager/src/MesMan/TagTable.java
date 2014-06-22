package MesMan;

import java.util.*;

/**
 * タグテーブル.
 * @author t_sato
 *
 */
public class TagTable extends TableEx
{
	private String TAG = "Tag";
	private String DESCRIPTION = "Description";
	private String CODE = "Code";
	private String SUBSTITUTION = "Substitution";
	
	/**
	 * コンストラクタ.
	 * @param messageMan メッセージ管理
	 * @param width UI幅
	 * @param height UI高さ
	 */
	public TagTable(MessageManager messageMan, int width, int height)
	{
		super(messageMan, width, height);
		
		String[] columns = {TAG, DESCRIPTION, CODE, SUBSTITUTION};
		addColumnNames(columns);
		
		addNotDeleteColumn(TAG);
		addNotDeleteColumn(DESCRIPTION);
		addNotDeleteColumn(CODE);
		addNotDeleteColumn(SUBSTITUTION);
	}
	
	/**
	 * タグのバイトを取得.
	 * @param index 取得するタグの番号
	 * @param charset 文字コード
	 * @return バイト配列
	 */
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
	
	/**
	 * タグの列番号取得
	 * @return タグの列番号
	 */
	public int getColumnTagIndex()
	{
		return getColumnIndex(TAG);
	}
	
	/**
	 * 説明列番号取得.
	 * @return 説明列番号
	 */
	public int getColumnDescriptionIndex()
	{
		return getColumnIndex(DESCRIPTION);
	}
	
	/**
	 * コードの列番号取得.
	 * @return コードの列番号
	 */
	public int getColumnCodeIndex()
	{
		return getColumnIndex(CODE);
	}
	
	/**
	 * 置き換え文字の列番号取得.
	 * @return　置き換え文字の列番号
	 */
	public int getColumnSubstitution()
	{
		return getColumnIndex(SUBSTITUTION);
	}
}
