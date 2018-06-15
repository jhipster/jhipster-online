import { Moment } from 'moment';
import { IOwnerIdentity } from './owner-identity.model';

export interface IEntityStats {
    id?: number;
    fields?: number;
    relationships?: number;
    pagination?: string;
    dto?: boolean;
    service?: boolean;
    fluentMethods?: boolean;
    date?: Moment;
    owner?: IOwnerIdentity;
}

export class EntityStats implements IEntityStats {
    constructor(
        public id?: number,
        public fields?: number,
        public relationships?: number,
        public pagination?: string,
        public dto?: boolean,
        public service?: boolean,
        public fluentMethods?: boolean,
        public date?: Moment,
        public owner?: IOwnerIdentity
    ) {
        this.dto = false;
        this.service = false;
        this.fluentMethods = false;
    }
}
