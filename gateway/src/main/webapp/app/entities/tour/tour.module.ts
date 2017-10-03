import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    TourService,
    TourPopupService,
    TourComponent,
    TourDetailComponent,
    TourDialogComponent,
    TourPopupComponent,
    TourDeletePopupComponent,
    TourDeleteDialogComponent,
    tourRoute,
    tourPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tourRoute,
    ...tourPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TourComponent,
        TourDetailComponent,
        TourDialogComponent,
        TourDeleteDialogComponent,
        TourPopupComponent,
        TourDeletePopupComponent,
    ],
    entryComponents: [
        TourComponent,
        TourDialogComponent,
        TourPopupComponent,
        TourDeleteDialogComponent,
        TourDeletePopupComponent,
    ],
    providers: [
        TourService,
        TourPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayTourModule {}
