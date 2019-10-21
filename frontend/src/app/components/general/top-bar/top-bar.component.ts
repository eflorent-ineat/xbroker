import { Component, OnInit } from '@angular/core';
import { Title }     from '@angular/platform-browser';
import { UserService }     from '../../../user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

  title: string

  constructor(public titleService: Title, public userService: UserService, private router: Router) { }

  ngOnInit() {

  }

  logout() {
    this.userService.logout()
          .subscribe(success => {
            if (success) {
              this.router.navigate(['/login']);
            }
          });
  }

}
