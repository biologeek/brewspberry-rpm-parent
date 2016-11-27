package net.brewspberry.main.business.beans.builders;

import net.brewspberry.main.business.beans.User;
import net.brewspberry.main.business.beans.UserProfile;

public class DefaultUserBuilder extends UserBuilder {

	public DefaultUserBuilder() {
		super();
	}

	@Override
	public User profile() {
		user.setUs_profile(UserProfile.READER);
		return user;
	}
}
