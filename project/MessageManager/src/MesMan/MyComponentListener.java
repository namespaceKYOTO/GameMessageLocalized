package MesMan;

import java.awt.event.*;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Stack;

/**
 * UIリサイズ時用のリスナー.
 * @author t_sato
 *
 */
public class MyComponentListener implements ComponentListener
{
	private Container contener;
	Stack<TableEx> tables;
	
	/**
	 * コンストラクタ.
	 * @param base 通知時のサイズの元となるコンテナ
	 * @param tables リサイズを通知するテーブルs
	 */
	public MyComponentListener(Container base, Stack<TableEx> tables)
	{
		this.contener = base;
		this.tables = tables;
	}
	
	public void componentResized(ComponentEvent e)
	{
		Dimension dimention = contener.getSize();
		for (TableEx table : tables) {
			table.resize(new Dimension(dimention.width, dimention.height));
		}
	}
	
	public void componentMoved(ComponentEvent e){};
	public void componentShown(ComponentEvent e){};
	public void componentHidden(ComponentEvent e){};
}
