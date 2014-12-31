package MesMan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * コンフィグファイル関係.
 * @author t_sato
 *
 */
public class Config
{
	private static String DefaultDirectoryLabel = "\"defaultDirectory\":";
	public static String DefaultDirectory = "C:\\";
	
	private static String DefaultLanguageLabel = "\"defaultLanguage\":";
	public static int DefaultLanguage = MesTableDefine.Language_ENG;
	
	private static String DefaultCharactorSizeLabel = "\"defaultCharactorSize\":";
	public static int DefaultCharactorSize = 0;
	
	private static int directoryIdx = 0;
	private static int languageIdx = 1;
	private static int charactorsizeIdx = 2;
	
	private LinkedList<String> buffer;
	private String fileName;
	private String defaultDirectory;
	private Integer defaultLanguage;
	private Integer defaultCharactorSize;
	
	/**
	 * コンストラクタ.
	 * @param configFile コンフィグファイル名
	 */
	public Config(String configFile)
	{
		this.fileName = configFile;
		buffer = new LinkedList<String>();
//		reset();
		
		File file = new File(configFile);
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			int count = 0;
			while((line = br.readLine()) != null) {
				buffer.add(count, line);
				++count;
			}
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			reset();
		}
		finally
		{
			defaultDirectory = buffer.get(directoryIdx).substring(DefaultDirectoryLabel.length());
			defaultLanguage = Integer.valueOf(buffer.get(languageIdx).substring(DefaultLanguageLabel.length()));
			defaultCharactorSize = Integer.valueOf(buffer.get(charactorsizeIdx).substring(DefaultCharactorSizeLabel.length()));
		}
	}
	
	/**
	 * 設定のリセット.
	 */
	public void reset()
	{
		buffer.clear();
		buffer.add(directoryIdx, DefaultDirectoryLabel + DefaultDirectory);
		buffer.add(languageIdx, DefaultLanguageLabel + String.valueOf(DefaultLanguage));
		buffer.add(charactorsizeIdx, DefaultCharactorSizeLabel + String.valueOf(DefaultCharactorSize));
	}
	
	/**
	 * 現在の設定を保存. 
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

	/**
	 * デフォルトディレクトリの取得.
	 * @return デフォルトディレクトリ
	 */
	public String getDefaultDirectory() {
		return defaultDirectory;
	}

	/**
	 * デフォルトディレクトリの設定.
	 * @param defaultDirectory デフォルトディレクトリ
	 */
	public void setDefaultDirectory(String defaultDirectory) {
		this.defaultDirectory = defaultDirectory;
		buffer.remove(directoryIdx);
		buffer.add(directoryIdx, DefaultDirectoryLabel + defaultDirectory);
	}

	/**
	 * デフォルト言語の取得.
	 * @return デフォルト言語
	 */
	public Integer getDefaultLanguage() {
		return defaultLanguage;
	}

	/**
	 * デフォルト言語の設定.
	 * @param defaultLanguage デフォルト言語
	 */
	public void setDefaultLanguage(Integer defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
		buffer.remove(languageIdx);
		buffer.add(languageIdx, DefaultLanguageLabel + String.valueOf(defaultLanguage));
	}

	/**
	 * デフォルト文字サイズの取得.
	 * @return デフォルト文字サイズ
	 */
	public Integer getDefaultCharactorSize() {
		return defaultCharactorSize;
	}

	/**
	 * デフォルト文字サイズの設定.
	 * @param defaultCharactorSize デフォルト文字サイズ
	 */
	public void setDefaultCharactorSize(Integer defaultCharactorSize) {
		this.defaultCharactorSize = defaultCharactorSize;
		buffer.remove(charactorsizeIdx);
		buffer.add(charactorsizeIdx, DefaultCharactorSizeLabel + String.valueOf(defaultCharactorSize));
	}
}
