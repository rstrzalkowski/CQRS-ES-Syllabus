import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import {AlertService} from "../../../../services/alert.service";

@Component({
  selector: 'app-unassign-role',
  templateUrl: './unassign-role.component.html'
})
export class UnassignRoleComponent implements OnInit {
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
      this.user.roles.forEach(role => {
        role = role.replace("SYLLABUS_", "");
        this.roles.push(role)
      });
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

  unassign(role: string) {
    if (this.user) {
      this.userService.unassignRole(this.user.id, role).subscribe(() => {
        this.alertService.showAlert("success", "Role has been unassigned")
        this.success.emit();
      }, error => {
        this.alertService.showAlert("danger", "Error during unassignment. Refresh this page and try again")
        this.success.emit();
      })
    }
  }
}
