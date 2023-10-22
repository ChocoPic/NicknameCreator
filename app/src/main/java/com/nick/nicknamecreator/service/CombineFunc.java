package com.nick.nicknamecreator.service;

import android.util.Log;

import java.util.Random;

public class CombineFunc {
    Random rand = new Random();

    /* 초중종성 조합 함수 */
    public String createC(int[] ChoData, int[] JunData, int[] JonData){
        int range1=0;
        int range2=0;
        int range3=0;
        for(int cc: ChoData){range1 += cc;}
        for(int cc: JunData){range2 += cc;}
        for(int cc: JonData){range3 += cc;}
        char chr;
        int a=0, b=0, c=0;

        //초성 있는 경우
        int total = 0;
        if(ChoData.length==1){
            a = ChoData[0];
        }else{//초성 없는 경우
            int cho = rand.nextInt(range1);
            for(int i=0; i<ChoData.length; i++){
                int cur = ChoData[i];
                total += cur;
                if (cho <= total){
                    a = i ;
                    break;
                }
            }
        }
        int jun = rand.nextInt(range2);
        total = 0;
        for(int i=0; i<JunData.length; i++){
            int cur = JunData[i];
            total += cur;
            if (jun <= total){
                b = i ;
                break;
            }
        }
        int jon = rand.nextInt(range3);
        total = 0;
        for(int i=0; i<JonData.length; i++){
            int cur = JonData[i];
            total += cur;
            if (jon <= total){
                c = i ;
                break;
            }
        }
        chr = (char)makeCh(a,b,c);
//        Log.d("랜덤",chr+":"+a+" "+b+" "+c);

        return String.valueOf(chr);
    }
    public int makeCh(int a, int b, int c){
        return (0xAC00 + 21*28*a +28*b + c);
    }

    /* 한글자 고르는 함수 */
    public char pickOne(int[] CntData, char[] CharData){
        int range=0;
        for(int c: CntData){range += c;}
        int charIdx = 0;

        int randValue = rand.nextInt(range);
        int total = 0;
        for(int i=0; i<CntData.length; i++){
            total += CntData[i];
            if(randValue <= total){
                charIdx = i;
                break;
            }
        }
        return CharData[charIdx];
    }

    /* 두글자 고르는 함수 */
    public String pickTwo(String[] StrData){
        int a = rand.nextInt(StrData.length);
        return StrData[a];
    }

}
