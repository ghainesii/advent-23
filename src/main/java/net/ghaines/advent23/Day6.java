package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
class Day6 {

	/* Solutions in case refactoring breaks anything
4403592
38017587
	 */

	void run() throws IOException {
		var resource = new ClassPathResource("day6.txt").getFile().toPath();
		var lines = Files.readAllLines(resource);
		puzzle1(lines);
		puzzle2(lines);
	}

	private void puzzle1(List<String> input) {
		var times = input.get(0).split("\\W+");
		var distances = input.get(1).split("\\W+");
		var answer = 1;
		for (int i = 1; i < times.length; i++) {
			var time = Integer.parseInt(times[i]);
			var distGoal = Integer.parseInt(distances[i]);
			var winPossibilities = 0;
			for (int j = 1; j < time; j++) {
				var distTraveled = j * (time - j);
				if (distTraveled > distGoal) winPossibilities++;
			}
			answer *= winPossibilities;
		}
		System.out.println(answer);
	}

	private void puzzle2(List<String> input) {
		var time = Long.parseLong(input.get(0).split(":")[1].replaceAll(" ", ""));
		var distGoal = Long.parseLong(input.get(1).split(":")[1].replaceAll(" ", ""));
		var winPossibilities = 0;
		for (int j = 1; j < time; j++) {
			var distTraveled = j * (time - j);
			if (distTraveled > distGoal) winPossibilities++;
		}
		System.out.println(winPossibilities);
	}
}