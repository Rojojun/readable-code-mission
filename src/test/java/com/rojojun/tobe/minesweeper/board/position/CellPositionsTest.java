package com.rojojun.tobe.minesweeper.board.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @DisplayName("")
    @Test
    void subtract() {
        // given

        // when

        // then

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