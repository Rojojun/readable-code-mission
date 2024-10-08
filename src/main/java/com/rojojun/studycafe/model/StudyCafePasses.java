package com.rojojun.studycafe.model;

import java.util.List;

public class StudyCafePasses {
    private final List<StudyCafePass> passList;

    private StudyCafePasses(List<StudyCafePass> passList) {
        this.passList = passList;
    }

    public static StudyCafePasses of(List<StudyCafePass> passList) {
        return new StudyCafePasses(passList);
    }

    public List<StudyCafePass> getListBy(StudyCafePassType studyCafePassType) {
        return passList.stream()
                .filter(studyCafePassType::isTypeEqual)
                .toList();
    }
}
