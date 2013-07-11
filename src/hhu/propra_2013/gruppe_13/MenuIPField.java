package hhu.propra_2013.gruppe_13;

import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComponent;
import javax.swing.event.CaretListener;
import java.awt.event.KeyAdapter;
import javax.swing.event.CaretEvent;
import java.awt.event.KeyEvent;
import javax.swing.text.PlainDocument;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.BorderFactory;

/********************************************************************************************************************************
 * 

Title: JPanel was das Aussehen und verhalten von einem IP Address feld hat</p>
 *
 * 

Description: Steht unter der LGPL (c) by TAJ</p>
 *
 * 

Copyright: Copyright (c) 2007</p>
 *
 * 

Company: Taschek Jörg </p>
 *
 * @author Taschek Joerg
 * @version 1.0 Erstversion 10.08.2007
 * @versoin 2.0 28.08.2007 Kleine Änderungen an der Klasse selbst - liegt jetzt auf noch einem Panel um den schwarzen Rand zu haben
 * desweiteren gibt es einen Einfüg Modus, ist keine vollständige IP ausgefüllt, so wird null zurückgegeben und eine kleine main
 * zum Testen ist in der Klasse
 *******************************************************************************************************************************/
class MenuIPField extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private boolean MOVE_ON_FULL = true;
	private JTextFieldIP fields[] = new JTextFieldIP[4];
	private JTextField sep[] = new JTextField[3];
	private boolean INSERT_MODE = false;

	/******************************************************************************************************************************
	 * Standardkonstruktor für das Feld
	 *****************************************************************************************************************************/
	MenuIPField()
	{
		super(new FlowLayout(FlowLayout.LEFT,0,0));
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		for(int x = 0; x != fields.length; x++)
		{
			fields[x] = new JTextFieldIP();
			fields[x].setBorder(null);
			fields[x].setHorizontalAlignment(JTextField.CENTER);
			fields[x].setPreferredSize(new Dimension(35,20));
		}
		for(int x = 0; x != fields.length - 1; x++)
		{
			fields[x].setDocument(new LimitDocument(fields[x + 1]));
		}
		fields[fields.length - 1].setDocument(new LimitDocument(fields[0]));
		for(int x = 0; x != sep.length; x++)
		{
			sep[x] = new JTextField();
			sep[x].setFocusable(false);
			sep[x].setDocument(new NoWriteDocument());
			sep[x].setText(".");
			sep[x].setBorder(null);
			sep[x].setPreferredSize( new Dimension(5,20));
		}
		setBackgroundFields(Color.WHITE);
		panel.add(fields[0]);
		panel.add(sep[0]);
		panel.add(fields[1]);
		panel.add(sep[1]);
		panel.add(fields[2]);
		panel.add(sep[2]);
		panel.add(fields[3]);
		this.add(panel);
	}


	/******************************************************************************************************************************
	 * Methode gibt die IP Adresse zurück
	 * @return String Falsch ungültige IP, gibt es null zurück
	 *****************************************************************************************************************************/
	String getIP()
	{
		StringBuffer strRet = new StringBuffer();
		for(int x = 0; x != fields.length; x++)
		{
			if(x > 0)
				strRet.append(".");
			//wenn ein Feld nicht befüllt ist, dann null zurückgeben
			if(fields[x].getText() == null || fields[x].getText().trim().length() == 0)
				return null;
			strRet.append(fields[x].getText());
		}
		return strRet.toString();
	}

	/******************************************************************************************************************************
	 * Methode setzt die IP Addresse auf die Felder
	 * @param ipAddr String
	 *****************************************************************************************************************************/
	void setIP(String ipAddr)
	{
		String ips[] = ipAddr.split("\\.");
		if(ips == null || ips.length != 4)
			throw new RuntimeException("Not a valid ip address: " + ipAddr);
		for(int x = 0; x != fields.length; x++)
			fields[x].setText(ips[x]);
	}

	/******************************************************************************************************************************
	 * Methode gibt für die Listener die IDs zurück
	 * @param comp Component
	 * @return int
	 *****************************************************************************************************************************/
	private int getField(Component comp)
	{
		for(int x = 0; x != fields.length; x++)
			if(fields[x].equals(comp))
				return x;
		return -1;
	}

	/******************************************************************************************************************************
	 * Setzt die Hintergrundfarbe
	 * @param color Color
	 *****************************************************************************************************************************/
	void setBackgroundFields(Color color)
	{
		for(int x = 0; x != fields.length; x++)
			fields[x].setBackground(color);
		for(int x = 0; x != sep.length; x++)
			sep[x].setBackground(color);
	}

	/******************************************************************************************************************************
	 *
	 * @return Color
	 *****************************************************************************************************************************/
	Color getBackgroundFields()
	{
		return fields[0].getBackground();
	}

	/******************************************************************************************************************************
	 * Setzt die Vordergrundfarbe
	 * @param color Color
	 *****************************************************************************************************************************/
	void setForegroundFields(Color color)
	{
		for(int x = 0; x != fields.length; x++)
			fields[x].setForeground(color);
		for(int x = 0; x != sep.length; x++)
			sep[x].setForeground(color);
	}

	/******************************************************************************************************************************
	 *
	 * @return Color
	 *****************************************************************************************************************************/
	Color getForegroundFields()
	{
		return fields[0].getForeground();
	}

	/******************************************************************************************************************************
	 *
	 * @return JComponent
	 *****************************************************************************************************************************/
	JComponent getFinishFocus()
	{
		return ((LimitDocument)fields[fields.length - 1].getDocument()).getNextFocus();
	}

	/******************************************************************************************************************************
	 * Gibt die Komponente an, nachdem die ganze IP eingegeben wurde wohin der Focus kommt
	 * @param nextFocus JComponent
	 *****************************************************************************************************************************/
	void setFinishFocus(JComponent nextFocus)
	{
		((LimitDocument)fields[fields.length - 1].getDocument()).setNextFocus(nextFocus);
	}

	/******************************************************************************************************************************
	 *
	 * @return boolean
	 *****************************************************************************************************************************/
	boolean isMovingOnFull()
	{
		return MOVE_ON_FULL;
	}

	/******************************************************************************************************************************
	 * Setzt wenn die IP Adresse eingegeben worden ist, ob man aufs nächste Feld springen soll
	 * @param aValue boolean
	 *****************************************************************************************************************************/
	void setMovingOnFull(boolean aValue)
	{
		MOVE_ON_FULL = aValue;
	}

	/*****************************************************************************************************************************
	 *
	 * 

	Title: Eigenes TextField welches die Listener dranhängt und sich den caret Event merkt</p>
	 *
	 * 

	Description: Steht unter LGPL</p>
	 *
	 * 

	Copyright: Copyright (c) 2007</p>
	 *
	 * 

	Company: POS Systemhaus</p>
	 *
	 * @author Taschek Joerg
	 * @version 1.0 Erstversion 10.08.2007
	 *****************************************************************************************************************************/
	
	private class JTextFieldIP extends JTextField
	{
		private static final long serialVersionUID = 3981962012844207454L;
		
		private boolean event = false;
		JTextFieldIP()
		{
			super();
			this.addCaretListener(new CaretListener()
			{
				@Override
				public void caretUpdate(CaretEvent e)
				{
					event = true;
				}
			});
			this.addKeyListener(new KeyAdapter()
			{
				private boolean pressed = false;
				
				@Override
				public void keyReleased(KeyEvent e)
				{
					if(pressed)
					{
						if(e.getKeyCode() == KeyEvent.VK_LEFT)
						{
							int id = getField(e.getComponent());
							if(id >= 1)
							{
								if(!event)
									fields[--id].requestFocus();
								else
									event = false;
							}
						}
						else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
						{
							int id = getField(e.getComponent());
							if(id != -1 && id < fields.length - 1)
							{
								if(!event)
									fields[++id].requestFocus();
								else
									event = false;
							}
						}
						else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
						{
							int id = getField(e.getComponent());
							if(id >= 1) //beim 0er feld wird da nix gemacht
							{
								if(fields[id].getText().trim().length() == 0) //wenn nix drinnen ist
								{
									if(((LimitDocument)fields[id].getDocument()).isEmpty())
									{
										fields[--id].requestFocus();
										fields[id].getCaret().setDot(fields[id].getText().length()); //setzt den Cursor nach ganz hinten
									}
									((LimitDocument)fields[id].getDocument()).setEmpty(true);
								}
							}
						}
						else if(e.getKeyCode() == KeyEvent.VK_INSERT)
							INSERT_MODE = !INSERT_MODE; //umkehren des aktuellen Status
						pressed = false;
					}
				}

				@Override
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_RIGHT || 
							e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_INSERT)
						pressed = true;
				}
			});
		}
	}

	/******************************************************************************************************************************
	 *
	 * 

Title: Dokument lässt keine Zahlen > 255 eingeben und bei einem "." als Eingabe springt es auf das nächste Feld</p>
	 *
	 * 

Description: Unter LGPL</p>
	 *
	 * 

Copyright: Copyright (c) 2007</p>
	 *
	 * 

Company: POS Systemhaus</p>
	 *
	 * @author Taschek Joerg
	 * @version 1.0 Erstversion 10.08.2007
	 *****************************************************************************************************************************/
	private class LimitDocument extends PlainDocument
	{
		private static final long serialVersionUID = -6732017731498793565L;
		private JComponent nextFocus = null;
		private boolean empty = false;
		LimitDocument(JComponent nextFocus)
		{
			setNextFocus(nextFocus);
			this.addDocumentListener(new DocumentListener()
			{
				public void insertUpdate(DocumentEvent e)
				{
					if(getLength() > 0)
						empty = false;
				}

				public void removeUpdate(DocumentEvent e)
				{
				}

				public void changedUpdate(DocumentEvent e)
				{
					if(getLength() > 0)
						empty = false;
				}
			});
		}

		boolean isEmpty()
		{
			return empty;
		}

		void setEmpty(boolean aValue)
		{
			this.empty = aValue;
		}

		void setNextFocus(JComponent nextFocus)
		{
			this.nextFocus = nextFocus;
		}

		JComponent getNextFocus()
		{
			return this.nextFocus;
		}

		@Override
		public void insertString(int offset, String s, AttributeSet attributeSet) throws BadLocationException
		{
			if(s != null && s.equals("."))
			{
				if(nextFocus != null && nextFocus.isEnabled())
					nextFocus.requestFocus();
			}
			if(!INSERT_MODE)
			{
				if (getLength() + (s != null ? s.length() : 0) > 3)
					return;
			}
			try{
				Integer.parseInt(s);
			}
			catch(Exception ex)   //only allow integer values
			{
				Toolkit.getDefaultToolkit().beep(); //macht ein DÜT
				return ;
			}
			try{
				if(!INSERT_MODE)
				{
					StringBuffer strBuf = new StringBuffer(getText(0, getLength()));
					strBuf.insert(offset, s);
					int i = Integer.parseInt(strBuf.toString());
					if (i > 255)
					{
						Toolkit.getDefaultToolkit().beep();
						return;
					}
				}
				else
				{
					StringBuffer strBuf = new StringBuffer(getText(0, getLength()));
					strBuf.replace(offset, offset + s.length(),s);
					int i = Integer.parseInt(strBuf.toString());
					if (i > 255)
					{
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					remove(offset,s.length());
				}
			}catch(Exception ex)
			{
				Toolkit.getDefaultToolkit().beep();
			}
			super.insertString(offset,s, attributeSet);
			if(MOVE_ON_FULL)
			{//wenn es nicht der INSERT Modus ist und die länge von 3 erreicht ist oder wenn es im insert Modus ist und er auf der 3ten(2) position ist
				if ( ( !INSERT_MODE && getLength() == 3 ) || (INSERT_MODE && offset == 2) )
				{
					if (nextFocus != null && nextFocus.isEnabled())
					{
						if(nextFocus instanceof JTextFieldIP)
							((JTextFieldIP)nextFocus).getCaret().setDot(0); //setzt den Fokus auf das vorderste Zeichen
						nextFocus.requestFocus();
					}
				}
			}
		}
	}

	/******************************************************************************************************************************
	 *
	 * 

Title: Klasse lässt keine Eingaben in einem Textfeld zu</p>
	 *
	 * 

Description: Steht unter LGPL</p>
	 *
	 * 

Copyright: Copyright (c) 2007</p>
	 *
	 * 

Company: POS Systemhaus</p>
	 *
	 * @author Taschek Joerg
	 * @version 1.0 Erstversion 10.08.2007
	 *****************************************************************************************************************************/
	private class NoWriteDocument extends PlainDocument
	{
		private static final long serialVersionUID = -980146827590316938L;

		@Override
		public void insertString(int offset, String s, AttributeSet attributeSet) throws BadLocationException
		{
			super.remove(0, getLength());
			super.insertString(0,".",attributeSet);
		}
	}
}