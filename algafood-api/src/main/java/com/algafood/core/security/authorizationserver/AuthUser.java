package com.algafood.core.security.authorizationserver;

import com.algafood.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {

	private static final long serialVersionUID = 1L;
	
	private String fullName;
	private Long userId;


	public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);

		this.userId = usuario.getId();
		this.fullName = usuario.getNome();
	}
	
}