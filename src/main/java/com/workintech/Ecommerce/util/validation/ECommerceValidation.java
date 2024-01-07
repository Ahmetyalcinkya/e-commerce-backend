package com.workintech.Ecommerce.util.validation;

import com.workintech.Ecommerce.exceptions.ECommerceException;
import org.springframework.http.HttpStatus;

public class ECommerceValidation {

    public static void checkString(String value, String field, int length){
        if(value == null || value.isEmpty()) throw new ECommerceException(field + " cannot be null or empty! ", HttpStatus.BAD_REQUEST);
        if(value.length() <= 3 || value.length() > length) throw new ECommerceException(field + " length cannot bigger than " + length + " and cannot smaller" +
                "than 0!", HttpStatus.BAD_REQUEST);
    }
    public static void checkPhone(long phoneNumber){
        String phoneStr = Long.toString(phoneNumber);
        char firstChar = phoneStr.charAt(0);
        if(firstChar == '0') throw new ECommerceException("The phone number shouldn't begin with 0!", HttpStatus.BAD_REQUEST);
    }
    public static void checkRating(double rating){
        if(rating < 0 || rating > 5) throw new ECommerceException("Rating must be between 0 and 5!", HttpStatus.BAD_REQUEST);
    }
    public static void checkPrice(double price){
        if (price < 0) throw new ECommerceException("Price cannot smaller than 0!", HttpStatus.BAD_REQUEST);
    }
    public static void checkSellCountAndStock(int value){
        if(value < 0 ) throw new ECommerceException("Sell count cannot smaller than 0!", HttpStatus.BAD_REQUEST);
    }
    public static void checkImageLength(String[] images){
        if (images.length == 0) throw new ECommerceException("You must upload at least 1 picture!", HttpStatus.BAD_REQUEST);
        if (images.length > 3) throw new ECommerceException("You can upload 3 urls for each picture!", HttpStatus.BAD_REQUEST);
    }
    public static void checkCategory(long id){
        if (id < 0 || id > 14) throw new ECommerceException("You should enter a valid categoryID!", HttpStatus.BAD_REQUEST);
    }
    public static void checkPassword(String password){
        if (password.isEmpty()) throw new ECommerceException("You should enter a valid password!", HttpStatus.BAD_REQUEST);
        if (password.length() < 8) throw new ECommerceException("You should enter a valid password!", HttpStatus.BAD_REQUEST);
//        String REGEX = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[ -/:-@\[-`{-~]).{6,64}$/";
//        password.matches(REGEX);
    }
}
