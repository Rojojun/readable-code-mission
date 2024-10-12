package com.rojojun.tobe.minesweeper.io;

import com.rojojun.tobe.minesweeper.board.GameBoard;
import com.rojojun.tobe.minesweeper.exception.GameException;

public interface OutputHandler {

    void showGameStartComments();

    void showBoard(GameBoard board);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();

    void showCommentForUserAction();

    void showExceptionMessage(GameException e);

    void showSimpleMessage(String message);

}
