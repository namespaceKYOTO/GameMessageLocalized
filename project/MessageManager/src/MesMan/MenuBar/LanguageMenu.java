package MesMan.MenuBar;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import MesMan.MesTableDefine;
import MesMan.MessageManager;

public class LanguageMenu extends MenuBarBase
{
	private JMenuItem jpn;
	private JMenuItem eng;
	private JMenuItem deu;
	private JMenuItem fra;
	private JMenuItem ita;
	private JMenuItem spa;
	private JMenuItem rus;
	private JMenuItem tha;

	/**
	 * コンストラクタ.
	 * @param parent UI親
	 * @param mesman メッセージ管理
	 */
	public LanguageMenu(Component parent, MessageManager mesman)
	{
		super(parent, mesman, mesman.getMessage(MesTableDefine.mes_language));
		
		JMenu menu = getMenu();
		
		jpn = new JMenuItem(mesman.getMessage(MesTableDefine.mes_JPN));
		eng = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ENG));
		deu = new JMenuItem(mesman.getMessage(MesTableDefine.mes_DEU));
		fra = new JMenuItem(mesman.getMessage(MesTableDefine.mes_FRA));
		ita = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ITA));
		spa = new JMenuItem(mesman.getMessage(MesTableDefine.mes_SPA));
		rus = new JMenuItem(mesman.getMessage(MesTableDefine.mes_RUS));
		tha = new JMenuItem(mesman.getMessage(MesTableDefine.mes_THA));
		jpn.addActionListener(this);
		eng.addActionListener(this);
		deu.addActionListener(this);
		fra.addActionListener(this);
		ita.addActionListener(this);
		spa.addActionListener(this);
		rus.addActionListener(this);
		tha.addActionListener(this);
		menu.add(jpn);
		menu.add(eng);
		menu.add(deu);
		menu.add(fra);
		menu.add(ita);
		menu.add(spa);
		menu.add(rus);
		menu.add(tha);
	}
	
	/**
	 * UI表示言語変更.
	 */
	public void LanguageChange()
	{
		getMenu().setText(getMesman().getMessage(MesTableDefine.mes_language));
	}

	public void actionPerformed(ActionEvent arg0)
	{
		Object obj = arg0.getSource();
		this.getMesman().setLanguageNo(getLanguageNo(obj));
		this.getMenuBar().languageChange();
	}
	
	/**
	 * 言語番号取得.
	 * @param src メニューアイテム
	 * @return 言語番号
	 */
	private int getLanguageNo(Object src)
	{
			 if(src == jpn){return MesTableDefine.Language_JPN; }
		else if(src == eng){return MesTableDefine.Language_ENG; }
		else if(src == deu){return MesTableDefine.Language_DEU; }
		else if(src == fra){return MesTableDefine.Language_FRA; }
		else if(src == ita){return MesTableDefine.Language_ITA; }
		else if(src == spa){return MesTableDefine.Language_SPA; }
		else if(src == rus){return MesTableDefine.Language_RUS; }
		else if(src == tha){return MesTableDefine.Language_THA; }
		return MesTableDefine.Language_ENG;
	}
}
