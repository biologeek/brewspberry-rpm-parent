package net.brewspberry.business;

import net.brewspberry.business.beans.User;

public interface ISpecificUserDao {

	public User returnUserByCredentials(User user);

	public boolean checkIfUserIsActiveAndNotBlocked(User user);

	public User getUserByCookieData(User user);

}
