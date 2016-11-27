package net.brewspberry.main.business.beans.builders;

import net.brewspberry.main.business.beans.User;
import net.brewspberry.main.business.beans.UserProfile;

public class BrewerBuilder extends UserBuilder {

	@Override
	public User profile() {
		user.setUs_profile(UserProfile.BREWER);
		return user;
	}
}
