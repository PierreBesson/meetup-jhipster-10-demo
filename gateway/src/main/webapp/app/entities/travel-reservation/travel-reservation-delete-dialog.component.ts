import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TravelReservation } from './travel-reservation.model';
import { TravelReservationPopupService } from './travel-reservation-popup.service';
import { TravelReservationService } from './travel-reservation.service';

@Component({
    selector: 'jhi-travel-reservation-delete-dialog',
    templateUrl: './travel-reservation-delete-dialog.component.html'
})
export class TravelReservationDeleteDialogComponent {

    travelReservation: TravelReservation;

    constructor(
        private travelReservationService: TravelReservationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.travelReservationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'travelReservationListModification',
                content: 'Deleted an travelReservation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-travel-reservation-delete-popup',
    template: ''
})
export class TravelReservationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private travelReservationPopupService: TravelReservationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.travelReservationPopupService
                .open(TravelReservationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
