package net.brewspberry.test.service;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.beans.builders.DefaultUserBuilder;
import net.brewspberry.test.AbstractTest;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
public class UserServiceImplTest extends AbstractTest{

	@Autowired
	ISpecificUserService service;
	@Autowired
	IGenericService<User> userService;

	public UserServiceImplTest() {
		super();
	}

	@Test
	public void shouldNotBeNull() {

		Assert.assertNotNull(service);

	}

	@Test
	public void shouldCheckIfUserIsActiveAndNotBlocked() {

		System.out.println("**** shouldCheckIfUserIsActiveAndNotBlocked ****");

		User user = new DefaultUserBuilder().login("test").age(10)
				.firstName("Blabla").build();

		Assert.assertFalse(service.checkIfUserIsActiveAndNotBlocked(user));

		user.setUs_active(true);

		Assert.assertTrue(service.checkIfUserIsActiveAndNotBlocked(user));

		user.setUs_force_inactivated(true);

		Assert.assertFalse(service.checkIfUserIsActiveAndNotBlocked(user));

	}

	@Test
	public void shouldSaveUserInDB() {
		System.out.println("**** shouldSaveUserInDB ****");

		User user = new DefaultUserBuilder().login("test").age(10)
				.firstName("Blabla").password("1234").build();

		try {
			Assert.assertNotNull(userService.save(user));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
