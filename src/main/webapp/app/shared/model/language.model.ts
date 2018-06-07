import { IYoRC } from './yo-rc.model';

export interface ILanguage {
    id?: number;
    isoCode?: string;
    yoRC?: IYoRC;
}

export class Language implements ILanguage {
    constructor(public id?: number, public isoCode?: string, public yoRC?: IYoRC) {}
}
