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
    var id = this.route.snapshot.params['id'];
    var idRevision = this.route.snapshot.params['idRevizije'];
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
    this.userService.getReviews(id, idRevision)
    .then(reviews => {
      console.log(reviews);
      this.users = reviews.korisnik;
      console.log(this.users);
    })
    .catch(() => this.toastr.error('Nema recenzenata u sistemu.'));
  }

  save(){
    if (this.selected.length === 2){
      var id = this.route.snapshot.params['id'];
      var idRevision = this.route.snapshot.params['idRevizije'];
      console.log(this.selected);
      this.revision.review1 = this.selected[0].korisnickoIme;
      this.revision.review2 = this.selected[1].korisnickoIme;
      console.log(this.revision);
      this.workService.addReview(id,idRevision, this.revision)
          .then(res => this.router.navigate(['/urednik/naucniRadovi/uObradi']));
    }else{
      this.toastr.error('Morate izabrati dva recenzenta.');

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



}
