package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
class Day8 {

	/* Solutions in case refactoring breaks anything
12599
8245452805243
	 */

	enum Puzzle {
		ONE, TWO
	}

	void run() throws IOException {
		var resource = new ClassPathResource("day8.txt").getFile().toPath();
		var lines = Files.readAllLines(resource);
		solve(lines, Puzzle.ONE);
		solve(lines, Puzzle.TWO);
	}

	record Direction(String left, String right) {
	}

	private void solve(List<String> inputs, Puzzle puzzle) {
		var directions = inputs.getFirst();
		var map = new HashMap<String, Direction>();
		for (int i = 1; i < inputs.size(); i++) {
			if (inputs.get(i).isBlank()) continue;
			var input = inputs.get(i).split(" ");
			var key = input[0];
			var left = input[2].replace("(", "").replace(",", "");
			var right = input[3].replace(")", "");
			var dir = new Direction(left, right);
			map.put(key, dir);
		}
		if (puzzle.equals(Puzzle.ONE)) {
			var steps = 0;
			var next = "AAA";
			while (!next.equals("ZZZ")) {
				for (int i = 0; i < directions.length(); i++) {
					var direction = directions.charAt(i);
					var mapEntry = map.get(next);
					next = direction == 'L' ? mapEntry.left : mapEntry.right;
					steps++;
					if ("ZZZ".equals(next)) {
						break;
					}
				}
			}
			System.out.println(steps);
		} else {
			var startPositions = map.keySet().stream().filter(key -> key.endsWith("A")).toList();
			var stepList = new ArrayList<Integer>();
			for (var pos : startPositions) {
				var dir = map.get(pos);
				var step = 0;
				var done = false;
				while (!done) {
					for (char direction : directions.toCharArray()) {
						step++;
						pos = direction == 'L' ? dir.left : dir.right;
						if (pos.endsWith("Z")) {
							done = true;
							break;
						}
						dir = map.get(pos);
					}
				}
				stepList.add(step);
			}
			var lcm = findLCM(stepList);
			System.out.println(lcm);
		}
	}

	private long findLCM(List<Integer> steps) {
		var num = (long) steps.getFirst();
		for (var step : steps) {
			num = lcm(num, step);
		}
		return num;
	}

	private static long lcm(long a, long b) {
		return (a / gcd(a, b)) * b;
	}

	private static long gcd(long a, long b) {
		while (b != 0) {
			var temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

}