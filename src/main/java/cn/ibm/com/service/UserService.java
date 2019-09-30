package cn.ibm.com.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import cn.ibm.com.dao.LabUserDao;
import cn.ibm.com.entity.LabUser;

public class UserService implements UserDetailsService {

	@Autowired
	private LabUserDao labUserDao;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LabUser user = this.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User does not exist.");
        }

        GrantedAuthority roleUser = new SimpleGrantedAuthority("ROLE_USER");

        Collection<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        grantedAuths.add(roleUser);

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuths);

	}
	
	
	public List<LabUser> findAllUsers() {
        return labUserDao.findAll();
    }
	

    public LabUser findByUsername(String username) {
        return labUserDao.findByUsername(username);
    }
    

    public LabUser saveUser(LabUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return labUserDao.save(user);
    }
    

    public void updateUser(LabUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        labUserDao.save(user);
    }

}
