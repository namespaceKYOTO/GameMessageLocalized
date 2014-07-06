package MesMan;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import MesMan.MenuBar.SettingMenu;

/**
 * テーブルメニュー
 * @author t_sato
 *
 */
public class TableMenu implements ChangeListener
{
	private MesMan mesman;
	private JTabbedPane tab;
	private MesTable mesTable;
	private TagTable tagTable;
	private CharacterSizeTable charSizeTable;
	private ResultTable resultTable;
	
	/**
	 * コンストラクタ.
	 * @param mesman メッセージデータ管理
	 * @param width UI幅
	 * @param height UI高さ
	 */
	public TableMenu(MesMan mesman, int width, int height)
	{
		this.mesman = mesman;
		
		this.mesTable = new MesTable(mesman, width, height);
		this.tagTable = new TagTable(mesman, width, height);
		this.charSizeTable = new CharacterSizeTable(mesman, width, height);
		this.resultTable = new ResultTable(mesman, width, height);
		
		tab = new JTabbedPane();
		tab.addChangeListener(this);
		tab.addTab("Tag", this.tagTable.getPanel());
		tab.addTab("Message", this.mesTable.getPanel());
		tab.addTab("CharacterSize", this.charSizeTable.getPanel());
		tab.addTab("Result", this.resultTable.getPanel());
	}
	
	/**
	 * メッセージテーブル取得.
	 * @return　メッセージテーブル
	 */
	public MesTable getMesTable()
	{
		return this.mesTable;
	}
	
	/**
	 * タグテーブル取得.
	 * @return タグテーブル
	 */
	public TagTable getTagTable()
	{
		return this.tagTable;
	}
	
	/**
	 * 文字サイズテーブル取得.
	 * @return 文字サイズテーブル
	 */
	public CharacterSizeTable getCharsizeTable()
	{
		return this.charSizeTable;
	}
	
	/**
	 * 結果テーブル取得.
	 * @return　結果テーブル
	 */
	public ResultTable getResultTable()
	{
		return this.resultTable;
	}
	
	/**
	 * ベースコンポーネントの取得.
	 * @return コンポーネント
	 */
	public JComponent getComponent()
	{
		return this.tab;
	}
	
	/**
	 * UIの表示言語変更
	 */
	public void LanguageChange()
	{
		mesTable.LanguageChange();
		tagTable.LanguageChange();
		charSizeTable.LanguageChange();
		resultTable.LanguageChange();
	}
	
	/**
	 * タブの選択.
	 * @param index 選択するタブの番号
	 */
	public void setTabSelectedIndex(int index)
	{
		this.tab.setSelectedIndex(index);
	}
	
	/**
	 * メッセージのサイズチェック.
	 * @param language チェックする言語
	 */
	public void checkMessageSize(String language)
	{
		this.charSizeTable.check(this.mesTable, this.tagTable, this.resultTable, language);
	}

	/* (非 Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent arg0) {
		int selectedIdx = this.tab.getSelectedIndex();
		SettingMenu settingMenu = (SettingMenu)this.mesman.getMenubar().get(1);
		if(settingMenu == null) { 
			return;
		}
		
		if(selectedIdx == 0 || selectedIdx == 1 || selectedIdx == 3) {
			settingMenu.changeMenu(SettingMenu.eMenuType.Normal);
		}
		else if(selectedIdx == 2) {
			settingMenu.changeMenu(SettingMenu.eMenuType.CharactorSize);
		}
	}
}
