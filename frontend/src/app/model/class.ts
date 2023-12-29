import {User} from "./user";

export interface ClassPage {
  content: Class[],
  totalPages: number,
  last: boolean,
  number: number
}

export interface Class {
  id: string;
  createdAt: Date;
  updatedAt: Date;
  level: number;
  name: string;
  fullName: string;
  supervisingTeacher: User;
  archived: boolean;
  students: User[];
}
