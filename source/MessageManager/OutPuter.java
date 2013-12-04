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
	public void outPut(File file, Stack<Stack<String>> tagTable, Stack<Stack<String>> mesTable)
	{
		String outPutFileName = getOutputFileName(file);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get Output File Name
	/*---------------------------------------------------------------------*/
	public String getOutputFileName(File file)
	{
		System.out.println(file.getName());
		String ret = file.getName();
		String[] split = ret.split("/");
		ret = split[split.length - 1];
		System.out.println(ret);
		ret = ret.replace(".","_");
		System.out.println(ret);
		return ret;
	}
}
