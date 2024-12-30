package com.eazybytes.card.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Card extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
    @Column(updatable = false)
    private String cardNumber;
    private String mobileNumber;
    private String cardType;
    private double totalLimit;
    private double amountUsed;
    private double availableAmount;
}


//CREATE TABLE IF NOT EXISTS `cards` (
//        `card_id` int NOT NULL AUTO_INCREMENT,
//  `mobile_number` varchar(15) NOT NULL,
//  `card_number` varchar(100) NOT NULL,
//  `card_type` varchar(100) NOT NULL,
//  `total_limit` int NOT NULL,
//        `amount_used` int NOT NULL,
//        `available_amount` int NOT NULL,
//        `created_at` date NOT NULL,
//        `created_by` varchar(20) NOT NULL,
//  `updated_at` date DEFAULT NULL,
//        `updated_by` varchar(20) DEFAULT NULL,
//PRIMARY KEY (`card_id`)
//);