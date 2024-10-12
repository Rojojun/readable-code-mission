package com.rojojun.tobe.minesweeper.io.sign;

import com.rojojun.tobe.minesweeper.board.cell.CellSnapshot;

public interface CellSignProvidable {

    boolean supports(CellSnapshot cellSnapshot);

    String provide(CellSnapshot cellSnapshot);

}
