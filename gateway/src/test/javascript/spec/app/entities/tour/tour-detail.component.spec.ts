/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TourDetailComponent } from '../../../../../../main/webapp/app/entities/tour/tour-detail.component';
import { TourService } from '../../../../../../main/webapp/app/entities/tour/tour.service';
import { Tour } from '../../../../../../main/webapp/app/entities/tour/tour.model';

describe('Component Tests', () => {

    describe('Tour Management Detail Component', () => {
        let comp: TourDetailComponent;
        let fixture: ComponentFixture<TourDetailComponent>;
        let service: TourService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [TourDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TourService,
                    JhiEventManager
                ]
            }).overrideTemplate(TourDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TourDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TourService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Tour(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tour).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
