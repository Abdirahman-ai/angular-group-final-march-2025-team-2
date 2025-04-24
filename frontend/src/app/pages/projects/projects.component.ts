import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Project } from 'src/app/models/project.model';
import { ProjectService } from 'src/app/services/project.service';
import { Location } from '@angular/common';
import { Team } from 'src/app/models/team.model'
import { TeamService } from 'src/app/services/team.service';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent {
  teamId!: number;
  projects: Project[] = [];
  showCreateForm = false;
  teamName: string = '';
  newProject = { name: '', description: '', active: true };

  showEditModal = false;
  editedProject: Project | null = null;

  constructor(
    private route: ActivatedRoute, 
    private projectService: ProjectService,
    private teamService: TeamService, 
    private location: Location
  ){}

  ngOnInit(): void {
    this.teamId = Number(this.route.snapshot.paramMap.get('teamId'));
    this.loadProjects();
    this.loadTeamName();
    console.log(this.teamName);
  }

  loadProjects(): void {
    this.projectService.getProjectsForTeam(this.teamId).subscribe({
      next: (data) => this.projects = data,
      error: (err) => console.error('Error loading projects:', err)
    });
  }

  loadTeamName(): void {
    this.teamService.getTeamById(this.teamId).subscribe({
      next: (team: Team) => this.teamName = team.name,
      error: (err) => console.error('Error fetching team:', err)
    });
  }

  createProject(): void {
    this.openCreateForm();
  }

  editProject(project: Project): void {
    this.editedProject = { ...project };
    this.showEditModal = true;
  }

  saveEditedProject(): void {
    if (!this.editedProject?.id) return;

    this.projectService.updateProject(this.editedProject.id, this.editedProject).subscribe({
      next: () => {
        this.showEditModal = false;
        this.loadProjects();
      },
      error: (err) => console.error('Error updating project:', err)
    });
  }

  goBack(): void {
    this.location.back();
  }

  openCreateForm(): void {
    this.showCreateForm = true;
  }

  closeCreateForm(): void {
    this.showCreateForm = false;
    this.newProject = { name: '', description: '', active: true };
  }

  submitNewProject(): void {
    this.projectService.createProject(this.teamId, {
      name: this.newProject.name,
      description: this.newProject.description,
      active: true
    }).subscribe({
      next: (project) => {
        this.projects.push(project);
        this.closeCreateForm();
      },
      error: (err) => console.error('Failed to create project:', err)
    });
  }

  deleteProject(projectId: number): void {
    if (confirm('Are you sure you want to delete this project?')) {
      this.projectService.deleteProject(projectId).subscribe({
        next: () => {
          this.showEditModal = false;
          this.loadProjects();
        },
        error: (err) => console.error('Error deleting project:', err)
      });
    }
  }
}
