package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
class Day1 {

	/* Solutions in case refactoring breaks anything
	55108
	56324
	 */

	void run() throws IOException {
		var sum = new AtomicInteger();
		var resource = new ClassPathResource("day1.txt").getFile().toPath();
		Files.readAllLines(resource).forEach(line -> {
			var digit1 = findFirstDigit(line);
			var digit2 = findFirstDigit(new StringBuffer(line).reverse().toString());
			var stringNum = String.valueOf(digit1) + digit2;
			sum.addAndGet(Integer.parseInt(stringNum));
		});
		System.out.println(sum.get());
		var sum2 = new AtomicInteger();
		Files.readAllLines(resource).forEach(line -> {
			var digit1 = findDigit(line, Position.FIRST);
			var digit2 = findDigit(line, Position.LAST);
			var stringNum = String.valueOf(digit1) + digit2;
			sum2.addAndGet(Integer.parseInt(stringNum));
		});
		System.out.println(sum2.get());
	}

	private int findFirstDigit(String line) {
		return line.chars()
				.mapToObj(x -> (char) x)
				.filter(Character::isDigit)
				.map(Character::getNumericValue)
				.findFirst().orElse(0);
	}

	enum Position {
		FIRST, LAST
	}

	private int findDigit(String line, Position position) {
		var map = Map.ofEntries(
				Map.entry("one", 1),
				Map.entry("two", 2),
				Map.entry("three", 3),
				Map.entry("four", 4),
				Map.entry("five", 5),
				Map.entry("six", 6),
				Map.entry("seven", 7),
				Map.entry("eight", 8),
				Map.entry("nine", 9),
				Map.entry("1", 1),
				Map.entry("2", 2),
				Map.entry("3", 3),
				Map.entry("4", 4),
				Map.entry("5", 5),
				Map.entry("6", 6),
				Map.entry("7", 7),
				Map.entry("8", 8),
				Map.entry("9", 9)
		);
		var pos = -1;
		var num = 0;
		for (Map.Entry<String, Integer> e : map.entrySet()) {
			var key = e.getKey();
			switch (position) {
				case Position.FIRST -> {
					var curPos = line.indexOf(key);
					if (pos == -1 || (curPos < pos && curPos > -1)) {
						pos = curPos;
						num = e.getValue();
					}
				}
				case Position.LAST -> {
					var curPos = line.lastIndexOf(key);
					if (curPos > pos) {
						pos = curPos;
						num = e.getValue();
					}
				}
			}
		}
		return num;
	}

}
