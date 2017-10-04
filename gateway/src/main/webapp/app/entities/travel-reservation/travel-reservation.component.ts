import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { TravelReservation } from './travel-reservation.model';
import { TravelReservationService } from './travel-reservation.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-travel-reservation',
    templateUrl: './travel-reservation.component.html'
})
export class TravelReservationComponent implements OnInit, OnDestroy {
travelReservations: TravelReservation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private travelReservationService: TravelReservationService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.travelReservationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.travelReservations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTravelReservations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TravelReservation) {
        return item.id;
    }
    registerChangeInTravelReservations() {
        this.eventSubscriber = this.eventManager.subscribe('travelReservationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
