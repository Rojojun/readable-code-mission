package com.rojojun.studycafe.io.provider;

import com.rojojun.studycafe.model.DisplayablePass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface FileProvider {
    default List<String[]> readFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<String[]> parsedLines = new ArrayList<>();
            for (String line : lines) {
                String[] values = line.split(",");
                parsedLines.add(values);
            }
            return parsedLines;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다: " + filePath, e);
        }
    }
}
