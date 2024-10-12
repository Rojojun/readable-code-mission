package com.rojojun.tobe.minesweeper.board.cell;

public enum CellStatus {
    EMPTY("빈 칸"),
    FLAG("깃발"),
    LAND_MINE("지뢰"),
    NUMBER("숫자"),
    UNCHECKED("확인 전");

    CellStatus(String description) {
        this.description = description;
    }

    private final String description;

}
