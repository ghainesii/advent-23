package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
class Day3 {

	/* Solutions in case refactoring breaks anything
539637
82818007
	 */

	void run() throws IOException {
		var resource = new ClassPathResource("day3.txt").getFile().toPath();
		var lines = Files.readAllLines(resource);
		puzzle1(lines);
		puzzle2(lines);
	}

	private void puzzle1(List<String> lines) {
		var ans = 0;
		for (int i = 0; i < lines.size(); i++) {
			var lineBefore = i == 0 ? null : lines.get(i - 1);
			var line = lines.get(i);
			var lineAfter = i == lines.size() - 1 ? null : lines.get(i + 1);
			ans += puzzle1(lineBefore, line, lineAfter);
		}
		System.out.println(ans);
	}

	int puzzle1(String lineBefore, String line, String lineAfter) {
		var sum = 0;
		var matcher = Pattern.compile("\\d+").matcher(line);
		while (matcher.find()) {
			var start = matcher.start();
			var end = matcher.end();
			var number = matcher.group();
			if (isAdjacentSymbol(lineBefore, start, end) ||
					isAdjacentSymbol(line, start, end) ||
					isAdjacentSymbol(lineAfter, start, end)) {
				sum += Integer.parseInt(number);
			}
		}
		return sum;
	}

	private void puzzle2(List<String> lines) {
		var ans = 0;
		for (int i = 0; i < lines.size(); i++) {
			var lineBefore = i == 0 ? null : lines.get(i - 1);
			var line = lines.get(i);
			var lineAfter = i == lines.size() - 1 ? null : lines.get(i + 1);
			ans += puzzle2(lineBefore, line, lineAfter);
		}
		System.out.println(ans);
	}

	int puzzle2(String lineBefore, String line, String lineAfter) {
		var sum = 0;
		var matcher = Pattern.compile("\\*").matcher(line);
		while (matcher.find()) {
			var charPos = matcher.start();
			var adjacentParts = new ArrayList<Integer>();
			adjacentParts.addAll(getAdjacentParts(lineBefore, charPos));
			adjacentParts.addAll(getAdjacentParts(line, charPos));
			adjacentParts.addAll(getAdjacentParts(lineAfter, charPos));
			if (adjacentParts.size() == 2) {
				sum += adjacentParts.get(0) * adjacentParts.get(1);
			}
		}
		return sum;
	}

	private List<Integer> getAdjacentParts(String line, int pos) {
		var parts = new ArrayList<Integer>();
		var matcher = Pattern.compile("\\d+").matcher(line);
		while (matcher.find()) {
			var start = matcher.start();
			var end = matcher.end();
			var number = matcher.group();
			if (start - 1 <= pos && pos <= end) {
				parts.add(Integer.parseInt(number));
			}
		}
		return parts;
	}

	private static boolean isAdjacentSymbol(String line, int start, int end) {
		if (line != null) {
			var matcher = Pattern.compile("[^\\w\\s.]").matcher(line);
			while (matcher.find()) {
				var charPos = matcher.start();
				if (start - 1 <= charPos && charPos <= end) {
					return true;
				}
			}
		}
		return false;
	}
}
