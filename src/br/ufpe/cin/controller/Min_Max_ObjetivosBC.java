package br.ufpe.cin.controller;

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
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.gson.Gson;

import br.ufpe.cin.model.Ranking;
import br.ufpe.cin.model.RankingValores;
import br.ufpe.cin.support.Utils;
import br.ufpe.cin.view.FileUploadView;
import br.ufpe.cin.view.MultObjView;
import br.ufpe.cin.view.PesoView;

public class Min_Max_ObjetivosBC {
	public static List<Integer> indicesObj = new ArrayList<Integer>();
	public static double[][] matrizfinal;
	public static double[][] matriznormalizada;
	public static double[] pesosfinal;
	public static int[] criteriofinal;
	public static Ranking testerank;

	private static Ranking pegarResultID(){
		Ranking retorno = new Ranking();
		Date data = new Date();
		retorno.setTimestamp(data);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = "default";
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		}
		retorno.setUser(currentUserName);
		return retorno;
	}

	public static List<RankingValores> preencheVariaveis(String distancia){
		List<RankingValores> lista = new ArrayList<RankingValores>();
		int linhas = FileUploadView.linhas;
		int colunas = FileUploadView.colunas;
		int indice = 0;

		for (String obj : FileUploadView.objetivosOriginais) {
			if(MultObjView.objetivosSelecionados.contains(obj)){
				indicesObj.add(indice);
			}
			indice++;
		}

		matrizfinal = new double[linhas][indicesObj.size()];

		for (int i = 0; i < linhas; i++) {
			for (int j = 0, l = 0; j < colunas; j++) {
				if(indicesObj.contains(j)){
					matrizfinal[i][l] = FileUploadView.matrizEntrada[i][j];
					l++;
				}
			}
		}

		criteriofinal = new int[indicesObj.size()];
		int contacriterios = 0;
		pesosfinal = new double[indicesObj.size()];
		int contapesos = 0;

		for (String objatual : MultObjView.objetivosSelecionados){
			if(PesoView.testePeso == true){
				for (Entry<String, Float> pair : PesoView.multiPesos.entrySet()){
					if(objatual.equals(pair.getKey())){
						pesosfinal[contapesos] = pair.getValue();
						contapesos++;
						for (Entry<String, String> par : MultObjView.multiObj.entrySet()){
							if(par.getKey().equals(pair.getKey())){
								if(par.getValue().equals("Maximizar")){
									criteriofinal[contacriterios] = 1;
									contacriterios++;
								}
								else if (par.getValue().equals("Minimizar")){
									criteriofinal[contacriterios] = 0;
									contacriterios++;
								}
							}
						}
					}
				}
			}
			else{
				for (int i = 0; i < indicesObj.size(); i++) {
					pesosfinal[i] = 1.0;					
				}
				for (Entry<String, String> par : MultObjView.multiObj.entrySet()){
					if(par.getKey().equals(par.getKey())){
						if(par.getValue().equals("Maximizar")){
							criteriofinal[contacriterios] = 1;
							contacriterios++;
						}
						else if (par.getValue().equals("Minimizar")){
							criteriofinal[contacriterios] = 0;
							contacriterios++;
						}
					}
				}
			}
			break;
		}
		System.out.println("Calculando Pivot...");
		double[] pivots = Preenche_Min_Max(indicesObj.size(), matrizfinal, pesosfinal, linhas, criteriofinal);

		Utils.imprimirMatriz(matrizfinal, linhas, indicesObj.size());
		double[] vetorfinal = new double[(linhas*2)];

		matriznormalizada = matrizfinal;

		if(distancia.equals("euclidiana")){
			double[] rankings = DistanciasBC.euclidianamulti(vetorfinal, matrizfinal, pesosfinal, pivots, linhas, indicesObj.size());	
			int j = 0;
			for(int i = 0; i < rankings.length; i++){
				RankingValores temp = new RankingValores();
				j++;
				temp.setRanking(j);
				temp.setValor(rankings[i]);
				temp.setCenario(rankings[i+1]);
				lista.add(temp);
				i++;
			}
			Gson gson = new Gson();
			String rankingsjson = gson.toJson(lista);
			Ranking save = pegarResultID();
			save.setDistancia(distancia);
			save.setRanking(rankingsjson);
			testerank = save;
		}
		return lista;
	}


	public static double[] Preenche_Min_Max(int num_objetivos, double[][] matriz, double[] pesos, int LINHA, int[] criterio_objetivos){
		double temp1, temp2;
		double[] pivot = new double[num_objetivos];
		double[] minimo_colunas = new double[num_objetivos];
		double[] maximo_colunas = new double[num_objetivos];;
		for(int i=0; i<num_objetivos; i++)
		{
			temp1 = matriz[0][i];    //calculo do minimo
			for(int j=0; j<LINHA; j++)
			{
				if(matriz[j][i] <= temp1)
				{
					temp1 = matriz[j][i];
				}
			}
			minimo_colunas[i] = temp1;

			temp2 = matriz[0][i];     //calculo do maximo
			for(int j=0; j<LINHA; j++)
			{
				if(matriz[j][i] >= temp2)
				{
					temp2 = matriz[j][i];
				}
			}
			maximo_colunas[i]= temp2;
		}

		//normalizacao
		for(int i=0; i<num_objetivos; i++)
		{
			//normalizar valores da matriz
			for (int j=0; j<LINHA; j++)
			{
				matriz[j][i]= (matriz[j][i] - minimo_colunas[i])/(maximo_colunas[i] - minimo_colunas[i]);
			}
		}

		for(int i=0; i<num_objetivos; i++)
		{
			temp1 = matriz[0][i];    //calculo do minimo
			for(int j=0; j<LINHA; j++)
			{
				if(matriz[j][i] <= temp1)
				{
					temp1 = matriz[j][i];
				}
			}
			minimo_colunas[i] = temp1;

			temp2 = matriz[0][i];     //calculo do maximo
			for(int j=0; j<LINHA; j++)
			{
				if(matriz[j][i] >= temp2)
				{
					temp2 = matriz[j][i];
				}
			}
			maximo_colunas[i]= temp2;
			if(criterio_objetivos[i] == 0)  //minimo
			{
				pivot[i] = temp1;
			}
			else if(criterio_objetivos[i] == 1)  //maximo
			{
				pivot[i] = temp2;
			}
		}
		return pivot;
	}


	public static List<RankingValores> preencheVariaveis(String distancia, double pe) {
		List<RankingValores> lista = new ArrayList<RankingValores>();
		int linhas = FileUploadView.linhas;
		int colunas = FileUploadView.colunas;
		int indice = 0;

		for (String obj : FileUploadView.objetivosOriginais) {
			if(MultObjView.objetivosSelecionados.contains(obj)){
				indicesObj.add(indice);
			}
			indice++;
		}

		matrizfinal = new double[linhas][indicesObj.size()];

		for (int i = 0; i < linhas; i++) {
			for (int j = 0, l = 0; j < colunas; j++) {
				if(indicesObj.contains(j)){
					matrizfinal[i][l] = FileUploadView.matrizEntrada[i][j];
					l++;
				}
			}
		}

		criteriofinal = new int[indicesObj.size()];
		int contacriterios = 0;
		pesosfinal = new double[indicesObj.size()];
		int contapesos = 0;

		for (String objatual : MultObjView.objetivosSelecionados){
			if(PesoView.testePeso == true){
				for (Entry<String, Float> pair : PesoView.multiPesos.entrySet()){
					if(objatual.equals(pair.getKey())){
						pesosfinal[contapesos] = pair.getValue();
						contapesos++;
						for (Entry<String, String> par : MultObjView.multiObj.entrySet()){
							if(par.getKey().equals(pair.getKey())){
								if(par.getValue().equals("Maximizar")){
									criteriofinal[contacriterios] = 1;
									contacriterios++;
								}
								else if (par.getValue().equals("Minimizar")){
									criteriofinal[contacriterios] = 0;
									contacriterios++;
								}
							}
						}
					}
				}
			}
			else{
				for (int i = 0; i < indicesObj.size(); i++) {
					pesosfinal[i] = 1.0;					
				}
				for (Entry<String, String> par : MultObjView.multiObj.entrySet()){
					if(par.getKey().equals(par.getKey())){
						if(par.getValue().equals("Maximizar")){
							criteriofinal[contacriterios] = 1;
							contacriterios++;
						}
						else if (par.getValue().equals("Minimizar")){
							criteriofinal[contacriterios] = 0;
							contacriterios++;
						}
					}
				}
			}
			break;
		}
		System.out.println("Calculando Pivot...");
		double[] pivots = Preenche_Min_Max(indicesObj.size(), matrizfinal, pesosfinal, linhas, criteriofinal);

		Utils.imprimirMatriz(matrizfinal, linhas, indicesObj.size());
		double[] vetorfinal = new double[(linhas*2)];
		matriznormalizada = matrizfinal;
		if(distancia.equals("minkoski")){
			int j = 0;
			double[] rankings = DistanciasBC.minkowskimulti(vetorfinal, matrizfinal, pesosfinal, pivots, linhas, indicesObj.size(), pe);	
			for(int i = 0; i < rankings.length; i++){
				RankingValores temp = new RankingValores();
				j++;
				temp.setRanking(j);
				temp.setValor(rankings[i]);
				temp.setCenario(rankings[i+1]);
				lista.add(temp);
				i++;
			}
			Gson gson = new Gson();
			String rankingsjson = gson.toJson(lista);
			Ranking save = pegarResultID();
			save.setDistancia(distancia);
			save.setRanking(rankingsjson);
			testerank = save;
		}
		else if(distancia.equals("manhattan")){
			int j = 0;
			double[] rankings = DistanciasBC.manhattanmulti(vetorfinal, matrizfinal, pesosfinal, pivots, linhas, indicesObj.size());	
			for(int i = 0; i < rankings.length; i++){
				j++;
				RankingValores temp = new RankingValores();
				temp.setRanking(j);
				temp.setValor(rankings[i]);
				temp.setCenario(rankings[i+1]);
				lista.add(temp);
				i++;
			}
			Gson gson = new Gson();
			String rankingsjson = gson.toJson(lista);
			Ranking save = pegarResultID();
			save.setDistancia(distancia);
			save.setRanking(rankingsjson);
			testerank = save;
		}
		return lista;
	}
}
