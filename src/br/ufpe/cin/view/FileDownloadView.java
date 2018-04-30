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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.ufpe.cin.controller.Min_Max_ObjetivosBC;

@ManagedBean
public class FileDownloadView {
	private StreamedContent file;
    public static double[][] matrizsaida;
	
    public FileDownloadView() {   
    	String saida = "";
    	matrizsaida = Min_Max_ObjetivosBC.matriznormalizada;
    	for (double[] ds : matrizsaida) {
    		for (double d : ds) {
				saida = saida + Double.toString(d) + "\t";
			}
    		saida = saida + "\n";
		}
    	
        InputStream stream = new ByteArrayInputStream(saida.getBytes(StandardCharsets.UTF_8));
        file = new DefaultStreamedContent(stream, "text/csv", FileUploadView.nomeArquivo + "_MiPACE_Output.csv");
        
    }
 
    public StreamedContent getFile() {
        return file;
    }
}
