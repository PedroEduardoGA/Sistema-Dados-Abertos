import { Component, OnInit } from '@angular/core';
import { UploadService } from "./upload.service";
import {workspaceSchemaPath} from "@angular/cli/src/utilities/config";

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})
export class UploadComponent implements OnInit {
  // Variable to store shortLink from api response
  completed: boolean = false;
  loading: boolean = false; // Flag variable
  //@ts-ignore
  file: File = null; // Variable to store file
  //@ts-ignore
  ocorrenciasJSON: string = null;
  //@ts-ignore
  vitimasJSON: string = null;

  //@ts-ignore
  poptotalJSON: string = null;

  // colocar o serviço
  constructor(private uploadService: UploadService) { }

  ngOnInit(): void { }

  // On file Select
  onChange(event: any) {
    this.file = event.target.files[0];

  }

  private async treatIndiceFile(){
    const data = await this.file.arrayBuffer();
    const XLSX = require("xlsx");
    const wb = XLSX.read(data);

    const ocorrencias = wb.SheetNames[0];
    const vitimas = wb.SheetNames[1];

    const ocorrenciasJson = XLSX.utils.sheet_to_json(wb.Sheets[ocorrencias]);
    ocorrenciasJson.map((obj: any) => {
      switch (obj['Mês']){
        case 'janeiro':
          obj['Mês'] = 1;
          break;
        case 'fevereiro':
          obj['Mês'] = 2;
          break;
        case 'março':
          obj['Mês'] = 3;
          break;
        case 'abril':
          obj['Mês'] = 4;
          break;
        case 'maio':
          obj['Mês'] = 5;
          break;
        case 'junho':
          obj['Mês'] = 6;
          break;
        case 'julho':
          obj['Mês'] = 7;
          break;
        case 'agosto':
          obj['Mês'] = 8;
          break;
        case 'setembro':
          obj['Mês'] = 9;
          break;
        case 'outubro':
          obj['Mês'] = 10;
          break;
        case 'novembro':
          obj['Mês'] = 11;
          break;
        case 'dezembro':
          obj['Mês'] = 12;
          break;
        default:
          break;
      }
      obj['nome_est'] = obj['UF']
      obj['tipo'] = obj['Tipo Crime']
      obj['mes'] = obj['Mês']
      obj['ano'] = obj['Ano']
      obj['quantidade_ocorrencias'] = obj['Ocorrências']
      delete obj['Ocorrências']
      delete obj['Mês']
      delete obj['Tipo Crime']
      delete obj['UF']
      delete obj['Ano']
    })
    this.ocorrenciasJSON = JSON.stringify(ocorrenciasJson);

    const vitimasJson = XLSX.utils.sheet_to_json(wb.Sheets[vitimas]);
    vitimasJson.map((obj: any) => {
      switch (obj['Sexo da Vítima']) {
        case 'Masculino':
          obj['Sexo da Vítima']= 'M';
          break;
        case 'Feminino':
          obj['Sexo da Vítima']= 'F';
          break;
        case 'Sexo NI':
          obj['Sexo da Vítima']= 'NI';
          break;
        default:
          break;
      }
      switch (obj['Mês']){
        case 'janeiro':
          obj['Mês'] = 1;
          break;
        case 'fevereiro':
          obj['Mês'] = 2;
          break;
        case 'março':
          obj['Mês'] = 3;
          break;
        case 'abril':
          obj['Mês'] = 4;
          break;
        case 'maio':
          obj['Mês'] = 5;
          break;
        case 'junho':
          obj['Mês'] = 6;
          break;
        case 'julho':
          obj['Mês'] = 7;
          break;
        case 'agosto':
          obj['Mês'] = 8;
          break;
        case 'setembro':
          obj['Mês'] = 9;
          break;
        case 'outubro':
          obj['Mês'] = 10;
          break;
        case 'novembro':
          obj['Mês'] = 11;
          break;
        case 'dezembro':
          obj['Mês'] = 12;
          break;
        default:
          break;
      }
      obj['nome_est'] = obj['UF']
      obj['tipo'] = obj['Tipo Crime']
      obj['mes'] = obj['Mês']
      obj['ano'] = obj['Ano']
      obj['sexo'] = obj['Sexo da Vítima']
      obj['quantidade_vitimas'] = obj['Vítimas']
      delete obj['Vítimas']
      delete obj['Mês']
      delete obj['Tipo Crime']
      delete obj['UF']
      delete obj['Sexo da Vítima']
      delete obj['Ano']
    })
    this.vitimasJSON = JSON.stringify(vitimasJson);
    // console.log(ocorrenciasJson);
    // console.log(vitimasJson);
  }

  // OnClick of button Upload
  onIndiceUpload() {
    this.loading = !this.loading;
    this.treatIndiceFile().then(() =>{
      this.uploadService.uploadIndiceCrimes(this.ocorrenciasJSON,  this.file.name).subscribe({
        next: (data) => {
          console.log("Upload de indice crimes: ", data);
        },
        error: (erro) => {
          alert("Erro no upload!");
          console.error(erro, erro.message);
        }
      });
      this.uploadService.uploadIndiceCrimesVitimas(this.vitimasJSON, this.file.name).subscribe({
        next: (data) => {
          console.log("Upload de indice crimes com vitimas: ", data);
        },
        error: (erro) => {
          alert("Erro no upload!");
          console.error(erro, erro.message);
        }
      });
      }
    )

  }

  onPopUpload() {
    this.loading = !this.loading;
    this.treatPopFile().then(() => {
      this.uploadService.uploadPop(this.poptotalJSON, this.file.name).subscribe({
        next: (data) => {
          console.log("Upload de pop total: ", data);
        },
        error: (erro) => {
          alert("Erro no upload!");
          console.error(erro, erro.message);
        }
        })
    });

  }

  private async treatPopFile() {
    const data = await this.file.arrayBuffer();
    let flag = 0;
    if (this.file.name.includes('2015') || this.file.name.includes('2016')){
      flag = 1;
    }
    const XLSX = require("xlsx");
    const wb = XLSX.read(data);
    let popTotal = wb.Sheets[wb.SheetNames[0]];
    const range = XLSX.utils.decode_range(popTotal['!ref']);
    range.s.r = 1;
    range.e.r = 34;
    if(flag){
      range.s.r = 2;
      range.e.r = 35;
    }
    popTotal['!ref'] = XLSX.utils.encode_range(range);
    const popTotalJSON = XLSX.utils.sheet_to_json(popTotal);
    popTotalJSON.map((obj: any) =>{
      obj['nome_est'] = obj['BRASIL E UNIDADES DA FEDERAÇÃO']
      obj['pop_total'] = parseInt(String(obj['POPULAÇÃO ESTIMADA']).split('.').join("").split('(')[0]);
      delete obj['BRASIL E UNIDADES DA FEDERAÇÃO'];
      delete obj['POPULAÇÃO ESTIMADA'];
      }
    )
    // console.log(popTotalJSON);
    this.poptotalJSON = JSON.stringify(popTotalJSON);
  }
}
