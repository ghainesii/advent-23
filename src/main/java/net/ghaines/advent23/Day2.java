package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

@Component
class Day2 {

	/* Solutions in case refactoring breaks anything
	2204
	71036
	 */

	void run() throws IOException {
		var sum = new AtomicInteger();
		var resource = new ClassPathResource("day2.txt").getFile().toPath();
		Files.readAllLines(resource).forEach(line -> {
			sum.addAndGet(getPoints(line));
		});
		System.out.println(sum.get());
		var sum1 = new AtomicInteger();
		Files.readAllLines(resource).forEach(line -> {
			sum1.addAndGet(getPowers(line));
		});
		System.out.println(sum1.get());
	}


	private int getPowers(String line) {

		var maxRed = -1;
		var maxGreen = -1;
		var maxBlue = -1;
		var startPos = line.indexOf(":") + 2;
		var lineWithoutId = line.substring(startPos);
		var hands = lineWithoutId.split("; ");
		for (String h : hands) {
			var dice = h.split(", ");
			for (String d : dice) {
				var die = d.split(" ");
				var count = Integer.parseInt(die[0]);
				var color = die[1];
				switch (color) {
					case "red" -> {
						if (count > maxRed) maxRed = count;
					}
					case "green" -> {
						if (count > maxGreen) maxGreen = count;
					}
					case "blue" -> {
						if (count > maxBlue) maxBlue = count;
					}
					default -> throw new IllegalStateException("Unexpected value: " + color);
				}
				;
			}
		}
		return maxRed * maxGreen * maxBlue;
	}

	private int getPoints(String line) {

		var gameId = line.split(" ")[1].replace(":", "");
		var points = Integer.parseInt(gameId);
		var startPos = line.indexOf(":") + 2;
		var lineWithoutId = line.substring(startPos);
		var hands = lineWithoutId.split("; ");
		for (String h : hands) {
			var dice = h.split(", ");
			for (String d : dice) {
				var die = d.split(" ");
				var count = Integer.parseInt(die[0]);
				var color = die[1];
				var isPossible = switch (color) {
					case "red" -> count <= 12;
					case "green" -> count <= 13;
					case "blue" -> count <= 14;
					default -> throw new IllegalStateException("Unexpected value: " + color);
				};
				if (!isPossible) {
					points = 0;
					break;
				}
			}
		}
		return points;
	}
}
