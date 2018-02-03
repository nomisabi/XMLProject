import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeAuthorComponent } from './home-author/home-author.component';
import { HomeReviewerComponent } from './home-reviewer/home-reviewer.component';
import { HomeEditorComponent } from './home-editor/home-editor.component';
import { HomeComponent } from './home/home.component';

const routers: Routes = [
  { path: 'prijava', component: LoginComponent },
  { path: 'registracija', component: RegisterComponent},
  { path: '', component: HomeComponent},
  { path: 'autor', component: HomeAuthorComponent},
  { path: 'recenzent', component: HomeReviewerComponent},
  { path: 'urednik', component: HomeEditorComponent},
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [ RouterModule.forRoot(routers) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
