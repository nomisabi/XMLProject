import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

const routers: Routes = [
  { path: 'prijava', component: LoginComponent },
  { path: 'registracija', component: RegisterComponent},
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [ RouterModule.forRoot(routers) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
