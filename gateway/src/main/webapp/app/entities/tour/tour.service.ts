import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { JhiDateUtils } from 'ng-jhipster';

import { Tour } from './tour.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TourService {

    private resourceUrl = 'catalog/api/tours';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(tour: Tour): Observable<Tour> {
        const copy = this.convert(tour);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(tour: Tour): Observable<Tour> {
        const copy = this.convert(tour);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Tour> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Tour.
     */
    private convertItemFromServer(json: any): Tour {
        const entity: Tour = Object.assign(new Tour(), json);
        entity.timeOfDeparture = this.dateUtils
            .convertDateTimeFromServer(json.timeOfDeparture);
        entity.timeOfReturn = this.dateUtils
            .convertDateTimeFromServer(json.timeOfReturn);
        return entity;
    }

    /**
     * Convert a Tour to a JSON which can be sent to the server.
     */
    private convert(tour: Tour): Tour {
        const copy: Tour = Object.assign({}, tour);

        copy.timeOfDeparture = this.dateUtils.toDate(tour.timeOfDeparture);

        copy.timeOfReturn = this.dateUtils.toDate(tour.timeOfReturn);
        return copy;
    }
}
