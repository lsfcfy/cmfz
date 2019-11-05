package com.baizhi;

import com.fasterxml.jackson.databind.MapperFeature;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Char {
    public static void main(String[] args){
        System.out.println("请输入一个字符串：");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        Map<Character,Integer> map = new HashMap<>();
                char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if(map.containsKey(aChar)){
                Integer num = map.get(aChar);
                num++;
                map.put(aChar,num);
            }else{
                map.put(aChar,1);
            }
        }
        Set<Character> characters = map.keySet();
        for (Character character : characters) {
            Integer integer = map.get(character);
            System.out.println(character+"字符个数"+integer);
        }
    }
}
