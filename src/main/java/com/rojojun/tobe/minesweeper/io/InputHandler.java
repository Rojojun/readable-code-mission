package com.rojojun.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.user.UserAction;

public interface InputHandler {
    String getUserInput();

    UserAction getUserAction();

    CellPosition getCellPositionFromUser();
}
