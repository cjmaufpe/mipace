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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.ufpe.cin.model.Ranking;
import br.ufpe.cin.model.repository.IRankingDAO;

@Controller
public class RankingBC {

	@Autowired
	private IRankingDAO dao;

	public void salvarRanking(Ranking ranking) {
		dao.save(ranking);
	}

	public void excluirRanking(Ranking ranking) {
		dao.delete(ranking);
	}

	public List<Ranking> listarRanking() {
		return dao.findAll();
	}

}
