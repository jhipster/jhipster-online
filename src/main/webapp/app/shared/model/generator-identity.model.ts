import { IOwnerIdentity } from './owner-identity.model';

export interface IGeneratorIdentity {
    id?: number;
    host?: string;
    userAgent?: string;
    guid?: string;
    generator?: IOwnerIdentity;
}

export class GeneratorIdentity implements IGeneratorIdentity {
    constructor(
        public id?: number,
        public host?: string,
        public userAgent?: string,
        public guid?: string,
        public generator?: IOwnerIdentity
    ) {}
}
