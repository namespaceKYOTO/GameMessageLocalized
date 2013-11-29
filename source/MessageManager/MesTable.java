/*---------------------------------------------------------------------*/
/*!
 * @brief	Message Table
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

public class MesTable implements MouseListener, ActionListener
{
	// ISO 3166-1
	private String[] country = {"Label", "JPN", "USA", "GBR", "DEU", "FRA", "ITA", "ESP"};
	private Stack<Stack<String>> row;
	private Stack<String> columnName;
	
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private JPopupMenu popup;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public MesTable(int width, int height)
	{
		this.row = new Stack<Stack<String>>();
		this.columnName = new Stack<String>();
		for(String str : this.country)
		{
			this.columnName.push(str);
		}
		
		Dimension preferredMesTable = new Dimension(width, height);
		this.tableModel = new DefaultTableModel(this.row, this.columnName);
		this.table = new JTable(this.tableModel);
		this.table.setPreferredSize(preferredMesTable);
		this.table.addMouseListener(this);
		
		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.setPreferredSize(preferredMesTable);
		
		this.panel = new JPanel();
		this.panel.add(this.scrollPane);
		this.panel.setPreferredSize(preferredMesTable);
		
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
}
