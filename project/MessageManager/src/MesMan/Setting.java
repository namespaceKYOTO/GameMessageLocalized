package MesMan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * 設定.
 * @author t_sato
 *
 */
public class Setting {
	
	private String fileName;
	private String defaultDirectory;
	private Integer defaultLanguage;
	LinkedList<String> buffer;
	
	private String DefaultDirectoryLabel = "\"defaultDirectory\":";
	private String DefaultLanguageLabel = "\"defaultLanguage\":";
	private String DefaultDirectory = "C:\\";
	private int Defaultlanguage = MesTableDefine.Language_ENG;
	
	private static int directoryIdx = 0;
	private static int languageIdx = 1;

	/**
	 * コンストラクタ.
	 * @param settingFile 設定ファイル
	 */
	public Setting(String settingFile)
	{
		buffer = new LinkedList<String>();
		fileName = null;
		defaultDirectory = new String(DefaultDirectory);
		defaultLanguage = Defaultlanguage;
		try
		{
			fileName = settingFile;
			File file = new File(fileName);
			if(!file.exists())
			{
				buffer.add(DefaultDirectoryLabel + DefaultDirectory);
				buffer.add(DefaultLanguageLabel + String.format("%d", Defaultlanguage));
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			// ファイルから情報取得
			String line = null;
			while((line = br.readLine()) != null) {
				buffer.add(line);
			}
			br.close();
			
			defaultDirectory = buffer.get(directoryIdx).substring(DefaultDirectoryLabel.length());
			defaultLanguage = Integer.valueOf(buffer.get(languageIdx).substring(DefaultLanguageLabel.length()));
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			reset();
		}
	}
	
	/**
	 * デフォルト言語取得.
	 * @return 言語番号
	 */
	public Integer getDefaultLanguage() {
		return defaultLanguage;
	}

	/**
	 * デフォルト言語設定.
	 * @param defaultLanguage 言語番号
	 */
	public void setDefaultLanguage(int defaultLanguage)
	{
		buffer.remove(languageIdx);
		buffer.add(languageIdx, DefaultLanguageLabel + String.valueOf(defaultLanguage));
		this.defaultLanguage = new Integer(defaultLanguage);
	}
	
	/**
	 * デフォルトディレクト取得.
	 * @return
	 */
	public String getDefaultDirectory()
	{
		return defaultDirectory;
	}
	/**
	 * デフォルトディレクトリ設定.
	 * @param defaultDirectory ディレクトリ
	 */
	public void setDefaultDirectory(String defaultDirectory)
	{
		buffer.remove(directoryIdx);
		buffer.add(directoryIdx, DefaultDirectoryLabel + defaultDirectory);
		this.defaultDirectory = defaultDirectory;
	}
	
	/**
	 * 設定のリセット.
	 */
	public void reset()
	{
		defaultDirectory = new String(DefaultDirectory);
		defaultLanguage = MesTableDefine.Language_ENG;
		
		buffer.clear();
		buffer.add(directoryIdx, DefaultDirectoryLabel + defaultDirectory);
		buffer.add(languageIdx, DefaultLanguageLabel + String.valueOf(defaultLanguage));
	}
	
	/**
	 * 設定の保存.
	 */
	public void save()
	{
		try
		{
			File file = new File(fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			PrintWriter pw = new PrintWriter(file);
			
			for (String str : this.buffer) {
				pw.write(str);
				pw.write("\r\n");
				pw.flush();
			}
			pw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
	}
}
