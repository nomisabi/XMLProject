import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { WorkService } from '../../works/work.service';
import * as FileSaver from 'file-saver'; 

@Component({
  selector: 'app-show-xml',
  templateUrl: './show-xml.component.html',
  styleUrls: ['./show-xml.component.css']
})
export class ShowXmlComponent implements OnInit {

  xml:string;
  constructor(
    private workService: WorkService,
    private router: Router,
    private route: ActivatedRoute){
      
    }
  ngOnInit() {
    this.workService.getXml(this.route.snapshot.params['id']).then(
      res =>{this.xml=res;
      console.log(res)});
  }

  download(){
    var blob = new Blob([this.xml], {type: "application/xml;charset=utf-8"});
    FileSaver.saveAs(blob, "naucniRad.xml");
  }
}
