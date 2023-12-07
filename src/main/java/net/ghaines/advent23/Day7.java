package net.ghaines.advent23;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
class Day7 {

	/* Solutions in case refactoring breaks anything
253910319
254083736
	 */

	enum Puzzle {
		ONE, TWO
	}

	void run() throws IOException {
		var resource = new ClassPathResource("day7.txt").getFile().toPath();
		var lines = Files.readAllLines(resource);
		solve(lines, Puzzle.ONE);
		solve(lines, Puzzle.TWO);
	}

	enum HandCombinations {
		HIGH_CARD,
		ONE_PAIR,
		TWO_PAIR,
		THREE_KIND,
		FULL_HOUSE,
		FOUR_KIND,
		FIVE_KIND
	}

	Map<Character, Integer> CARD_VALUE_1 = Map.of(
			'T', 1,
			'J', 2,
			'Q', 3,
			'K', 4,
			'A', 5);

	Map<Character, Integer> CARD_VALUE_2 = Map.of(
			'T', 1,
			'Q', 2,
			'K', 3,
			'A', 4);

	class Hand implements Comparable<Hand> {
		private String hand;
		private int bet;
		private Integer score;
		private Puzzle puzzle;

		public Hand(String hand, int bet, Puzzle puzzle) {
			this.hand = hand;
			this.bet = bet;
			this.puzzle = puzzle;
		}

		public String getHand() {
			return hand;
		}

		public int getBet() {
			return bet;
		}

		public int getScore() {

			if (this.score != null) return this.score;

			var matchList = new ArrayList<Integer>();
			var jCount = (int) hand.chars().filter(c -> c == 'J').count();
			var highestMatch = 0;
			var matchIdx = 0;
			var i = 0;
			while (i < hand.length()) {
				var card = hand.charAt(i);
				var matches = (int) hand.chars()
						.filter(c -> c == card && (this.puzzle == Puzzle.ONE || card != 'J')).count();
				if (existingCard(hand, card, i)) {
					matches = 0;
				}
				matchList.add(matches);
				if (matches > highestMatch) {
					highestMatch = matches;
					matchIdx = i;
				}
				i++;
			}
			if (this.puzzle == Puzzle.TWO) {
				matchList.set(matchIdx, highestMatch + jCount);
			}

			var score = 0;
			if (matchList.contains(5)) {
				score = HandCombinations.FIVE_KIND.ordinal();
			} else if (matchList.contains(4)) {
				score = HandCombinations.FOUR_KIND.ordinal();
			} else if (matchList.contains(3) && matchList.contains(2)) {
				score = HandCombinations.FULL_HOUSE.ordinal();
			} else if (matchList.contains(3)) {
				score = HandCombinations.THREE_KIND.ordinal();
			} else if (matchList.stream().filter(match -> match == 2).count() == 2) {
				score = HandCombinations.TWO_PAIR.ordinal();
			} else if (matchList.contains(2)) {
				score = HandCombinations.ONE_PAIR.ordinal();
			}
			this.score = score;
			return score;
		}

		@Override
		public int compareTo(Hand o) {
			if (this.getScore() != o.getScore()) {
				return Integer.compare(this.getScore(), o.getScore());
			}

			for (int i = 0; i < this.hand.length(); i++) {
				var thisCard = this.puzzle == Puzzle.ONE ?
						hand.charAt(i) : hand.replaceAll("J", "1").charAt(i);
				var thatCard = this.puzzle == Puzzle.ONE ?
						o.getHand().charAt(i) : o.getHand().replaceAll("J", "1").charAt(i);
				if (thisCard == thatCard) {
					continue;
				}
				if (Character.isAlphabetic(thisCard) && Character.isAlphabetic(thatCard)) {
					var thisVal = this.puzzle == Puzzle.ONE ? CARD_VALUE_1.get(thisCard) : CARD_VALUE_2.get(thisCard);
					var thatVal = this.puzzle == Puzzle.ONE ? CARD_VALUE_1.get(thatCard) : CARD_VALUE_2.get(thatCard);
					return Integer.compare(thisVal, thatVal);
				}
				if (Character.isAlphabetic(thisCard) && !Character.isAlphabetic(thatCard)) {
					return 1;
				}
				if (!Character.isAlphabetic(thisCard) && Character.isAlphabetic(thatCard)) {
					return -1;
				}
				return Integer.compare(thisCard, thatCard);
			}
			return 0;
		}
	}

	private void solve(List<String> inputs, Puzzle puzzle) {
		var hands = new ArrayList<Hand>();
		for (var input : inputs) {
			var handStr = input.split("\\W+")[0];
			var bet = Integer.parseInt(input.split("\\W+")[1]);
			var hand = new Hand(handStr, bet, puzzle);
			hands.add(hand);
		}
		Collections.sort(hands);
		var sum = 0;
		for (int i = 0; i < hands.size(); i++) {
			var hand = hands.get(i);
			sum += (hand.getBet() * (i + 1));
		}
		System.out.println(sum);

	}

	boolean existingCard(String hand, char card, int i) {
		while (i > 0) {
			if (hand.charAt(i - 1) == card) {
				return true;
			}
			i--;
		}
		return false;
	}
}