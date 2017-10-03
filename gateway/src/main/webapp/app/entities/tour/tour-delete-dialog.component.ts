import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tour } from './tour.model';
import { TourPopupService } from './tour-popup.service';
import { TourService } from './tour.service';

@Component({
    selector: 'jhi-tour-delete-dialog',
    templateUrl: './tour-delete-dialog.component.html'
})
export class TourDeleteDialogComponent {

    tour: Tour;

    constructor(
        private tourService: TourService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tourService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tourListModification',
                content: 'Deleted an tour'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tour-delete-popup',
    template: ''
})
export class TourDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tourPopupService: TourPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tourPopupService
                .open(TourDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
