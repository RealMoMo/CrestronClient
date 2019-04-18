package com.hht.crestron;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void splitStr(){
        String content = "eType:100,joinNumber:5,joinValue:0";
        String[] split = content.split(",");
        for (String s : split){
            System.out.println(s);
            String[] split1 = s.split(":");
            for (String ss: split1){
                System.out.println("last:"+ss);
            }
        }

    }


    @Test
    public void splitStr2(){
        String content = "eType:100,joinNumber:5,joinValue:0";
        content = content.replace(":",",");
        System.out.println(content);
        String[] split = content.split(",");
        for (String s : split){
            System.out.println(s);
        }

    }
}