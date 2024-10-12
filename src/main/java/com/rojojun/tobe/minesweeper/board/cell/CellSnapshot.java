package com.rojojun.tobe.minesweeper.board.cell;

public class CellSnapshot {
    private final CellStatus status;
    private final int nearbyLandMineCounts;

    private CellSnapshot(CellStatus status, int nearbyLandMineCounts) {
        this.status = status;
        this.nearbyLandMineCounts = nearbyLandMineCounts;
    }

    public static CellSnapshot of(CellStatus status, int nearbyLandMineCounts) {
        return new CellSnapshot(status, nearbyLandMineCounts);
    }

    public static CellSnapshot ofEmpty() {
        return new CellSnapshot(CellStatus.EMPTY, 0);
    }

    public static CellSnapshot ofFlag() {
        return new CellSnapshot(CellStatus.FLAG, 0);
    }

    public static CellSnapshot ofLandMine() {
        return new CellSnapshot(CellStatus.LAND_MINE, 0);
    }

    public static CellSnapshot ofNumber(int nearbyLandMineCounts) {
        return new CellSnapshot(CellStatus.NUMBER, nearbyLandMineCounts);
    }

    public static CellSnapshot ofUnchecked() {
        return new CellSnapshot(CellStatus.UNCHECKED, 0);
    }


    public CellStatus getStatus() {
        return status;
    }

    public int getNearbyLandMineCount() {
        return nearbyLandMineCounts;
    }

    public boolean isSameStatus(CellStatus cellStatus) {
        return status == cellStatus;
    }
}
