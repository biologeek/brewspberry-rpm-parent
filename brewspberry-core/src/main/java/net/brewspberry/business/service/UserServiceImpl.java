package net.brewspberry.business.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.User;
import net.brewspberry.dao.UserDaoImpl;
import net.brewspberry.exceptions.DAOException;

public class UserServiceImpl implements IGenericService<User> {

	private UserDaoImpl userDao;

	@Override
	public User save(User arg0) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getElementById(long id) {
		User result = new User();
		if (id > 0){
			result = userDao.getElementById(id);
		}
		return null;
	}

	@Override
	public List<User> getAllElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteElement(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteElement(User arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	private String encryptPassword (String password, String algorithm){
		MessageDigest  md;
		String messageMD5ed;
		
		try {
			md = MessageDigest.getInstance("MD5");
			
			byte[] passByte = password.getBytes();
			
			md.update(passByte);
			byte[] dgested = md.digest();
			
			StringBuilder bd = new StringBuilder();
			for (byte oct : dgested){
				
				bd.append(Integer.toHexString(oct));
				
			}
			messageMD5ed = bd.toString();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		return messageMD5ed;
	}

}
