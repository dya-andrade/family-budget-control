package br.com.challenge.apirest.alura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
	
	private static String[] RECEITA = { "listaDespesas", "listaDespesasMes", "resumoMes", "resumoMesExcel" };

	private static String[] DESPESA = { "listaReceitas", "listaReceitasMes", "resumoMes", "resumoMesExcel" };
	
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
		for (String cacheName : RECEITA) {
			evictAllCacheValues(cacheName);
		}
	}
	
	public void clearAllCachesDespesa() {
		for (String cacheName : DESPESA) {
			evictAllCacheValues(cacheName);
		}
	}
}
