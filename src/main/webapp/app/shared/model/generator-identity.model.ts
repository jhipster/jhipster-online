import { IUser } from 'app/core/user/user.model';

export interface IGeneratorIdentity {
    id?: number;
    host?: string;
    guid?: string;
    owner?: IUser;
}

export class GeneratorIdentity implements IGeneratorIdentity {
    constructor(public id?: number, public host?: string, public guid?: string, public owner?: IUser) {}
}
