import { Component, OnInit } from '@angular/core';
import {ReportsService} from "./reports.service";
import {CARGA} from "../../models/Cargas";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {
  cargasList: CARGA[] = [];
  showCargasTable: boolean = false;


  constructor(private reportService: ReportsService) { }

  ngOnInit(): void {
    this.showCargasTable = !this.showCargasTable
    this.reportService.getCargasLista().subscribe(cargasList => {
      this.cargasList = cargasList;
    });
  }

}
