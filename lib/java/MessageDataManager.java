package MesMan;

import java.io.*;

/**
 * メッセージデータ管理クラス.
 * @author t_sato
 *
 */
public class MessageDataManager
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
	 * 言語取得.
	 * @return 言語番号
	 */
	public int getLanguageNo() {
		return languageNo;
	}

	/**
	 * 言語設定.
	 * @param languageNo 言語番号
	 */
	public void setLanguageNo(int languageNo) {
		this.languageNo = languageNo;
	}

	/**
	 * コンストラクタ.
	 * @param binFileName メッセージバイナリのファイル名
	 */
	public MessageDataManager(String binFileName)
	{
		this.Open(binFileName);
	}
	
	/**
	 * メッセージバイナリのオープン.
	 * @param binFileName メッセージバイナリのファイル名
	 */
	public void Open(String binFileName)
	{
		try
		{
			InputStream fileStream = this.getClass().getResourceAsStream(binFileName);
			this.readData = new byte[(int)fileStream.available()];
			System.out.println(String.format("read byte %d", this.readData.length));
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
			this.getMessageOffset( MesOffsetOffset, this.messageNum, this.languageNum);
			
			this.mesDataIdx = MesOffsetOffset + messageOffset.length * 4;
		}
		catch(IOException e)
		{
			System.out.println("File Open Failed : " + e.getMessage());
		}
	}
	
	/**
	 * 4Byteデータ取得.
	 * @param offset 取得開始位置
	 * @return 4Byteデータ
	 */
	private int getData(int offset)
	{
		if(0 <= offset && offset + 4 < this.readData.length)
		{
			int ret =	((int)this.readData[offset + 0])<<24 | 
						((int)this.readData[offset + 1])<<16 | 
						((int)this.readData[offset + 2])<<8 |
						((int)this.readData[offset + 3]);
//			System.out.println(String.format("Get Byte : %x %x %x %x",this.readData[offset + 0],this.readData[offset + 1],this.readData[offset + 2],this.readData[offset + 3]));
//			System.out.println(String.format("         : %x", ret));
			
			return ret;
		}
		
		return 0;
	}
	
	/**
	 * 文字列データ取得.
	 * @param offset 取得開始位置
	 * @param length 文字列サイズ
	 * @return 文字列
	 */
	private String getData(int offset, int length)
	{
		return new String(this.readData, offset, length);
	}
	
	/**
	 * メッセージの取得Offset値取得.
	 * @param offset 取得開始位置
	 * @param messageNum メッセージ数
	 * @param languageNum 言語数
	 */
	private void getMessageOffset(int offset, int messageNum, int languageNum)
	{
		this.messageOffset = new int[messageNum*languageNum];
		for(int i = 0; i < (messageNum*languageNum); ++i) {
			int dataIdx = offset + (i * 4);
			int byte1 = (((int)this.readData[dataIdx + 0])<<24) & 0xFF000000;
			int byte2 = (((int)this.readData[dataIdx + 1])<<16) & 0x00FF0000;
			int byte3 = (((int)this.readData[dataIdx + 2])<< 8) & 0x0000FF00;
			int byte4 = (((int)this.readData[dataIdx + 3])    ) & 0x000000FF;
			this.messageOffset[i] = byte1 + byte2 + byte3 + byte4;
//			System.out.println(String.format("%d : 0x%04x : 0x%02x 0x%02x 0x%02x 0x%02x", i, this.messageOffset[i], 
//					this.readData[dataIdx + 0],
//					this.readData[dataIdx + 1],
//					this.readData[dataIdx + 2],
//					this.readData[dataIdx + 3]));
		}
	}
	
	/**
	 * メッセージ取得.
	 * @param mesNo 取得するメッセージ番号
	 * @return　メッセージ
	 */
	public String getMessage(int mesNo)
	{
		String ret = null;
		try
		{
			int idx = (mesNo * languageNum) + languageNo;
			int mesLength = this.messageOffset[idx + 1] - this.messageOffset[idx];
			int offset = this.mesDataIdx + this.messageOffset[idx]; 
//			System.out.println(String.format("Message Offset %x", offset));
			ret = new String(this.readData, offset, mesLength, this.charset);
		}
		catch(UnsupportedEncodingException e)
		{
		}
		return ret;
	}
}
