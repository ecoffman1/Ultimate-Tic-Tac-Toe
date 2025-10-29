import javax.swing.JFrame;
import java.awt.Toolkit;

public class Main extends JFrame
{
	Board board;
	Controller controller;
	View view;
	int width;
	int height;
	public static int turn = 0;
	public static final String[] items = {
		"X",
		"O",
		"Board"
	};

	public Main()
	{
		this.width = 900;
		this.height = 900;
		// Instantiate the three main objects
		this.board = new Board();
		this.view = new View(this.board);
		this.controller = new Controller(board, view);

		// Set some window properties
		this.setTitle("Ultimate Tic Tac Toe");
		this.setSize(this.width, this.height);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	public void run()
	{
		// Main loop
		while(true)
		{
			view.update(this.getWidth(), this.getHeight());
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen
			// Go to sleep for a brief moment
			try
			{
				Thread.sleep(25);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args)
	{
		Main m = new Main();
		m.run();
	}
}
