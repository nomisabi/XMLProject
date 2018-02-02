import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { ToastModule, ToastOptions } from 'ng2-toastr/ng2-toastr';

import { TokenInterceptorService } from './services/token-interceptor.service';
import { AuthService } from './login/auth.service';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from './/app-routing.module';
import { RegisterComponent } from './register/register.component';


export class CustomOption extends ToastOptions {
  animate = 'flyRight'; // you can override any options available
  newestOnTop = false;
  showCloseButton = true;
}


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    AngularFontAwesomeModule,
    ToastModule.forRoot(),
    AppRoutingModule
  ],
  providers: [
    AuthService,
    { provide: ToastOptions, useClass: CustomOption },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
