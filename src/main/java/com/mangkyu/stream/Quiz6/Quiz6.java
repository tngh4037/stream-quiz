package com.mangkyu.stream.Quiz6;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class Quiz6 {

    private Student[] stuArr;

    public Quiz6() {
        stuArr = new Student[]{
                new Student("나자바", true, 1, 1, 300),
                new Student("김지미", false, 1, 1, 250),
                new Student("김자바", true, 1, 1, 200),
                new Student("이지미", false, 1, 2, 150),
                new Student("남자바", true, 1, 2, 100),
                new Student("안지미", false, 1, 2, 50),
                new Student("황지미", false, 1, 3, 100),
                new Student("강지미", false, 1, 3, 150),
                new Student("이자바", true, 1, 3, 200),
                new Student("나자바", true, 2, 1, 300),
                new Student("김지미", false, 2, 1, 250),
                new Student("김자바", true, 2, 1, 200),
                new Student("이지미", false, 2, 2, 150),
                new Student("남자바", true, 2, 2, 100),
                new Student("안지미", false, 2, 2, 50),
                new Student("황지미", false, 2, 3, 100),
                new Student("강지미", false, 2, 3, 150),
                new Student("이자바", true, 2, 3, 200)
        };
    }

    // stuArr에서 불합격(150점 미만)한 학생의 수를 남자와 여자로 구별하여라. (Boolean, List)
    public Map<Boolean, List<Student>> quiz1() {
        // Collectors.groupingBy 는 Java Stream API에서 데이터를 그룹화하는 데 사용되는 Collector 이다.
        // 이를 통해 스트림의 요소를 특정 기준에 따라 그룹화하여 "Map"으로 결과를 수집할 수 있다.
        // groupingBy는 주로 데이터 집합을 그룹으로 나누거나 특정 조건에 따라 데이터를 분류할 때 유용하다.

        return Arrays.stream(stuArr)
                .filter(s -> s.getScore() < 150)
                .collect(Collectors.groupingBy(Student::isMale));
    }

    // 각 반별 총점을 학년 별로 나누어 구하여라 (Map<Integer, Map<Integer, Integer>>)
    public Map<Integer, Map<Integer, Integer>> quiz2() {
        return Arrays.stream(stuArr)
                .collect(groupingBy(s -> s.getHak(), groupingBy(s -> s.getBan(), summingInt(s -> s.getScore()))));
    }
}

// [ Collectors.groupingby ]
// Collectors.groupingBy의 downstream 파라미터는 그룹화된 결과에 대해 추가적인 집계 연산이나 변환을 적용하는 데 사용됩니다.
// downstream은 "그룹화된 각 그룹에 대해 수행할 추가 작업을 정의하는 Collector"입니다.
// 이 파라미터를 사용하면, 그룹화된 데이터에 대해 더 복잡한 집계나 변환 작업을 할 수 있습니다.

// 참고)
// classifier: 각 요소를 그룹화하는 기준이 되는 함수입니다. 스트림의 요소를 이 함수를 통해 그룹화 키로 변환합니다.
// downstream: 그룹화된 각 항목에 대해 추가적으로 적용할 집계 연산을 정의합니다. Collector 타입으로, 각 그룹에 대해 최종 결과를 생성합니다.

// ex) downstream으로는 Collectors.counting(), Collectors.summingInt(), Collectors.toSet() 등 다양한 Collector를 사용할 수 있습니다.

class GroupingByExample1 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Edward");

        // 이름의 첫 문자로 그룹화
        Map<Character, List<String>> groupedByFirstLetter = names.stream()
                .collect(Collectors.groupingBy(name -> name.charAt(0)));

        System.out.println(groupedByFirstLetter);
    }
}

class GroupingByExample2 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Edward");

        // 이름의 첫 문자로 그룹화하고, 각 그룹의 문자열 길이 합계를 계산
        Map<Character, Integer> lengthSumByFirstLetter = names.stream()
                .collect(Collectors.groupingBy(
                        name -> name.charAt(0),
                        Collectors.summingInt(String::length) // downstream collector
                ));

        System.out.println(lengthSumByFirstLetter);
    }
}

class GroupingByExample3 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Alice", "Edward", "Bob");

        // 이름의 첫 문자로 그룹화하고, 각 그룹에서 중복을 제거
        Map<Character, Set<String>> uniqueNamesByFirstLetter = names.stream()
                .collect(Collectors.groupingBy(
                        name -> name.charAt(0),
                        Collectors.toSet() // downstream collector
                ));

        System.out.println(uniqueNamesByFirstLetter);
    }
}