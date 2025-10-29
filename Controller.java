import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;


class Controller implements MouseListener, ActionListener
{
	View view;
	Board board;

	Controller(Board b, View v)
	{
		this.board = b;
		this.view = v;
		this.view.addMouseListener(this);
		this.view.reset.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.view.reset){
			this.board.reset();
			Main.turn = 0;
		}
	}

	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == 1)
		{
			this.view.place(e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) 
	{	}
	
	public void mouseEntered(MouseEvent e) 
	{	}
	
	public void mouseExited(MouseEvent e) 
	{	}
	
	public void mouseClicked(MouseEvent e) 
	{	}
}
