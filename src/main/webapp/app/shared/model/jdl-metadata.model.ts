import { IUser } from 'app/core/user/user.model';

export interface IJdlMetadata {
  id?: number;
  key?: number;
  name?: string;
  isPublic?: boolean;
  user?: IUser;
}

export class JdlMetadata implements IJdlMetadata {
  constructor(public id?: number, public key?: number, public name?: string, public isPublic?: boolean, public user?: IUser) {
    this.isPublic = this.isPublic || false;
  }
}
