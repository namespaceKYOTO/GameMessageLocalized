package MesMan;

import javax.swing.JPanel;

/**
 * ヘルプ.
 * @author t_sato
 *
 */
public class Help
{
//    JOptionPane.showMessageDialog(this, "JOptionPane");
	// ようこそ　-> Dialog標示
	// ヘルプ目次　-> Dialog標示
	// テストデータ -> テストデータ標示
	private MessageManager mesman;
	private int width;
	private int height;
	
	/**
	 * コンストラクタ.
	 * @param mesman メッセージ管理
	 * @param width　UI幅
	 * @param height UI高さ
	 */
	public Help(MessageManager mesman, int width, int height)
	{
		this.mesman = mesman;
		this.width = width;
		this.height = height;
	}

	/**
	 * UIの表示言語変更.
	 */
	public void LanguageChange()
	{
		
	}
}
