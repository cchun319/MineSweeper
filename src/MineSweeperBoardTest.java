import static org.junit.Assert.*;

import org.junit.Test;

public class MineSweeperBoardTest {

	@Test
	public void testConstructor() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		assertNotNull(MSB);
	}
	
	@Test
	public void testGetWidth() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		assertEquals(4, MSB.width());
	}
	
	@Test
	public void testGetHeight() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		assertEquals(4, MSB.height());
	}
	
	@Test
	public void testGetCell() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OOOO" ,"OOOO" ,"O++O" ,"+OOO");
		assertEquals(MineSweeperCell.COVERED_CELL, MSB.getCell(0, 0));
		assertEquals(MineSweeperCell.MINE, MSB.getCell(1, 2));
		assertEquals(MineSweeperCell.INVALID_CELL, MSB.getCell(4, 1));

	}

	@Test
	public void testUncoverCell() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OOOO" ,"OOOO" ,"O++O" ,"+OOO");
		MSB.uncoverCell(0, 0);
		MSB.uncoverCell(1, 1);
		MSB.uncoverCell(1, 2);
		assertEquals(MineSweeperCell.ADJACENT_TO_0, MSB.getCell(0, 0));
		assertEquals(MineSweeperCell.ADJACENT_TO_2, MSB.getCell(1, 1));
		assertEquals(MineSweeperCell.UNCOVERED_MINE, MSB.getCell(1, 2));
	}
	
	@Test
	public void testFlagCell() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OOOO" ,"OOOO" ,"O++O" ,"+OOO");
		MSB.flagCell(0, 0);
		MSB.flagCell(1, 2);
		assertEquals(MineSweeperCell.FLAG, MSB.getCell(0, 0));
		assertEquals(MineSweeperCell.FLAGGED_MINE, MSB.getCell(1, 2));
		MSB.flagCell(1, 2);
		assertEquals(MineSweeperCell.MINE, MSB.getCell(1, 2));
		MSB.flagCell(0, 0);
		assertEquals(MineSweeperCell.COVERED_CELL, MSB.getCell(0, 0));
		
	}
	
	@Test
	public void testGameWon() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OOOO" ,"OOOO" ,"O++O" ,"+OOO");
		assertEquals(false, MSB.isGameWon());
		MSB.uncoverCell(0, 0);
		assertEquals(false, MSB.isGameWon());
		MSB.flagCell(1, 2);
		assertEquals(false, MSB.isGameWon());
		MSB.flagCell(2, 2);
		assertEquals(false, MSB.isGameWon());
		MSB.flagCell(0, 3);
		assertEquals(false, MSB.isGameWon());

		MSB.uncoverCell(1, 0);
		MSB.uncoverCell(2, 0);
		MSB.uncoverCell(3, 0);
		MSB.uncoverCell(0, 1);
		MSB.uncoverCell(1, 1);
		MSB.uncoverCell(2, 1);
		MSB.uncoverCell(3, 1);
		MSB.uncoverCell(0, 2);
		MSB.uncoverCell(3, 2);
		MSB.uncoverCell(1, 3);
		MSB.uncoverCell(2, 3);
		MSB.uncoverCell(3, 3);

		assertEquals(true, MSB.isGameWon());
		MSB.loadBoardState("    ", "1221" ,"2MM1" ,"+321");
		assertEquals(false, MSB.isGameWon());
		
		MSB.loadBoardState("    ", "F221" ,"2MM1" ,"*321");
		assertEquals(false, MSB.isGameWon());
	}
	
	@Test
	public void testGameLost() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OOOO" ,"OOOO" ,"O++O" ,"+OOO");
		MSB.uncoverCell(0, 0);
		assertEquals(false, MSB.isGameLost());
		MSB.uncoverCell(1, 1);
		assertEquals(false, MSB.isGameLost());
		MSB.uncoverCell(1, 2);
		assertEquals(true, MSB.isGameLost());
	}
	
	@Test
	public void testAroundMines() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OOOO" ,"OOOO" ,"O++O" ,"+OOO");
		assertEquals(0, MSB.numberOfAdjacentMines(0, 0));
		assertEquals(2, MSB.numberOfAdjacentMines(0, 2));
		assertEquals(1, MSB.numberOfAdjacentMines(0, 3));
	}
	
	@Test
	public void testReveal() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OOOO" ,"OOOO" ,"O++O" ,"+OOO");
		MSB.setCell(1,2 , MineSweeperCell.FLAGGED_MINE);
		MSB.setCell(0,0 , MineSweeperCell.FLAG);
		MSB.revealBoard();
		assertEquals(MineSweeperCell.ADJACENT_TO_0, MSB.getCell(0, 0));
		assertEquals(MineSweeperCell.UNCOVERED_MINE, MSB.getCell(1, 2));
		assertEquals(MineSweeperCell.ADJACENT_TO_3, MSB.getCell(1, 3));

	}
	
	@Test
	public void testSetCell() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OOOO" ,"OOOO" ,"O++O" ,"+OOO");
		MSB.setCell(0, 0, MineSweeperCell.MINE);
		assertEquals(MineSweeperCell.MINE, MSB.getCell(0, 0));
		MSB.setCell(1, 2, MineSweeperCell.COVERED_CELL);
		assertEquals(MineSweeperCell.COVERED_CELL, MSB.getCell(1, 2));

	}
	
	@Test
	public void testSetMines() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		int[] pos = {0 , 13};
		MSB.setMines(pos);
		assertEquals(MineSweeperCell.MINE, MSB.getCell(0, 0));
		assertEquals(MineSweeperCell.MINE, MSB.getCell(1, 3));
     	assertEquals(MineSweeperCell.COVERED_CELL, MSB.getCell(1, 2));
	}
	
	@Test
	public void testloadBoardState() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OFM*" ," 123" ,"4++5" ,"+678");

		assertEquals(MineSweeperCell.COVERED_CELL, MSB.getCell(0, 0));
		assertEquals(MineSweeperCell.FLAG, MSB.getCell(1, 0));
     	assertEquals(MineSweeperCell.FLAGGED_MINE, MSB.getCell(2, 0));
     	assertEquals(MineSweeperCell.UNCOVERED_MINE, MSB.getCell(3, 0));
		assertEquals(MineSweeperCell.ADJACENT_TO_0, MSB.getCell(0, 1));
     	assertEquals(MineSweeperCell.ADJACENT_TO_1, MSB.getCell(1, 1));
     	assertEquals(MineSweeperCell.ADJACENT_TO_2, MSB.getCell(2, 1));
		assertEquals(MineSweeperCell.ADJACENT_TO_3, MSB.getCell(3, 1));
     	assertEquals(MineSweeperCell.ADJACENT_TO_4, MSB.getCell(0, 2));
     	assertEquals(MineSweeperCell.MINE, MSB.getCell(1, 2));
		assertEquals(MineSweeperCell.MINE, MSB.getCell(2, 2));
     	assertEquals(MineSweeperCell.ADJACENT_TO_5, MSB.getCell(3, 2));
     	assertEquals(MineSweeperCell.MINE, MSB.getCell(0, 3));
     	assertEquals(MineSweeperCell.ADJACENT_TO_6, MSB.getCell(1, 3));
		assertEquals(MineSweeperCell.ADJACENT_TO_7, MSB.getCell(2, 3));
     	assertEquals(MineSweeperCell.ADJACENT_TO_8, MSB.getCell(3, 3));
	}
	
	@Test
	public void testminesPos() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		int[] minesPOS = MSB.minesPos(16, 5);
		assertEquals(5, minesPOS.length);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
    public void testloadBoardStateIllegal1() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OFM*" ," 123" ,"4++5" ,"+67a");
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testloadBoardStateIllegal2() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OFM*" ,"123" ,"4++5" ,"+67a");
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testloadBoardStateIllegal3() {
		MineSweeperBoard MSB = new MineSweeperBoard(4, 4, 0);
		MSB.loadBoardState("OFM*" ,"123" ,"4++5");
    }
	
}
