package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
class Day9 {

	/* Solutions in case refactoring breaks anything
1479011877
973
	 */

	enum Puzzle {
		ONE, TWO
	}

	void run() throws IOException {
		var resource = new ClassPathResource("day9.txt").getFile().toPath();
		var lines = Files.readAllLines(resource);
		solve(lines, Puzzle.ONE);
		solve(lines, Puzzle.TWO);
	}

	private void solve(List<String> inputs, Puzzle puzzle) {
		var sum = 0;
		for (var input : inputs) {
			var nums = Arrays.stream(input.split(" ")).map(Integer::parseInt).toList();
			sum += puzzle == Puzzle.ONE ? getNext(nums) : getPrior(nums);
		}
		System.out.println(sum);

	}

	private Integer getNext(List<Integer> nums) {
		var diffs = getDifference(nums);
		var differenceIsZero = diffs.stream().distinct().count() == 1;
		if (differenceIsZero) {
			return nums.getLast() + diffs.getFirst();
		}
		return nums.getLast() + getNext(diffs);
	}

	private Integer getPrior(List<Integer> nums) {
		var diffs = getDifference(nums);
		var differenceIsZero = diffs.stream().distinct().count() == 1;
		if (differenceIsZero) {
			return nums.getFirst() - diffs.getFirst();
		}
		return nums.getFirst() - getPrior(diffs);
	}

	private List<Integer> getDifference(List<Integer> nums) {
		var diffs = new ArrayList<Integer>();
		for (int i = 0; i < nums.size() - 1; i++) {
			var diff = nums.get(i + 1) - nums.get(i);
			diffs.add(diff);
		}
		return diffs;
	}

}