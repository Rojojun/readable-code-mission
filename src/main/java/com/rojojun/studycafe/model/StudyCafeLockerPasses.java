package com.rojojun.studycafe.model;

import java.util.List;
import java.util.Optional;

public class StudyCafeLockerPasses {
    private final List<StudyCafeLockerPass> studyCafeLockerPassList;

    private StudyCafeLockerPasses(List<StudyCafeLockerPass> studyCafeLockerPassList) {
        this.studyCafeLockerPassList = studyCafeLockerPassList;
    }

    public static StudyCafeLockerPasses of(List<StudyCafeLockerPass> studyCafeLockerPassList) {
        return new StudyCafeLockerPasses(studyCafeLockerPassList);
    }

    public Optional<StudyCafeLockerPass> findMatchingLockerPass(StudyCafePass selectedPass) {
        return studyCafeLockerPassList.stream()
                .filter(option -> selectedPass.isPassTypeEqual(option) && selectedPass.isDurationEqual(option))
                .findFirst();
    }
}
