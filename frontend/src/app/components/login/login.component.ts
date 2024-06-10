import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {ThemeService} from "../../services/theme.service";
import {AlertService} from "../../services/alert.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {

  email = "";
  password = "";
  loading = false;
  chooseRoleModalOpened = false;

  constructor(private authService: AuthService,
              private router: Router,
              public themeService: ThemeService,
              private alertService: AlertService,
              private userService: UserService) {
  }

  ngOnInit(): void {
  }

  login() {
    this.authService.login(this.email, this.password).subscribe((result) => {
      this.loading = true

      // @ts-ignore
      const roles = this.getAppRolesFromJwt(result.body.token);
      if (roles.length === 0) {
        this.alertService.showAlert("danger", "Wait for administrator to assign you a role")
        this.loading = false
        return;
      } else if (roles.length > 1) {
        this.chooseRoleModalOpened = true;
      } else {
        this.roleHasBeenChosen(roles[0] || "")
      }
      this.authService.saveJWT(result)
    }, error => {
      this.alertService.showAlert("danger", "Wrong credentials")
      this.loading = false
    })
  }

  getAppRolesFromJwt(jwt?: string): string[] {
    const decodedJWT = this.authService.decodeJWT(jwt || this.authService.getJwtFromStorage()!)
    let roles: string[] = []
    decodedJWT?.realm_access?.roles.forEach((role: string) => {
      role = role.replace("SYLLABUS_", "");
      if (["STUDENT", "TEACHER", "DIRECTOR", "ADMIN"].includes(role)) {
        roles.push(role)
      }
    })
    return roles;
  }

  roleHasBeenChosen(role: string) {
    localStorage.setItem("role", role)
    this.chooseRoleModalOpened = false;
    this.userService.getLoggedInUserObservable()?.subscribe((result) => {
      if (result) {
        this.userService.user = result
      }
      this.router.navigate(['/dashboard'])
    })
  }

  closeRoleModal() {
    this.loading = false
    this.chooseRoleModalOpened = false;
  }
}
