package com.madsen.rx.second.domain;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public interface Second {

    boolean idMatches(long id);

    boolean nameMatches(String name);

    boolean addressMatches(String address);

    <T> Optional<T> extract(Function<SecondVo, Optional<T>> strategy);

    Second copyWithId(long id);

    class SecondVo {
        public final long id;
        public final String name;
        public final String address;


        public SecondVo(final long id, final String name, final String address) {
            this.id = id;
            this.name = name;
            this.address = address;
        }


        @Override
        public int hashCode() {
            return Long.hashCode(id);
        }


        @Override
        public boolean equals(final Object obj) {
            return obj instanceof SecondVo && equals((SecondVo) obj);
        }


        private boolean equals(final SecondVo vo) {
            return id == vo.id &&
                    name.equals(vo.name) &&
                    address.equals(vo.address);
        }
    }
}
