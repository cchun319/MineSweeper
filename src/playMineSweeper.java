import static org.junit.Assert.assertEquals;

public class playMineSweeper {

	public static void main(String[] args) {
		MineSweeperBoard B = new MineSweeperBoard(5, 4, 6); //column, row, mines
		System.out.println(B.toString());
		for(int i = 0; i < B.height(); i++)
		{
			for(int j = 0; j < B.width(); j++)
			{
				int number = B.numberOfAdjacentMines(j, i);
				System.out.print(number);
			}
			System.out.print("\n");
		}
		
//		for(int i = 0; i < B.height(); i++)
//		{
//			for(int j = 0; j < B.width(); j++)
//			{
//				B.flagCell(j, i);
//				System.out.print(B.getCell(j, i) + " ");
//			}
//			System.out.print("\n");
//
//		}
		
//		System.out.println(B.toString());
		
		B.setCell(0, 0, MineSweeperCell.MINE);
		B.setCell(2, 3, MineSweeperCell.MINE);

		for(int i = 0; i < B.height(); i++)
		{
			for(int j = 0; j < B.width(); j++)
			{
				B.revealBoard();
				System.out.print(B.getCell(j, i) + " ");
			}
			System.out.print("\n");
		}
		
		System.out.println(B.toString());
		
		MineSweeperBoard C = new MineSweeperBoard(4, 4, 0); ;//column, row, mines
		C.loadBoardState("OOOO","OOOO", "O++O", "+OOO");
		C.uncoverCell(0, 0);

//		C.flagCell(0, 3);

		C.uncoverCell(1, 0);
		C.uncoverCell(2, 0);
		C.uncoverCell(3, 0);
		C.uncoverCell(0, 1);
		C.uncoverCell(1, 1);
		C.uncoverCell(2, 1);
		C.uncoverCell(3, 1);
		C.uncoverCell(0, 2);
		C.uncoverCell(3, 2);
		C.uncoverCell(1, 3);
		C.uncoverCell(2, 3);
		C.uncoverCell(3, 3);
		
		C.flagCell(1, 2);
		C.flagCell(2, 2);
		
		System.out.println(C.toString());
		System.out.println(C.isGameWon());


		for(int i = 0; i < C.height(); i++)
		{
			for(int j = 0; j < C.width(); j++)
			{
				int number1 = C.numberOfAdjacentMines(j, i);
				System.out.print(number1);
			}
			System.out.print("\n");
		}
	
		
	}
}
