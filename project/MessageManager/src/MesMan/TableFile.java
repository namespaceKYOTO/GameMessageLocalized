/*---------------------------------------------------------------------*/
/*!
 * @brief	Table File Base
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.io.*;
import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableFile
{
	static private String DELIMITER = "\t";
	static private String NEWLINE = "\r\n";
	
	private String suffix;
	private String charset;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public TableFile(String suffix, String charset)
	{
		this.suffix = suffix;
		this.charset = charset;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	Table File Open
	/*---------------------------------------------------------------------*/
	public void open(File file, TableEx tableEx)
	{
		JTable table = tableEx.getTable();
		DefaultTableModel tableModel = tableEx.getTableModel();
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
		
		try
		{
			FileInputStream fileStrem = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileStrem, this.charset));
			
			String line;
			
			// Column comment
			line = bufferedReader.readLine();
			// Column
			line = bufferedReader.readLine();
			String[] column = line.split(DELIMITER);
			for(String val : column)
			{
				tableModel.addColumn(val);
			}
			
			// Row comment
			line = bufferedReader.readLine();
			// Row
			while((line = bufferedReader.readLine()) != null)
			{
				String[] rowData = line.split(DELIMITER);
				Stack<String>row = new Stack<String>();
				for(String val : rowData)
				{
					row.push(val);
				}
				tableModel.addRow(row);
			}
			
			// redisplay
			int rc = tableModel.getRowCount();
			table.scrollRectToVisible(table.getCellRect(rc, 0, false));
			
			bufferedReader.close();
		}
		catch(IOException e)
		{
			System.out.println("Failed File Open : " + e.getMessage());
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	Table File Save
	/*---------------------------------------------------------------------*/
	public void save(File file, TableEx table)
	{
		try
		{
			if(file.getName().endsWith(this.suffix) == false)
			{
				File newFile = new File(file.getParent(), file.getName() + this.suffix);
				file = newFile;
			}
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file, this.charset);
			
			// Column Comment
			pw.println("# Column Data");
			
			// Column
			for(int i = 0; i < table.getColumnName().size(); ++i)
			{
				pw.print(table.getColumnName().get(i));
				if(i + 1 < table.getColumnName().size())
				{
					pw.print(DELIMITER);
				}
			}
			pw.print(NEWLINE);
			
			// Row Comment
			pw.println("# Row Data");
			
			// Row
			Stack<Stack<String>> rowData = table.getRow();
			for(int row = 0; row < rowData.size(); ++row)
			{
				Stack<String> columnData = rowData.get(row);
				for(int column = 0; column < columnData.size(); ++column)
				{
					pw.print(columnData.get(column));
					if(column + 1 < columnData.size())
					{
						pw.print(DELIMITER);
					}
				}
				pw.print(NEWLINE);
			}
			
			// finish
			pw.flush();
			pw.close();
		}
		catch(IOException e)
		{
			System.out.println("Failed File Save : " + e.getMessage());
		}
	}
}
