package com.sunglow.find_my_pet.tests;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("All Tests")
@SelectPackages({
    "com.sunglow.find_my_pet.controller",
    "com.sunglow.find_my_pet.controller.health",
    "com.sunglow.find_my_pet.service",
    "com.sunglow.find_my_pet.repository",
    "com.sunglow.find_my_pet.exception",
    "com.sunglow.find_my_pet.initialiseDB",
})
public class AllTestsSuite {

}
