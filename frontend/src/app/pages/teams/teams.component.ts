import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Team } from 'src/app/models/team.model';
import { TeamService } from 'src/app/services/team.service';
import { UserService } from '../../services/user.service';
import { BasicUserDto } from 'src/app/models/basic-user.model';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {
  teams: Team[] = [];
  companyId!: number;
  showCreateForm = false;
  selectedUser: BasicUserDto | null = null;

  newTeam = {
    name: '',
    description: '',
    teammates: [] as BasicUserDto[]
  };

  allUsers: BasicUserDto[] = [];

  constructor(
    private teamService: TeamService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const storedCompanyId = localStorage.getItem('companyId');
    if (storedCompanyId) {
      this.companyId = Number(storedCompanyId);
      this.loadTeams();
      this.loadUsers();
    }
  }

  loadTeams(): void {
    this.teamService.getTeams(this.companyId).subscribe({
      next: (teams) => {
        this.teams = teams;
        this.teams.forEach((team) => {
          this.teamService.getProjectsForTeam(team.id).subscribe({
            next: (projects) => (team as any).projects = projects,
            error: () => (team as any).projects = []
          });
        });
      }
    });
  }

  loadUsers(): void {
    this.userService.getUsers(this.companyId).subscribe({
      next: (users: BasicUserDto[]) => this.allUsers = users
    });
  }

  goToProjects(teamId: number): void {
    this.router.navigate(['/projects', teamId]);
  }

  openCreateForm(): void {
    this.showCreateForm = true;
    this.newTeam = { name: '', description: '', teammates: [] };
  }

  closeCreateForm(): void {
    this.showCreateForm = false;
  }

  removeTeammate(userId: number): void {
    this.newTeam.teammates = this.newTeam.teammates.filter(u => u.id !== userId);
  }

  submitNewTeam(): void {
    const team: Team = {
      name: this.newTeam.name,
      description: this.newTeam.description,
      teammates: this.newTeam.teammates,
      projects: [],
      id: 0
    };

    this.teamService.createTeam(this.companyId, team).subscribe({
      next: () => {
        this.closeCreateForm();
        this.loadTeams();
      }
    });
  }

  addTeammate(user: BasicUserDto | null): void {
    if (user && !this.newTeam.teammates.find(u => u.id === user.id)) {
      this.newTeam.teammates.push(user);
      this.selectedUser = null; 
    }
  }
}
