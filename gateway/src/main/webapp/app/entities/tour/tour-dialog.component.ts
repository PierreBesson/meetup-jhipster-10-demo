import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tour } from './tour.model';
import { TourPopupService } from './tour-popup.service';
import { TourService } from './tour.service';

@Component({
    selector: 'jhi-tour-dialog',
    templateUrl: './tour-dialog.component.html'
})
export class TourDialogComponent implements OnInit {

    tour: Tour;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tourService: TourService,
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
        if (this.tour.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tourService.update(this.tour));
        } else {
            this.subscribeToSaveResponse(
                this.tourService.create(this.tour));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tour>) {
        result.subscribe((res: Tour) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Tour) {
        this.eventManager.broadcast({ name: 'tourListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-tour-popup',
    template: ''
})
export class TourPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tourPopupService: TourPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tourPopupService
                    .open(TourDialogComponent as Component, params['id']);
            } else {
                this.tourPopupService
                    .open(TourDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
