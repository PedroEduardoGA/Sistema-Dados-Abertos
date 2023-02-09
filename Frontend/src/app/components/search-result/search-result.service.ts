import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchResultService {
  apiOpcao: string = "/sda-api/dados-gerais/consult-dados?opcao=";


  constructor(private http: HttpClient) { }
  postSearch(search: string, option: string): Observable<any>{
    const header = new HttpHeaders().append('Content-Type', 'application/json');
    return this.http.post(this.apiOpcao.concat(option), search, {headers: header});
  }
}
