/* Jaguar-jCompany Developer Suite. Powerlogic 2010-2014. Please read licensing information in your installation directory. 
 *  Contact Powerlogic for more information or contribute with this project: suporte@powerlogic.com.br - www.powerlogic.com.br  */
package br.com.cubekode.jilotracktest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.powerlogic.jcompany.commons.PlcBaseUserProfileVO;
import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefault;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcUtil;
import com.powerlogic.jcompany.controller.util.PlcBaseUserProfileUtil;

@SPlcUtil
@QPlcDefault
public class AppUserProfileUtil extends PlcBaseUserProfileUtil {

	private static final long serialVersionUID = 1L;

	@Override
	public PlcBaseUserProfileVO registrySpecificProfile(HttpServletRequest request, HttpServletResponse response, PlcBaseUserProfileVO plcPerfilVO) throws Exception {
		return plcPerfilVO;
	}
}
