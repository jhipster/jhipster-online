import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { YoRCService } from 'app/entities/yo-rc/yo-rc.service';
import { IYoRC, YoRC } from 'app/shared/model/yo-rc.model';

describe('Service Tests', () => {
  describe('YoRC Service', () => {
    let injector: TestBed;
    let service: YoRCService;
    let httpMock: HttpTestingController;
    let elemDefault: IYoRC;
    let expectedResult: IYoRC | IYoRC[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(YoRCService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new YoRC(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        false,
        'AAAAAAA',
        false,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        false,
        false,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a YoRC', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate
          },
          returnedFromService
        );

        service.create(new YoRC()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a YoRC', () => {
        const returnedFromService = Object.assign(
          {
            jhipsterVersion: 'BBBBBB',
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            gitProvider: 'BBBBBB',
            nodeVersion: 'BBBBBB',
            os: 'BBBBBB',
            arch: 'BBBBBB',
            cpu: 'BBBBBB',
            cores: 'BBBBBB',
            memory: 'BBBBBB',
            userLanguage: 'BBBBBB',
            year: 1,
            month: 1,
            week: 1,
            day: 1,
            hour: 1,
            serverPort: 'BBBBBB',
            authenticationType: 'BBBBBB',
            cacheProvider: 'BBBBBB',
            enableHibernateCache: true,
            websocket: true,
            databaseType: 'BBBBBB',
            devDatabaseType: 'BBBBBB',
            prodDatabaseType: 'BBBBBB',
            searchEngine: true,
            messageBroker: true,
            serviceDiscoveryType: true,
            buildTool: 'BBBBBB',
            enableSwaggerCodegen: true,
            clientFramework: 'BBBBBB',
            useSass: true,
            clientPackageManager: 'BBBBBB',
            applicationType: 'BBBBBB',
            jhiPrefix: 'BBBBBB',
            enableTranslation: true,
            nativeLanguage: 'BBBBBB',
            hasProtractor: true,
            hasGatling: true,
            hasCucumber: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of YoRC', () => {
        const returnedFromService = Object.assign(
          {
            jhipsterVersion: 'BBBBBB',
            creationDate: currentDate.format(DATE_TIME_FORMAT),
            gitProvider: 'BBBBBB',
            nodeVersion: 'BBBBBB',
            os: 'BBBBBB',
            arch: 'BBBBBB',
            cpu: 'BBBBBB',
            cores: 'BBBBBB',
            memory: 'BBBBBB',
            userLanguage: 'BBBBBB',
            year: 1,
            month: 1,
            week: 1,
            day: 1,
            hour: 1,
            serverPort: 'BBBBBB',
            authenticationType: 'BBBBBB',
            cacheProvider: 'BBBBBB',
            enableHibernateCache: true,
            websocket: true,
            databaseType: 'BBBBBB',
            devDatabaseType: 'BBBBBB',
            prodDatabaseType: 'BBBBBB',
            searchEngine: true,
            messageBroker: true,
            serviceDiscoveryType: true,
            buildTool: 'BBBBBB',
            enableSwaggerCodegen: true,
            clientFramework: 'BBBBBB',
            useSass: true,
            clientPackageManager: 'BBBBBB',
            applicationType: 'BBBBBB',
            jhiPrefix: 'BBBBBB',
            enableTranslation: true,
            nativeLanguage: 'BBBBBB',
            hasProtractor: true,
            hasGatling: true,
            hasCucumber: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creationDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a YoRC', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
