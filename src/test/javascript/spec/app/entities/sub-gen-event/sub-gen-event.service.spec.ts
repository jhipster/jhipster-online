import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SubGenEventService } from 'app/entities/sub-gen-event/sub-gen-event.service';
import { ISubGenEvent, SubGenEvent } from 'app/shared/model/sub-gen-event.model';

describe('Service Tests', () => {
  describe('SubGenEvent Service', () => {
    let injector: TestBed;
    let service: SubGenEventService;
    let httpMock: HttpTestingController;
    let elemDefault: ISubGenEvent;
    let expectedResult: ISubGenEvent | ISubGenEvent[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SubGenEventService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SubGenEvent(0, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
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

      it('should create a SubGenEvent', () => {
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

        service.create(new SubGenEvent()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SubGenEvent', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 1,
            week: 1,
            day: 1,
            hour: 1,
            source: 'BBBBBB',
            type: 'BBBBBB',
            event: 'BBBBBB',
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

      it('should return a list of SubGenEvent', () => {
        const returnedFromService = Object.assign(
          {
            year: 1,
            month: 1,
            week: 1,
            day: 1,
            hour: 1,
            source: 'BBBBBB',
            type: 'BBBBBB',
            event: 'BBBBBB',
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

      it('should delete a SubGenEvent', () => {
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
