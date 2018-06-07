import { ILanguage } from './language.model';
import { IOwnerIdentity } from './owner-identity.model';

export interface IYoRC {
    id?: number;
    jhipsterVersion?: string;
    serverPort?: string;
    authenticationType?: string;
    cacheProvider?: string;
    enableHibernateCache?: boolean;
    websocket?: boolean;
    databaseType?: string;
    devDatabaseType?: string;
    prodDatabaseType?: string;
    searchEngine?: boolean;
    messageBroker?: boolean;
    serviceDiscoveryType?: boolean;
    buildTool?: string;
    enableSwaggerCodegen?: boolean;
    clientFramework?: string;
    useSass?: boolean;
    clientPackageManager?: string;
    applicationType?: string;
    jhiPrefix?: string;
    enableTranslation?: boolean;
    nativeLanguage?: string;
    gitProvider?: string;
    hasProtractor?: boolean;
    hasGatling?: boolean;
    hasCucumber?: boolean;
    selectedLanguages?: ILanguage[];
    owner?: IOwnerIdentity;
}

export class YoRC implements IYoRC {
    constructor(
        public id?: number,
        public jhipsterVersion?: string,
        public serverPort?: string,
        public authenticationType?: string,
        public cacheProvider?: string,
        public enableHibernateCache?: boolean,
        public websocket?: boolean,
        public databaseType?: string,
        public devDatabaseType?: string,
        public prodDatabaseType?: string,
        public searchEngine?: boolean,
        public messageBroker?: boolean,
        public serviceDiscoveryType?: boolean,
        public buildTool?: string,
        public enableSwaggerCodegen?: boolean,
        public clientFramework?: string,
        public useSass?: boolean,
        public clientPackageManager?: string,
        public applicationType?: string,
        public jhiPrefix?: string,
        public enableTranslation?: boolean,
        public nativeLanguage?: string,
        public gitProvider?: string,
        public hasProtractor?: boolean,
        public hasGatling?: boolean,
        public hasCucumber?: boolean,
        public selectedLanguages?: ILanguage[],
        public owner?: IOwnerIdentity
    ) {
        this.enableHibernateCache = false;
        this.websocket = false;
        this.searchEngine = false;
        this.messageBroker = false;
        this.serviceDiscoveryType = false;
        this.enableSwaggerCodegen = false;
        this.useSass = false;
        this.enableTranslation = false;
        this.hasProtractor = false;
        this.hasGatling = false;
        this.hasCucumber = false;
    }
}
