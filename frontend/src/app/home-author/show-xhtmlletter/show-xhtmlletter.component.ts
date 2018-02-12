import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { WorkService } from '../../works/work.service';
import * as FileSaver from 'file-saver'; 

@Component({
  selector: 'app-show-xhtmlletter',
  templateUrl: './show-xhtmlletter.component.html',
  styleUrls: ['./show-xhtmlletter.component.css']
})
export class ShowXhtmlletterComponent implements OnInit {

  new_temp:string;
  constructor(
    private workService: WorkService,
    private router: Router,
    private route: ActivatedRoute){
      
    }
  ngOnInit() {
    this.workService.getHtmlPismo(this.route.snapshot.params['id'], this.route.snapshot.params['revId']).then(
      res =>{this.new_temp=res;
      console.log(res)});
  }

  download(){
    var blob = new Blob([this.new_temp], {type: "text/html;charset=utf-8"});
    FileSaver.saveAs(blob, "pismo.html");
  }

}
