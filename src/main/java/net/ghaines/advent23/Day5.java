package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
class Day5 {

	/* Solutions in case refactoring breaks anything
51580674
99751240
	 */

	void run() throws IOException {
		var resource = new ClassPathResource("day5.txt").getFile().toPath();
		var lines = Files.readAllLines(resource);
		puzzle1(lines);
		puzzle2(lines);
	}

	record Line(long dest, long source, long len) {
	}

	private void puzzle1(List<String> input) {
		var seeds = Arrays.stream(input.get(0).split(": ")[1].split(" ")).map(Long::parseLong).toList();
		var lineMapList = new ArrayList<ArrayList<Line>>();
		var lineList = new ArrayList<Line>();
		for (int i = 1; i < input.size(); i++) {
			String line = input.get(i);
			if (!line.isEmpty()) {
				if (line.contains("map")) {
					lineList = new ArrayList<>();
					lineMapList.add(lineList);
				} else {
					var mapLine = line.split(" ");
					var dest = Long.parseLong(mapLine[0]);
					var source = Long.parseLong(mapLine[1]);
					var len = Long.parseLong(mapLine[2]);
					lineList.add(new Line(dest, source, len));
				}
			}
		}
		var locs = new ArrayList<Long>();
		for (Long seed : seeds) {
			var next = seed;
			for (List<Line> line : lineMapList) {
				next = findDest(line, next);
			}
			locs.add(next);
		}
		var min = locs.stream().min(Long::compareTo);
		System.out.println(min);
	}

	private void puzzle2(List<String> input) {
		var lineMapList = new ArrayList<ArrayList<Line>>();
		var lineList = new ArrayList<Line>();
		for (int i = 1; i < input.size(); i++) {
			String line = input.get(i);
			if (!line.isEmpty()) {
				if (line.contains("map")) {
					lineList = new ArrayList<>();
					lineMapList.add(lineList);
				} else {
					var mapLine = line.split(" ");
					var dest = Long.parseLong(mapLine[0]);
					var source = Long.parseLong(mapLine[1]);
					var len = Long.parseLong(mapLine[2]);
					lineList.add(new Line(dest, source, len));
				}
			}
		}

		var min = Long.MAX_VALUE;
		var seedArr = input.get(0).split(": ")[1].split(" ");
		for (int i = 0; i + 1 < seedArr.length; i += 2) {
			var seedStart = Long.parseLong(seedArr[i]);
			var seedLen = Long.parseLong(seedArr[i + 1]);
			for (int j = 0; j < seedLen; j++) {
				var next = seedStart + j;
				for (List<Line> line : lineMapList) {
					next = findDest(line, next);
				}
				if (next < min) min = next;
			}
		}
		System.out.println(min);
	}

	long findDest(List<Line> lines, long num) {
		for (Line line : lines) {
			if (num >= line.source() && num < line.source() + line.len()) {
				return line.dest() + (num - line.source());
			}
		}
		return num;
	}

}