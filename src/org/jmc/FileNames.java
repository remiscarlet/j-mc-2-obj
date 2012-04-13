package org.jmc;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



@SuppressWarnings("serial")
public class FileNames extends JFrame {
	
	Preferences pref;
	JTextField tfObj,tfMtl;
	
	public FileNames()
	{
		pref=MainWindow.settings.getPreferences();
		
		JPanel mp=new JPanel();
		mp.setLayout(new BoxLayout(mp, BoxLayout.PAGE_AXIS));				
		add(mp);
		
		JPanel pObj=new JPanel();
		pObj.setLayout(new BoxLayout(pObj, BoxLayout.LINE_AXIS));
		pObj.setMaximumSize(new Dimension(Short.MAX_VALUE,20));		
		JLabel lObj=new JLabel("OBJ file name: ");
		tfObj=new JTextField(pref.get("OBJ_FILE_NAME", "minecraft.obj"));
		pObj.add(lObj);
		pObj.add(tfObj);
		
		JPanel pMtl=new JPanel();
		pMtl.setLayout(new BoxLayout(pMtl, BoxLayout.LINE_AXIS));
		pMtl.setMaximumSize(new Dimension(Short.MAX_VALUE,20));
		JLabel lMtl=new JLabel("MTL file name: ");
		tfMtl=new JTextField(pref.get("MTL_FILE_NAME", "minecraft.mtl"));		
		pMtl.add(lMtl);
		pMtl.add(tfMtl);

		DocumentListener save_listener=new DocumentListener() {			
			@Override
			public void removeUpdate(DocumentEvent e) {
				save_settings();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				save_settings();
			}			
			@Override
			public void changedUpdate(DocumentEvent e) {
				save_settings();				
			}
		};
		
		tfObj.getDocument().addDocumentListener(save_listener);
		tfMtl.getDocument().addDocumentListener(save_listener);

		JPanel pClose=new JPanel();
		pClose.setLayout(new BoxLayout(pClose, BoxLayout.LINE_AXIS));
		pClose.setMaximumSize(new Dimension(Short.MAX_VALUE,20));
		JButton bClose=new JButton("Close");
		bClose.setMaximumSize(new Dimension(Short.MAX_VALUE,20));
		pClose.add(bClose);
		
		bClose.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		mp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		mp.add(pObj);
		mp.add(pMtl);
		mp.add(pClose);
		
		pack();
		
		//this hack is easiest since pack seems to ignore minimum sizes...
		if(getWidth()<400)
		{
			setSize(new Dimension(400,getHeight()));
		}
	}
	
	private void save_settings()
	{
		String txt=tfObj.getText();
		if(txt.length()>0) pref.put("OBJ_FILE_NAME",txt);
		txt=tfMtl.getText();
		if(txt.length()>0) pref.put("MTL_FILE_NAME",txt);
	}
	
	public void display()
	{
		Point p=MainWindow.main.getLocation();
		p.x+=(MainWindow.main.getWidth()-getWidth())/2;
		p.y+=(MainWindow.main.getHeight()-getHeight())/2;
		setLocation(p);
		setVisible(true);
	}
	
	public String getOBJName()
	{
		return tfObj.getText();
	}
	
	public String getMTLName()
	{
		return tfMtl.getText();
	}
}
