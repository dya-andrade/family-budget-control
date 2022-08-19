package br.com.challenge.apirest.alura.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.challenge.apirest.alura.exceptions.InvalidJwtAuthenticationException;
import br.com.challenge.apirest.alura.services.security.AutorizacaoService;
import br.com.challenge.apirest.alura.vo.security.TokenVO;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}") // ler do .yml
	private String secretKey = "secret";

	@Value("${security.jwt.token.expire-length:3600000}") // ler do .yml
	private long validityInMilliseconds = 3600000; // 1h

	@Autowired
	private AutorizacaoService autorizacaoService;

	Algorithm algorithm = null;

	@PostConstruct // executa uma ação logo após inicialização do spring, similar ao BeforeAll
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // cripto
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}

	public TokenVO createAccessToken(String email, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		var accessToken = getAccessToken(email, roles, now, validity);
		var refreshToken = getRefreshToken(email, roles, now);

		return new TokenVO(email, true, now, validity, accessToken, refreshToken);
	}
	
	public TokenVO refreshToken(String refreshToken) {
		if(refreshToken.contains("Bearer ")) 
			refreshToken = refreshToken.substring("Bearer ".length());
		
		JWTVerifier verifier = JWT.require(algorithm).build(); // algorithm para abrir o token
		DecodedJWT decodedJWT = verifier.verify(refreshToken); // abre o token
		
		String email = decodedJWT.getSubject();
		List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
		
		return createAccessToken(email, roles);
	}

	private String getAccessToken(String email, List<String> roles, Date now, Date validity) {
		// url do servidor de onde o token foi gerado
		String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();

		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(validity).withSubject(email)
				.withIssuer(issuerUrl).sign(algorithm) // assinatura
				.strip();
	}

	private String getRefreshToken(String email, List<String> roles, Date now) {
		Date validityRefreshToken = new Date(now.getTime() + (validityInMilliseconds * 3)); // 3h

		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(validityRefreshToken)
				.withSubject(email).sign(algorithm) // assinatura
				.strip();
	}

	public Authentication getAuthentication(String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		UserDetails userDetails = autorizacaoService.loadUserByUsername(decodedJWT.getSubject());

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private DecodedJWT decodedToken(String token) {
		var alg = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(alg).build(); // algorithm para abrir o token
		DecodedJWT decodedJWT = verifier.verify(token); // abre o token

		return decodedJWT;
	}

	// PEGA e VALIDA o token quando usuario estiver autenticando

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization"); // cabeçalho da requisição, ele vem com token

		if (bearerToken != null && bearerToken.startsWith("Bearer "))
			return bearerToken.substring("Bearer ".length()); // remove o Bearer do token e deixa apenas o token

		return null;
	}

	public boolean validateToken(String token) {
		
		DecodedJWT decodedJWT = decodedToken(token);
		
		try {
			
			if(decodedJWT.getExpiresAt().before(new Date())) //se o token expira antes de AGORA, entao é um token expirado
				return false;
			
			return true;
			
		} catch (Exception e) {
			throw new InvalidJwtAuthenticationException("Token JWT expirado ou inválido!");
		}
	}
}
