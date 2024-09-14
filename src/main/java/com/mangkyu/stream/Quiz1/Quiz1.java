package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Quiz1 {

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                .map(hobbies -> hobbies[1].replaceAll("\\s", ""))
                .flatMap(hobby -> Arrays.stream(hobby.split(":")))
                .collect(Collectors.toMap(hobby -> hobby, hobby -> 1, (oldValue, newValue) -> ++oldValue));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return  csvLines.stream()
                .filter(users -> users[0].startsWith("정"))
                .map(hobbies -> hobbies[1].replaceAll("\\s", ""))
                .flatMap(hobby -> Arrays.stream(hobby.split(":")))
                .collect(Collectors.toMap(hobby -> hobby, hobby -> 1, (oldValue, newValue) -> ++oldValue));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();
        String targetStr = "좋아";
        return csvLines.stream()
                .map(intros -> intros[2])
                .map(intro -> {
                    String str = intro.replace(targetStr, "");
                    return (intro.length() - str.length()) / targetStr.length();
                })
                .reduce(0, (beforeValue, newValue) -> beforeValue + newValue);
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}

// 이름, 취미, 소개
// 김프로, 축구:농구:야구, 구기종목 좋아요
// 정프로, 개발:당구:축구, 개발하는데 뛰긴 싫어
// 앙몬드, 피아노, 죠르디가 좋아요 좋아좋아너무좋아
// 죠르디, 스포츠댄스:개발, 개발하는 죠르디 좋아
// 박프로, 골프:야구, 운동이 좋아요
// 정프로, 개발:축구:농구, 개발도 좋고 운동도 좋아