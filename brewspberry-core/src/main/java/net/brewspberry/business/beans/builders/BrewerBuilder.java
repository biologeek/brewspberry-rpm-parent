package net.brewspberry.business.beans.builders;

import net.brewspberry.business.beans.User;
import net.brewspberry.business.beans.UserProfile;

public class BrewerBuilder extends UserBuilder {

	@Override
	public User profile() {
		user.setUs_profile(UserProfile.BREWER);
		return user;
	}
}
