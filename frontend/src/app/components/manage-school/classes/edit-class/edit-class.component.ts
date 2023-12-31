import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {AlertService} from "../../../../services/alert.service";
import {ClassService} from "../../../../services/class.service";
import {Observable} from "rxjs";
import {User} from "../../../../model/user";
import {UserService} from "../../../../services/user.service";
import {Class} from "../../../../model/class";

@Component({
  selector: 'app-edit-class',
  templateUrl: './edit-class.component.html'
})
export class EditClassComponent implements OnInit {

  //Data
  shortName: string = ""
  fullName: string = ""
  teacherId: string | undefined
  level: number | undefined
  levels: number[] = [1, 2, 3, 4, 5, 6, 7, 8];
  teachers$: Observable<User[]> = this.userService.getAllNotSupervisingActiveTeachers()
  //end data


  //Loading
  loading = false
  //end loading

  @Input() class: Class | undefined
  @Output() close: EventEmitter<any> = new EventEmitter()
  @Output() success: EventEmitter<any> = new EventEmitter()

  constructor(private alertService: AlertService,
              private classService: ClassService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.shortName = this.class?.name || ""
    this.fullName = this.class?.fullName || ""
    this.teacherId = this.class?.supervisingTeacher.id
    this.level = this.class?.level
  }

  @HostListener('document:keydown', ['$event']) onKeydownHandler(event: KeyboardEvent) {
    if (event.key === "Escape") {
      this.closeModal()
    }
  }

  submit() {
    if (this.shortName === '' || this.fullName === '' || this.level === undefined || this.teacherId === undefined) {
      this.alertService.showAlert('warning', 'Fill all the required fields.')
      return
    }

    this.loading = true
    this.classService.updateClass(this.class?.id, this.shortName, this.fullName, this.level, this.teacherId).subscribe((result) => {
      this.alertService.showAlert('success', 'Class has been successfully updated!')
      this.success.emit()
    }, error => {
      if (error.status === 409) {
        this.alertService.showAlert('danger', 'Class with same short name and level already exists.')
      } else {
        this.alertService.showAlert('danger', 'Something went wrong during updating a class. Make sure form is valid')
      }
      this.loading = false
    })
  }

  closeModal() {
    this.close.emit()
  }
}
