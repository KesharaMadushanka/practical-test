package com.quantacomit.practical_test.Util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AgeCalculator {
    public static int calculateCurrentAgeInDays(LocalDate birthdate){
        if (birthdate == null) {
            throw new IllegalArgumentException("Birthdate cannot be null");
        }
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(birthdate, currentDate);
    }
}
