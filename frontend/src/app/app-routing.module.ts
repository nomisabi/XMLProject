import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeAuthorComponent } from './home-author/home-author.component';
import { HomeReviewerComponent } from './home-reviewer/home-reviewer.component';
import { HomeEditorComponent } from './home-editor/home-editor.component';
import { HomeComponent } from './home/home.component';
import { AddWorkComponent } from './home-author/add-work/add-work.component';
import { WorksComponent } from './works/works.component';
import { MyWorksComponent } from './home-author/my-works/my-works.component';
import { NewWorksComponent } from './home-editor/new-works/new-works.component';
import { WorkDetailComponent } from './work-detail/work-detail.component';
import { ProgressWorkComponent } from './home-editor/progress-work/progress-work.component';
import { AddReviewComponent } from './home-editor/add-review/add-review.component';
import { WorkDetailAuthorComponent } from './home-author/work-detail-author/work-detail-author.component';
import { ShowXhtmlComponent } from './work-detail/show-xhtml/show-xhtml.component';
import { WorksForMeComponent } from './home-reviewer/works-for-me/works-for-me.component';
import { WorkDetailReviewerComponent } from './home-reviewer/work-detail-reviewer/work-detail-reviewer.component';
import { ShowXmlComponent } from './work-detail/show-xml/show-xml.component';
import { MyWorksReviewerComponent } from './home-reviewer/my-works-reviewer/my-works-reviewer.component';
import { AddNewReviewComponent } from './home-reviewer/add-new-review/add-new-review.component';
import { RevisionDetailsComponent } from './work-detail/revision-details/revision-details.component';
import { WorkDetailEditorComponent } from './home-editor/work-detail-editor/work-detail-editor.component';
import { ShowXhtmlreviewerComponent } from './home-reviewer/show-xhtmlreviewer/show-xhtmlreviewer.component';

const routers: Routes = [
  { path: 'prijava', component: LoginComponent },
  { path: 'registracija', component: RegisterComponent},
  { path: '', 
    component: HomeComponent,
    children: [
      {path: '', component: WorksComponent},
      {
        path:'naucniRadovi/:id',
        component: WorkDetailComponent
      },
      {
        path:'naucniRadovi/:id/revizija/:i',
        component: RevisionDetailsComponent
      },
      {
        path:'naucniRadovi/:id/revizija/:i#:pog',
        component: RevisionDetailsComponent
      },
      {
        path:'naucniRadovi/:id/xhtml',
        component: ShowXhtmlComponent
      },
      {
        path:'naucniRadovi/:id/xml',
        component: ShowXmlComponent
      }

  ]},
  { path: 'autor', 
    component: HomeAuthorComponent,
    children: [
    {
      path: 'naucniRadovi/novi',
      component: AddWorkComponent
    },
    {
      path:'naucniRadovi/moji',
      component: MyWorksComponent
    },
    {
      path: 'naucniRadovi/:id',
      component: WorkDetailAuthorComponent
    },
    {
      path:'naucniRadovi/:id/revizija/:i',
      component: RevisionDetailsComponent
    },
    {
      path:'naucniRadovi/:id/revizija/:i#:pog',
      component: RevisionDetailsComponent
    },
    {
      path:'naucniRadovi/:id/xhtml',
      component: ShowXhtmlComponent
    },
    {
      path:'naucniRadovi/:id/xml',
      component: ShowXmlComponent
    }
  ]},
  { path: 'recenzent', 
    component: HomeReviewerComponent,
    children: [
      { 
        path:'naucniRadovi/dodeljeni',
        component: WorksForMeComponent
      },
      { 
        path:'naucniRadovi/prihvaceni',
        component: MyWorksReviewerComponent
      },
      {
        path: 'naucniRadovi/:id/revizije/:idRevizije/recenzija',
        component: AddNewReviewComponent
      },
      {
        path: 'naucniRadovi/:id/revizije/:idRevizije',
        component: WorkDetailReviewerComponent
      },
      {
        path:'naucniRadovi/:id/xhtml',
        component: ShowXhtmlreviewerComponent
      },
    ]},
  { path: 'urednik', 
    component: HomeEditorComponent,
    children: [
      {
        path: 'naucniRadovi/poslati',
        component: NewWorksComponent
      },
      {
        path: 'naucniRadovi/uObradi',
        component: ProgressWorkComponent
      },
      {
        path: 'naucniRadovi/objavljeni',
        component: WorksComponent
      },
      {
        path:'naucniRadovi/:id/revizije/:idRevizije/recenzent',
        component: AddReviewComponent
      },
      {
        path:'naucniRadovi/:id',
        component: WorkDetailEditorComponent
      },
      {
        path:'naucniRadovi/:id/revizija/:i',
        component: RevisionDetailsComponent
      },
      {
        path:'naucniRadovi/:id/revizija/:i#:pog',
        component: RevisionDetailsComponent
      },
      {
        path:'naucniRadovi/:id/xhtml',
        component: ShowXhtmlComponent
      },
      {
        path:'naucniRadovi/:id/xml',
        component: ShowXmlComponent
      }
      
      
    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [ RouterModule.forRoot(routers) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
