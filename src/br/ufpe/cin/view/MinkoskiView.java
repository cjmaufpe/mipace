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

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "minkoskiView")
public class MinkoskiView {
	
	public static double pe;
	public double pesinho;
	
	public MinkoskiView(){
	}
	
	public double getPesinho() {
		return pesinho;
	}
	public void setPesinho(double pesinho) {
		this.pesinho = pesinho;
		pe = pesinho;
	}

	public String processar(){
		return "minkoski2";
	}

}