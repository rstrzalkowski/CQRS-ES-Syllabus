import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import {AlertService} from "../../../../services/alert.service";

@Component({
  selector: 'app-assign-role',
  templateUrl: './assign-role.component.html'
})
export class AssignRoleComponent implements OnInit {
  //Loading
  loading = false
  roles: string[] = []
  //end loading


  @Input() user: User | undefined;

  @Output() close: EventEmitter<any> = new EventEmitter()
  @Output() success: EventEmitter<string> = new EventEmitter()

  constructor(private userService: UserService,
              private alertService: AlertService) {
  }

  ngOnInit(): void {
    if (this.user) {
      this.roles = ["STUDENT", "TEACHER", "DIRECTOR", "ADMIN"]
      this.user.roles.forEach((userRole => {
        this.roles = this.roles.filter(role => {
          userRole = userRole.replace("SYLLABUS_", "")
          return role != userRole
        })
      }))
    }
  }

  @HostListener('document:keydown', ['$event']) onKeydownHandler(event: KeyboardEvent) {
    if (event.key === "Escape") {
      this.closeModal()
    }
  }

  closeModal() {
    this.close.emit()
  }

  assign(role: string) {
    if (this.user) {
      this.userService.assignRole(this.user.id, role).subscribe(() => {
        this.alertService.showAlert("success", "Role has been assigned")
        this.success.emit();
      }, error => {
        this.alertService.showAlert("danger", "Error during assignment. Refresh this page and try again")
        this.success.emit();
      })
    }
  }
}
