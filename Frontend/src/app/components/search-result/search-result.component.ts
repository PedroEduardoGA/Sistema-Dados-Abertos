import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SearchResultService } from './search-result.service';
import { Chart, registerables } from 'chart.js';
import { EstadoR } from 'src/app/models/EstadoR';
Chart.register(...registerables)

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.scss']
})
export class SearchResultComponent implements OnInit {

  search: string = "";
  option: string = "";
  nome: any[] = [];
  quantidade: any[] = [];
  ano: any[] = [];
  colordata: any[] = [];
  colors: any[] = []
  chartdata: any;
  chart = [];
  cards: boolean = false;
  estados: EstadoR[] = []

  constructor(private searchService: SearchResultService, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.search = params['search'];
      this.option = params['option'];
    })
    this.colors = ['#4E8897', '#5D1786', "#4D00FF", "#A200FF", "#5C1187", "#533664", "#0D4362", "#3F4484", "#14596B", "#54146B",
      "#1836AF", "#6918AF", "#D52093", "#52C9A5", "#3E7911", "#795611", "#D59F33", "#339FD5", "#4333D5", "#6A389C",
      "#2132A4", "#53684D", "#7F77EE", "#D677EE", "#EE77D2", "#EE779A", "#D042DA"]
  }

  ngOnInit(): void {
    console.log(this.search);

    this.searchService.postSearch(JSON.parse(this.search), this.option).subscribe({
      next: (data) => {
        console.log("busca realizada", data);
        this.chartdata = data;
        this.treatData();
      },
      error: (erro) => {
        alert("Erro na busca");
        console.error(erro, erro.message);
      }
    });
  }

  private getAno(): string {
    const s = JSON.parse(this.search);
    const ano = s.ano;
    return ano;
  }

  private getRegiao(): string {
    const s = JSON.parse(this.search);
    const regiao = s.regiao;
    return regiao;
  }

  private getTipo(): string {
    const s = JSON.parse(this.search);
    const tipo = s.tipo;
    return tipo;
  }

  treatData() {
    switch (this.option) {
      case ("1"): {
        if (this.chartdata != null) {
          for (let i = 0; i < this.chartdata.length; i++) {
            this.nome.push(this.chartdata[i].nome);
            this.quantidade.push(this.chartdata[i].qtdTotal);
            this.colordata.push(this.colors[i]);
          }
        }
        this.renderChart1(this.nome, this.quantidade, this.colordata, "pie");
        break;
      }
      case ("2"): {
        if (this.chartdata != null) {
          for (let i = 0; i < this.chartdata.length; i++) {
            this.nome.push(this.chartdata[i].nome);
            this.quantidade.push(this.chartdata[i].qtdTotal);
            this.colordata.push(this.colors[i]);
          }
        }
        this.renderChart2(this.nome, this.quantidade, this.colordata, "bar");
        break;
      }
      case ("3"): {
        if(this.chartdata != null){
          this.cards = true;
          for (let i = 0; i < this.chartdata.length; i++) {
            let nome = this.chartdata[i].nome;
            let percentual = this.chartdata[i].percentual;
            let estado = new EstadoR(nome, percentual, parseInt(this.getAno()));
            this.estados.push(estado);
          }
        }
        break;
      }
      case ("4"): {
        let d1 = [];
        let d2 = [];
        let d3 = [];
        console.log(this.chartdata)
        if(this.chartdata != null) {
          for(let i = 0; i < this.chartdata.length; i += 3){
            this.ano.push(this.chartdata[i].ano);
              d1.push(this.chartdata[i].quantidade_vitimas);
              d2.push(this.chartdata[i+1].quantidade_vitimas);
              d3.push(this.chartdata[i+2].quantidade_vitimas);
          }
        }
        this.renderChart4(this.ano, d1, d2, d3, "bar");
        break;
      }
      case ("5"): {
        if (this.chartdata != null) {
          for (let i = 0; i < this.chartdata.length; i++) {
            this.nome.push(this.chartdata[i].nome);
            this.quantidade.push(this.chartdata[i].qtdTotal);
            this.colordata.push(this.colors[i]);
            this.ano.push(this.chartdata[i].ano);
          }
        }
        this.renderChart5(this.ano, this.quantidade, this.colordata, "line");
        break;
      }
    }
  }

  private titleCase(str: string) {
    var splitStr = str.toLowerCase().split(' ');
    for (var i = 0; i < splitStr.length; i++) {
      splitStr[i] = splitStr[i].charAt(0).toLowerCase() + splitStr[i].substring(1);
    }
    return splitStr.join(' ');
  }

  renderChart1(labeldata: any, maindata: any, colors: any, type: any) {
    let text = '';
    if (this.getTipo() == "Todos") {
      text = " total";
    } else {
      text = " de " + this.titleCase(this.getTipo());
    }
    const myChart = new Chart('opcao', {
      type: type,
      data: {
        labels: labeldata,
        datasets: [{
          label: 'Quantidade de Ocorrências',
          data: maindata,
          backgroundColor: colors,
          borderColor: ['#3E1E6F'],
          borderWidth: 1
        }]
      },
      options: {
        plugins: {
          legend: {
            position: 'top',
          },
          title: {
            display: true,
            text: "Numero de ocorrências" + text + " por estado em " + this.getAno(),
            font: { size: '20px' }
          }
        },
        responsive: true,
      }
    });
  }
  renderChart2(labeldata: any, maindata: any, colors: any, type: any) {
    let text = ""
    if (this.getRegiao() == "Brasil") {
      text = "no";
    } else {
      text = "na";
    }
    console.log(maindata)
    console.log(labeldata)
    const myChart = new Chart('opcao', {
      type: type,
      data: {
        labels: labeldata,
        datasets: [{
          label: 'Quantidade de Ocorrências',
          data: maindata,
          backgroundColor: colors,
          borderColor: ['#3E1E6F'],
          borderWidth: 1
        }]
      },
      options: {
        plugins: {
          legend: {
            position: 'top',
          },
          title: {
            display: true,
            text: "Numero de ocorrências total de crimes " + text + " " + this.getRegiao() + " em " + this.getAno(),
            font: { size: '20px' }
          }
        },
        responsive: true,
      }
    });
  }
  renderChart4(labeldata: any, d1: any, d2:any, d3:any, type:any){
    let text = ''
    let brasil = false;
    if (this.getRegiao() == "Brasil") {
      text = text + " no " + JSON.parse(this.search).estado;
      brasil = true;
    } else {
      text = text + " na " + this.getRegiao();
    }
    const myChart = new Chart('opcao', {
      type: type,
      data: {
        labels: labeldata,
        datasets: [{
          label: 'Feminino',
          data: d1,
          backgroundColor: '#FB75FF',
          borderColor: ['#3E1E6F'],
          borderWidth: 1
        },
          {
          label: 'Masculino',
          data: d2,
          backgroundColor: '#00A2FF',
          borderColor: ['#3E1E6F'],
          borderWidth: 1
          },
          {
          label: 'Não Identificado',
          data: d3,
          backgroundColor: '#FFFFFF',
          borderColor: ['#3E1E6F'],
          borderWidth: 1
          }]
      },
      options: {
        plugins: {
          legend: {
            position: 'top',
          },
          title: {
            display: true,
            text: "Diferença na Quantidade de Crimes com Morte por Sexo das Vítimas" + text,
            font: { size: '20px' }
          }
        },
        responsive: true,
        scales: {
          x: {
            stacked: true,
          },
          y: {
            stacked: true,
          }
        }
      }
    });
  }

  renderChart5(labeldata: any, maindata: any, colors: any, type: any) {
    const option = JSON.parse(this.search).opcao;
    let text:string = ""
    if(option == 'populacao'){
      text = text + 'da população';
    } else if(option == 'crimes'){
      text = text + 'dos ' + option;
    } else {
      text = text + 'dos crimes com morte';
    }

    if (this.getRegiao() == "Brasil") {
      text = text + " no";
    } else {
      text = text + " na";
    }

    const myChart = new Chart('opcao', {
      type: type,
      data: {
        labels: labeldata,
        datasets: [{
          label: 'Quantidade de Ocorrências',
          data: maindata,
          backgroundColor: colors,
          borderColor: ['#3E1E6F'],
          borderWidth: 1
        }]
      },
      options: {
        plugins: {
          legend: {
            position: 'top',
          },
          title: {
            display: true,
            text: "Crescimento " + text + " " + this.nome[0] + " desde 2015",
            font: { size: '20px' }
          }
        },
        responsive: true,
      }
    });
  }
}
