import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WorkService } from '../../works/work.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-new-review',
  templateUrl: './add-new-review.component.html',
  styleUrls: ['./add-new-review.component.css']
})
export class AddNewReviewComponent implements OnInit {

  recenzija: any;
  titleAuthor: string;
  titleEditor: string;

  constructor(private workService:WorkService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location) { }

  ngOnInit() {
    this.titleAuthor = '';
    this.titleEditor = '';
    this.workService.getMyREc(this.route.snapshot.params['id'],this.route.snapshot.params['idRevizije'] )
        .then(rec => 
          {
            this.recenzija = rec;
            console.log(rec);
          });
  }

  save(){
    console.log(this.recenzija);
    console.log(this.titleAuthor);
    this.recenzija.sadrzaj.pitanja.push({'tekstPitanja': this.titleAuthor});
    this.recenzija.sadrzaj.pitanja.push({'tekstPitanja': this.titleEditor});
    this.workService.addRec(this.recenzija, this.route.snapshot.params['id'],this.route.snapshot.params['idRevizije']);
    
  }

  cancel(){
    this.location.back();
  }

  onSelectionChange(id: number, opcija: string){
    console.log(id);
    console.log(opcija);
    this.recenzija.sadrzaj.pitanja[id].odgovor = opcija;
    console.log(this.recenzija);    

    

  }

}
