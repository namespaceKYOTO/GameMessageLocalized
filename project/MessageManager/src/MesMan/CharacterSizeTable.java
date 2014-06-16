package MesMan;

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
	
	private void sort()
	{
		
	}
	
	public void check(MesTable mestable, TagTable tagtable, String language)
	{
		int languageIdx = mestable.getColumnIndex(language);
		if( languageIdx < 0 ) { return; }
		
		Stack<Stack<String>> row = getRow();
		int labelIdx = mestable.getColumnLabelIndex();
		int sizeIdx = mestable.getColumnSizeIndex();
		int mesLimitSize = 0;
		int line = 0;
		
		Stack<String> sizeOverStack = new Stack<String>();
		
		for (Stack<String> column : row) {
			String label = column.get(labelIdx);
			if(label != null && label.length() > 0) {
				mesLimitSize = Integer.valueOf(column.get(sizeIdx));
				line = 0;
			}
			
			int mesSize = getMessageSize(column.get(labelIdx), tagtable);
			if( mesLimitSize < mesSize ) {
				// サイズオーバー
				String str = String.format("%s : line %d, over %d", label, line, mesSize - mesLimitSize);
				sizeOverStack.push(str);
				System.out.println(str);
			}
			++line;
		}
	}
	
	public int getCharacterSize(String src)
	{
		int leftIdx = 0;
		int rightIdx = getRow().size() - 2;
		int charIdx = this.getCharacterSize(CHARACTER);
		int sizeIdx = this.getColumnIndex(SIZE);
		int size = 0;
		Stack<Stack<String>> row = getRow();
		int dstValue = valueOf(src.getBytes());
		
		while(leftIdx < rightIdx)
		{
			int middleIdx = (leftIdx + rightIdx) / 2;
			Stack<String> column = row.get(middleIdx);
			int charValue = valueOf(column.get(charIdx).getBytes());
			int diff = dstValue - charValue;
			if( diff == 0 ) {
				size = Integer.valueOf(column.get(sizeIdx));
				break;
			}
			else if( diff > 0 ) { rightIdx = middleIdx - 1; }
			else				{ leftIdx = middleIdx + 1; }
		}
		
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
		String[] split = null;
		
		for (Stack<String> column : row) {
			String tagName = column.get(0);
			if(tagName == null) continue;
			
			String tagMessage = column.get(2);
			int tagSize = 0;
			for(int i = 0; i < tagMessage.length(); ++i)
			{
				tagSize += getCharacterSize(tagMessage.substring(i, i+1));
			} 
			
			int index = str.indexOf(tagName);
			if(index != -1)
			{
				split = str.split(tagName);
				for (String string : split) {
					ret += getMessageSize(string, tagtable);
				}
				ret += (split.length - 1) * tagSize;
			}
			else {
				for(int i = 0; i < str.length(); ++i)
				{
					ret += getCharacterSize(tagMessage.substring(i, i+1));
				}
			}
		}
		
		return ret;
	}
}
