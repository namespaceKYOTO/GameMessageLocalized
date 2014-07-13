package MesMan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * コンフィグファイル関係.
 * @author t_sato
 *
 */
public class Config
{
	private Stack<String> buffer;
	
	/**
	 * コンストラクタ.
	 * @param configFile コンフィグファイル名
	 */
	public Config(String configFile)
	{
		buffer = new Stack<String>();
		File file = new File(configFile);
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			LinkedList<String> buffer = new LinkedList<String>();
			String line = null;
			while((line = br.readLine()) != null) {
				buffer.add(line);
			}
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();	
		}
	}
}
