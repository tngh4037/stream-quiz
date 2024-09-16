package com.mangkyu.stream.Quiz5;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Quiz5 {

    private static final String[] STRING_ARR = {"aaa", "bb", "c", "dddd"};

    // 5.1 모든 문자열의 길이를 더한 결과를 출력하여라.
    public int quiz1() {
        return Arrays.stream(STRING_ARR)
                .mapToInt(str -> str.length())
                .sum();
    }

    // 5.2 문자열 중에서 가장 긴 것의 길이를 출력하시오.
    public int quiz2() {
        return Arrays.stream(STRING_ARR)
                .mapToInt(str -> str.length())
                .max()
                .orElse(0);
    }

    // 5.3 임의의 로또번호(1~45)를 정렬해서 출력하시오.
    public List<Integer> quiz3() {
        // java.util.Random 클래스의 ints() 메서드를 사용하여 랜덤한 IntStream을 생성할 수 있다.
        /*
        ex)
            Random random = new Random();
            // 1부터 46까지의 랜덤 숫자를 5개 생성
            IntStream randomNumbers = random.ints(5, 1, 47);

            // 결과를 리스트로 변환하고 출력
            randomNumbers.boxed()
                         .collect(Collectors.toSet())
                         .forEach(System.out::println);
         */

        return new Random().ints(1, 46)
                .distinct()
                .limit(6)
                .boxed()
                .sorted()
                .collect(Collectors.toList());
    }

    // 5.4 두 개의 주사위를 굴려서 나온 눈의 합이 6인 경우를 모두 출력하시오.
    public List<Integer[]> quiz4() {
        return IntStream.rangeClosed(1, 6)
                .boxed()
                .flatMap(i -> IntStream.rangeClosed(1, 6)
                            .boxed()
                            .filter(v -> i+v == 6)
                            .map(v -> new Integer[]{i, v}))
                .collect(Collectors.toList());
    }

}
