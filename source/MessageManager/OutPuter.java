/*---------------------------------------------------------------------*/
/*!
 * @brief	file output
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.io.*;
import java.util.*;

public class OutPuter
{
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public OutPuter()
	{
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	file output
	/*---------------------------------------------------------------------*/
	public void outPut(File file, TagTable tagTable, MesTable mesTable, int outFileFlag, int charaCodeFlag)
	{
		String outPutFileName = getOutputFileName(file);
		
		// .bin
		if( (outFileFlag & CheckParamPanel.OUT_FILE_BIN) != 0x00 )
		{
			String outPutBinFileName = outPutFileName  + ".bin";
			
			//if()
			{
				OutputBinary(outPutBinFileName, tagTable, mesTable, "UTF8");
			}
		}
		
		// .c
		if( (outFileFlag & CheckParamPanel.OUT_FILE_C) != 0x00 )
		{
			String outPutCFileName = outPutFileName  + ".c";
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get Output File Name
	/*---------------------------------------------------------------------*/
	private String getOutputFileName(File file)
	{
		System.out.println(file.getName());
		String ret = file.getName();
		
		// splits directory
		String[] split = ret.split("/");
		ret = split[split.length - 1];
		
		// splits suffix
		if(ret.endsWith(".bin"))
		{
			ret = ret.substring(0,ret.lastIndexOf(".bin"));
		}
		// splits suffix
		else if(ret.endsWith(".c"))
		{
			ret = ret.substring(0,ret.lastIndexOf(".c"));
		}
		
		ret = ret.replace(".","_");
		return ret;
	}
	
	private void OutputBinary(String name, TagTable tagTable, MesTable mesTable, String charset)
	{
		try
		{
			File file = new File(name);
			file.createNewFile();
		}
		catch(IOException e)
		{
		}
	}
}
