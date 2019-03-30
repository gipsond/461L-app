package com.example.resyoume;

import com.example.resyoume.db.Converters;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ConvertersUnitTests {

    @Test
    public void fromTimeStamp_shouldReturnNull_ifValueNull() {
        assertNull(Converters.fromTimestamp(null));
    }

    @Test
    public void dateToTimestamp_shouldReturnNull_ifValueNull() {
        assertNull(Converters.dateToTimestamp(null));
    }



    private void assertEqualsSelf_whenPassedThrough_dateToTimestamp_and_fromTimestamp_and_backwards(Long value) {
        Date valueDate = new Date(value);
        assertEquals(value, Converters.dateToTimestamp(Converters.fromTimestamp(value)));
        assertEquals(valueDate, Converters.fromTimestamp(Converters.dateToTimestamp(valueDate)));
    }

    @Test
    public void fromTimeStamp_and_dateToTimestamp_shouldBeInverses_1() {
        Long input = 0L;
        assertEqualsSelf_whenPassedThrough_dateToTimestamp_and_fromTimestamp_and_backwards(input);
    }

    @Test
    public void fromTimeStamp_and_dateToTimestamp_shouldBeInverses_2() {
        Long input = 1553911405482L;
        assertEqualsSelf_whenPassedThrough_dateToTimestamp_and_fromTimestamp_and_backwards(input);
    }

    @Test
    public void fromTimeStamp_and_dateToTimestamp_shouldBeInverses_3() {
        Long input = 2000000000000L;
        assertEqualsSelf_whenPassedThrough_dateToTimestamp_and_fromTimestamp_and_backwards(input);
    }

    @Test
    public void fromTimeStamp_and_dateToTimestamp_shouldBeInverses_4() {
        Long input = -1L;
        assertEqualsSelf_whenPassedThrough_dateToTimestamp_and_fromTimestamp_and_backwards(input);
    }

}
