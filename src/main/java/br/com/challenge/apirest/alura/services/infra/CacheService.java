package br.com.challenge.apirest.alura.services.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
	
	private static String[] DESPESAS = { "listaDespesas", "listaDespesasMes", "resumoMes", "resumoMesExcel" };

	private static String[] RECEITAS = { "listaReceitas", "listaReceitasMes", "resumoMes", "resumoMesExcel" };
	
	@Autowired
	private CacheManager cacheManager;


	private void evictAllCacheValues(String cacheName) {
	    cacheManager.getCache(cacheName).clear();
	}
	
	public void evictAllCaches() {
	    cacheManager.getCacheNames().stream()
	      .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}

	public void clearAllCachesReceita() {
		for (String cacheName : RECEITAS) {
			evictAllCacheValues(cacheName);
		}
	}
	
	public void clearAllCachesDespesa() {
		for (String cacheName : DESPESAS) {
			evictAllCacheValues(cacheName);
		}
	}
}
