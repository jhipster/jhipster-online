import { Moment } from 'moment';
import { IGeneratorIdentity } from 'app/shared/model//generator-identity.model';

export interface IEntityStats {
    id?: number;
    year?: number;
    month?: number;
    week?: number;
    day?: number;
    hour?: number;
    fields?: number;
    relationships?: number;
    pagination?: string;
    dto?: string;
    service?: string;
    fluentMethods?: boolean;
    date?: Moment;
    owner?: IGeneratorIdentity;
}

export class EntityStats implements IEntityStats {
    constructor(
        public id?: number,
        public year?: number,
        public month?: number,
        public week?: number,
        public day?: number,
        public hour?: number,
        public fields?: number,
        public relationships?: number,
        public pagination?: string,
        public dto?: string,
        public service?: string,
        public fluentMethods?: boolean,
        public date?: Moment,
        public owner?: IGeneratorIdentity
    ) {
        this.fluentMethods = false;
    }
}
