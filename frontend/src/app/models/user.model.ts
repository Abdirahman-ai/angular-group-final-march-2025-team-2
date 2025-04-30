import { Profile } from "./profile.model";

export interface FullUser {
  id: number;
  profile: Profile;
  admin: boolean;
  active: boolean;
  status: string;
}
