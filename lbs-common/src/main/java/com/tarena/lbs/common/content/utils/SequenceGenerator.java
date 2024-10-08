package com.tarena.lbs.common.content.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
    public static Integer sequence(){
        return UUID.randomUUID().toString().hashCode()&Integer.MAX_VALUE;
    }
}
