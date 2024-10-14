package com.rojojun.tobe.minesweeper.board.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CellPositionsTest {
    @DisplayName("게임을 초기에 시작할 때 count 수 만큼 무작위한 셀을 추출한다.")
    @Test
    void extractRandomPositions() {
        // given
        int count = 3;
        CellPositions cellPositions = CellPositions.of(cellPositionList3By3Size);

        // when
        List<CellPosition> result = cellPositions.extractRandomPositions(count);

        // then
        assertThat(result).hasSize(count);
        assertThat(result).containsExactlyInAnyOrderElementsOf(
                result.stream().distinct().toList()
        );
        assertThat(cellPositions.getPositions())
                .containsAll(result);
    }

    @DisplayName("게임을 초기에 시작할 때 추출된 셀 수 만큼 남은 셀을 반환한다.")
    @Test
    void subtract() {
        // given
        List<CellPosition> random3ExtractedCellList = new ArrayList<>(cellPositionList3By3Size);
        Collections.shuffle(random3ExtractedCellList);
        random3ExtractedCellList = random3ExtractedCellList.subList(0, 3);
        CellPositions cellPositions = CellPositions.of(cellPositionList3By3Size);

        // when
        List<CellPosition> result = cellPositions.subtract(random3ExtractedCellList);

        // then
        assertThat(result).hasSize(6);
        assertThat(result).doesNotContainAnyElementsOf(
                random3ExtractedCellList.stream().distinct().toList()
        );
        assertThat(result.size() + random3ExtractedCellList.size()).isEqualTo(9);
    }

    private List<CellPosition> cellPositionList3By3Size = List.of(
            CellPosition.of(0, 0),
            CellPosition.of(0, 1),
            CellPosition.of(0, 2),
            CellPosition.of(1, 0),
            CellPosition.of(1, 1),
            CellPosition.of(1, 2),
            CellPosition.of(2, 0),
            CellPosition.of(2, 1),
            CellPosition.of(2, 2)
    );
}