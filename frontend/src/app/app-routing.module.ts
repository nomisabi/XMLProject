import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeAuthorComponent } from './home-author/home-author.component';
import { HomeReviewerComponent } from './home-reviewer/home-reviewer.component';
import { HomeEditorComponent } from './home-editor/home-editor.component';
import { HomeComponent } from './home/home.component';
import { AddWorkComponent } from './add-work/add-work.component';
import { WorksComponent } from './works/works.component';
import { MyWorksComponent } from './my-works/my-works.component';
import { NewWorksComponent } from './new-works/new-works.component';
import { WorkDetailComponent } from './work-detail/work-detail.component';
import { ProgressWorkComponent } from './progress-work/progress-work.component';
import { AddReviewComponent } from './add-review/add-review.component';
import { WorkDetailAuthorComponent } from './work-detail-author/work-detail-author.component';

const routers: Routes = [
  { path: 'prijava', component: LoginComponent },
  { path: 'registracija', component: RegisterComponent},
  { path: '', 
    component: HomeComponent,
    children: [
      {path: '', component: WorksComponent}

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
    }
  ]},
  { path: 'recenzent', component: HomeReviewerComponent},
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
        path:'naucniRadovi/:id/recenzent',
        component: AddReviewComponent
      },
      
    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [ RouterModule.forRoot(routers) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
