package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
class Day4 {

	/* Solutions in case refactoring breaks anything
26218
	 */

	void run() throws IOException {
		var resource = new ClassPathResource("day4.txt").getFile().toPath();
		var lines = Files.readAllLines(resource);
		var sum = 0;
		for (String s : lines) {
			sum += puzzle1(s);
		}
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