package cn.ibm.com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.ibm.com.entity.LabUser;

public interface LabUserDao extends JpaRepository<LabUser, Long>{
	
	public LabUser findByUsername(String username);

}
