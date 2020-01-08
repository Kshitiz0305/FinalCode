package com.agatsa.testsdknew;

import com.agatsa.testsdknew.ui.PersonalDetailsActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PersonalDetailsActivityTest {
    private PersonalDetailsActivity personalDetailsActivity;

@Before
public  void  initialize (){
    personalDetailsActivity = new PersonalDetailsActivity();

}




    @Test
    public void getAge() {
        assertEquals(27,personalDetailsActivity.getAge(1992,07,12));



    }
}