import { IUser } from 'app/core/user/user.model';
import { ILanguage } from './language.model';

export interface IYoRC {
    id?: number;
    jhipsterVersion?: string;
    serverPort?: string;
    authenticationType?: string;
    cacheProvider?: string;
    enableHibernateCache?: string;
    websocket?: string;
    databaseType?: string;
    devDatabaseType?: string;
    prodDatabaseType?: string;
    searchEngine?: string;
    messageBroker?: string;
    serviceDiscoveryType?: string;
    buildTool?: string;
    enableSwaggerCodegen?: string;
    clientFramework?: string;
    useSass?: string;
    clientPackageManager?: string;
    applicationType?: string;
    testFrameworks?: string;
    jhiPrefix?: string;
    enableTranslation?: string;
    nativeLanguage?: string;
    gitProvider?: string;
    owner?: IUser;
    languages?: ILanguage[];
}

export class YoRC implements IYoRC {
    constructor(
        public id?: number,
        public jhipsterVersion?: string,
        public serverPort?: string,
        public authenticationType?: string,
        public cacheProvider?: string,
        public enableHibernateCache?: string,
        public websocket?: string,
        public databaseType?: string,
        public devDatabaseType?: string,
        public prodDatabaseType?: string,
        public searchEngine?: string,
        public messageBroker?: string,
        public serviceDiscoveryType?: string,
        public buildTool?: string,
        public enableSwaggerCodegen?: string,
        public clientFramework?: string,
        public useSass?: string,
        public clientPackageManager?: string,
        public applicationType?: string,
        public testFrameworks?: string,
        public jhiPrefix?: string,
        public enableTranslation?: string,
        public nativeLanguage?: string,
        public gitProvider?: string,
        public owner?: IUser,
        public languages?: ILanguage[]
    ) {}
}
