package com.example.grewordspractice.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomNumberListGenerator {

    public static List<Integer> generateNumbers(int numOfnum){
        List<Integer> num = new ArrayList<>();
        Random random = new Random();
        int rn;
        for(int i = 0; i<numOfnum; i++){
            rn = random.nextInt(100);
            if(Collections.frequency(num, rn)==0){
                num.add(rn);
            }
        }
        return num;
    }


}
