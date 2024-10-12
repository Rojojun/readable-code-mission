package com.rojojun.tobe.minesweeper.board.position;

import java.util.Objects;

public class CellPosition {
    private final int rowIndex;
    private final int columnIndex;

    private CellPosition(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || columnIndex < 0) {
            throw new IllegalArgumentException("Row and column positions must be non-negative");
        }
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public static CellPosition of(int rowIndex, int columnIndex) {
        return new CellPosition(rowIndex, columnIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition that = (CellPosition) o;
        return rowIndex == that.rowIndex && columnIndex == that.columnIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, columnIndex);
    }

    public boolean isRowSizeMoreThanOrEqaul(int rowSize) {
        return this.rowIndex >= rowSize;
    }

    public boolean isColIndexMoreThanOrEqaul(int columnSize) {
        return this.columnIndex >= columnSize;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public CellPosition calculatePositionBy(RelativePosition relativePosition) {
        if (this.isCalculatable(relativePosition)) {
            return CellPosition.of(
                    this.rowIndex + relativePosition.getDeltaRow(),
                    this.columnIndex + relativePosition.getDeltaCol());
        }
        throw new IllegalArgumentException("Cannot calculate position because the position is out of bounds");
    }

    public boolean isCalculatable(RelativePosition relativePosition) {
        return this.rowIndex + relativePosition.getDeltaRow() >= 0 &&
                this.columnIndex + relativePosition.getDeltaCol() >= 0;
    }

    private boolean isMovealbe(CellPosition cellPosition, RelativePosition relativePosition) {
        return cellPosition.isCalculatable(relativePosition);
    }

    public boolean isRowIndexLessThan(int rowSize) {
        return this.rowIndex < rowSize;
    }

    public boolean isColIndexLessThan(int columnIndex) {
        return this.columnIndex < columnIndex;
    }
}
