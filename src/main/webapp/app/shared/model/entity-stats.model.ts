import { Moment } from 'moment';
import { IOwnerIdentity } from 'app/shared/model//owner-identity.model';

export interface IEntityStats {
    id?: number;
    fields?: number;
    relationships?: number;
    pagination?: string;
    dto?: string;
    service?: string;
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
        public dto?: string,
        public service?: string,
        public fluentMethods?: boolean,
        public date?: Moment,
        public owner?: IOwnerIdentity
    ) {
        this.fluentMethods = false;
    }
}
