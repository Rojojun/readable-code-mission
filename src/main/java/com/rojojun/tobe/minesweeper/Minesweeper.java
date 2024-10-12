package com.rojojun.tobe.minesweeper;

import cleancode.minesweeper.tobe.game.Game;
import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;
import cleancode.minesweeper.tobe.minesweeper.board.GameStatus;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import cleancode.minesweeper.tobe.minesweeper.exception.GameExcpetion;
import cleancode.minesweeper.tobe.minesweeper.io.BoardIndexConverter;
import cleancode.minesweeper.tobe.minesweeper.io.InputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.OutputHandler;
import cleancode.minesweeper.tobe.minesweeper.user.UserAction;

public class Minesweeper implements Game {
    private final GameBoard gameBoard;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private GameStatus gameStatus;

    public Minesweeper(GameConfig gameConfig) {
        gameBoard = new GameBoard(gameConfig.getLevel());
        this.inputHandler = gameConfig.getInputHandler();
        this.outputHandler = gameConfig.getOutputHandler();
        gameStatus = GameStatus.IN_PROGRESS;
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
        gameStatus = GameStatus.IN_PROGRESS;
    }

    @Override
    public void run() {
        outputHandler.showGameStartComments();
        gameBoard.initializeGame();

        while (gameBoard.isInProgress()) {
            try {
                outputHandler.showBoard(gameBoard);

                CellPosition cellPosition = getCellInputFromUser();
                UserAction userActionInput = getUserActionInputFromUser();
                actOnCell(cellPosition, userActionInput);
            } catch (GameExcpetion e) {
                outputHandler.showExceptionMessage(e);
            } catch (Exception e) {
                outputHandler.showSimpleExceptionMessage("프로그램에 문제가 생겼습니다.");
            }
        }

        outputHandler.showBoard(gameBoard);

        if (gameBoard.isWinStatus()) {
            outputHandler.showGameWinningComment();
        }
        if (gameBoard.isLoseStatus()) {
            outputHandler.showGameLosingComment();
        }
    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        CellPosition cellPosition = inputHandler.getCellPositionFromUser();
        if (gameBoard.isInvalidCellPosition(cellPosition)) {
            throw new GameExcpetion("잘못된 좌표를 선택하셨습니다.");
        }
        return cellPosition;
    }

    private void actOnCell(CellPosition cellPosition, UserAction userActionInput) {
        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flagAt(cellPosition);
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            gameBoard.openAt(cellPosition);

            return;
        }

        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private boolean doesUserChooseToOpenCell(UserAction userActionInput) {
        return userActionInput == UserAction.OPEN;
    }

    private boolean doesUserChooseToPlantFlag(UserAction userActionInput) {
        return userActionInput == UserAction.FLAG;
    }

    private UserAction getUserActionInputFromUser() {
        outputHandler.showCommentForUserAction();
        return inputHandler.getUserAction();
    }
}
