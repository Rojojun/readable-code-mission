package com.rojojun.studycafe.io.provider;

import com.rojojun.studycafe.model.StudyCafePass;
import com.rojojun.studycafe.model.StudyCafePassType;
import com.rojojun.studycafe.model.StudyCafePasses;

import java.util.ArrayList;
import java.util.List;

public class CafeInfoFileProvider implements FileProvider{
    private static final String PASS_LIST_CSV = "src/main/resources/studycafe/pass-list.csv";

    public StudyCafePasses readStudyCafePasses() {
        List<String[]> lines = readFile(PASS_LIST_CSV);
        List<StudyCafePass> studyCafePasses = new ArrayList<>();

        for (String[] values : lines) {
            StudyCafePass pass = createPass(values);
            studyCafePasses.add(pass);
        }

        return StudyCafePasses.of(studyCafePasses);
    }

    private StudyCafePass createPass(String[] values) {
        StudyCafePassType type = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);
        double discountRate = Double.parseDouble(values[3]);

        StudyCafePass pass = StudyCafePass.of(type, duration, price, discountRate);
        return pass;
    }
}
