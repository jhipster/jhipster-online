import { Moment } from 'moment';

export interface ICrashReport {
    id?: number;
    date?: Moment;
    source?: string;
    env?: string;
    stack?: string;
    yorc?: string;
    jdl?: string;
}

export class CrashReport implements ICrashReport {
    constructor(
        public id?: number,
        public date?: Moment,
        public source?: string,
        public env?: string,
        public stack?: string,
        public yorc?: string,
        public jdl?: string
    ) {}
}
