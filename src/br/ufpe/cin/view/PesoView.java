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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
public class PesoView {
	private float pesoAtual;
	private String atual;
	public static boolean testePeso = false;
	public static int contador = 0;
	public static HashMap<String,Float> multiPesos = new HashMap<String,Float>();

	public String getAtual() {
		if(contador < MultObjView.objetivosSelecionados.size()){
			this.atual = MultObjView.objetivosSelecionados.get(contador);
			return atual;
		}
		else{
			return "distancias";
		}
	}

	public void setAtual(String atual) {
		this.atual = atual;
	}

	public float getPesoAtual() {
		return pesoAtual;
	}

	public void setPesoAtual(float number2) {
		multiPesos.put(MultObjView.objetivosSelecionados.get(contador), number2);
		contador++;
		this.pesoAtual = number2;
	}

	public String processar(ActionEvent actionEvent){
		System.out.println("Peso atual: " + pesoAtual);
		PesoView.testePeso = true;
		return "pesook";
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}