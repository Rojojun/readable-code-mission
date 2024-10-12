package com.rojojun.tobe.minesweeper.io;

import com.rojojun.tobe.minesweeper.board.position.CellPosition;
import com.rojojun.tobe.minesweeper.user.UserAction;

public interface InputHandler {

    UserAction getUserActionFromUser();

    CellPosition getCellPositionFromUser();

}
