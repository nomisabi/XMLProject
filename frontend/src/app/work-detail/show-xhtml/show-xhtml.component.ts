import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { WorkService } from '../../works/work.service';
import * as FileSaver from 'file-saver'; 

@Component({
  selector: 'app-show-xhtml',
  templateUrl: './show-xhtml.component.html',
  styleUrls: ['./show-xhtml.component.css']
})
export class ShowXhtmlComponent implements OnInit {

  new_temp:string;
  constructor(
    private workService: WorkService,
    private router: Router,
    private route: ActivatedRoute){
      
    }
  ngOnInit() {
    this.workService.getHtml(this.route.snapshot.params['id']).then(
      res =>{this.new_temp=res;
      console.log(res)});
  }

  download(){
    var blob = new Blob([this.new_temp], {type: "text/html;charset=utf-8"});
    FileSaver.saveAs(blob, "naucniRad.html");
  }

}
