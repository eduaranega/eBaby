package application;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.tobeagile.training.ebaby.services.PostOffice;



public class PostOfficeTest {

    PostOffice postOffice;

    @Before
    public void setup() {
    	postOffice = PostOffice.getInstance();
    	postOffice.clear();
    }

    @Test
    public void testSendEmail() {
    	postOffice.sendEMail("dummy@yahoo.com", "Test email... yahoo!");
    	assertEquals(1, postOffice.size()); // only 1 email for now
    }

    @Test
    public void testFindEmail() {
    	postOffice.sendEMail("dummy@yahoo.com", "Test email... yahoo!");
    	String email = postOffice.findEmail("dummy@yahoo.com", "Test email... yahoo!");
    	assertTrue(email.contains("Test email... yahoo!"));
    	assertTrue(email.contains("dummy@yahoo.com"));	
    }
    
    @Test
    public void testDoesLogContain() {
            postOffice.sendEMail("dummy@yahoo.com", "Test email... yahoo!");
            assertTrue(postOffice.doesLogContain("dummy@yahoo.com", "Test email... yahoo!"));
    }

}
