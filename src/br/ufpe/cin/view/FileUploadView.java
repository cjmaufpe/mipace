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

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

@ManagedBean
public class FileUploadView {

	public static double[][] matrizEntrada;
	public static int colunas;
	public static int linhas;
	public static UploadedFile file;
	public static List<String> multObjetivos = null;
	public static List<String> objetivosOriginais = null;
	public static String nomeArquivo;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file1) {
		multObjetivos = new ArrayList<String>();
		objetivosOriginais = new ArrayList<String>();
		file = file1;
	}

	public String upload() {
		if(file != null) {
			String fileName = file.getFileName();
			if(!fileName.equals("")){
				if(fileName.endsWith(".txt") || fileName.endsWith(".csv")){
					System.out.println(fileName);
					nomeArquivo = fileName.substring(0, fileName.lastIndexOf("."));
					byte[] contents = file.getContents();
					System.out.println("CONTEUDO DO ARQUIVO:");
					String s = new String(contents);
					String[] lines = s.split("\n");
					
					int contador = 0;
					for (String linha : lines){
						contador++;
						System.out.println(contador + " -> " + linha);
					}
					
					String[] objetivos = lines[0].split("\t");
					for (String obj : objetivos) {
						multObjetivos.add(obj);
						objetivosOriginais.add(obj);
					}
					colunas = multObjetivos.size();
					linhas = contador - 1;
					matrizEntrada = new double[linhas][colunas];
					
					for (int i = 0; i < linhas; i++) {
						for (int j = 0; j < colunas; j++) {
							String[] valores = lines[i+1].split("\t");
							double valoratual = Double.parseDouble(valores[j].replace(",", "."));
							matrizEntrada[i][j] = valoratual;
						}
					}
					
					FacesMessage message = new FacesMessage("Arquivo recebido com sucesso: ", file.getFileName());
					FacesContext.getCurrentInstance().addMessage(null, message);
					return "uploadok";
					
				}
				else{
					FacesMessage message = new FacesMessage("ERROR: ", file.getFileName() + " A extensao deve ser: .txt ou .csv");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			}
			else{
				FacesMessage message = new FacesMessage("ERROR: ", file.getFileName() + " Tente novamente.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}

		return "tentedenovo";

	}
}


