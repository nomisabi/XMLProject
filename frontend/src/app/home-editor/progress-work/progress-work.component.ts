import { Component, OnInit } from '@angular/core';
import { WorkService } from '../../works/work.service';

@Component({
  selector: 'app-progress-work',
  templateUrl: './progress-work.component.html',
  styleUrls: ['./progress-work.component.css']
})
export class ProgressWorkComponent implements OnInit {

  works: WorkInterface[];

  constructor(private workService: WorkService) { }

  ngOnInit() {
    this.getWorks();
  }

  getWorks(){
    this.workService.progressWorks()
        .then(works => this.works = works);
  }

}
