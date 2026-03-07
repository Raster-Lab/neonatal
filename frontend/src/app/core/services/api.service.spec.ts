import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ApiService } from './api.service';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(ApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should perform GET request', () => {
    const mockData = { id: 1, name: 'Test Patient' };

    service.get('/patients/1').subscribe((data) => {
      expect(data).toEqual(mockData);
    });

    const req = httpMock.expectOne('/api/v1/patients/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockData);
  });

  it('should perform POST request', () => {
    const mockBody = { name: 'New Patient' };
    const mockResponse = { id: 1, name: 'New Patient' };

    service.post('/patients', mockBody).subscribe((data) => {
      expect(data).toEqual(mockResponse);
    });

    const req = httpMock.expectOne('/api/v1/patients');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockBody);
    req.flush(mockResponse);
  });

  it('should perform PUT request', () => {
    const mockBody = { name: 'Updated Patient' };
    const mockResponse = { id: 1, name: 'Updated Patient' };

    service.put('/patients/1', mockBody).subscribe((data) => {
      expect(data).toEqual(mockResponse);
    });

    const req = httpMock.expectOne('/api/v1/patients/1');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockBody);
    req.flush(mockResponse);
  });

  it('should perform DELETE request', () => {
    const mockResponse = { success: true };

    service.delete('/patients/1').subscribe((data) => {
      expect(data).toEqual(mockResponse);
    });

    const req = httpMock.expectOne('/api/v1/patients/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(mockResponse);
  });
});
