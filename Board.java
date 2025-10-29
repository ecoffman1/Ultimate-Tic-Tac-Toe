import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.lang.Math;
import javax.swing.JList;

class Board
{
	ArrayList<Mini_Board> boards;
	boolean isWinner;
	Board()
	{
		this.boards = new ArrayList<Mini_Board>();
		this.isWinner = false;
		// Add Mini Boards
		for(int i = 0; i < 9;i++)
		{
			boards.add(new Mini_Board());
		}	
	}

	public void update(int index, int next)
	{
		if(!this.boards.get(index).playable){
			return;
		}
		if(this.boards.get(next).status == 2){
			for(Mini_Board i: this.boards){
				i.setPlayable(false);
			}
			this.boards.get(next).setPlayable(true);
		}else {
			for(Mini_Board i : this.boards){
				i.setPlayable(true);
			}
		}
		ArrayList<Integer> values = new ArrayList<Integer>();
		for(Mini_Board i:this.boards){
			values.add(i.status);
		}

		Winner winner = check(values);
		boolean win = winner.win;
		if(win){
			this.isWinner = win;
			for(Mini_Board i:this.boards){
				i.setPlayable(false);
			}
		}
	}

    public void reset()
    {
		this.boards = new ArrayList<Mini_Board>();
		for(int i = 0; i < 9;i++)
		{
			boards.add(new Mini_Board());
		}	

    }

	public static Winner check(ArrayList<Integer> values){
		boolean win = false;
		int val = 2;
		// check columns
		for(int i = 0;i<3;i++){
			if(values.get(i) == 2){
				continue;
			}
			if(values.get(i) == values.get(i+3) && values.get(i+3) == values.get(i+6)){
				win = true;
				val = values.get(i);
			}
		}
		//check rows
		for(int i = 0;i<7;i+=3)
		{
			if(values.get(i) == 2){
				continue;
			}
			if(values.get(i) == values.get(i+1)&& values.get(i+1) == values.get(i+2)){
				val = values.get(i);
				win = true;
			}
		}
		//check diagnols 
		if(values.get(0) != 2 && values.get(0) == values.get(4) && values.get(4) == values.get(8)){
			val = values.get(0);
			win = true;
		}
		
		if(values.get(2) != 2 && values.get(2)==values.get(4) && values.get(4) == values.get(6)){
			val = values.get(2);
			win = true;
		}
		return new Winner(win, val);
	}
}

class Mini_Board
{
	ArrayList<Integer> values;
	int status;
	boolean playable;
	Mini_Board()
	{
		this.status = 2;
		this.playable = true;
		// Initalize all values to nothing
		this.values = new ArrayList<Integer>();
		for(int i = 0;i < 9;i++){
			this.values.add(2);
		}
	}
	// update square then check for win on mini board
	//2 is the default state(neither x nor o), 0 is x, 1 is o
	public void Update(int index){
		if(!(this.status == 2 && this.values.get(index) == 2)){
			return;
		}

		if(!(this.playable)){
			return;
		}
		// change value based on whose turn it is
		this.values.set(index, Main.turn%2);

		// Check for win each time updated
		Winner winner = Board.check(values);
		boolean win = winner.win;
		int val = winner.val;
		if(win) {
			this.status = val;
		} else {
			Main.turn++;
		}
	}

	public void setPlayable(boolean state){
		this.playable = state;
	}
}


class Winner{
	boolean win;
	int val;

	Winner(boolean win, int val){
		this.win = win;
		this.val = val;
	}
}




