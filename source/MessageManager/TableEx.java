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
import javax.swing.table.DefaultTableColumnModel;

public class TableEx implements MouseListener, ActionListener
{
	private Stack<Stack<String>> row;
	private Stack<String> columnName;
	
	private String POPUP_ADD_ROW = "add row";
	private String POPUP_INSERT_ROW = "insert row";
	private String POPUP_REMOVE_ROW = "remove row";
	private String POPUP_ADD_COLUMN = "add column";
	private String POPUP_REMOVE_COLUMN = "remove column";
	private String POPUP_NUM_1 = "1";
	private String POPUP_NUM_10 = "10";
	private String POPUP_NUM_100 = "100";
	
	private JPanel				panel;
	private JScrollPane			scrollPane;
	private JTable				table;
	private DefaultTableModel	tableModel;
	private JPopupMenu			popup;
	private JPopupMenu			popupNum;
	private JMenu				addItem;
	private JMenu				insertItem;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public TableEx(String[] columns, int width, int height)
	{
		this.row = new Stack<Stack<String>>();
		this.columnName = new Stack<String>();
		for(String str : columns)
		{
			this.columnName.push(str);
		}
		
		Dimension preferredMesTable = new Dimension(width, height);
		this.tableModel = new DefaultTableModel(this.row, this.columnName);
		this.tableModel.addRow(new Stack<String>());
		this.table = new JTable(this.tableModel);
		//this.table.setPreferredSize(preferredMesTable));
		this.table.setSize(preferredMesTable);
		this.table.addMouseListener(this);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.setPreferredSize(preferredMesTable);
		
		this.panel = new JPanel();
		this.panel.add(this.scrollPane);
		//this.panel.setPreferredSize(preferredMesTable);
		
		this.addItem = new JMenu(POPUP_ADD_ROW);
		this.insertItem = new JMenu(POPUP_INSERT_ROW);
		JMenuItem removeItem = new JMenuItem(POPUP_REMOVE_ROW);
		JMenuItem addColumn = new JMenuItem(POPUP_ADD_COLUMN);
		JMenuItem removeColumn = new JMenuItem(POPUP_REMOVE_COLUMN);
		this.addItem.addActionListener(this);
		this.insertItem.addActionListener(this);
		removeItem.addActionListener(this);
		addColumn.addActionListener(this);
		removeColumn.addActionListener(this);
		this.popup = new JPopupMenu();
		this.popup.add(this.addItem);
		this.popup.add(this.insertItem);
		this.popup.add(removeItem);
		this.popup.add(addColumn);
		this.popup.add(removeColumn);
		
		// add,intert popup menu
		{
			JMenuItem Item1 = new JMenuItem(POPUP_NUM_1);
			JMenuItem Item10 = new JMenuItem(POPUP_NUM_10);
			JMenuItem Item100 = new JMenuItem(POPUP_NUM_100);
			Item1.addActionListener(this);
			Item10.addActionListener(this);
			Item100.addActionListener(this);
			this.addItem.add(Item1);
			this.addItem.add(Item10);
			this.addItem.add(Item100);
		}
		{
			JMenuItem Item1 = new JMenuItem(POPUP_NUM_1);
			JMenuItem Item10 = new JMenuItem(POPUP_NUM_10);
			JMenuItem Item100 = new JMenuItem(POPUP_NUM_100);
			Item1.addActionListener(this);
			Item10.addActionListener(this);
			Item100.addActionListener(this);
			this.insertItem.add(Item1);
			this.insertItem.add(Item10);
			this.insertItem.add(Item100);
		}
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
	
	public int getColumnIndex(String column)
	{
		return this.columnName.search(column);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	Mouse event
	/*---------------------------------------------------------------------*/
	public void mousePressed(MouseEvent e)
	{
		if(e.isPopupTrigger())
		{
			this.popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if(e.isPopupTrigger())
		{
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
		// add row
		if(e.getActionCommand().equals(POPUP_ADD_ROW))
		{
		}
		// intert row
		else if(e.getActionCommand().equals(POPUP_INSERT_ROW))
		{
		}
		// remove row
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
		// add column
		else if(e.getActionCommand().equals(POPUP_ADD_COLUMN))
		{
			JOptionPane pane = new JOptionPane("add column name", JOptionPane.YES_OPTION);
			String input = pane.showInputDialog("add column name");
			if(input.length() > 0)
			{
				this.tableModel.addColumn(input);
			}
		}
		// remove column
		else if(e.getActionCommand().equals(POPUP_REMOVE_COLUMN))
		{
			JOptionPane pane = new JOptionPane("remove column name", JOptionPane.YES_OPTION);
			String input = pane.showInputDialog("remove column name");
			DefaultTableColumnModel model = (DefaultTableColumnModel)this.table.getColumnModel();
			try
			{
				model.removeColumn(model.getColumn(model.getColumnIndex(input)));
			}
			catch(IllegalArgumentException exce)
			{
				System.out.println("not found column");
			}
		}
		// add, insert 1
		else if(e.getActionCommand().equals(POPUP_NUM_1) || e.getActionCommand().equals(POPUP_NUM_10) || e.getActionCommand().equals(POPUP_NUM_100))
		{
			System.out.println(String.format("POPUP MENU ADD : %d", Integer.valueOf(e.getActionCommand()).intValue()));
			if(addItem.isSelected())
			{	
				System.out.println("Add Row");
				this.addRow(Integer.valueOf(e.getActionCommand()).intValue());
			}
			else if(insertItem.isSelected())
			{
				System.out.println("Insert Row");
				this.insertRow(Integer.valueOf(e.getActionCommand()).intValue());
			}
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	resize
	/*---------------------------------------------------------------------*/
	public void resize(Dimension dimension)
	{
		System.out.println("== Resize ==");
		Dimension tabelDimension = new Dimension(dimension.width - 32, dimension.height - 32);
		
//		this.table.setPreferredSize(tabelDimension);
		this.scrollPane.setPreferredSize(tabelDimension);
//		this.panel.setPreferredSize(dimension);
		
		this.table.setSize(tabelDimension);
		this.scrollPane.setSize(tabelDimension);
		this.panel.setSize(dimension);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	add row
	/*---------------------------------------------------------------------*/
	public void addRow(int num)
	{
		for(int i = 0; i < num; ++i)
		{
			int rc = this.tableModel.getRowCount();
			this.tableModel.addRow(new Stack<String>());
			this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	insert row
	/*---------------------------------------------------------------------*/
	public void insertRow(int num)
{
		int selectedRow = this.table.getSelectedRow();
		if(selectedRow != -1)
		{
			for(int i = 0; i < num; ++i)
			{
				int rc = this.tableModel.getRowCount();
				this.tableModel.insertRow(selectedRow, new Stack<String>());
				this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
			}
		}
		else
		{
			System.out.println("not select row");
		}
	}
}
