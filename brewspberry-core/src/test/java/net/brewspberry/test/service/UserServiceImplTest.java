package net.brewspberry.test.service;

import net.brewspberry.business.ISpecificUserService;
import net.brewspberry.business.beans.User;
import net.brewspberry.business.beans.builders.DefaultUserBuilder;
import net.brewspberry.business.beans.builders.UserBuilder;
import net.brewspberry.util.SpringCoreConfiguration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreConfiguration.class)
public class UserServiceImplTest {

	@Autowired
	ISpecificUserService service;

	@Test
	public void shouldNotBeNull() {

		Assert.assertNotNull(service);

	}

	@Test
	public void shouldCheckIfUserIsActiveAndNotBlocked() {
		User user = new DefaultUserBuilder().login("test")
				.age(10).firstName("Blabla").build();

		Assert.assertFalse(service.checkIfUserIsActiveAndNotBlocked(user));

		user.setUs_active(true);
		
		Assert.assertTrue(service.checkIfUserIsActiveAndNotBlocked(user));

		
		user.setUs_force_inactivated(true);
		
		Assert.assertFalse(service.checkIfUserIsActiveAndNotBlocked(user));

	}

}
