import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
import java.lang.Math;

class View extends JPanel
{
	BufferedImage[] images;
	Board board;
	int centerx;
	int centery;
	int board_width;
	int board_height;
	int offset;
	JButton reset;


	View(Board b)
	{
		this.reset = new JButton("reset");
		this.add(reset);
		this.board = b;
		this.offset = 30;
		this.board_height = 800;
		this.board_width = 800;
		// Load the images
		this.images = new BufferedImage[Main.items.length];
		for (int i = 0; i < Main.items.length; i++) {
			BufferedImage image = null;
			try
			{
				image = ImageIO.read(new File("images/" + Main.items[i] + ".png"));
			} catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
			this.images[i] = image;
		}	
	}

	public void update(int w, int h){
		this.centerx = w/2;
		this.centery = h/2;
	}

	public void place(int x, int y){
		if(x < centerx-board_width/2 || x > centerx+board_width/2){
			return;
		}
		if(y < centery-board_height/2 || y > centery+board_height/2){
			return;
		}

		int col = Math.abs(x-(centerx-board_width/2))/(this.board_width/3);
		int row = Math.abs(y-(centery-board_height/2))/(this.board_height/3);
		int colb = Math.abs(x-(centerx-board_width/2))/(this.board_width/9)%3;
		int rowb = Math.abs(y-(centery-board_height/2))/(this.board_height/9)%3;
		int cur_board_index = row*3+col;
		int next_board_index = rowb*3+colb;
		this.board.boards.get(cur_board_index).Update(next_board_index);
		this.board.update(cur_board_index, next_board_index);
	}

	public void paintComponent(Graphics g)
	{
		
		// Clear the background
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		BufferedImage main_board = this.images[2];
		int w = main_board.getWidth();
		int h = main_board.getHeight();
		this.board_width = w;
		this.board_height = h;
		g.drawImage(main_board, centerx - w/2, centery - h/2, null);
		// Draw Smaller Boards
		int mini_width = w/3;
		int mini_height = h/3;
		int index = 0;
		int mini_centery = centery-h/2 + mini_height/2;
		for(int i = 0; i < 3 ;i++){
			int mini_centerx = centerx-w/2 + mini_width/2;
			for(int j = 0; j < 3;j++){
				BufferedImage image = this.images[this.board.boards.get(i*3+j).status];
				int we = image.getWidth();
				int he = image.getHeight();
				g.drawImage(image, mini_centerx - mini_width/2 + offset, mini_centery - mini_height/2 + offset, mini_centerx + mini_width/2 - offset, mini_centery + mini_height/2 - offset,0,0,we,he, null);
				this.paintMiniBoard(g,mini_centerx, mini_centery, mini_width - offset*2, mini_height-offset*2, index);
				mini_centerx += mini_width;
				index++;
			}
			mini_centery += mini_height;
		}
	}

	public void paintMiniBoard(Graphics g, int x, int y, int w, int h, int index)
	{
		int square_width = w/3;
		int square_height = h/3;
		int mini_centery = y-h/2 + square_height/2;
		int square = 0;
		int offset = this.offset/3;
		Mini_Board tmp = this.board.boards.get(index);
		for(int i = 0; i < 3 ;i++){
			int mini_centerx = x-w/2 + square_width/2;
			for(int j = 0; j < 3;j++){
				int val = tmp.values.get(square);
				if(val < 2 && tmp.status == 2){
					BufferedImage symbol = this.images[val];
					int imgw = symbol.getWidth();
					int imgh = symbol.getHeight();
					g.drawImage(symbol, mini_centerx - square_width/2 + offset, mini_centery - square_height/2 + offset, mini_centerx + square_width/2 - offset, mini_centery + square_height/2 - offset,0,0,imgw,imgh, null);
				}
				mini_centerx += square_width;
				square++;
			}
			mini_centery += square_height;
		}
	}
}

