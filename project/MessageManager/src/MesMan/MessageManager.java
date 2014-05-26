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
	private static int SignatureOffset = 0;
	private static int DateOffset = 4;
	private static int MesNumOffset = 8;
	private static int LangNumOffset = 12;
	private static int CharsetOffset = 16;
	private static int MesOffsetOffset = 20;
	
	private static int UTF8 = 0;
	private static int UTF16BE = 1;
	private static int UTF16LE = 2;
	
	private int messageNum;
	private int languageNum;
	private String charset;
	private byte[] readData;
	private int[] messageOffset;
	private int mesDataIdx;
	private int languageNo;
	
	/**
	 * @param mtblFileName
	 */
	public MessageManager(String mtblFileName)
	{
		this.Open(mtblFileName);
	}
	
	/**
	 * @param mtblFileName
	 */
	public void Open(String mtblFileName)
	{
		try
		{
			File file = new File(mtblFileName);
			FileInputStream fileStream = new FileInputStream(file);
			int readBytes = fileStream.read(this.readData);
			
			// Singnature
			String text = this.getData(SignatureOffset, 4);
			if(!text.equals("TEXT"))
			{
				System.out.println("File Open Failed : Signature is different");
			}

			// creation date  YYYY MM DD (2Byte 1Byte 1Byte)
			// DateOffset
			
			// Message Num
			this.messageNum = this.getData(MesNumOffset);
			
			// Language Num
			this.languageNum = this.getData(LangNumOffset);
			
			// charsets
			int charSetNo = this.getData(CharsetOffset);
			if( charSetNo == UTF8 ) 	{ this.charset = new String("UTF-8"); }
			if( charSetNo == UTF16BE)	{ this.charset = new String("UTF-16BE"); }
			if( charSetNo == UTF16LE)	{ this.charset = new String("UTF-16LE"); }

			// Message Offset (Message Num * Language Num * 4Byte)
			this.setMessageOffset( MesOffsetOffset, this.messageNum, this.languageNum);
			
//			this.mesDataIdx = 
		}
		catch(IOException e)
		{
			System.out.println("File Open Failed : " + e.getMessage());
		}
	}
	
	/**
	 * @param offset
	 * @return
	 */
	private int getData(int offset)
	{
		if(0 <= offset && offset + 4 < this.readData.length)
		{
			int ret =	((int)this.readData[offset + 0])<<24 | 
						((int)this.readData[offset + 1])<<16 | 
						((int)this.readData[offset + 2])<<8 |
						((int)this.readData[offset + 3]);
			System.out.println(String.format("Get Byte : %x %x %x %x",this.readData[offset + 0],this.readData[offset + 1],this.readData[offset + 1],this.readData[offset + 2]));
			System.out.println(String.format("         : %x", ret));
			
			return ret;
		}
		
		return 0;
	}
	
	/**
	 * @param offset
	 * @param length
	 * @return
	 */
	private String getData(int offset, int length)
	{
		return new String(this.readData, offset, length);
	}
	
	/**
	 * @param offset
	 * @param messageNum
	 * @param languageNum
	 */
	private void setMessageOffset(int offset, int messageNum, int languageNum)
	{
		this.messageOffset = new int[messageNum*languageNum];
		for(int i = 0; i < (messageNum*languageNum); ++i) {
			int dataIdx = offset + (i *4);
			this.messageOffset[i] = ((int)this.readData[dataIdx + 0])<<24 | 
									((int)this.readData[dataIdx + 1])<<16 | 
									((int)this.readData[dataIdx + 2])<< 8 |
									((int)this.readData[dataIdx + 3]);
		}
	}
	
	public String getMessage(int mesNo)
	{
		String ret = null;
		try
		{
			int idx = mesNo * languageNum + languageNo;
			int mesLength = this.messageOffset[idx + 1] - this.messageOffset[idx];  
			ret = new String(this.readData, idx, mesLength, this.charset);
		}
		catch(UnsupportedEncodingException e)
		{
		}
		return ret;
	}
}
