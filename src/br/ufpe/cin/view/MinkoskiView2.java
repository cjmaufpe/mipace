package br.ufpe.cin.view;

/**
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.ufpe.cin.controller.Min_Max_ObjetivosBC;
import br.ufpe.cin.model.Ranking;
import br.ufpe.cin.model.RankingValores;
import br.ufpe.cin.support.Fachada;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "minkoskiMB")
public class MinkoskiView2 {
	
	@Autowired
	private Fachada fachada;
	
	private Ranking ranking;

	public String retorno = "Processando...";
	public List<RankingValores> listarankig;
	public static List<RankingValores> temp = Min_Max_ObjetivosBC.preencheVariaveis("minkoski",MinkoskiView.pe);

	public List<RankingValores> getListarankig() {
		this.setListarankig(temp);
		ranking = Min_Max_ObjetivosBC.testerank;
		fachada.salvarRanking(ranking);
		return this.listarankig;
	}

	public void setListarankig(List<RankingValores> listarankig) {
		this.listarankig = temp;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}