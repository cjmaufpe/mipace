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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
public class MultObjView {

	public static List<String> objetivosSelecionados;
	public static boolean acabou = false;
	public static int atual = 0;
	public static List<String> controle = null;
	public static HashMap<String,String> multiObj = new HashMap<String,String>();
	private String objetivoAtual;
	private String respostaAtual;

	public HashMap<String, String> getMultiObj() {
		if(atual == 0){
			for(int i = 0; i < FileUploadView.multObjetivos.size(); i++){
				multiObj.put(FileUploadView.multObjetivos.get(i), "");
			}
		}
		return multiObj;
	}

	public void setMultiObj(HashMap<String, String> multiObjtela) {
		multiObj = multiObjtela;
	}

	public List<String> getControle() {
		return controle;
	}

	public void setControle(List<String> controletela) {
		controle = controletela;
	}

	public String getRespostaAtual() {
		return respostaAtual;
	}

	public void setRespostaAtual(String respostaAtual) {
		this.respostaAtual = respostaAtual;
		this.objetivoAtual = controle.get(atual);
		multiObj.put(objetivoAtual, respostaAtual);
		atual = atual + 1;
		if(atual < controle.size()){
			addMessage("Objetivo definido: "+objetivoAtual+" "+respostaAtual);
			this.objetivoAtual = controle.get(atual);
		}
	}

	public String getObjetivoAtual() {
		controle = FileUploadView.multObjetivos;
		if(acabou){
			addMessage("Objetivos definidos com sucesso!");
			return "ok";
		}
		this.objetivoAtual = controle.get(atual);
		return objetivoAtual;
	}

	public void setObjetivoAtual(String objetivoAtual) {
		this.objetivoAtual = objetivoAtual;
	}

	public String processarOpcoes(ActionEvent actionEvent){
		if(atual == controle.size()){
			atual = 0;
			acabou = true;
			for (Map.Entry<String,String> pair : multiObj.entrySet()) {
				if(pair.getValue().equals("Omitir")){
					for(int j = 0; j < controle.size(); j++){
						if(controle.get(j).equals(pair.getKey())){
							controle.remove(j);
						}
					}
				}
			}
			MultObjView.objetivosSelecionados = controle;
			return "ok";
		}
		this.respostaAtual = "";
		return "";
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}


