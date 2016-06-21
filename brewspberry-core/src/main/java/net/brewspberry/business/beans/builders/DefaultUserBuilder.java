package net.brewspberry.business.beans.builders;

import net.brewspberry.business.beans.User;
import net.brewspberry.business.beans.UserProfile;

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
