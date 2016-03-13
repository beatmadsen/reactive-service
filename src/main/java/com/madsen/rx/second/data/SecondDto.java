package com.madsen.rx.second.data;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
public class SecondDto {

    public final String name;
    public final String address;

    // for jackson
    private SecondDto(){
        name = "";
        address = "";
    }


    public SecondDto(final String name, final String address) {

        this.name = name;
        this.address = address;
    }


    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof SecondDto)) {
            return false;
        }
        final SecondDto dto = (SecondDto) o;
        return name.equals(dto.name) && address.equals(dto.address);
    }


    @Override
    public int hashCode() {
        final String cat = name + "||" + address;
        return cat.hashCode();
    }
}
