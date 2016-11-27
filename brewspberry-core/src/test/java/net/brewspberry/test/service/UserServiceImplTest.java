package net.brewspberry.test.service;

import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificUserService;
import net.brewspberry.main.business.beans.User;
import net.brewspberry.main.business.beans.builders.DefaultUserBuilder;
import net.brewspberry.test.AbstractTest;
import net.brewspberry.test.util.config.SpringCoreTestConfiguration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreTestConfiguration.class)
public class UserServiceImplTest extends AbstractTest{

	@Autowired
	ISpecificUserService service;
	@Autowired
	@Qualifier("userServiceImpl")
	IGenericService<User> userService;

	public UserServiceImplTest() {
		super();
	}

	@Test
	@Transactional
	public void shouldNotBeNull() {

		Assert.assertNotNull(service);

	}

	@Test
	@Transactional
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
	@Transactional
	public void shouldSaveUserInDB() {
		System.out.println("**** shouldSaveUserInDB ****");

		User user = new DefaultUserBuilder().login("test").age(10)
				.firstName("Blabla").password("1234").build();

		try {
			Assert.assertNotNull(userService.save(user));
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

}
