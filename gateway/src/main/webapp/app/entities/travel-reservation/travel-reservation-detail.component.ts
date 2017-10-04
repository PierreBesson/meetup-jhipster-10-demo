import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TravelReservation } from './travel-reservation.model';
import { TravelReservationService } from './travel-reservation.service';

@Component({
    selector: 'jhi-travel-reservation-detail',
    templateUrl: './travel-reservation-detail.component.html'
})
export class TravelReservationDetailComponent implements OnInit, OnDestroy {

    travelReservation: TravelReservation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private travelReservationService: TravelReservationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTravelReservations();
    }

    load(id) {
        this.travelReservationService.find(id).subscribe((travelReservation) => {
            this.travelReservation = travelReservation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTravelReservations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'travelReservationListModification',
            (response) => this.load(this.travelReservation.id)
        );
    }
}
