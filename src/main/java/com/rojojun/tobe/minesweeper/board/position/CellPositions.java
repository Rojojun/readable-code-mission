package com.rojojun.tobe.minesweeper.board.position;

import cleancode.minesweeper.tobe.minesweeper.board.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {
    private final List<CellPosition> cellPositions;

    private CellPositions(List<CellPosition> cellPositions) {
        this.cellPositions = cellPositions;
    }

    public static CellPositions of(List<CellPosition> cellPositions) {
        return new CellPositions(cellPositions);
    }

    public static CellPositions from(Cell[][] cells) {
        List<CellPosition> cellPositions = new ArrayList<>();

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositions.add(cellPosition);
            }
        }

        return of(cellPositions);
    }

    public List<CellPosition> getPositions() {
        return new ArrayList<>(cellPositions);
    }

    public List<CellPosition> extractRandomPositions(int landmineCount) {
        List<CellPosition> positions = new ArrayList<>(cellPositions);
        Collections.shuffle(positions);

        return cellPositions.subList(0, landmineCount);
    }

    public List<CellPosition> sutract(List<CellPosition> positions) {
        List<CellPosition> innerCellPositions = new ArrayList<>(cellPositions);

        CellPositions positionsToSubtract = CellPositions.of(positions);

        return innerCellPositions.stream()
                .filter(positionsToSubtract::doesNotContain)
                .toList();
    }

    private boolean doesNotContain(CellPosition position) {
        return !cellPositions.contains(position);
    }
}
