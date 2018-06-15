import { Moment } from 'moment';
import { IOwnerIdentity } from './owner-identity.model';

export interface ISubGenEvent {
    id?: number;
    source?: string;
    type?: string;
    event?: string;
    date?: Moment;
    owner?: IOwnerIdentity;
}

export class SubGenEvent implements ISubGenEvent {
    constructor(
        public id?: number,
        public source?: string,
        public type?: string,
        public event?: string,
        public date?: Moment,
        public owner?: IOwnerIdentity
    ) {}
}
