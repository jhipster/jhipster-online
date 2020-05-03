export interface IUserApplication {
    id?: number;
    name?: string;
    shared?: boolean;
    sharedLink?: string;
    userId?: string;
    configuration?: any;
}

export class UserApplication implements IUserApplication {
    constructor(
        public id?: number,
        public name?: string,
        public shared?: boolean,
        public sharedLink?: string,
        public userId?: string,
        public configuration?: any
    ) {
        this.shared = false;
    }
}
