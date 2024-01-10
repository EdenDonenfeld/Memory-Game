package com.example.cardmemory;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {
    private final int NUMBER_OF_CARDS;
    private List<ImageView> cards;
    private List<Integer> cardNumbers = new ArrayList<>();
    private List<Integer> cardImages = new ArrayList<>();
    private List<Boolean> flipped = new ArrayList<>();

    private Handler handler = new Handler();
    private boolean isClickable = true;

    public Game(int number, List<ImageView> cards) {
        NUMBER_OF_CARDS = number;
        this.cards = cards;
    }

    private void InitList() {
        for (int i = 0; i < NUMBER_OF_CARDS; i++) {
            flipped.add(false);
        }
    }

    private void PressingCard(ImageView card) {
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickable) {
                    if (!flipped.get(cards.indexOf(card))) {
                        RevealingCardImage(card);
                    }
                }
            }
        });
    }

    private void RevealingCardImage(ImageView card) {
        int cardIndex = cards.indexOf(card);
        flipped.set(cardIndex, true);
        int newCard = cardImages.get(cardIndex);
        card.setImageResource(newCard);
        if (Collections.frequency(flipped, true) == 2) {
            isClickable = false; // Disable card clicking during delay
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int index1 = flipped.indexOf(true);
                    int index2 = flipped.lastIndexOf(true);
                    FlipTwoCardsImages(cards.get(index1), cards.get(index2));
                    isClickable = true; // Enable card clicking after delay
                }
            }, 1000); // 1 second delay
        }
    }

    private void FlipTwoCardsImages(ImageView card1, ImageView card2) {
        flipped.set(cards.indexOf(card1), false);
        flipped.set(cards.indexOf(card2), false);
        if (!Objects.equals(cardNumbers.get(cards.indexOf(card1)), cardNumbers.get(cards.indexOf(card2)))) {
            card1.setImageResource(R.drawable.card);
            card2.setImageResource(R.drawable.card);
        }
    }





}
