package com.damon.jdk8.opt;

import org.junit.Test;

import java.util.Optional;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/7/14 17:17
 */
public class SimpleTest {

    @Test
    public void test() {
        Optional< String > fullName = Optional.ofNullable( null );
        System.out.println( "Full Name is set? " + fullName.isPresent() );
        System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]" ) );
        System.out.println( fullName.map( s -> "1Hey " + s + "!" ).orElse( "2Hey Stranger!" ) );
        System.out.println();

        Optional< String > firstName = Optional.of( "Tom" );
        System.out.println( "First Name is set? " + firstName.isPresent() );
        System.out.println( "First Name: " + firstName.orElseGet( () -> "[none]" ) );
        System.out.println( firstName.map( s -> "1Hey " + s + "!" ).orElse( "2Hey Stranger!" ) );
        System.out.println();
    }
}
