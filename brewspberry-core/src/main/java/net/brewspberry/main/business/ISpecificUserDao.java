package net.brewspberry.main.business;

import net.brewspberry.main.business.beans.User;

public interface ISpecificUserDao {

	public User returnUserByCredentials(User user);

	public boolean checkIfUserIsActiveAndNotBlocked(User user);

	public User getUserByCookieData(User user);

}
