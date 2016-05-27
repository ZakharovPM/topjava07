package ru.javawebinar.topjava.util;


import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 1,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 1,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.APRIL, 1,20,0), "Ужин", 500)
        );
        List<UserMealWithExceed> userMealWithExceeds =  getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        for (UserMealWithExceed userMealWithExceed:userMealWithExceeds)
            System.out.println(userMealWithExceed);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate,Boolean> exceedMap = new HashMap<>();
        LocalDate tempDate1 = mealList.get(0).getDateTime().toLocalDate(),tempDate2;
        int caloriesCount = caloriesPerDay;
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        // Проверка количества калорий в день
        for (UserMeal userMeal:mealList)
        {
            tempDate2 = userMeal.getDateTime().toLocalDate();

            if (caloriesCount >=0)
                exceedMap.put(tempDate1,false);
            else
                exceedMap.put(tempDate1,true);

            if (tempDate1.equals(tempDate2))
                caloriesCount -= userMeal.getCalories();

            else
            {
                caloriesCount = caloriesPerDay;
                caloriesCount -= userMeal.getCalories();
                tempDate1= tempDate2;
            }
        }

        //Фильтрация
        for (UserMeal userMeal:mealList)
        {

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(),startTime,endTime))
            {

                userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(),exceedMap.get(userMeal.getDateTime().toLocalDate())));
            }

        }

        return userMealWithExceeds;
    }
}
