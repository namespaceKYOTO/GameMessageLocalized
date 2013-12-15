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
	private String[] country = {"Label", "Description", "JPN", "ENG", "DEU", "FRA", "ITA", "SPA"};
	private Stack<Stack<String>> row;
	private Stack<String> columnName;
	
	private String POPUP_ADD_ROW = "add row";
	private String POPUP_INSERT_ROW = "insert row";
	private String POPUP_REMOVE_ROW = "remove row";
	
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
		
		JMenuItem addItem = new JMenuItem(POPUP_ADD_ROW);
		JMenuItem insertItem = new JMenuItem(POPUP_INSERT_ROW);
		JMenuItem removeItem = new JMenuItem(POPUP_REMOVE_ROW);
		addItem.addActionListener(this);
		insertItem.addActionListener(this);
		removeItem.addActionListener(this);
		this.popup = new JPopupMenu();
		this.popup.add(addItem);
		this.popup.add(insertItem);
		this.popup.add(removeItem);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	getter
	/*---------------------------------------------------------------------*/
	public JPanel getPanel()
	{
		return this.panel;
	}
	
	public JTable getTable()
	{
		return this.table;
	}
	
	public DefaultTableModel getTableModel()
	{
		return this.tableModel;
	}
	
	public Stack<String> getColumnName()
	{
		return this.columnName;
	}
	
	public Stack<Stack<String>> getRow()
	{
		return this.row;
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
		if(e.getActionCommand().equals(POPUP_ADD_ROW))
		{
			int rc = this.tableModel.getRowCount();
			this.tableModel.addRow(new Stack<String>());
			
			this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
		}
		else if(e.getActionCommand().equals(POPUP_INSERT_ROW))
		{
			int selectedRow = this.table.getSelectedRow();
			if(selectedRow != -1)
			{
				System.out.println("insert Row");
				int rc = this.tableModel.getRowCount();
				this.tableModel.insertRow(selectedRow, new Stack<String>());
				this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
			}
			else
			{
				System.out.println("not select row");
			}
		}
		else if(e.getActionCommand().equals(POPUP_REMOVE_ROW))
		{
			int selectedRow = this.table.getSelectedRow();
			if(selectedRow != -1)
			{
				this.tableModel.removeRow(selectedRow);
				int rc = this.tableModel.getRowCount() - 1;
				this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
			}
		}
	}
}
