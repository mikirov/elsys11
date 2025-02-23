package org.elsys.cardgame.deck;

import org.elsys.cardgame.api.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public abstract class AbstractDeckTest {

	private Deck deck;

	private Deck clearDeck;

	public abstract int deckSize();

	public abstract int handSize();

	public abstract Deck defaultDeck();

	public abstract List<Rank> orderedRanks();

	@Before
	public void before() {
		deck = defaultDeck();
		clearDeck = defaultDeck();
	}

	@Test
	public void testSize() {
		assertEquals(deckSize(), deck.size());
	}

	@Test
	public void testDeal() {
		Hand hand = deck.deal();
		System.out.println("cards in hand");
		for(Card c: hand.getCards()){
			System.out.println(c);
		}
		assertEquals("Hand size is correct", handSize(), hand.size());
		assertEquals("New size of deck is correct", deckSize() - handSize(), deck.size());
		System.out.println("cards in clear deck");
		for(Card c: clearDeck.getCards()){
			System.out.println(c);
		}

		clearDeck.getCards().removeAll(hand.getCards());
		System.out.println("cards in deck after removal");
		for(Card c: clearDeck.getCards()){
			System.out.println(c);
		}
		assertEquals("Cards left in deck are correct", clearDeck.getCards(), deck.getCards());

		hand.getCards().addAll(deck.getCards());
		assertEquals("Cards in hand are correct", defaultDeck().getCards(), hand.getCards());
	}

	@Test
	public void testShuffle() {
		deck.shuffle();
		assertNotEquals("Shuffle works correctly", clearDeck.getCards(), deck.getCards());
	}

	@Test
	public void testSort() {
		deck.shuffle();
		deck.sort();
		assertEquals("Sort works correctly", clearDeck.getCards(), deck.getCards());
	}

	@Test
	public void testDrawTopCard() {
		Card card = deck.drawTopCard();
		assertEquals("Deck size is correct", deckSize() - 1, deck.size());
		assertEquals("Card rank is correct", orderedRanks().get(0), card.getRank());
		assertEquals("Card suit is correct", Suit.CLUBS, card.getSuit());
	}

	@Test
	public void testTopCard() {
		Card card = deck.topCard();
		assertEquals("Deck size is correct", deckSize(), deck.size());
		assertEquals("Card is correct", orderedRanks().get(0), card.getRank());
		assertEquals("Card suit is correct", Suit.CLUBS, card.getSuit());
	}

	@Test
	public void testDrawBottomCard() {
		Card card = deck.drawBottomCard();
		assertEquals("Deck size is correct", deckSize() - 1, deck.size());
		List<Rank> ranks = orderedRanks();
		assertEquals("Card is correct", ranks.get(ranks.size() -1), card.getRank());
		assertEquals("Card suit is correct", Suit.SPADES, card.getSuit());
	}

	@Test
	public void testBottomCard() {
		Card card = deck.bottomCard();
		assertEquals("Deck size is correct", deckSize(), deck.size());
		List<Rank> ranks = orderedRanks();
		assertEquals("Card is correct", ranks.get(ranks.size() - 1), card.getRank());
		assertEquals("Card suit is correct", Suit.SPADES, card.getSuit());
	}

}
