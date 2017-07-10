package com.braintags.lookup;


import org.junit.Test;

import java.io.FileNotFoundException;

public class MainTests {

    @Test(expected = IllegalArgumentException.class)
    public void mainWithEmptyArgsTest() throws Throwable {
        String[] args = new String[0];
        Main.main(args);
    }

    @Test(expected = FileNotFoundException.class)
    public void fileNotFoundTest() throws Throwable {
        String[] args = {"somefile"};
        Main.main(args);
    }
}
