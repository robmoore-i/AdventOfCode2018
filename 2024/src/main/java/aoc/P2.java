package aoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P2 {

    public static void main(String[] args) throws IOException {
        var puzzleInput = Paths.get("2024/puzzle_inputs/puzzle_input_2.txt");
        var reports = Files.readAllLines(puzzleInput).parallelStream()
            .filter(it -> !it.isBlank())
            .map(line -> new Report(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList()))
            .toList();
        var numSafeReports = reports.parallelStream().filter(Report::isSafe).count();
        System.out.println(numSafeReports);
    }

    private record Report(List<Integer> levels) {

        public boolean isSafe() {
            if (levels.size() <= 1) {
                return true;
            }

            var differences = new ArrayList<Integer>(levels.size() - 1);
            for (int i = 0; i < levels.size() - 1; i++) {
                differences.add(levels.get(i + 1) - levels.get(i));
            }

            boolean smallCondition = differences.parallelStream().map(Math::abs).allMatch(it -> it >= 1 && it <= 3);
            boolean sameSignCondition = differences.parallelStream().allMatch(it -> it > 0) || differences.parallelStream().allMatch(it -> it < 0);

            return smallCondition && sameSignCondition;
        }
    }
}
