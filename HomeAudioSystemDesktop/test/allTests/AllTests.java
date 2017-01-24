package allTests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controller.TestHomeAudioSystemController;
import persistence.TestPersistence;


@RunWith(Suite.class)
@SuiteClasses({ TestHomeAudioSystemController.class, TestPersistence.class })
public class AllTests{

}
