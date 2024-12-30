package com.uexcel.message.dto;


/**
 * @param accountNumber
 * @param name
 * @param email
 * @param mobileNumber
 */
public record AccountMsgDto(Long accountNumber, String name, String email, String mobileNumber){
}
