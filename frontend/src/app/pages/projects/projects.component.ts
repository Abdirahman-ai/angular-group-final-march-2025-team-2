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
  projects: any[] = [];

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
    // Logic for adding new project, e.g., opening a modal
    console.log('Creating new project...');
  }

  editProject(project: Project): void {
    // Logic for editing project, e.g., navigating to edit view or opening modal
    console.log('Editing project:', project);
  }

  goBack(): void {
    this.location.back();
  }


}
