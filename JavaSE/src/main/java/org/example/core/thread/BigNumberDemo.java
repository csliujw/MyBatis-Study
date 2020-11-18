package org.example.core.thread;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 高精度大数
 */
public class BigNumberDemo {
    public static void main(String[] args) {
        BigInteger bigInteger = new BigInteger("01231313123131231");
        // 返回的是一个新的对象
        BigInteger add = bigInteger.add(new BigInteger("1231313"));
        System.out.println(add.toString());

        // 高精度浮点计算
        BigDecimal bigDecimal = new BigDecimal("12.656");
        BigDecimal add1 = bigDecimal.add(new BigDecimal("1231231.123123461231"));
        System.out.println(add1);
    }
}
