export interface Project {
    id: number;
    name: string;
    description: string;
    active: boolean;
    team: {
      id: number;
      name: string;
      description: string;
    };
  }
  