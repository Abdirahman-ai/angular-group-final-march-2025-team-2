import { BasicUserDto } from './basic-user.model'; 

export interface Team {
  id: number;
  name: string;
  description: string;
  teammates: BasicUserDto[];
  projects: any[]; 
}
