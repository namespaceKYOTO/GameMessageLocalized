package MesMan;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

import MesMan.MenuBar.SettingMenu;

/**
 * 文字サイズテーブル.
 * @author t_sato
 *
 */
public class CharacterSizeTable extends TableEx
{
	private static String CHARACTER = "Character";
	private static String SIZE = "Size";
	private static String charset = "UTF-16";

	/**
	 * コンストラクタ.
	 * @param mesman メッセージデータ管理
	 * @param width　UI幅
	 * @param height UI幅
	 */
	public CharacterSizeTable(MesMan mesman, int width, int height)
	{
		super(mesman, width, height);
		
		String[] columns = {CHARACTER, SIZE};
		addColumnNames(columns);
		
		addNotDeleteColumn(CHARACTER);
		addNotDeleteColumn(SIZE);
	}
	
	/**
	 * 文字サイズチェック.
	 * @param mestable チェックするメッセージテーブル
	 * @param tagtable メッセージで使用しているタグテーブル
	 * @param resultTable チェック結果表示先テーブル
	 * @param language チェックする言語
	 */
	public void check(MesTable mestable, TagTable tagtable, ResultTable resultTable, String language)
	{
		charSort();
		
		int languageIdx = mestable.getColumnIndex(language);
		if( languageIdx < 0 ) { return; }
		
		Stack<Stack<String>> row = mestable.getRow();
		int labelIdx = mestable.getColumnLabelIndex();
		int sizeIdx = mestable.getColumnSizeIndex();
		int mesLimitSize = 0;
		
		// 前回の結果などが残っていないように消しておく 
		resultTable.removeAll();
		
		for (Stack<String> column : row) {
			String label = column.get(labelIdx);
			if(label != null && label.length() > 0) {
				mesLimitSize = Integer.valueOf(column.get(sizeIdx));
			}
			
			int mesSize = getMessageSize(column.get(languageIdx), tagtable);
			if( mesLimitSize < mesSize ) {
				// 結果を格納
				Stack<String> result = new Stack<String>();
				result.push(label);
				result.push(String.format("%d Over", mesSize - mesLimitSize));
				resultTable.getTableModel().addRow(result);
			}
		}
	}
	
	/**
	 * 文字サイズ取得.
	 * @param src　サイズを取得する文字
	 * @param leftIdx 検索範囲最小(テーブル内を２分探索用)
	 * @param rightIdx 検索範囲最大(テーブル内を２分探索用)
	 * @return　文字サイズ
	 */
	public int getCharacterSize(String src, int leftIdx, int rightIdx)
	{
		Stack<Stack<String>> row = getRow();
		int charIdx = this.getColumnIndex(CHARACTER);
		int sizeIdx = this.getColumnIndex(SIZE);
		int dstValue = 0;
		try {
			dstValue = valueOf(src.getBytes(charset));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return 0;
		}
		SettingMenu settingMenu = (SettingMenu) this.mesman.getMenubar().get(1);
		int size = 0;
		if(settingMenu != null) {
			// 文字が見つからなかったときのサイズ
			size = settingMenu.getDefaultCharactorSize().intValue();
		}
		
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
	
	/**
	 * byte配列をintに変換.
	 * @param src　byte配列
	 * @return　変換した値
	 */
	public int valueOf(byte[] src)
	{
		int  ret = 0;
		
		int bitSift = 0;
		for (byte b : src) {
			ret = (ret<<bitSift) | b;
			++bitSift;
		}		
		return ret;
	}
	
	/**
	 * メッセージのサイズを取得.
	 * @param str メッセージ
	 * @param tagtable メッセージで使用しているタグテーブル
	 * @return メッセージサイズ
	 */
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
	
	/**
	 * 文字のソート.
	 * 文字をUnicodeの順番にソートする.
	 */
	public void charSort()
	{
		Stack<Stack<String>> row = this.getRow();
		quickSort(row, 0, row.size() - 1);
		
		// Debug
		debugPrint();
	}
	
	/**
	 * ソート. 
	 * @param dst　ソート先
	 * @param left 範囲 最小
	 * @param right　範囲 最大
	 */
	private void quickSort(Stack<Stack<String>> dst, int left, int right)
	{
		if(left >= right) {
			return;
		}
		
		int store = partition(dst, left, right);
		
		if(store < left || right < store) {
			return;
		}
		
		quickSort(dst, left, store - 1);
		quickSort(dst, store + 1, right);
	}
	
	/**
	 * 中心の値から小さい物と大きい物に分ける.
	 * @param dst　分ける先
	 * @param left 範囲 最小
	 * @param right　範囲 最大
	 * @return
	 */
	private int partition(Stack<Stack<String>> dst, int left, int right)
	{
		int charIdx = getColumnIndex(CHARACTER);
		int pivotIdx = (left + right) / 2;
		String pivot = dst.get(pivotIdx).get(charIdx);
		int pivotCode = 0;
		
		if(pivot == null || pivot.length() <= 0) {
			return 0;
		}
		
		try
		{
			pivotCode = valueOf(pivot.getBytes(charset));
			
			swap(dst, right, pivotIdx);
			
			int num = right - 1;
			int store = left;
			for(int i = left; i <= num; ++i)
			{
				String codeStr = dst.get(i).get(charIdx);
				if(codeStr == null || codeStr.length() <= 0) {
					swap(dst, i, num);
					swap(dst, num, num + 1);
					--num;
					--i;
					continue;
				}
				int code = this.valueOf(codeStr.getBytes(charset));
				if(code <= pivotCode) {
					swap(dst, i, store);
					++store;
				}
			}
			swap(dst, store, num + 1);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return 0;
		}
		
		return 0;
	}
	
	/**
	 * 交換.
	 * @param dst 交換元
	 * @param idx1 交換index1
	 * @param idx2 交換index2
	 */
	private void swap(Stack<Stack<String>> dst, int idx1, int idx2)
	{
		Stack<String> idx1Obj = dst.get(idx1);
		Stack<String> idx2Obj = dst.get(idx2);
		
		dst.remove(idx1);
		dst.add(idx1, idx2Obj);
		
		dst.remove(idx2);
		dst.add(idx2, idx1Obj);
	}
	
	/**
	 * デバック出力.
	 */
	private void debugPrint()
	{
		File file = new File("./debugPrint.txt");
		try {
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file);
			
			Stack<Stack<String>> row = this.getRow();
			for (Stack<String> stack : row) {
				String code = stack.get(0);
				if(code != null && code.length() > 0) {
					int value = this.valueOf(code.getBytes(charset));
					pw.write(code + String.format(" : 0x%x\r\n", value));
				}
			}
			pw.flush();
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
