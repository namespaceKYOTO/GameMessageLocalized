package MesMan;

public class ResultTable extends TableEx
{
	private String LABEL = "Label";
	private String OVER = "Over";

	public ResultTable(MessageManager messageMan, int width, int height)
	{
		super(messageMan, width, height);
		
		String[] colum = {LABEL, OVER};
		this.addColumnNames(colum);
		
		this.addNotDeleteColumn(LABEL);
		this.addNotDeleteColumn(OVER);
	}

}
