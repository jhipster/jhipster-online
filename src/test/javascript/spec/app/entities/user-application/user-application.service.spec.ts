import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserApplicationService } from 'app/entities/user-application/user-application.service';
import { IUserApplication, UserApplication } from 'app/shared/model/user-application.model';

describe('Service Tests', () => {
  describe('UserApplication Service', () => {
    let injector: TestBed;
    let service: UserApplicationService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserApplication;
    let expectedResult: IUserApplication | IUserApplication[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserApplicationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserApplication(0, 'AAAAAAA', false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserApplication', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserApplication()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserApplication', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            shared: true,
            sharedLink: 'BBBBBB',
            userId: 'BBBBBB',
            configuration: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserApplication', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            shared: true,
            sharedLink: 'BBBBBB',
            userId: 'BBBBBB',
            configuration: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserApplication', () => {
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
