package MesMan;

/**
 * 結果テーブル.
 * @author t_sato
 *
 */
public class ResultTable extends TableEx
{
	private String LABEL = "Label";
	private String OVER = "Over";

	/**
	 * コンストラクタ.
	 * @param messageMan メッセージテーブル
	 * @param width UI幅
	 * @param height UI高さ
	 */
	public ResultTable(MessageManager messageMan, int width, int height)
	{
		super(messageMan, width, height);
		
		String[] colum = {LABEL, OVER};
		this.addColumnNames(colum);
		
		this.addNotDeleteColumn(LABEL);
		this.addNotDeleteColumn(OVER);
	}

}
