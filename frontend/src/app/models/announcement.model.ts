export interface Announcement {
    id: number;
    title: string;
    message: string;
    date: string; // ISO format
    author: {
      profile: {
        firstName: string;
        lastName: string;
      };
    };
  }
  