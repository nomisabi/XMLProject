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
        path:'naucniRadovi/:id/revizije/:idRevizije/recenzent',
        component: AddReviewComponent
      },
      {
        path:'naucniRadovi/:id',
        component: WorkDetailComponent
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
