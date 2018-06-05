import { IYoRC } from './yo-rc.model';

export interface ILanguage {
    id?: number;
    langKey?: string;
    yorcs?: IYoRC[];
}

export class Language implements ILanguage {
    constructor(public id?: number, public langKey?: string, public yorcs?: IYoRC[]) {}
}
