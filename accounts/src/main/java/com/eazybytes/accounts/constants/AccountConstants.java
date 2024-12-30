package com.eazybytes.accounts.constants;

public class AccountConstants {

    private AccountConstants() {
        //restrict instantiation
    }
    public static final String SAVINGS = "savings";
    public static final String ADDRESS = "123, Main Street, New York, USA";
    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201 = "Account details created successfully";
    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Request processed successfully";
    public static final String STATUS_417 = "417";
    public static final String MESSAGE_417_Update = "Update failed. Please try again or contact dev team";
    public static final String MESSAGE_417_Delete = "An error failed. Please try again or contact dev team";
    public static final  String  STATUS_200_desc = "HTTP Status OK";
    public static final  String  STATUS_201_desc = "HTTP Status Created";
    public static final  String  STATUS_404_desc = "Not Found";
    public static final  String  STATUS_500_desc = "Internal Server Error";
    public static final  String  STATUS_417_desc = "Exception Failed";
}
