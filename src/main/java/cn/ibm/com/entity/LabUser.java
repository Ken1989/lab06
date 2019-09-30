package cn.ibm.com.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@Entity
@Table(name="lab_user")
public class LabUser {
	
	@Id
	private long id;
	private String name;
	private Date birthdate;
	private String sex;
	private String id_no;
	private String phone_num;
	private String email;
	private Date create_time;
	private Date modify_time;
	private String password;
	private String status;
	
}
