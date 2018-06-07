import { IUser } from 'app/core/user/user.model';

export interface IOwnerIdentity {
    id?: number;
    owner?: IUser;
}

export class OwnerIdentity implements IOwnerIdentity {
    constructor(public id?: number, public owner?: IUser) {}
}
