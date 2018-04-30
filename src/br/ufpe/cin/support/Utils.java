package br.ufpe.cin.support;

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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

	/**
	 * Funcao para debug: imprime na tela o vetor ordenado
	 * @param vetor
	 * @param LINHA
	 */
	public static void imprimir_vetor_ordenado(double[] vetor, int LINHA){
		int i=0,j=0;
		for(i=0; i<(LINHA*2);)	    {
			System.out.println(("Ranking "+(j+1)+" -> "+vetor[i]+" Conf= "+vetor[i+1]));
			j++;
			i = i+2;
		}
	}

	/**
	 * Funcao para criar arquivo de texto no disco
	 * @param content
	 * @param filename
	 */
	public static void salvar_resultado(String content, String filename) {
		try {
			File file = new File(filename);
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); //true to append
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.err.println("Erro ao abrir arquivo TXT:"+filename);
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void imprimir_matriz(double[][] matriz, int LINHA, int COLUNA){
	    int i,j;
	    for (i=0; i<LINHA; i++)
	    {
	        for (j=0; j<COLUNA; j++)
	        {
	            if(j != COLUNA-1)
	            {
	                System.out.printf("%.9f\t",matriz[i][j]);
	            }
	            else
	            {
	            	System.out.printf("%.0f\t",matriz[i][j]);
	            }
	        }
	        System.out.printf("\n\n");
	    }
	}
	
	public static void salvar_matriz(double[][] matriz, int LINHA,  int COLUNA){
		String salvar = "";
	    int i,j;
	    for (i=0; i<LINHA; i++){
	        for (j=0; j<COLUNA; j++){
	            if(j != COLUNA-1){
	                salvar = salvar + matriz[i][j]+"\t";
	            }
	        }
	        salvar = salvar + "\n";
	    }
	    Utils.salvar_resultado(salvar, "grafico.txt");
	}
	
	public static void imprimirMatriz(double[][] matriz, int linha, int coluna){
		int i,j;
		for (i=0; i<linha; i++)
		{
			for (j=0; j<coluna; j++)
			{
				System.out.print(matriz[i][j] + "\t");
			}
			System.out.print("\n");
		}
	}
	
}
