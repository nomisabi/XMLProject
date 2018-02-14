import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { UserService } from '../../work-detail/user.service';
import { WorkService } from '../../works/work.service';


@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.css']
})
export class AddReviewComponent implements OnInit {

  revision: RevisionInterface;
  users: any;
  selected: any[];
  
  param: string;
  review: number;

  constructor(private userService: UserService,
              private workService: WorkService,
              private router: Router,
              private route: ActivatedRoute,
              private location: Location,
              private toastr: ToastsManager,
              private vcr: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    this.selected = [];
    this.revision = {
      id: '',
      title: '',
      status: '',
      review1: '',
      review2: '',
      hasLetter: false,
      review: null,
      reviews: []
    }

    this.getReviews();
  }

  getReviews(){
    var id = this.route.snapshot.params['id'];
    var idRevision = this.route.snapshot.params['idRevizije'];

    this.userService.getReviews(id, idRevision)
    .then(reviews => {
      console.log(reviews);
      this.users = reviews.korisnik;
      console.log(this.users);
      this.getWork();
    })
    .catch(() => this.toastr.error('Nema recenzenata u sistemu.'));

  }

  getWork(){
    var id = this.route.snapshot.params['id'];
    var idRevision = this.route.snapshot.params['idRevizije'];
    this.review = 0;
    this.workService.getWork(id)
        .then(work => 
          {
            for(let revision of work.revisions){
              if (revision.id === idRevision){
                this.revision = revision;
                var prihvacen: number = 0;
                var ceka: number = 0;
                var odbijen: number = 0;
                if(revision.status === 'POSLAT'){
                  this.review = 2;
                }
                else if(revision.status === 'U_OBRADI'){
                  for(let review of revision.reviews){
                    if(review.status === 'PRIHVACEN'){
                      prihvacen ++;
                    }else if(review.status === 'CEKA_SE'){
                      ceka ++;
                    }else if(review.status === 'ODBIJEN'){
                      odbijen ++;
                    }
                  }

                  if (prihvacen === 1 && ceka === 0 && odbijen > 0){
                    console.log('dodaj recenzenta');
                    this.review = 1;
                  }
                  if (prihvacen === 0 && ceka === 1 && odbijen > 0){
                    console.log('dodaj recenzenta');
                    this.review = 1;
                  }
                  if (prihvacen === 0 && ceka === 0 && odbijen > 0){
                    console.log('dodaj recenzenta');
                    this.review = 2;
                  }
                  console.log(this.review);
                }
               }
            }
          });
  }

  save(){
    if (this.selected.length === this.review){
      var id = this.route.snapshot.params['id'];
      var idRevision = this.route.snapshot.params['idRevizije'];
      console.log(this.selected);
      if (this.review === 2){
        this.revision.review1 = this.selected[0].korisnickoIme;
        this.revision.review2 = this.selected[1].korisnickoIme;
      }else if (this.review === 1){
        this.revision.review1 = this.selected[0].korisnickoIme;
      }
      console.log(this.revision);
      this.workService.addReview(id,idRevision, this.revision)
          .then(res => this.router.navigate(['/urednik/naucniRadovi/uObradi']));
    }else{
      if (this.review === 2){
        this.toastr.error('Morate izabrati dva recenzenta.');
      }
      else if (this.review === 1){
        this.toastr.error('Morate izabrati jednog recezenta.');

      }

    }
  }

  cancel(){
    this.location.back();
  }

  add(user){
    if (this.selected.length < 2){
      this.selected.push(user);
      console.log(this.selected);
    }else{
      this.toastr.error('Ne mozete dodati vise od dva recenzenta.');
    }
  }

  delete(user){
    for(let i in this.selected){
      if (this.selected[i].id === user.id){
        this.selected.splice(+i, 1);
      }
    }
  }

  contains(user): boolean{
    for(let u of this.selected){
      if(u.id === user.id){
        return true;
      }
    }
    return false;
  }

  find(){
    var id = this.route.snapshot.params['id'];
    var idRevision = this.route.snapshot.params['idRevizije'];
    if (this.param === ""){
      this.getReviews();
    }else{
      this.userService.findReviews(id, idRevision, this.param)
          .then(reviews => {
            console.log(reviews);
            this.users = reviews.korisnik;
          })
          .catch(() => {
            this.toastr.error('Trazeni korisnik ne postoji.');
            this.users = [];
          });
      }
  }

}
