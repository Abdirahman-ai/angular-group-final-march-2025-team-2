import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Project } from 'src/app/models/project.model';
import { ProjectService } from 'src/app/services/project.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent {
  teamId!: number;
  projects: Project[] = [];
  showCreateForm = false;
  newProject = { name: '', description: '', active: true };

  showEditModal = false;
  editedProject: Project | null = null;

  constructor(
    private route: ActivatedRoute, 
    private projectService: ProjectService, 
    private location: Location
  ){}

  ngOnInit(): void {
    this.teamId = Number(this.route.snapshot.paramMap.get('teamId'));
    this.loadProjects();
  }

  loadProjects(): void {
    this.projectService.getProjectsForTeam(this.teamId).subscribe({
      next: (data) => this.projects = data,
      error: (err) => console.error('Error loading projects:', err)
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
}
