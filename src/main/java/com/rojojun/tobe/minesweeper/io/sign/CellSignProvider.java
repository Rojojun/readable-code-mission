package com.rojojun.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellStatus;

import java.util.Arrays;

public enum CellSignProvider implements CellSignProvidable {
    EMPTY("빈 칸", CellStatus.EMPTY) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return EMPTY_SIGN;
        }
    },
    FLAG("깃발", CellStatus.FLAG) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return FLAG_SIGN;
        }
    },
    LAND_MINE("지뢰", CellStatus.LAND_MINE) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return MINE_SIGN;
        }
    },
    NUMBER("숫자", CellStatus.NUMBER) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return String.valueOf(cellSnapshot.getNearbyLandMineCount());
        }
    },
    UNCHECKED("확인 전", CellStatus.UNCHECKED) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return UNCHECKED_SIGN;
        }
    };

    private static final String EMPTY_SIGN = "■";
    private static final String FLAG_SIGN = "⚑";
    private static final String MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";

    CellSignProvider(String description, CellStatus status) {
        this.description = description;
        this.status = status;
    }

    private final String description;
    private final CellStatus status;

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(status);
    }

    public static String findCellSignFrom(CellSnapshot cellSnapshot) {
        return Arrays.stream(values())
                .filter(provider -> provider.supports(cellSnapshot))
                .findFirst()
                .map(provider -> provider.provide(cellSnapshot))
                .orElseThrow(() -> new IllegalArgumentException("Unknown cell sign"));
    }
}
