
/**
 * @author chunchang
 * 
 */

public class MineSweeperBoard extends MineSweeperBoardBase {

	public int row;
	public int column;
	public MineSweeperCell[][] state;
	public int numOfMines;

	/**
	 * Constructor takes number of columns, rows and mines to construct the object,
	 * create a 2D minesweepercell array for
	 * 
	 * @param width  columns of board
	 * @param height rows of board
	 * @param mines  number of mines in the game
	 */
	public MineSweeperBoard(int width, int height, int mines) {
		this.state = new MineSweeperCell[height][width];
		this.row = height;
		this.column = width;
		this.numOfMines = mines;
		int[] posOfMines = minesPos(row * column, mines);
		setMines(posOfMines);
		coverCell();
	}

	/**
	 * randomly generate position for mines on the board, the method takes total
	 * number of mines and all possible index as input
	 * 
	 * @param limit         the biggest index a mine can have
	 * @param numberOfMines total number of mines in the game
	 * @return array of unique integer as index of mines on board
	 */
	public int[] minesPos(int limit, int numberOfMines) {
		int[] pos = new int[numberOfMines];
		int rand_count = 0;
		int rand;
		boolean check_exist;
		while (rand_count < numberOfMines) {
			check_exist = false;
			rand = (int) (Math.random() * limit); //
			for (int i = 0; i < rand_count; i++) {
				if (pos[i] == rand) {
					check_exist = true;
					break;
				}
			}
			if (check_exist == false) {
				pos[rand_count] = rand;
				System.out.print(rand + " ");
				rand_count++;
			}
		}
		return pos;
	}

	/**
	 * serMines takes the position of mines generated randomly and put them into the
	 * board with nested loops. check if the position if there is a mine, if not,
	 * put it
	 * 
	 * @param pos array of integers as the indices of mines
	 */
	public void setMines(int[] pos) {
		int row_ind;
		int col_ind;
		for (int i = 0; i < pos.length; i++) {
			row_ind = pos[i] / this.column;
			col_ind = pos[i] % this.column;
			if (this.state[row_ind][col_ind] != MineSweeperCell.MINE) {
				this.state[row_ind][col_ind] = MineSweeperCell.MINE;
			}
		}
	}

	/**
	 * coverCell makes all positions with no mine as covered cell
	 */
	public void coverCell() {
		for (int i = 0; i < this.state.length; i++) {
			for (int j = 0; j < this.state[i].length; j++) {
				if (this.state[i][j] != MineSweeperCell.MINE) {
					this.state[i][j] = MineSweeperCell.COVERED_CELL;
				}
			}
		}
	}

	@Override
	public int width() {
		return this.column;
	}

	@Override
	public int height() {
		return this.row;
	}

	// x width y height
	@Override
	public MineSweeperCell getCell(int x, int y) {
		if (x >= 0 && x < this.column && y >= 0 && y < this.row) {
			return this.state[y][x];
		}
		return MineSweeperCell.INVALID_CELL;
	}

	@Override
	public void uncoverCell(int x, int y) {
		int row_ind = y;
		int col_ind = x;
		if (this.state[row_ind][col_ind] == MineSweeperCell.FLAG) {
			// do nothing
		} else if (this.state[row_ind][col_ind] == MineSweeperCell.MINE) {
			// game is over
			this.state[row_ind][col_ind] = MineSweeperCell.UNCOVERED_MINE;

		} else if (this.state[row_ind][col_ind] != MineSweeperCell.MINE
				&& this.state[row_ind][col_ind] != MineSweeperCell.FLAG
				&& this.state[row_ind][col_ind] != MineSweeperCell.UNCOVERED_MINE) {
			// get number of mines around the cell
			int mines_around = numberOfAdjacentMines(col_ind, row_ind);
			this.state[row_ind][col_ind] = MineSweeperCell.adjacentTo(mines_around);
		}

	}

	@Override
	public void flagCell(int x, int y) {
		int row_ind = y;
		int col_ind = x;
		if (this.state[row_ind][col_ind] == MineSweeperCell.COVERED_CELL) {
			this.state[row_ind][col_ind] = MineSweeperCell.FLAG;
		} else if (this.state[row_ind][col_ind] == MineSweeperCell.FLAG) {
			this.state[row_ind][col_ind] = MineSweeperCell.COVERED_CELL;
		} else if (this.state[row_ind][col_ind] == MineSweeperCell.FLAGGED_MINE) {
			this.state[row_ind][col_ind] = MineSweeperCell.MINE;
		} else if (this.state[row_ind][col_ind] == MineSweeperCell.MINE) {
			this.state[row_ind][col_ind] = MineSweeperCell.FLAGGED_MINE;
		}
	}

	@Override
	public boolean isGameLost() {
		for (int i = 0; i < this.state.length; i++) {
			for (int j = 0; j < this.state[i].length; j++) {
				if (this.state[i][j] == MineSweeperCell.UNCOVERED_MINE) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isGameWon() {
		int mines_flagged = 0;
		int uncovered = 0;
		for (int i = 0; i < this.state.length; i++) {
			for (int j = 0; j < this.state[i].length; j++) {
				if (this.state[i][j] == MineSweeperCell.FLAGGED_MINE) {
					mines_flagged++;
				} else if (this.state[i][j] == MineSweeperCell.UNCOVERED_MINE
						|| this.state[i][j] == MineSweeperCell.FLAG || this.state[i][j] == MineSweeperCell.COVERED_CELL
						|| this.state[i][j] == MineSweeperCell.INVALID_CELL
						|| this.state[i][j] == MineSweeperCell.MINE) {
					return false;
				} else {
					uncovered++;
				}

			}
		}
		int should_uncover = this.row * this.column - this.numOfMines;
//		System.out.println(mines_flagged + " " + this.numOfMines + " " + covered + " " + should_cover);
//		if (mines_flagged == this.numOfMines && uncovered == should_uncover) {
//			return true;
//		}
		return true;
	}

	@Override
	public int numberOfAdjacentMines(int x, int y) {
		int row_uplimit;
		int row_downlimit;
		int col_leftlimit;
		int col_rightlimit;

		int mines = 0;

		if (y == 0) {
			row_uplimit = y;
		} else {
			row_uplimit = y - 1;
		}

		if (y == this.row - 1) {
			row_downlimit = y;
		} else {
			row_downlimit = y + 1;
		}

		if (x == 0) {
			col_leftlimit = x;
		} else {
			col_leftlimit = x - 1;
		}

		if (x == this.column - 1) {
			col_rightlimit = x;
		} else {
			col_rightlimit = x + 1;
		}

		// left
		for (int i = row_uplimit; i <= row_downlimit; i++) {
			if (this.state[i][col_leftlimit] == MineSweeperCell.MINE
					|| this.state[i][col_leftlimit] == MineSweeperCell.FLAGGED_MINE
					|| this.state[i][col_leftlimit] == MineSweeperCell.UNCOVERED_MINE) {
				if (i != y || col_leftlimit != x) {
					mines++;
				}
			}
		}
		// down

		for (int i = col_leftlimit + 1; i <= col_rightlimit; i++) {
			if (this.state[row_downlimit][i] == MineSweeperCell.MINE
					|| this.state[row_downlimit][i] == MineSweeperCell.FLAGGED_MINE
					|| this.state[row_downlimit][i] == MineSweeperCell.UNCOVERED_MINE) {
				if (i != x || row_downlimit != y) {
					mines++;
				}
			}
		}

		for (int i = row_downlimit - 1; i >= row_uplimit; i--) {
			if (this.state[i][col_rightlimit] == MineSweeperCell.MINE
					|| this.state[i][col_rightlimit] == MineSweeperCell.FLAGGED_MINE
					|| this.state[i][col_rightlimit] == MineSweeperCell.UNCOVERED_MINE) {
				if (i != y || col_rightlimit != x) {
					mines++;
				}
			}
		}

		for (int i = col_rightlimit - 1; i > col_leftlimit; i--) {
			if (this.state[row_uplimit][i] == MineSweeperCell.MINE
					|| this.state[row_uplimit][i] == MineSweeperCell.FLAGGED_MINE
					|| this.state[row_uplimit][i] == MineSweeperCell.UNCOVERED_MINE) {
				if (i != x || row_uplimit != y) {
					mines++;
				}
			}
		}
		return mines;
	}

	@Override
	public void revealBoard() {
		for (int i = 0; i < this.state.length; i++) {
			for (int j = 0; j < this.state[i].length; j++) {
				if (this.state[i][j] == MineSweeperCell.FLAGGED_MINE || this.state[i][j] == MineSweeperCell.MINE) {
					this.state[i][j] = MineSweeperCell.UNCOVERED_MINE;
				} else if (this.state[i][j] == MineSweeperCell.COVERED_CELL
						|| this.state[i][j] == MineSweeperCell.FLAG) {
					this.state[i][j] = MineSweeperCell.adjacentTo(numberOfAdjacentMines(j, i));
				}
			}
		}

	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return super.equals(other);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public void loadBoardState(String... rows) {
		if (rows.length != height()) {
			throw new IllegalArgumentException("loadBoardState() was called with " + rows.length
					+ " Strings when the board has " + height() + " rows");
		}

		for (int y = 0; y < height(); y++) {
			if (rows[y].length() != width()) {
				throw new IllegalArgumentException("loadBoardState() was called with row " + y + " = \"" + rows[y]
						+ "\" (only " + rows[y].length() + " characters) when the board has " + width() + " columns");
			}

			for (int x = 0; x < width(); x++) {
				switch (rows[y].charAt(x)) {
				case 'O':
					setCell(x, y, MineSweeperCell.COVERED_CELL);
					break;

				case 'F':
					setCell(x, y, MineSweeperCell.FLAG);
					break;

				case 'M':
					setCell(x, y, MineSweeperCell.FLAGGED_MINE);
					this.numOfMines++;
					break;

				case '+':
					setCell(x, y, MineSweeperCell.MINE);
					this.numOfMines++;
					break;

				case '*':
					setCell(x, y, MineSweeperCell.UNCOVERED_MINE);
					this.numOfMines++;
					break;

				case ' ':
					setCell(x, y, MineSweeperCell.ADJACENT_TO_0);
					break;

				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					setCell(x, y, MineSweeperCell.adjacentTo(rows[y].charAt(x) - '0'));
					break;

				default:
					throw new IllegalArgumentException("loadBoardState() was called with row " + y + " = \"" + rows[y]
							+ "\", but '" + rows[y].charAt(x) + "' is not a recognized cell state");
				}
			}
			this.row = this.state.length;
			this.column = this.state[0].length;
		}
	}

	@Override
	protected void setCell(int x, int y, MineSweeperCell value) {
		if (x >= 0 && x < this.column && y >= 0 && y < this.row) {
			this.state[y][x] = value;
		}
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

}
