package com.madsen.rx.first.data;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public class FirstDto {

    public final long id;
    public final String name;

    // for jackson
    private FirstDto(){
        id = 0;
        name = null;
    }


    public FirstDto(final long id, final String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof FirstDto)) {
            return false;
        }
        final FirstDto firstDto = (FirstDto) o;

        return id == firstDto.id && name.equals(firstDto.name);
    }


    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
