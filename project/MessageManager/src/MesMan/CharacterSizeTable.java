package MesMan;

import java.io.UnsupportedEncodingException;
import java.util.Stack;

public class CharacterSizeTable extends TableEx
{
	private static String CHARACTER = "Character";
	private static String SIZE = "Size";

	public CharacterSizeTable(MessageManager messageMan, int width, int height)
	{
		super(messageMan, width, height);
		
		String[] columns = {CHARACTER, SIZE};
		addColumnNames(columns);
		
		addNotDeleteColumn(CHARACTER);
		addNotDeleteColumn(SIZE);
	}
	
//	private void sort()
//	{
//		
//	}
	
	public void check(MesTable mestable, TagTable tagtable, ResultTable resultTable, String language)
	{
		int languageIdx = mestable.getColumnIndex(language);
		if( languageIdx < 0 ) { return; }
		
		Stack<Stack<String>> row = mestable.getRow();
		int labelIdx = mestable.getColumnLabelIndex();
		int sizeIdx = mestable.getColumnSizeIndex();
		int mesLimitSize = 0;
		int line = 0;
		
		// 前回の結果などが残っていないように消しておく 
		resultTable.removeAll();
		
		for (Stack<String> column : row) {
			String label = column.get(labelIdx);
			if(label != null && label.length() > 0) {
				mesLimitSize = Integer.valueOf(column.get(sizeIdx));
				line = 0;
			}
			
			int mesSize = getMessageSize(column.get(languageIdx), tagtable);
			if( mesLimitSize < mesSize ) {
				// 結果を格納
				Stack<String> result = new Stack<String>();
				result.push(label);
				result.push(String.format("%d Over", mesSize - mesLimitSize));
				resultTable.getTableModel().addRow(result);
			}
			++line;
		}
	}
	
	public int getCharacterSize(String src, int leftIdx, int rightIdx)
	{
		Stack<Stack<String>> row = getRow();
		int charIdx = this.getColumnIndex(CHARACTER);
		int sizeIdx = this.getColumnIndex(SIZE);
		String charset = "UTF-16";
		int dstValue = 0;
		try {
			dstValue = valueOf(src.getBytes(charset));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return 0;
		}
		int size = 0;
		
		while(leftIdx < rightIdx)
		{
			int middleIdx = (leftIdx + rightIdx) / 2;
			Stack<String> column = row.get(middleIdx);
			String c = column.get(charIdx);
			if(c == null || c.length() <= 0) {
				// 空白があった場合はエラーにする
				// エラーをスローする？
			}
			int charValue = 0;
			try {
				charValue = valueOf(c.getBytes(charset));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return 0;
			}
			int diff = dstValue - charValue;
			if( diff == 0 ) {
				size = Integer.valueOf(column.get(sizeIdx));
				break;
			}
			else if( diff > 0 ) { leftIdx = middleIdx + 1; }
			else				{ rightIdx = middleIdx - 1; }
		}

		System.out.println("Check : " + src + String.format("  size %d  value 0x%x", size, dstValue));
		return size;
	}
	
	public int valueOf(byte[] src)
	{
		int  ret = 0;
		
		int bitSift = 0;
		for (byte b : src) {
			ret = (ret<<bitSift) | b;
		}		
		return ret;
	}
	
	public int getMessageSize(String str, TagTable tagtable)
	{
		if(str == null){ return 0; }
		
		int ret = 0;
		Stack<Stack<String>> row = tagtable.getRow();
		int tagIndex = tagtable.getColumnTagIndex();
		int substitutionIndex = tagtable.getColumnSubstitution();
		String[] split = null;
		boolean existTag = false;
		
		for (Stack<String> column : row) {
			String tagName = column.get(tagIndex);
			if(tagName == null) continue;
			existTag = true;
			
			String tagMessage = column.get(substitutionIndex);
			int tagSize = 0;
			for(int i = 0; i < tagMessage.length(); ++i)
			{
				tagSize += getCharacterSize(tagMessage.substring(i, i+1), 0, getRow().size() - 2);
			} 
			
			int index = str.indexOf(tagName);
			if(index != -1)
			{
				split = str.split(tagName);
				for (String string : split) {
					ret += getMessageSize(string, tagtable);
				}
				ret += ((split.length - 1) * tagSize);
			}
			else {
				for(int i = 0; i < str.length(); ++i)
				{
					String c = str.substring(i, i+1);
					System.out.print(c + " ");
					ret += getCharacterSize(str.substring(i, i+1), 0, getRow().size() - 2);
				}
				System.out.print("\n");
			}
		}
		
		if(existTag == false)
		{
			for(int i = 0; i < str.length(); ++i)
			{
				ret += getCharacterSize(str.substring(i, i+1), 0, getRow().size() - 2);
			}
		}
		
		return ret;
	}
}
