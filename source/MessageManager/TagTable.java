/*---------------------------------------------------------------------*/
/*!
 * @brief	Tab Table
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TagTable implements MouseListener, ActionListener
{
	private String[] tag = {"Tag Name", "Description"};
	private Stack<Stack<String>> tagRow;
	private Stack<String> tagColumnName;
	
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private JPopupMenu popup;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public TagTable(int width, int height)
	{
		this.tagRow = new Stack<Stack<String>>();
		this.tagColumnName = new Stack<String>();
		for(String str : tag)
		{
			this.tagColumnName.push(str);
		}
		
		Dimension preferredTagTable = new Dimension(width, height);
		this.tableModel = new DefaultTableModel(this.tagRow, this.tagColumnName);
		this.table = new JTable(this.tableModel);
		this.table.setPreferredSize(preferredTagTable);
		this.table.addMouseListener(this);
		
		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.setPreferredSize(preferredTagTable);
		
		this.panel = new JPanel();
		this.panel.add(this.scrollPane);
		this.panel.setPreferredSize(preferredTagTable);
		
		JMenuItem addItem = new JMenuItem("add row");
		JMenuItem deleteItem = new JMenuItem("delete row");
		addItem.addActionListener(this);
		this.popup = new JPopupMenu();
		this.popup.add(addItem);
		this.popup.add(deleteItem);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	getter
	/*---------------------------------------------------------------------*/
	public JPanel getPanel()
	{
		return this.panel;
	}
	
	public Stack<Stack<String>> getRow()
	{
		return this.tagRow;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	Mouse event
	/*---------------------------------------------------------------------*/
	public void mousePressed(MouseEvent e)
	{
		System.out.println("mousePressed");
		if(e.isPopupTrigger())
		{
			System.out.println("isPopupTrigger");
			this.popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		System.out.println("mousePressed");
		if(e.isPopupTrigger())
		{
			System.out.println("isPopupTrigger");
			this.popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	/*---------------------------------------------------------------------*/
	//*!brief	action event
	/*---------------------------------------------------------------------*/
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("actionPerformed");
		int rc = this.tableModel.getRowCount();
		this.tableModel.addRow(new Stack<String>());
		
		this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get tag code
	/*---------------------------------------------------------------------*/
	public Byte[] getTagCode(int index, String charset)
	{
		ArrayList<Byte> stack = new ArrayList<Byte>();
		
		Byte[] ret = new Byte[stack.size()];
		stack.toArray(ret);
		return ret;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	resize
	/*---------------------------------------------------------------------*/
	public void resize(Dimension dimension)
	{
		Dimension tabelDimension = new Dimension(dimension.width - 32, dimension.height - 32);
		
		this.table.setPreferredSize(tabelDimension);
		this.scrollPane.setPreferredSize(tabelDimension);
		this.panel.setPreferredSize(dimension);
		
		this.table.setSize(tabelDimension);
		this.scrollPane.setSize(tabelDimension);
		this.panel.setSize(dimension);
	}
}
