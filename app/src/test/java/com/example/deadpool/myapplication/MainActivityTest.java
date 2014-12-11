package com.example.deadpool.myapplication;

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
//@Config(emulateSdk = 18)
public class MainActivityTest {

    @Test
    public void testSomething() throws Exception {
        System.out.println("---------------------------------\n\n\n");
        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertTrue(false);
    }
}
