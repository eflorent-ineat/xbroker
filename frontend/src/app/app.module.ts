import { NgModule } from '@angular/core';
import { BrowserModule, Title }  from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from './material-module';
import {FlexLayoutModule} from "@angular/flex-layout";
import { LayoutModule } from '@angular/cdk/layout';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { AppRoutingModule } from './app.routing.module';
import { AuthGuard } from './auth.guard';
import { NavigationComponent } from './components/general/navigation/navigation.component';
import { TopBarComponent } from './components/general/top-bar/top-bar.component';
import { LoginComponent } from './user/components/login.component';
import { LandingComponent } from './components/landing/landing.component';
import { LeftMenuComponent } from './components/general/left-menu/left-menu.component';
import { EffectsModule } from '@ngrx/effects';
import { UserEffects } from './user/user.effects';
import { Store, StoreModule } from '@ngrx/store';
import { reducer as userReducer } from './user/user.reducers';
import {  httpInterceptorProviders } from './http-interceptors';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    LayoutModule,
    EffectsModule.forRoot([UserEffects]),
    // StoreModule.forRoot(userReducer]),
    StoreModule.forRoot({user: userReducer}),
       // Instrumentation must be imported after importing StoreModule (config is optional)
       StoreDevtoolsModule.instrument({
         maxAge: 25, // Retains last 25 states
         //logOnly: environment.production, // Restrict extension to log-only mode
       })

  ],
  declarations: [
    AppComponent,
    TopBarComponent,
    NavigationComponent,
    LeftMenuComponent,
    LoginComponent,
    LandingComponent
  ],
    providers: [
      Title,
      CookieService,
      AuthGuard,
      httpInterceptorProviders
    ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
