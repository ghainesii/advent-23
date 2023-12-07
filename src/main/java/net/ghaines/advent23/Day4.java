package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
class Day4 {

	/* Solutions in case refactoring breaks anything
26218
9997537
	 */

	void run() throws IOException {
		var resource = new ClassPathResource("day4.txt").getFile().toPath();
		var lines = Files.readAllLines(resource);
		var sum = 0;
		for (String s : lines) {
			sum += puzzle1(s);
		}
		System.out.println(sum);
		puzzle2(lines);
	}

	private void puzzle2(List<String> lines) {
		var tickets = new ArrayList<>(Collections.nCopies(lines.size(), 1));
		for (int i = 0; i < lines.size(); i++) {
			var allTickets = lines.get(i).replace("  ", " ").split(": ")[1];
			var winningTickets = allTickets.split(" \\| ")[0].split(" ");
			var myTickets = allTickets.split(" \\| ")[1].split(" ");
			var winSet = new HashSet<>(Arrays.asList(winningTickets));
			var count = 0;
			for (var ticket : myTickets) {
				if (winSet.contains(ticket)) {
					count++;
				}
			}
			var copies = tickets.get(i);
			while (copies > 0) {
				for (int j = 1; j <= count && i + j < lines.size(); j++) {
					var idx = i + j;
					tickets.set(idx, tickets.get(idx) + 1);
				}
				copies--;
			}
		}
		var sum = tickets.stream().mapToInt(Integer::intValue).sum();
		System.out.println(sum);
	}

	private int puzzle1(String line) {
		var allTickets = line.replace("  ", " ").split(": ")[1];
		var winningTickets = allTickets.split(" \\| ")[0].split(" ");
		var myTickets = allTickets.split(" \\| ")[1].split(" ");
		var winSet = new HashSet<>(Arrays.asList(winningTickets));
		var myTicketList = Arrays.asList(myTickets);
		var count = new AtomicInteger();
		myTicketList.forEach(ticket -> {
			if (winSet.contains(ticket)) {
				count.addAndGet(1);
			}
		});
		return (int) Math.pow(2, count.get() - 1);
	}

}