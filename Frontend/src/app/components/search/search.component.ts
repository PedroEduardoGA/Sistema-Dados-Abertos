import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ANO } from 'src/app/models/Ano';
import { ESTADO } from 'src/app/models/Estado';
import { REGIAO } from 'src/app/models/Regiao';
import { Search } from 'src/app/models/Search';
import { TIPO } from 'src/app/models/Tipo';
import { SearchService } from './search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  tiposList: TIPO[] = [];
  anosList: ANO[] = [];
  estadosList: ESTADO[] = [];
  regioesList: REGIAO[] = [];
  selectedAno: number = 2015;
  selectedTipo: string = "Roubo de Carga";
  selectedRegiao: string = "Brasil";
  selectedEstado: string = "Brasil";
  selectedOpcao: string = "populacao";

  constructor(private searchService: SearchService, private router: Router) { }

  ngOnInit(): void {
    this.searchService.getTiposList().subscribe(tiposList => {
      this.tiposList = tiposList;
    })
    this.searchService.getAnosList().subscribe(anosList => {
      this.anosList = anosList;
    });
    this.searchService.getEstadosList().subscribe(estadosList => {
      this.estadosList = estadosList;
    })
    this.searchService.getRegioesList().subscribe(regioesList => {
      this.regioesList = regioesList;
    })
  }

  onButtonClick(opcao: number): void {
    const search = new Search();
    switch(opcao) {
      case(1): {
        search.ano = this.selectedAno;
        search.tipo = this.selectedTipo;
        break;
      }
      case(2): {
        search.ano = this.selectedAno;
        search.regiao = this.selectedRegiao;
        break;
      }
      case(3): {
        search.ano = this.selectedAno;
        break;
      }
      case(4): {
        search.regiao = this.selectedRegiao;
        search.estado = this.selectedEstado;
        break;
      }
      case(5): {
        search.regiao = this.selectedRegiao;
        search.estado = this.selectedEstado;
        search.opcao = this.selectedOpcao;
        break;
      }
    }
    this.router.navigate(['/search-result'], {queryParams: { search: JSON.stringify(search), option: opcao.toString()}});
    console.log(search);
  }
}
