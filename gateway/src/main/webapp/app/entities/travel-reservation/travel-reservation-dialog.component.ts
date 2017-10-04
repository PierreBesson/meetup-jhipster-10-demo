import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TravelReservation } from './travel-reservation.model';
import { TravelReservationPopupService } from './travel-reservation-popup.service';
import { TravelReservationService } from './travel-reservation.service';

@Component({
    selector: 'jhi-travel-reservation-dialog',
    templateUrl: './travel-reservation-dialog.component.html'
})
export class TravelReservationDialogComponent implements OnInit {

    travelReservation: TravelReservation;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private travelReservationService: TravelReservationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.travelReservation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.travelReservationService.update(this.travelReservation));
        } else {
            this.subscribeToSaveResponse(
                this.travelReservationService.create(this.travelReservation));
        }
    }

    private subscribeToSaveResponse(result: Observable<TravelReservation>) {
        result.subscribe((res: TravelReservation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TravelReservation) {
        this.eventManager.broadcast({ name: 'travelReservationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-travel-reservation-popup',
    template: ''
})
export class TravelReservationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private travelReservationPopupService: TravelReservationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.travelReservationPopupService
                    .open(TravelReservationDialogComponent as Component, params['id']);
            } else {
                this.travelReservationPopupService
                    .open(TravelReservationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
