package br.edu.atitus.api_example.entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class UserEntity implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	@Column(length = 100, nullable = false)
	private String email;
	
	@Column(length = 100, nullable = false)
	@JsonIgnore
	private String password;
	
	@Column(length = 20)
	private String phoneNumber;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate birthDate;
	

	@Enumerated(EnumType.ORDINAL)
	private TypeUser Type;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthday) {
		this.birthDate = birthday;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TypeUser getType() {
		return Type;
	}

	public void setType(TypeUser type) {
		Type = type;
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
		}

	@Override
	public String getUsername() {
		return this.email;
		}
}
