import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { ToastModule, ToastOptions } from 'ng2-toastr/ng2-toastr';
import { DropdownModule } from 'ngx-dropdown';

import { UserService } from './work-detail/user.service';
import { WorkService } from './works/work.service';
import { UploadFileService } from './add-work/upload-file.service';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { AuthService } from './login/auth.service';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from './/app-routing.module';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { HomeAuthorComponent } from './home-author/home-author.component';
import { HomeEditorComponent } from './home-editor/home-editor.component';
import { HomeReviewerComponent } from './home-reviewer/home-reviewer.component';
import { AddWorkComponent } from './add-work/add-work.component';
import { WorksComponent } from './works/works.component';
import { MyWorksComponent } from './my-works/my-works.component';
import { NewWorksComponent } from './new-works/new-works.component';
import { ProgressWorkComponent } from './progress-work/progress-work.component';
import { WorkDetailComponent } from './work-detail/work-detail.component';


export class CustomOption extends ToastOptions {
  animate = 'flyRight'; // you can override any options available
  newestOnTop = false;
  showCloseButton = true;
}


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    HomeAuthorComponent,
    HomeEditorComponent,
    HomeReviewerComponent,
    AddWorkComponent,
    WorksComponent,
    MyWorksComponent,
    NewWorksComponent,
    ProgressWorkComponent,
    WorkDetailComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    AngularFontAwesomeModule,
    ToastModule.forRoot(),
    DropdownModule,
    AppRoutingModule
  ],
  providers: [
    AuthService,
    UploadFileService,
    WorkService,
    UserService,
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
