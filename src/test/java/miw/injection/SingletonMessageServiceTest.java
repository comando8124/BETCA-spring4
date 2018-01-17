package miw.injection;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class SingletonMessageServiceTest {

    private SingletonMessageService singletonMessageService;

    @Before
    public void before() {
        this.singletonMessageService = new SingletonMessageService();
    }

    @Test
    public void testGetMessage() {
        assertEquals("1", this.singletonMessageService.getMessage().substring(0, 1));
        assertEquals("2", this.singletonMessageService.getMessage().substring(0, 1));
        Logger.getLogger(this.getClass().getName()).debug(">>>>>> message: " + this.singletonMessageService.getMessage());        
    }
}