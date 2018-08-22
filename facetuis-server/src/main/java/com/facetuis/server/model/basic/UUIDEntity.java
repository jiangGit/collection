package com.facetuis.server.model.basic;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class UUIDEntity implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -6588910308728470737L;
	
	public UUIDEntity(){
	}
	
	@Id
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
