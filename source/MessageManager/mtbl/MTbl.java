/*---------------------------------------------------------------------*/
/*!
 * @brief	.mtbl File
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.io.*;
import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MTbl
{
	static private String MTBL = ".mtbl";
	static private String CHARACTOR_CODE = "UTF-16";
	static private String DELIMITER = "\t";
	static private String NEWLINE = "\r\n";
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public MTbl()
	{
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	.mtbl File Open
	/*---------------------------------------------------------------------*/
	public void open(File file, MesTable mesTable)
	{
		JTable table = mesTable.getTable();
		DefaultTableModel tableModel = mesTable.getTableModel();
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
		
		try
		{
			FileInputStream fileStrem = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileStrem, CHARACTOR_CODE));
			
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
		}
		catch(IOException e)
		{
			System.out.println("Failed File Open : " + e.getMessage());
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	.mtbl File Save
	/*---------------------------------------------------------------------*/
	public void save(File file, MesTable table)
	{
		try
		{
			if(file.getName().endsWith(MTBL) == false)
			{
				File newFile = new File(file.getParent(), file.getName() + MTBL);
				file = newFile;
			}
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file, CHARACTOR_CODE);
			
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
