package com.rojojun.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.board.cell.*;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;

import java.util.List;
import java.util.Stack;

public class GameBoard {
    private final Cell[][] board;
    private final int landmineCount;
    private GameStatus gameStatus;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        board = new Cell[rowSize][colSize];

        landmineCount = gameLevel.getMineCount();
        initializeGameStatuse();
    }

    public void initializeGame() {
        initializeGameStatuse();
        CellPositions cellPositions = CellPositions.from(board);

        List<CellPosition> allPositions = cellPositions.getPositions();

        for (CellPosition cellPosition : allPositions) {
            updateCellAtPosition(cellPosition, new EmptyCell());
        }

        List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landmineCount);
        for (CellPosition cellPosition : landMinePositions) {
            updateCellAtPosition(cellPosition, new LandMineCell());
        }

        List<CellPosition> numberPositionCandidates = cellPositions.sutract(landMinePositions);
        for (CellPosition cellPosition : numberPositionCandidates) {
            int count = countNearbyLandMines(cellPosition);
            if (count != 0) {
                updateCellAtPosition(cellPosition,new NumberCell(count));
            }
        }
    }

    private void initializeGameStatuse() {
        gameStatus = GameStatus.IN_PROGRESS;
    }

    private void updateCellAtPosition(CellPosition cellPosition, Cell cell) {
        board[cellPosition.getRowIndex()][cellPosition.getColumnIndex()] = cell;
    }

    public int getColSize() {
        return board[0].length;
    }

    public int getRowSize() {
        return board.length;
    }

    public void flagAt(CellPosition cellPosition) {
        findCell(cellPosition).flag();

        checkIfGameIsOver();
    }

    private void checkIfGameIsOver() {
        if (isAllCellChecked()) {
            changeGameStatusWin();
        }
    }

    private void changeGameStatusWin() {
        gameStatus = GameStatus.WIN;
    }

    public void open(CellPosition cellPosition) {
        findCell(cellPosition).open();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColumnIndex()];
    }

    public boolean isLandMineCell(CellPosition cellPosition) {
        return findCell(cellPosition).isLandMine();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        return cellPosition.isRowSizeMoreThanOrEqaul(rowSize) || cellPosition.isColIndexMoreThanOrEqaul(colSize);
    }

    public void openSurroundedCells(CellPosition cellPosition) {
        Stack<CellPosition> stack = new Stack<>();
        stack.push(cellPosition);

        while (!stack.isEmpty()) {
            openAndPushCellAt(stack);
        }
    }

    private void openAndPushCellAt(Stack<CellPosition> stack) {
        CellPosition cuurentCellPosition = stack.pop();

        if (findCell(cuurentCellPosition).isOpened()) {
            return;
        }
        if (isLandMineCell(cuurentCellPosition)) {
            return;
        }

        open(cuurentCellPosition);

        if (findCell(cuurentCellPosition).hasLandMineCount()) {
            return;
        }

        List<CellPosition> surroundedPositions = calculateSurroundedPositions(cuurentCellPosition);
        for (CellPosition surroundedPosition : surroundedPositions) {
            stack.push(surroundedPosition);
        }
    }

    public CellSnapshot getSnapshot(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSnapshot();
    }

    private static boolean isMovealbe(CellPosition cellPosition, RelativePosition relativePosition) {
        return cellPosition.isCalculatable(relativePosition);
    }

    public boolean isAllCellChecked() {
        Cells cells = Cells.from(board);
        return cells.isAllChecked();
    }

    private int countNearbyLandMines(CellPosition cellPosition) {
        return (int) calculateSurroundedPositions(cellPosition).stream()
                .filter(this::isLandMineCell)
                .count();
    }

    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::isCalculatable)
                .map(cellPosition::calculatePositionBy)
                .filter(postion -> postion.isRowIndexLessThan(getRowSize()))
                .filter(postion -> postion.isColIndexLessThan(getColSize()))
                .toList();
    }

    public boolean isInProgress() {
        return gameStatus == GameStatus.IN_PROGRESS;
    }

    public void openAt(CellPosition cellPosition) {
        if (isLandMineCell(cellPosition)) {
            open(cellPosition);
            changeGameStatusToLose();
            return;
        }

        openSurroundedCells(cellPosition);
        checkIfGameIsOver();
    }

    public boolean isLoseStatus() {
        return gameStatus == GameStatus.LOSE;
    }

    public boolean isWinStatus() {
        return gameStatus == GameStatus.WIN;
    }

    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }
}
