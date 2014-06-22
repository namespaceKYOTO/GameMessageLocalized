package MesMan;

import java.util.Stack;

/**
 * メッセージテーブル.
 * @author t_sato
 *
 */
public class MesTable extends TableEx
{
	private static String LABEL = "Label";
	private static String DESCRIPTION = "Description";
	private static String SIZE = "Size";
	
	/**
	 * コンストラクタ.
	 * @param messageMan　メッセージ管理クラス
	 * @param width　UI幅
	 * @param height UI高さ
	 */
	public MesTable(MessageManager messageMan, int width, int height)
	{
		super(messageMan, width, height);
		
		String[] columns = {LABEL, DESCRIPTION, SIZE, "JPN", "ENG", "DEU", "FRA", "ITA", "SPA"};
		addColumnNames(columns);
		
		addNotDeleteColumn(LABEL);
		addNotDeleteColumn(DESCRIPTION);
		addNotDeleteColumn(SIZE);
	}
	
	/**
	 * メッセージ数取得.
	 * @return メッセージ数
	 */
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
	
	/**
	 * 言語数取得.
	 * @return 言語数
	 */
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
	
	/**
	 * ラベルの列番号取得.
	 * @return　ラベルの列番号
	 */
	public int getColumnLabelIndex()
	{
		return getColumnIndex(LABEL);
	}
	
	/**
	 * 説明の列番号取得.
	 * @return 説明の列番号
	 */
	public int getColumnDescriptionIndex()
	{
		return getColumnIndex(DESCRIPTION);	
	}
	
	/**
	 * サイズの列番号取得.
	 * @return　サイズの列番号
	 */
	public int getColumnSizeIndex()
	{
		return getColumnIndex(SIZE);	
	}
}
