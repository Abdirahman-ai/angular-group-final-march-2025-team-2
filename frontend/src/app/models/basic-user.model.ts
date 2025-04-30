export interface BasicUserDto {
    id: number;
    username: string;
    profile: {
      firstName: string;
      lastName: string;
    };
  }
  