package application;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class UsersTest {
		
		private User user;
		private Users users;
	
	    @Before
	    public void setup() {
	            user = User.getInstance("dummy", "Dummy", "dummy@yahoo.com", "dummyid", "dummypasswd");
	            users = Users.getInstance();
	    }
	    
		@Test
		public void testRegisterUsers() {
			 assertTrue(users.registerUser(user)); // user registered
			 assertFalse(users.registerUser(user)); // user already registered
		}
		
		@Test
		public void testFindByUserName() {
			assertFalse(users.findByUserName(user.getUserName())); //user not found
			users.registerUser(user);
			assertTrue(users.findByUserName(user.getUserName())); //user found
		}
		
		@Test
		public void testLogIn() {
			assertFalse(users.logIn(user.getUserName(),user.getPassword())); // user not found
			users.registerUser(user);
			assertTrue(users.logIn(user.getUserName(),user.getPassword())); // user logged in
			assertFalse(users.logIn(user.getUserName(),"wrongpassword")); // wrong password
		}
		
		@Test
		public void testLogOut() {
			assertFalse(users.logOut(user.getUserName())); // user not found
			users.registerUser(user);
			users.logIn(user.getUserName(),user.getPassword());
			assertTrue(users.logOut(user.getUserName())); // user logged out
		}
	
}
