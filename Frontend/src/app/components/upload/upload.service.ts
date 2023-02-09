import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  baseApiUrlCrimes = "/sda-api/dados-crimes/insert-crimes/file?name=";
  baseApiUrlCrimesF = "/sda-api/dados-crimes/insert-crimes-vitimas/file?name=";
  baseApiUrlPop = "/sda-api/dados-estados/insert-pop/file?name=";

  constructor(private http:HttpClient) { }

  uploadIndiceCrimes(crimes: string, fileName: string):Observable<any> {
    const header = new HttpHeaders().append('Content-Type', 'application/json')
    return this.http.post(this.baseApiUrlCrimes.concat(fileName+"-ocorrencias"), crimes, { headers: header })
  }

  uploadIndiceCrimesVitimas(crimes: string, fileName: string):Observable<any> {
    const header = new HttpHeaders().append('Content-Type', 'application/json')
    return this.http.post(this.baseApiUrlCrimesF.concat(fileName+"-vitimas"), crimes, { headers: header })
  }

  uploadPop(popTotal: string, fileName: string): Observable<any>{
    const header = new HttpHeaders().append('Content-Type', 'application/json')
    return this.http.post(this.baseApiUrlPop.concat(fileName), popTotal, {headers: header});
  }
}
