package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// 스트림(Stream)은 Java의 컬렉션 프레임워크에서 제공하는 데이터 처리 API로, 데이터를 연속적으로 처리할 수 있는 "추상적인 개념"입니다.
// Stream 자체는 데이터를 저장하거나 보유하는 것이 아니라, 데이터의 처리를 "지연 평가 방식"으로 지원합니다.
public class Quiz1Ex {

    private static final String TARGET = "좋아";
    private static final int TARGET_LENGTH = TARGET.length();

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = new ArrayList<>();
        csvLines.add(new String[]{"김프로", " 축구:농구:야구", "구기종목 좋아요"});
        csvLines.add(new String[]{"정프로", " 개발:당구:축구", "개발하는데 뛰긴 싫어"});
        csvLines.add(new String[]{"앙몬드", " 피아노", "죠르디가 좋아요 좋아좋아너무좋아"});
        csvLines.add(new String[]{"죠르디", " 스포츠댄스:개발", "개발하는 죠르디 좋아"});
        csvLines.add(new String[]{"박프로", " 골프:야구", "운동이 좋아요"});
        csvLines.add(new String[]{"정프로", " 개발:축구:농구", "개발도 좋고 운동도 좋아"});

        // 1) map(line -> line[1].replaceAll("\\s", ""))
        //  ==> Stream<String> map = Stream.of("축구:농구:야구", "개발:당구:축구", "피아노", "스포츠댄스:개발", "골프:야구", "개발:축구:농구")
        // 참고) map 연산: 각 요소를 변환하여 새로운 스트림을 생성합니다. ( map 연산을 사용할 때, 스트림의 데이터는 변환되지만 최종적으로 생성되는 것은 하나의 스트림입니다. )
        //           ㄴ 역할: map 연산은 스트림의 각 요소를 주어진 함수로 변환합니다.
        //           ㄴ 결과: 변환된 요소들을 포함하는 새로운 스트림을 생성합니다.

        // 2) Arrays.stream(hobbies.split(":"))
        //  : "축구:농구:야구" → Stream.of("축구", "농구", "야구")
        //  : "개발:당구:축구" → Stream.of("개발", "당구", "축구")
        //  : "피아노" → Stream.of("피아노")
        //  : "스포츠댄스:개발" → Stream.of("스포츠댄스", "개발")
        //  : "골프:야구" → Stream.of("골프", "야구")
        //  : "개발:축구:농구" → Stream.of("개발", "축구", "농구")

        // 3) flatMap(hobbies -> Arrays.stream(hobbies.split(":")))  ( flatMap은 이러한 스트림들을 하나로 합쳐서 단일 스트림을 만듭니다. 최종적으로, 개별 취미 항목이 하나의 스트림으로 평탄화됩니다. )
        //  : Stream<String> = Stream.of("축구", "농구", "야구", "개발", "당구", "축구", "피아노", "스포츠댄스", "개발", "골프", "야구", "개발", "축구", "농구")
        // 참고) flatMap 연산: 각 요소를 여러 요소로 변환하고 평탄화하여 단일 스트림으로 만듭니다.
        //           ㄴ 역할: flatMap 연산은 각 요소를 여러 요소로 변환하고 이를 평탄화하여 단일 스트림으로 만듭니다. 즉, "중첩된 스트림을 평면적인 단일 스트림으로 만들어"줍니다.
        //           ㄴ 결과: flatMap은 이 배열의 요소들을 개별 스트림으로 만들어 평탄화합니다.
        // 참고) flatMap 메서드는 스트림의 중첩 수준에 상관없이 평탄화할 수 있지만, 한 번의 호출로는 직접적으로 한 단계만 평탄화합니다.
        // 만약 Stream<Stream<Stream<T>>> 같은 구조를 평탄화하려면, flatMap을 두 번 호출해야 합니다.

        // 4) collect(Collectors.toMap(...))
        //  : flatMap에서 얻어진 개별 취미 항목들을 수집하여 Map<String, Integer> 형태로 변환합니다. 각 취미의 출현 횟수를 계산합니다.
        return csvLines.stream()
                .map(line -> line[1].replaceAll("\\s", ""))
                .flatMap(hobbies -> Arrays.stream(hobbies.split(":")))
                .collect(Collectors.toMap(hobby -> hobby, hobby -> 1, (oldValue, newValue) -> ++newValue));
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
                .mapToInt(Integer::intValue)
                .sum();

        // 1) reduce(0, (a, b) -> a + b);
        // 이 함수는 스트림의 요소를 순회하면서, 현재 누적된 값(a)과 스트림의 현재 요소(b)를 더하여 최종 결과를 생성합니다.
        // 참고로 0은 초기값이다.
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext(); // 첫 번째 줄(헤더)을 읽어서 건너뛰기
        return csvReader.readAll(); // 나머지 모든 데이터 줄을 읽어 List<String[]>로 반환
    }

}

// [ 이름, 취미, 소개 ]
// 김프로, 축구:농구:야구, 구기종목 좋아요
// 정프로, 개발:당구:축구, 개발하는데 뛰긴 싫어
// 앙몬드, 피아노, 죠르디가 좋아요 좋아좋아너무좋아
// 죠르디, 스포츠댄스:개발, 개발하는 죠르디 좋아
// 박프로, 골프:야구, 운동이 좋아요
// 정프로, 개발:축구:농구, 개발도 좋고 운동도 좋아

