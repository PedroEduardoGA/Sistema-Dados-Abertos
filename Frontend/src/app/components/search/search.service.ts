import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ANO } from 'src/app/models/Ano';
import { ESTADO } from 'src/app/models/Estado';
import { REGIAO } from 'src/app/models/Regiao';
import { TIPO } from 'src/app/models/Tipo';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  apiTiposUrl: string = "/sda-api/dados-crimes/read-tipos";
  apiAnosUrl: string = "/sda-api/dados-gerais/read-anos";
  apiEstadosUrl: string = "/sda-api/dados-estados/read-estados";
  apiRegioesUrl: string = "/sda-api/dados-estados/read-regioes";


  constructor(private http: HttpClient) { }

  getTiposList(): Observable<any>{ return this.http.get<TIPO[]>(this.apiTiposUrl); }
  getAnosList(): Observable<any>{ return this.http.get<ANO[]>(this.apiAnosUrl); }
  getEstadosList(): Observable<any> {return this.http.get<ESTADO[]>(this.apiEstadosUrl);}
  getRegioesList(): Observable<any> {return this.http.get<REGIAO[]>(this.apiRegioesUrl);}

}
