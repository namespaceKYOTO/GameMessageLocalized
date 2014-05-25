/*---------------------------------------------------------------------*/
/*!
 * @brief	Message Manager
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.io.*;

public class MessageManager
{
	private int messageNum;
	private int languageNum;
	private String charset;
	private byte[] mesData;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public MessageManager(String mtblFileName)
	{
		this.Open(mtblFileName);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	mtbl File Open
	/*---------------------------------------------------------------------*/
	public void Open(String mtblFileName)
	{
		try
		{
			File file = new File(mtblFileName);
			FileInputStream fileStream = new FileInputStream(file);
			
			// Singnature
			String text = getData(0, 4);
			if(!text.equals("TEXT"))
			{
				System.out.println("File Open Failed : Signature is different");
			}
			
			// 
			int readBytes = fileStream.read(this.mesData);
			
			// 
			this.messageNum = this.getData(8);
			
			// 
			this.languageNum = this.getData(12);
			
			//
			this.charset = getData(16, 8);
		}
		catch(IOException e)
		{
			System.out.println("File Open Failed : " + e.getMessage());
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get read data
	/*---------------------------------------------------------------------*/
	private int getData(int offset)
	{
		if(0 <= offset && offset + 4 < this.mesData.length)
		{
			int ret =	((int)this.mesData[offset + 0])<<24 | 
						((int)this.mesData[offset + 1])<<16 | 
						((int)this.mesData[offset + 2])<<8 |
						((int)this.mesData[offset + 3]);
			System.out.println(String.format("Get Byte : %x %x %x %x",this.mesData[offset + 0],this.mesData[offset + 1],this.mesData[offset + 1],this.mesData[offset + 2]));
			System.out.println(String.format("         : %x", ret));
			
			return ret;
		}
		
		return 0;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get read data
	/*---------------------------------------------------------------------*/
	private String getData(int offset, int length)
	{
		return new String(this.mesData, offset, length);
	}
}
