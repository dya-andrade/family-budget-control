package br.com.challenge.apirest.alura.service.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.model.security.Usuario;
import br.com.challenge.apirest.alura.repository.security.UsuarioRepository;

@Service
public class AutorizacaoService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		if(usuario.isPresent()) return usuario.get();
		
		throw new UsernameNotFoundException("Email " + email + " não encontrado!");
	}

}
