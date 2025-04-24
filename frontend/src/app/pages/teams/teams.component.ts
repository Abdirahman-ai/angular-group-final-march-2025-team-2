import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Team } from 'src/app/models/team.model';
import { TeamService } from 'src/app/services/team.service';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {
  teams: Team[] = [];
  companyId!: number;

  constructor(private teamService: TeamService, private router: Router){}

  ngOnInit(): void {
    const storedUser = localStorage.getItem('user');
    const storedCompanyId = localStorage.getItem('companyId');

    if (storedUser && storedCompanyId) {
      this.companyId = Number(storedCompanyId);
      this.loadTeams();
    } else {
      console.error('Missing user or companyId in localStorage');
    }
  }

  loadTeams(): void {
    this.teamService.getTeams(this.companyId).subscribe({
      next: (teams) => {
        this.teams = teams;
  
        this.teams.forEach((team) => {
          this.teamService.getProjectsForTeam(team.id).subscribe({
            next: (projects) => {
              (team as any).projects = projects;
            },
            error: (err) => {
              console.error(`Failed to load projects for team ${team.id}:`, err);
              (team as any).projects = []; 
            }
          });
        });
      },
      error: (err) => {
        console.error('Failed to load teams:', err);
      }
    });
  }  

  createTeam(): void {
    const newTeam: Team = {
      name: `Team${this.teams.length + 1}`,
      description: '',
      teammates: [],
      projects: [], 
      id: 0 
    };

    this.teamService.createTeam(this.companyId, newTeam).subscribe({
      next: () => this.loadTeams(),
      error: (err) => console.error('Error creating team:', err)
    });
  }

  goToProjects(teamId: number): void {
    this.router.navigate(['/projects', teamId]);
  }

}
