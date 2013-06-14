package application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class UserTest {
		
		private User user;
	
	    @Before
	    public void setup() {
	            user = User.getInstance("dummy", "Dummy", "dummy@yahoo.com", "dummyid", "dummypasswd");
	    }
	    
	    @Test
		public void testCreateUser()
		{
	    	assertTrue(user.getUserName() == "dummyid");
	    	assertTrue(user.getUserEmail() == "dummy@yahoo.com");
	    	assertEquals(false, user.isSeller());
		}
	    
	    @Test
	    public void testUserLoggedOutInOut() {
	    	assertFalse(user.isLogged());
	    	user.setLogged(true);
	    	assertTrue(user.isLogged());
	    	user.setLogged(false);
	    	assertFalse(user.isLogged());
	    }
	    
		@Test
        public void testUserIsSeller() {
                user.setSeller(true);
                assertTrue(user.isSeller());
        }
	
}
