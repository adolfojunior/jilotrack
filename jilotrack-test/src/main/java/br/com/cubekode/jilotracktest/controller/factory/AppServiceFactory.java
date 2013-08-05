/* Jaguar-jCompany Developer Suite. Powerlogic 2010-2014. Please read licensing information in your installation directory. 
*  Contact Powerlogic for more information or contribute with this project: suporte@powerlogic.com.br - www.powerlogic.com.br  */ 
package br.com.cubekode.jilotracktest.controller.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


/**
 * Classe de factory para servir como fábrica de criação de classes utilitárias
 */
@Named("AppServiceFactory")
@ApplicationScoped
public class AppServiceFactory {
	
	/**
	 *
	 * jCompany 6.0.0 - Exemplo de método para gerar PlcEntityUtil
	 *
	@Factory(value="entityComunsUtil")
	public PlcEntityUtil geraEntidadeService() {
		return (PlcEntityUtil)Component.getInstance("AppMinhaEntidadeService",true);

	}
	*/ 

}
