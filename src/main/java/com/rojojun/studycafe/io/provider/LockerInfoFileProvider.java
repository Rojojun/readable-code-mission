package com.rojojun.studycafe.io.provider;

import com.rojojun.studycafe.model.DisplayablePass;
import com.rojojun.studycafe.model.StudyCafeLockerPass;
import com.rojojun.studycafe.model.StudyCafeLockerPasses;
import com.rojojun.studycafe.model.StudyCafePassType;

import java.util.ArrayList;
import java.util.List;

public class LockerInfoFileProvider implements FileProvider{
    private static final String LOCKER_CSV = "src/main/resources/studycafe/locker.csv";

    public StudyCafeLockerPasses readLockerPasses() {
        List<String[]> lines = readFile(LOCKER_CSV);
        List<StudyCafeLockerPass> lockerPasses = new ArrayList<>();

        for (String[] values : lines) {
            StudyCafePassType type = StudyCafePassType.valueOf(values[0]);
            int duration = Integer.parseInt(values[1]);
            int price = Integer.parseInt(values[2]);

            StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(type, duration, price);
            lockerPasses.add(lockerPass);
        }

        return StudyCafeLockerPasses.of(lockerPasses);
    }
}