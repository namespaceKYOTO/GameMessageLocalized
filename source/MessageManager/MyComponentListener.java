/*---------------------------------------------------------------------*/
/*!
 * @brief	My ComponentListener
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Rectangle;

public class MyComponentListener implements ComponentListener
{
	private MesTable mesTable;
	private TagTable tagTable;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public MyComponentListener(MesTable mesTable, TagTable tagTable)
	{
		this.mesTable = mesTable;
		this.tagTable = tagTable;
	}
	
	public void componentResized(ComponentEvent e)
	{
		Rectangle bound = e.getComponent().getBounds();
		this.mesTable.resize(new Dimension((bound.width / 3) * 2, bound.height - 40));
		this.tagTable.resize(new Dimension((bound.width / 3) * 1, bound.height - 40));
	}
	
	public void componentMoved(ComponentEvent e){};
	public void componentShown(ComponentEvent e){};
	public void componentHidden(ComponentEvent e){};
}
