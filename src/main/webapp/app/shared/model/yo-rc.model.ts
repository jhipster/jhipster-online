import { Moment } from 'moment';
import { IGeneratorIdentity } from 'app/shared/model/generator-identity.model';

export interface IYoRC {
  id?: number;
  jhipsterVersion?: string;
  creationDate?: Moment;
  gitProvider?: string;
  nodeVersion?: string;
  os?: string;
  arch?: string;
  cpu?: string;
  cores?: string;
  memory?: string;
  userLanguage?: string;
  year?: number;
  month?: number;
  week?: number;
  day?: number;
  hour?: number;
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
  withAdminUi?: boolean;
  useSass?: boolean;
  clientPackageManager?: string;
  applicationType?: string;
  jhiPrefix?: string;
  enableTranslation?: boolean;
  nativeLanguage?: string;
  hasProtractor?: boolean;
  hasGatling?: boolean;
  hasCucumber?: boolean;
  owner?: IGeneratorIdentity;
}

export class YoRC implements IYoRC {
  constructor(
    public id?: number,
    public jhipsterVersion?: string,
    public creationDate?: Moment,
    public gitProvider?: string,
    public nodeVersion?: string,
    public os?: string,
    public arch?: string,
    public cpu?: string,
    public cores?: string,
    public memory?: string,
    public userLanguage?: string,
    public year?: number,
    public month?: number,
    public week?: number,
    public day?: number,
    public hour?: number,
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
    public withAdminUi?: boolean,
    public useSass?: boolean,
    public clientPackageManager?: string,
    public applicationType?: string,
    public jhiPrefix?: string,
    public enableTranslation?: boolean,
    public nativeLanguage?: string,
    public hasProtractor?: boolean,
    public hasGatling?: boolean,
    public hasCucumber?: boolean,
    public owner?: IGeneratorIdentity
  ) {
    this.enableHibernateCache = this.enableHibernateCache || false;
    this.websocket = this.websocket || false;
    this.searchEngine = this.searchEngine || false;
    this.messageBroker = this.messageBroker || false;
    this.serviceDiscoveryType = this.serviceDiscoveryType || false;
    this.enableSwaggerCodegen = this.enableSwaggerCodegen || false;
    this.withAdminUi = this.withAdminUi || false;
    this.useSass = this.useSass || false;
    this.enableTranslation = this.enableTranslation || false;
    this.hasProtractor = this.hasProtractor || false;
    this.hasGatling = this.hasGatling || false;
    this.hasCucumber = this.hasCucumber || false;
  }
}
