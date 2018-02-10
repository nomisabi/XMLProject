import { Component, OnInit } from '@angular/core';
import { WorkService } from '../../works/work.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-revision-details',
  templateUrl: './revision-details.component.html',
  styleUrls: ['./revision-details.component.css']
})
export class RevisionDetailsComponent implements OnInit {

  revizija: RevizijaInterface;
  

  constructor(
              private workService: WorkService,
              private router: Router,
              private route: ActivatedRoute) {  }

  ngOnInit() {
    this.revizija={
      abstrakt:{
        stukturiranAbstakt:{ 
          designMethodologyApproach:'',
          findings:'',
          originalityValue:'',
          practicalImplications:'',
          purpose:'',
          researchLimitationsImplications:'',
          socialImplications:''
        },
        neStrukturiranAbstrakt:null},
      autor:null,
      id:null,
      kljucnaRec:[],
      naslov:'',
      referenca:null,
      sadrzaj:null,
      status:null
    }
    this.getRevizija();
  }


  getRevizija(){
    this.workService.getRevizija(this.route.snapshot.params['id'],this.route.snapshot.params['i'] )
        .then(revizija => {
          this.revizija = revizija;
            console.log('rev: '+JSON.stringify(this.revizija));
          }).catch(err=>console.log('error'));
  }

  goToNaucniRad(id){
    this.router.navigate(['naucniRadovi/',id])
  }

  goToRecenzija(id){

  }

  goToPoglavlja(id){
    /*let url:string='naucniRadovi/'+this.route.snapshot.params['id']+ '/revizija/'+this.route.snapshot.params['i']+'\#'+id;
    console.log(url);
    this.router.navigate([url])*/
    try {
      document.querySelector('#' + id).scrollIntoView();
    } catch (e) { }
  }
 
}
