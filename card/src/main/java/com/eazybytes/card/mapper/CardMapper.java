package com.eazybytes.card.mapper;

import com.eazybytes.card.constants.CardConstants;
import com.eazybytes.card.dto.CardDto;
import com.eazybytes.card.entity.Card;


public class CardMapper {

    public static Card mapToNewCard(String mobileNumber, Card card) {
        card.setCardType(CardConstants.CREDIT_CARD);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        card.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        card.setMobileNumber(mobileNumber);
        return card;
    }

    public static CardDto mapToCardDto(Card card, CardDto cardDto) {
        cardDto.setCardType(card.getCardType());
        cardDto.setAmountUsed(card.getAmountUsed());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        cardDto.setTotalLimit(card.getTotalLimit());
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setCardNumber(card.getCardNumber());
        return cardDto;
    }

    public static Card mapToCardUpdate(CardDto cardDto, Card card) {
        card.setCardType(cardDto.getCardType());
        card.setAmountUsed(cardDto.getAmountUsed());
        card.setAvailableAmount(cardDto.getAvailableAmount());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setMobileNumber(cardDto.getMobileNumber());
        return card;
    }

}


//private String cardNumber;
//private String mobileNumber;
//private String cardType;
//private int totalLimit;
//private int amountUsed;
//private int availableAmount;