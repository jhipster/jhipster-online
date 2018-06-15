import { IOwnerIdentity } from './owner-identity.model';

export interface IGeneratorIdentity {
    id?: number;
    host?: string;
    guid?: string;
    owner?: IOwnerIdentity;
}

export class GeneratorIdentity implements IGeneratorIdentity {
    constructor(public id?: number, public host?: string, public guid?: string, public owner?: IOwnerIdentity) {}
}
