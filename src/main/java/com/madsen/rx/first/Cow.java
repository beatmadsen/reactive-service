package com.madsen.rx.first;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public interface Cow {

    String mooh(String in);

    class Simple implements Cow {
        @Override
        public String mooh(final String in) {
            return "mooh" + in;
        }
    }
}
