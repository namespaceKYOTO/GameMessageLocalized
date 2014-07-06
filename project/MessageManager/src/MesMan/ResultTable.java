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
	 * @param mesman メッセージデータ管理
	 * @param width UI幅
	 * @param height UI高さ
	 */
	public ResultTable(MesMan mesman, int width, int height)
	{
		super(mesman, width, height);
		
		String[] colum = {LABEL, OVER};
		this.addColumnNames(colum);
		
		this.addNotDeleteColumn(LABEL);
		this.addNotDeleteColumn(OVER);
	}

}
