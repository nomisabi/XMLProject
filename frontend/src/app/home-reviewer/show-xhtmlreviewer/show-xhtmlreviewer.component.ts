import { Component, OnInit } from '@angular/core';
import { WorkService } from '../../works/work.service';
import { Router, ActivatedRoute } from '@angular/router';
import * as FileSaver from 'file-saver'; 

@Component({
  selector: 'app-show-xhtmlreviewer',
  templateUrl: './show-xhtmlreviewer.component.html',
  styleUrls: ['./show-xhtmlreviewer.component.css']
})
export class ShowXhtmlreviewerComponent implements OnInit {

  new_temp:string;
  constructor(
    private workService: WorkService,
    private router: Router,
    private route: ActivatedRoute){
      
    }
  ngOnInit() {
    this.workService.getHtmlNoAuthor(this.route.snapshot.params['id']).then(
      res =>{this.new_temp=res;
      console.log(res)});
  }

  download(){
    var blob = new Blob([this.new_temp], {type: "text/html;charset=utf-8"});
    FileSaver.saveAs(blob, "naucniRad.html");
  }

}
