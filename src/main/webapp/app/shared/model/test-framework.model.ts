import { IYoRC } from './yo-rc.model';

export interface ITestFramework {
    id?: number;
    testFramework?: string;
    yorcs?: IYoRC[];
}

export class TestFramework implements ITestFramework {
    constructor(public id?: number, public testFramework?: string, public yorcs?: IYoRC[]) {}
}
