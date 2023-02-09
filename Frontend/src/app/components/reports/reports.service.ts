import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CARGA} from "../../models/Cargas";

@Injectable({
  providedIn: 'root'
})
export class ReportsService {

  apiUrl: string = '/sda-api/cargas/read-cargas'

  constructor(private http: HttpClient) { }

  getCargasLista(): Observable<any>{

    return this.http.get<CARGA[]>(this.apiUrl);

  }
}
