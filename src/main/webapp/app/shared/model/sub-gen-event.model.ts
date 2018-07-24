import { Moment } from 'moment';
import { IOwnerIdentity } from 'app/shared/model//owner-identity.model';

export interface ISubGenEvent {
    id?: number;
    year?: number;
    month?: number;
    week?: number;
    day?: number;
    hour?: number;
    source?: string;
    type?: string;
    event?: string;
    date?: Moment;
    owner?: IOwnerIdentity;
}

export class SubGenEvent implements ISubGenEvent {
    constructor(
        public id?: number,
        public year?: number,
        public month?: number,
        public week?: number,
        public day?: number,
        public hour?: number,
        public source?: string,
        public type?: string,
        public event?: string,
        public date?: Moment,
        public owner?: IOwnerIdentity
    ) {}
}
