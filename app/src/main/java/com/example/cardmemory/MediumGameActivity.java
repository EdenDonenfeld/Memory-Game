package com.example.cardmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MediumGameActivity extends AppCompatActivity {
    int NUMBER_OF_CARDS = 16;

    /*
        cards - List of ImageViews containing all the ImageViews elements
        cardNumbers - List of Integers containing all the numbers
        for the images
        cardImages - List of Integers containing all the "numbers"
        of the 4 different images according to cardNumbers list
     */

    List<ImageView> cards = new ArrayList<>();
    List<Integer> cardNumbers = new ArrayList<>();
    List<Integer> cardImages = new ArrayList<>();
    List<Boolean> flipped = new ArrayList<>();

    private Handler handler = new Handler();
    private boolean isClickable = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_game);

        InitList();
        System.out.println("Flipped: " + flipped.toString());
        LoadImages();
        System.out.println("Cards: " + cards.toString());
        InsertCardNumbers();
        System.out.println("CardNumbers: " + cardNumbers.toString());
        System.out.println("CardImages: " + cardImages.toString());

        for (ImageView card : cards) {
            PressingCard(card);
        }

    }

    /*
        1. Pressing a card
        2. Revealing the card beneath
        3. Pressing another card
        4. Revealing the card beneath
        5. If the two cards are the same, success
        6. Otherwise, flip back both cards
        7. Repeat until all cards are revealed
        8. Make sure that there are only two cards from each image
     */

    private void InitList() {
        for (int i = 0; i < NUMBER_OF_CARDS; i++) {
            flipped.add(false);
        }
    }

    private void LoadImages() {
        // Inserting all the card elements into cards List.
        cards.add(findViewById(R.id.card1));
        cards.add(findViewById(R.id.card2));
        cards.add(findViewById(R.id.card3));
        cards.add(findViewById(R.id.card4));
        cards.add(findViewById(R.id.card5));
        cards.add(findViewById(R.id.card6));
        cards.add(findViewById(R.id.card7));
        cards.add(findViewById(R.id.card8));
        cards.add(findViewById(R.id.card9));
        cards.add(findViewById(R.id.card10));
        cards.add(findViewById(R.id.card11));
        cards.add(findViewById(R.id.card12));
        cards.add(findViewById(R.id.card13));
        cards.add(findViewById(R.id.card14));
        cards.add(findViewById(R.id.card15));
        cards.add(findViewById(R.id.card16));
    }

    private void InsertCardNumbers() {
        // Inserting random numbers into cardNumbers List
        // And inserting the images according to the random number
        // Random number between 0-3
        Random rnd = new Random();
        int rndNumber;

        for (int i = 0; i < NUMBER_OF_CARDS; i++) {
            rndNumber = rnd.nextInt(NUMBER_OF_CARDS/2);
            while (Collections.frequency(cardNumbers, rndNumber) == 2)
                rndNumber = rnd.nextInt(NUMBER_OF_CARDS/2);
            cardNumbers.add(rndNumber);
            switch(rndNumber) {
                case 0:
                    cardImages.add(R.drawable.card1);
                    break;
                case 1:
                    cardImages.add(R.drawable.card2);
                    break;
                case 2:
                    cardImages.add(R.drawable.card3);
                    break;
                case 3:
                    cardImages.add(R.drawable.card4);
                    break;
                case 4:
                    cardImages.add(R.drawable.card5);
                    break;
                case 5:
                    cardImages.add(R.drawable.card6);
                    break;
                case 6:
                    cardImages.add(R.drawable.card7);
                    break;
                case 7:
                    cardImages.add(R.drawable.card8);
                    break;
            }
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