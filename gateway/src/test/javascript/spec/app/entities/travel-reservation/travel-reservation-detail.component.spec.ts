/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TravelReservationDetailComponent } from '../../../../../../main/webapp/app/entities/travel-reservation/travel-reservation-detail.component';
import { TravelReservationService } from '../../../../../../main/webapp/app/entities/travel-reservation/travel-reservation.service';
import { TravelReservation } from '../../../../../../main/webapp/app/entities/travel-reservation/travel-reservation.model';

describe('Component Tests', () => {

    describe('TravelReservation Management Detail Component', () => {
        let comp: TravelReservationDetailComponent;
        let fixture: ComponentFixture<TravelReservationDetailComponent>;
        let service: TravelReservationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TravelReservationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TravelReservationService,
                    JhiEventManager
                ]
            }).overrideTemplate(TravelReservationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TravelReservationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TravelReservationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TravelReservation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.travelReservation).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
