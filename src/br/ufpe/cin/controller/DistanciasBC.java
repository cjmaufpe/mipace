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

public class DistanciasBC {

	/**Esse metodo ordena o vetor considerando o par linha e ranking
	 * 
	 * @param vetor
	 * @param LINHA
	 * @return o vetor ordenado
	 */
	public static void ordenar_par(double[] vetor, int LINHA){
		int i=0,j=0;
		double aux1,aux2,aux3,aux4;
		for(i=0; i<(LINHA*2);)
		{
			for(j=(i+2); j<(LINHA*2);)
			{
				if(vetor[i]>vetor[j])
				{
					aux1=vetor[i];
					aux2=vetor[i+1];
					aux3=vetor[j];
					aux4=vetor[j+1];
					vetor[i]=aux3;
					vetor[j]=aux1;
					vetor[i+1]=aux4;
					vetor[j+1]=aux2;
				}
				j = j+2;
			}
			i = i+2;
		}
	}

	
	/**funcao para calcular a distancia euclidiana dos pares considerando o menor par
	 * 
	 * @param vetor
	 * @param matriz
	 * @param pesos
	 * @param pivot
	 * @param LINHA
	 * @param COLUNA
	 * @return
	 */
	public static double[] euclidianamulti(double[] vetor, double[][] matriz, double[] pesos, double[] pivot, int LINHA, int numObjetivos){
		int i=0,j=0, v=0;
		double z = 1.0;
		double soma = 0.0;
		for(i=0; i<LINHA; i++){
			soma = 0.0;
			for(j=0; j<numObjetivos; j++){
				soma = soma + (pesos[j]*Math.pow((pivot[j] - matriz[i][j]),2));
			}
			System.out.println("\n soma: "+soma); //TODO linha de debug
			vetor[v] = Math.sqrt(soma);
			vetor[v+1]= z;
			v = v+2;
			z = z + 1.0;
		}
		
		ordenar_par(vetor,LINHA);
		return vetor;
	}
	
	public static double[] manhattanmulti(double[] vetor, double[][] matriz, double[] pesos, double[] pivot, int LINHA, int COLUNA){
		int i=0,j=0, v=0;
		double z = 1.0;
		double soma = 0.0;
		for(i=0; i<LINHA; i++){
			soma = 0.0;
			for(j=0; j<COLUNA-1; j++){
				soma = soma + (pesos[j]*Math.abs((pivot[j] - matriz[i][j])));
			}
			System.out.println("\n soma: "+soma); //TODO linha de debug
			vetor[v] = Math.sqrt(soma);
			vetor[v+1]= z;
			v = v+2;
			z = z + 1.0;
		}
		
		ordenar_par(vetor, LINHA);
		return vetor;
	}
	
	public static double[] minkowskimulti(double[] vetor, double[][] matriz, double[] pesos, double[] pivot, int LINHA, int COLUNA, double p){
	    int i=0,j=0, v=0;
	    double z = 1.0;
	    double soma = 0.0;
	    for(i=0; i<LINHA; i++){
	        soma = 0.0;
	        for(j=0; j<COLUNA-1; j++){
	            soma = soma + (pesos[j]*Math.pow(Math.abs((pivot[j] - matriz[i][j])),p));
	        }
	        System.out.println("\n soma: "+soma); //TODO linha de debug
	        vetor[v] = Math.pow(soma,1/p);
	        vetor[v+1]= z;
	        v = v+2;
	        z = z + 1.0;
	    }
	    ordenar_par(vetor, LINHA);
	    return vetor;
	}

}
