export class EstadoR {
  nome: string = '';
  percentual: number = -1;
  url: string = './assets/Bandeiras/'
  ano: number = 0;
  constructor (nome: string, percentual: number, ano: number){
    this.nome = nome;
    this.percentual = percentual;
    this.url = this.url + nome + '.png';
    this.ano = ano;
  }
}
