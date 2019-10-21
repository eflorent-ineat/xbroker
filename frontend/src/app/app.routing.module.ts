import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { LandingComponent } from './components/landing/landing.component'
import { LoginComponent } from './user/components/login.component';

const routes: Routes = [
        { path: 'login', component: LoginComponent },
        { path: '', component: LandingComponent, canActivate: [AuthGuard] },
        { path: '**', redirectTo: '/' }
        ];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule { }