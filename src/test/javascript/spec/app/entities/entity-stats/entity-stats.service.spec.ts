import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EntityStatsService } from 'app/entities/entity-stats/entity-stats.service';
import { IEntityStats, EntityStats } from 'app/shared/model/entity-stats.model';

describe('Service Tests', () => {
  describe('EntityStats Service', () => {
    let injector: TestBed;
    let service: EntityStatsService;
    let httpMock: HttpTestingController;
    let elemDefault: IEntityStats;
    let expectedResult: IEntityStats | IEntityStats[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EntityStatsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EntityStats(0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EntityStats', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );

        service.create(new EntityStats()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EntityStats', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 1,
            week: 1,
            day: 1,
            hour: 1,
            fields: 1,
            relationships: 1,
            pagination: 'BBBBBB',
            dto: 'BBBBBB',
            service: 'BBBBBB',
            fluentMethods: true,
            date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EntityStats', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 1,
            week: 1,
            day: 1,
            hour: 1,
            fields: 1,
            relationships: 1,
            pagination: 'BBBBBB',
            dto: 'BBBBBB',
            service: 'BBBBBB',
            fluentMethods: true,
            date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EntityStats', () => {
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
