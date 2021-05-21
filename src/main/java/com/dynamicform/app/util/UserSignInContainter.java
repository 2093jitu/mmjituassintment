package com.dynamicform.app.util;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Mohammad Lockman
 *
 */


@Getter
@Setter
public class UserSignInContainter {
	
	private  Long userId;
	private Long agentId;
    private String name;
	private String fullName;	
	private Long companyNo;
	private String companyName;
	private Long organizationNo;
	private String organizationName;
	private Long sessionNo;
	private Long empNo;
	private Long doctorNo;
	private String userName;
	private String ecodPass;
	private String plainPass;
	
	public UserSignInContainter(Long userId, Long companyNo, Long sessionNo) {
		this.userId = userId;
		this.companyNo = companyNo;
		this.sessionNo = sessionNo;
	}
	
	public UserSignInContainter(Long userId, Long companyNo, Long sessionNo,String encodPass, String plainPass) {
		this.userId = userId;
		this.companyNo = companyNo;
		this.sessionNo = sessionNo;
		this.ecodPass = encodPass;
		this.plainPass = plainPass;
	}

	public UserSignInContainter(Long userId, String name, Long companyNo, Long sessionNo, Long doctorNo) {
		this.userId = userId;
		this.name = name;
		this.companyNo = companyNo;
		this.sessionNo = sessionNo;
		this.doctorNo = doctorNo;
	}
	
	public UserSignInContainter(Long userId, String name, Long companyNo, Long sessionNo, Long empNo, Long doctorNo) {
		this.userId = userId;
		this.name = name;
		this.companyNo = companyNo;
		this.sessionNo = sessionNo;
		this.doctorNo = doctorNo;
		this.empNo = empNo;
	}

	public UserSignInContainter(Long userId,
								String name,
								Long companyNo,
								Long orgId,
								Long sessionNo,
								Long doctorNo,
								Long empNo) {
		this.userId = userId;
		this.name = name;
		this.companyNo = companyNo;
		this.sessionNo = sessionNo;
		this.doctorNo = doctorNo;
		this.empNo = empNo;
		this.organizationNo = orgId;
	}

	public UserSignInContainter(Long userId, String userName, Long companyNo, Long sessionNo) {
		this.userId = userId;
		this.userName = userName;
		this.companyNo = companyNo;
		this.sessionNo = sessionNo;
	}

    public UserSignInContainter(Long userId, Long companyNo, Long sessionNo, String companyName) {
        this.userId = userId;
        this.companyNo = companyNo;
        this.sessionNo = sessionNo;
        this.companyName = companyName;
    }

	public UserSignInContainter(Long userId, Long companyNo, Long organizationNo, Long sessionNo) {
		this.userId = userId;
		this.companyNo = companyNo;
		this.organizationNo = organizationNo;
		this.sessionNo = sessionNo;
	}
}
